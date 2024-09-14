import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.containsString;

public class PersonalAccountTest {

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
    }

    @Test
    public void toPersonalAccountTransferTest() {
        HomePage homePage = new HomePage(driver);
        homePage.clickPersonalAccountButton();
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        MatcherAssert.assertThat(personalAccountPage.profileText(), containsString("В этом разделе вы можете изменить свои персональные данные"));

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
