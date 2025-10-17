
package pages;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CartPage {
    private final AndroidDriver driver;
    public CartPage(AndroidDriver driver) { this.driver = driver; }

    // Seletores corretos baseados na inspeção
    private final By cartContent = By.xpath("//*[@content-desc='test-Cart Content']");
    private final By btnCheckout = By.xpath("//*[@content-desc='test-CHECKOUT']");
    private final By cartItems = By.xpath("//*[@content-desc='test-Item']");
    private final By removeButtons = By.xpath("//*[@content-desc='test-REMOVE']");

    public int countItems() {
        try {
            List<WebElement> items = driver.findElements(cartItems);
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void checkout() { 
        try {
            driver.findElement(btnCheckout).click();
            System.out.println("Checkout iniciado com sucesso");
        } catch (Exception e) {
            System.out.println("Botão checkout não encontrado");
        }
    }
    
    public boolean containsProduct(String productName) {
        try {
            WebElement product = driver.findElement(By.xpath(
                "//*[@content-desc='test-Item title' and @text='" + productName + "']"
            ));
            return product.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean containsProducts(String product1, String product2) {
        return containsProduct(product1) && containsProduct(product2);
    }
    
    public void clearCart() {
        try {
            System.out.println("🧹 Limpando carrinho...");
            List<WebElement> removeButtons = driver.findElements(this.removeButtons);
            int initialCount = removeButtons.size();
            
            System.out.println("📋 Encontrados " + initialCount + " itens para remover");
            
            // Remover todos os itens
            while (!removeButtons.isEmpty()) {
                removeButtons.get(0).click();
                Thread.sleep(1000); // Aguardar a remoção
                removeButtons = driver.findElements(this.removeButtons);
            }
            
            System.out.println("✅ Carrinho limpo - " + initialCount + " itens removidos");
        } catch (Exception e) {
            System.out.println("⚠️ Erro ao limpar carrinho: " + e.getMessage());
        }
    }
}
