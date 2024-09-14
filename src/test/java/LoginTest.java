import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class LoginTest {

    private static WebDriver driver;
    private final UserSteps userSteps = new UserSteps();
    private final String name = "alex";
    private final String email = "super-morkovka@yandex.ru";
    private final String password = "ffgffgfgggf";

    @Before
    public void setup() {
        userSteps.createUser(email, password, name);
        driver = DriverFactory.getDriver();
    }

    @Test
    public void logInFromHomePageTest() {
        driver.get("https://stellarburgers.nomoreparties.site");
        HomePage homePage = new HomePage(driver);
        homePage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginInfo(email, password);
        loginPage.clickLoginButton();
        assertTrue(homePage.createOrderButtonIsEnabled());
    }

    @Test
    public void logInFromPersonalAccountButtonTest() {
        driver.get("https://stellarburgers.nomoreparties.site");
        HomePage homePage = new HomePage(driver);
        homePage.clickPersonalAccountButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginInfo(email, password);
        loginPage.clickLoginButton();
        assertTrue(homePage.createOrderButtonIsEnabled());
    }

    @Test
    public void logInFromRegistrationFormTest() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.clickLogInButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginInfo(email, password);
        loginPage.clickLoginButton();
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.createOrderButtonIsEnabled());
    }

    @Test
    public void logInFromPasswordRecoveryFormTest() {
        driver.get("https://stellarburgers.nomoreparties.site/forgot-password");
        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        passwordRecoveryPage.clickLogInButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginInfo(email, password);
        loginPage.clickLoginButton();
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.createOrderButtonIsEnabled());
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
