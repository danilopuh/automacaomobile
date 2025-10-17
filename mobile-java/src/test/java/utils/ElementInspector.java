package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebElement;
import java.net.URI;
import java.util.List;

public class ElementInspector {
    public static void main(String[] args) throws Exception {
        // Configure HTTP client for Java 21 compatibility
        System.setProperty("webdriver.http.factory", "netty");
        
        UiAutomator2Options opts = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("Android Emulator")
                .setPlatformVersion("16")
                .setAppPackage("com.swaglabsmobileapp")
                .setAppActivity(".SplashActivity")
                .setNoReset(true)
                .autoGrantPermissions();
                
        AndroidDriver driver = new AndroidDriver(URI.create("http://localhost:4723/wd/hub").toURL(), opts);
        
        Thread.sleep(5000); // Aguarda carregar
        
        System.out.println("=== ESTRUTURA DA PÁGINA ===");
        
        // Busca por elementos com content-desc
        List<WebElement> elementsWithDesc = driver.findElements(
            org.openqa.selenium.By.xpath("//*[@content-desc]")
        );
        
        System.out.println("Elementos com content-desc:");
        for (WebElement element : elementsWithDesc) {
            String desc = element.getAttribute("content-desc");
            String className = element.getTagName();
            System.out.println("- " + className + " | content-desc: '" + desc + "'");
        }
        
        System.out.println("\n=== ELEMENTOS COM TEXTO ===");
        List<WebElement> elementsWithText = driver.findElements(
            org.openqa.selenium.By.xpath("//*[@text]")
        );
        
        for (WebElement element : elementsWithText) {
            String text = element.getAttribute("text");
            if (text != null && !text.trim().isEmpty()) {
                String className = element.getTagName();
                System.out.println("- " + className + " | text: '" + text + "'");
            }
        }
        
        System.out.println("\n=== ELEMENTOS DE INPUT ===");
        List<WebElement> inputs = driver.findElements(
            org.openqa.selenium.By.xpath("//android.widget.EditText")
        );
        
        for (WebElement input : inputs) {
            String hint = input.getAttribute("hint");
            String contentDesc = input.getAttribute("content-desc");
            String resourceId = input.getAttribute("resource-id");
            System.out.println("- EditText | hint: '" + hint + "' | content-desc: '" + contentDesc + "' | resource-id: '" + resourceId + "'");
        }
        
        System.out.println("\n=== ELEMENTOS DE BOTÃO ===");
        List<WebElement> buttons = driver.findElements(
            org.openqa.selenium.By.xpath("//android.widget.Button")
        );
        
        for (WebElement button : buttons) {
            String text = button.getAttribute("text");
            String contentDesc = button.getAttribute("content-desc");
            String resourceId = button.getAttribute("resource-id");
            System.out.println("- Button | text: '" + text + "' | content-desc: '" + contentDesc + "' | resource-id: '" + resourceId + "'");
        }
        
        driver.quit();
    }
}