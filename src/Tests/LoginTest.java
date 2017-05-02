package Tests;

import View.LoginView;
import org.junit.Test;

/**
 * Created by Didac on 01/05/2017.
 */
public class LoginTest {
    @Test
    public void loginUser() throws Exception {
        LoginView testLoginView = new LoginView();
        testLoginView.loginUser();
    }

}