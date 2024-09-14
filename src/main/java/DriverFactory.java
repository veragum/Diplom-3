import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;


public class DriverFactory {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = System.getProperty("browser", "firefox" );
            switch (browser) {
                case "chrome" :
                    return WebDriverManager.chromedriver().create();
                case "firefox":
                    return WebDriverManager.firefoxdriver().create();
            }
        }
        return driver;
    }
}
