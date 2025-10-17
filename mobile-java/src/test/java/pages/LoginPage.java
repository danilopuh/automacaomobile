
package pages;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginPage {
    private final AndroidDriver driver;
    public LoginPage(AndroidDriver driver) { this.driver = driver; }

    private final By inputUser = By.xpath("//*[@content-desc='test-Username']");
    private final By inputPass = By.xpath("//*[@content-desc='test-Password']");
    private final By btnLogin = By.xpath("//*[@content-desc='test-LOGIN']");

    public void login(String user, String pass) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            wait.until(ExpectedConditions.presenceOfElementLocated(inputUser));
            driver.findElement(inputUser).clear();
            driver.findElement(inputUser).sendKeys(user);
            
            driver.findElement(inputPass).clear();
            driver.findElement(inputPass).sendKeys(pass);
            
            driver.findElement(btnLogin).click();
            
            // Aguarda alguns segundos para o login processar
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("⚠️ Erro no login: " + e.getMessage());
        }
    }

    public boolean visible() {
        try {
            return driver.findElement(inputUser).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
