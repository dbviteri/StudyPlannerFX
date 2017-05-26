package Tests;

import Controller.UserController;
import Model.User;
import View.LoginView;
import org.junit.Test;

/**
 * Test
 */
public class LoginTest {
    @Test
    public void loginUser() throws Exception {
        LoginView testLoginView = new LoginView();
        testLoginView.directLogIn();
    }

    @Test
    public void addUserToDB(){
        UserController controller = new UserController();
        User user = new User("xdt","aef","4775","aefaf","aefeaf",false);
        controller.create(user);

        assert controller.userExists("aef");
        //System.out.println(userID);

    }

}