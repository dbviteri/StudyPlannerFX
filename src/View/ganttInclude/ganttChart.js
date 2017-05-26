(function (jQuery) {

    jQuery.fn.ganttChart = function () {

        var args = Array.prototype.slice.call(arguments);

        if (args.length == 1 && typeof(args[0]) == "object") {
            build.call(this, args[0]);
        }

        if (args.length == 2 && typeof(args[0]) == "string") {
            handleMethod.call(this, args[0], args[1]);
        }
    };

    function build(options) {

        var els = this;
        var defaults = {
            //showWeekends: true,
            cellWidth: 21,
            cellHeight: 31,
            slideWidth: 400,
            vHeaderWidth: 100
        };

        var opts = jQuery.extend(true, defaults, options);

        if (opts.data) {
            build();
        } else if (opts.dataUrl) {
            jQuery.getJSON(opts.dataUrl, function (data) {
                opts.data = data;
                build();
            });
        }

        function build() {

            var minDays = Math.floor((opts.slideWidth / opts.cellWidth) + 5);
            var startEnd = DateUtils.getBoundaryDatesFromData(opts.data, minDays);
            opts.start = startEnd[0];
            opts.end = startEnd[1];

            els.each(function () {

                var container = jQuery(this);
                var div = jQuery("<div>", {"class": "ganttchart"});
                new Chart(div, opts).render();
                container.append(div);

                var w = jQuery("div.ganttchart-vtheader", container).outerWidth() +
                    jQuery("div.ganttchart-slide-container", container).outerWidth();
                container.css("width", (w + 2) + "px");
            });
        }
    }

    function handleMethod(method, value) {

        if (method == "setSlideWidth") {
            var div = $("div.ganttchart", this);
            div.each(function () {
                var vtWidth = $("div.ganttchart-vtheader", div).outerWidth();
                $(div).width(vtWidth + value + 1);
                $("div.ganttchart-slide-container", this).width(value);
            });
        }
    }

    var Chart = function (div, opts) {

        function render() {
            addVtHeader(div, opts.data, opts.cellHeight);

            var slideDiv = jQuery("<div>", {
                "class": "ganttchart-slide-container",
                "css": {"width": opts.slideWidth + "px"}
            });

            dates = getDates(opts.start, opts.end);
            addHzHeader(slideDiv, dates, opts.cellWidth);
            //addGrid(slideDiv, opts.data, dates, opts.cellWidth, opts.showWeekends);
            addBlockContainers(slideDiv, opts.data);
            addBlocks(slideDiv, opts.data, opts.cellWidth, opts.start);
            div.append(slideDiv);
            applyLastClass(div.parent());
        }

        var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

        // Creates a 3 dimensional array [year][month][day] of every day
        // between the given start and end dates
        function getDates(start, end) {
            var dates = [];
            dates[start.getFullYear()] = [];
            dates[start.getFullYear()][start.getMonth()] = [start];
            var last = start;
            while (last.compareTo(end) == -1) {
                var next = last.clone().addDays(1);
                if (!dates[next.getFullYear()]) {
                    dates[next.getFullYear()] = [];
                }
                if (!dates[next.getFullYear()][next.getMonth()]) {
                    dates[next.getFullYear()][next.getMonth()] = [];
                }
                dates[next.getFullYear()][next.getMonth()].push(next);
                last = next;
            }
            return dates;
        }

        function addVtHeader(div, data, cellHeight) {
            var headerDiv = jQuery("<div>", {"class": "ganttchart-vtheader"});
            for (var i = 0; i < data.length; i++) {
                var itemDiv = jQuery("<div>", {"class": "ganttchart-vtheader-item"});
                itemDiv.append(jQuery("<div>", {
                    "class": "ganttchart-vtheader-item-name",
                    "css": {"height": (data[i].tasks.length * cellHeight) + "px"}
                }).append(data[i].assessmentTitle));
                var seriesDiv = jQuery("<div>", {"class": "ganttchart-vtheader-series"});
                seriesDiv.append(jQuery("<div>", {"class": "ganttchart-vtheader-series-name"}));
                for (var j = 0; j < data[i].tasks.length; j++) {
                    // seriesDiv.append(jQuery("<div>", { "class": "ganttchart-vtheader-series-name" })
                    //     .append(data[i].tasks[j].name));
                    seriesDiv.append(data[i].tasks[j].name)
                        .after("<div class='ganttchart-vtheader-series-name'>");
                    seriesDiv.append(jQuery("<div>", {"class": "ganttchart-vtheader-series-name"}));
                }
                itemDiv.append(seriesDiv);
                headerDiv.append(itemDiv);
            }
            div.append(headerDiv);
        }

        function addHzHeader(div, dates, cellWidth) {
            var headerDiv = jQuery("<div>", {"class": "ganttchart-hzheader"});
            var monthsDiv = jQuery("<div>", {"class": "ganttchart-hzheader-months"});
            var daysDiv = jQuery("<div>", {"class": "ganttchart-hzheader-days"});
            var totalW = 0;
            for (var y in dates) {
                for (var m in dates[y]) {
                    var w = dates[y][m].length * cellWidth;
                    totalW = totalW + w;
                    monthsDiv.append(jQuery("<div>", {
                        "class": "ganttchart-hzheader-month",
                        "css": {"width": (w - 1) + "px"}
                    }).append(monthNames[m] + "/" + y));
                    for (var d in dates[y][m]) {
                        daysDiv.append(jQuery("<div>", {"class": "ganttchart-hzheader-day"})
                            .append(dates[y][m][d].getDate()));
                    }
                }
            }
            monthsDiv.css("width", totalW + "px");
            daysDiv.css("width", totalW + "px");
            headerDiv.append(monthsDiv).append(daysDiv);
            div.append(headerDiv);
        }

        // function addGrid(div, data, dates, cellWidth, showWeekends) {
        //     var gridDiv = jQuery("<div>", { "class": "ganttchart-grid" });
        //     var rowDiv = jQuery("<div>", { "class": "ganttchart-grid-row" });
        //     for (var y in dates) {
        //         for (var m in dates[y]) {
        //             for (var d in dates[y][m]) {
        //                 var cellDiv = jQuery("<div>", { "class": "ganttchart-grid-row-cell" });
        //                 if (DateUtils.isWeekend(dates[y][m][d]) && showWeekends) {
        //                     cellDiv.addClass("ganttchart-weekend");
        //                 }
        //                 rowDiv.append(cellDiv);
        //             }
        //         }
        //     }
        //     var w = jQuery("div.ganttchart-grid-row-cell", rowDiv).length * cellWidth;
        //     rowDiv.css("width", w + "px");
        //     gridDiv.css("width", w + "px");
        //     for (var i = 0; i < data.length; i++) {
        //         for (var j = 0; j < data[i].tasks.length; j++) {
        //             gridDiv.append(rowDiv.clone());
        //         }
        //     }
        //     div.append(gridDiv);
        // }

        function addBlockContainers(div, data) {
            var blocksDiv = jQuery("<div>", {"class": "ganttchart-blocks"});
            for (var i = 0; i < data.length; i++) {
                for (var j = 0; j < data[i].tasks.length + data.length; j++) {
                    blocksDiv.append(jQuery("<div>", {"class": "ganttchart-block-container"}));
                }
            }
            div.append(blocksDiv);
        }

        function addBlocks(div, data, cellWidth, start) {
            var rows = jQuery("div.ganttchart-blocks div.ganttchart-block-container", div);
            var rowIdx = 0;
            for (var i = 0; i < data.length; i++) {

                var assessment = data[i];
                var size = DateUtils.daysBetween(assessment.start, assessment.deadline) + 1;
                var offset = DateUtils.daysBetween(start, assessment.start);
                var block = jQuery("<div>", {
                    "class": "ganttchart-block",
                    "title": assessment.name + ", " + size + " days",
                    "css": {
                        "width": ((size * cellWidth) - 9) + "px",
                        "margin-left": ((offset * cellWidth) + 3) + "px"
                    }
                });
                if (data[i].color) {
                    block.css("background-color", data[i].color);
                }
                block.append(jQuery("<div>", {"class": "ganttchart-block-text"}).text(size));

                jQuery(rows[rowIdx]).append(block);
                rowIdx = rowIdx + 1;
                for (var j = 0; j < data[i].tasks.length; j++) {

                    var tasks = data[i].tasks[j];
                    var size = DateUtils.daysBetween(tasks.start, tasks.deadline) + 1;
                    var offset = DateUtils.daysBetween(start, tasks.start);
                    var block = jQuery("<div>", {
                        "class": "ganttchart-block",
                        "title": tasks.name + ", " + size + " days",
                        "css": {
                            "width": ((size * cellWidth) - 9) + "px",
                            "margin-left": ((offset * cellWidth) + 3) + "px"
                        }
                    });
                    if (data[i].tasks[j].color) {
                        block.css("background-color", data[i].tasks[j].color);
                    }
                    block.append(jQuery("<div>", {"class": "ganttchart-block-text"}).text(size));
                    //rowIdx = rowIdx + 1;
                    jQuery(rows[rowIdx]).append(block);
                    rowIdx = rowIdx + 1;
                }
                rowIdx = rowIdx + 2;
            }
        }

        function applyLastClass(div) {
            jQuery("div.ganttchart-grid-row div.ganttchart-grid-row-cell:last-child", div).addClass("last");
            jQuery("div.ganttchart-hzheader-days div.ganttchart-hzheader-day:last-child", div).addClass("last");
            jQuery("div.ganttchart-hzheader-months div.ganttchart-hzheader-month:last-child", div).addClass("last");
        }

        return {
            render: render
        };
    };

    var ArrayUtils = {

        contains: function (arr, obj) {
            var has = false;
            for (var i = 0; i < arr.length; i++) {
                if (arr[i] == obj) {
                    has = true;
                }
            }
            return has;
        }
    };

    var DateUtils = {

        daysBetween: function (start, end) {
            if (!start || !end) {
                return 0;
            }
            start = Date.parse(start);
            end = Date.parse(end);
            if (start.getYear() == 1901 || end.getYear() == 8099) {
                return 0;
            }
            var count = 0, date = start.clone();
            while (date.compareTo(end) == -1) {
                count = count + 1;
                date.addDays(1);
            }
            return count;
        },

        // isWeekend: function (date) {
        //     return date.getDay() % 6 == 0;
        // },

        getBoundaryDatesFromData: function (data, minDays) {
            var minStart = new Date();
            maxEnd = new Date();
            for (var i = 0; i < data.length; i++) {
                var start = Date.parse(data[i].start);
                var end = Date.parse(data[i].deadline);
                if (i == 0) {
                    minStart = start;
                    maxEnd = end;
                }
                if (minStart.compareTo(start) == 1) {
                    minStart = start;
                }
                if (maxEnd.compareTo(end) == -1) {
                    maxEnd = end;
                }
                // for (var j = 0; j < data[i].series.length; j++) {
                //     var start = Date.parse(data[i].series[j].start);
                //     var end = Date.parse(data[i].series[j].end)
                //     if (i == 0 && j == 0) { minStart = start; maxEnd = end; }
                //     if (minStart.compareTo(start) == 1) { minStart = start; }
                //     if (maxEnd.compareTo(end) == -1) { maxEnd = end; }
                // }
            }

            // Insure that the width of the chart is at least the slide width to avoid empty
            // whitespace to the right of the grid
            if (DateUtils.daysBetween(minStart, maxEnd) < minDays) {
                maxEnd = minStart.clone().addDays(minDays);
            }

            return [minStart, maxEnd];
        }
    };

})(jQuery);