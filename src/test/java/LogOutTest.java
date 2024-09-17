import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.*;

import static org.junit.Assert.assertTrue;

public class LogOutTest {

    private static WebDriver driver;
    private final UserSteps userSteps = new UserSteps();
    private final String name = "alex";
    private final String email = "super-morkovka@yandex.ru";
    private final String password = "ffgffgfgggf";


    @Before
    public void setup() {
        driver = DriverFactory.getDriver();
        driver.get("https://stellarburgers.nomoreparties.site");
        userSteps.createUser(email, password, name);
        HomePage homePage = new HomePage(driver);
        homePage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginInfo(email, password);
        loginPage.clickLoginButton();
        homePage.clickPersonalAccountButton();
    }

    @Test
    public void logOutTest() {
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        personalAccountPage.clickLogOutButton();
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.logInButtonIsVisible());

    }

    @After
    public void tearDown() {
        String token = userSteps.loginUser(email, password)
                .extract().body().path("accessToken");
        if (token != null) {
            userSteps.deleteUser(token);
            driver.quit();
        }
    }

}


