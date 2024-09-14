import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;

public class RegistrationTest {

    private static WebDriver driver;
    private final UserSteps userSteps = new UserSteps();
    private final String name = "alex";
    private final String email = "super-morkovka@yandex.ru";
    private final String password = "ffgffgfgggf";
    private final String shortPassword = "ff";

    @Before
    public void setup() {
        driver = DriverFactory.getDriver();
        driver.get("https://stellarburgers.nomoreparties.site/register");
    }

    @Test
    public void successfulRegistrationTest() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.fillUserInfo(name, email, password);
        registrationPage.clickRegistrationButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginInfo(email, password);
        loginPage.clickLoginButton();
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.createOrderButtonIsEnabled());
    }

    @Test
    public void registrationWithShortPasswordTest() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.fillUserInfo(name, email, shortPassword);
        registrationPage.clickRegistrationButton();
        MatcherAssert.assertThat(registrationPage.errorText(), containsString("Некорректный пароль"));
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
