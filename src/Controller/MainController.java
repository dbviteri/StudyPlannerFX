//package Controller;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
///**
// * Created by Didac on 07/05/2017.
// */
////public interface MainController<T> {
////
////    DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
////
////    T find (String id); // For module code. We could use instead module id and not overload
////
////    T find (int id);
////
////    ArrayList<T> findAll (int id);
////
////    boolean update(T type);
////
////    boolean delete(T type);
////    //ArrayList<T> find(E type);
////}
//
//public abstract class MainController<T> {
//    DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
//
//    //abstract T find(Object... properties);
//
//    protected ResultSet queryDatabase (String sql, Object... properties){
//        try (
//                PreparedStatement statement = databaseHandler.prepareStatement(sql, properties);
//                ResultSet resultSet = statement.executeQuery()
//        ) {
//            if (resultSet.next()) return resultSet;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    //abstract T model(ResultSet resultSet) throws SQLException;
//}