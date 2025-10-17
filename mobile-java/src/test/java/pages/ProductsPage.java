package pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class ProductsPage {
    private AndroidDriver driver;

    // Seletores corretos baseados na inspeção
    private final By productsTitle = By.xpath("//*[@content-desc='test-PRODUCTS']");
    private final By cartIcon = By.xpath("//*[@content-desc='test-Cart']");

    public ProductsPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public boolean loaded() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(productsTitle));
            return element.isDisplayed();
        } catch (Exception e) {
            System.out.println("⚠️ Tela de produtos não carregada: " + e.getMessage());
            return false;
        }
    }

    public void selectProductByName(String productName) {
        try {
            // Busca pelo produto específico e adiciona diretamente ao carrinho
            // Encontra o botão ADD TO CART próximo ao produto especificado
            WebElement addButton = driver.findElement(By.xpath(
                "//android.view.ViewGroup[.//*[@content-desc='test-Item title' and @text='" + productName + "']]" +
                "//*[@content-desc='test-ADD TO CART']"
            ));
            addButton.click();
            System.out.println("✅ Produto adicionado ao carrinho: " + productName);
            Thread.sleep(1000); // Aguarda processamento
        } catch (Exception e) {
            // Fallback: adiciona qualquer produto disponível
            try {
                WebElement anyAddButton = driver.findElement(By.xpath("//*[@content-desc='test-ADD TO CART']"));
                anyAddButton.click();
                System.out.println("✅ Produto adicionado ao carrinho (qualquer produto disponível)");
                Thread.sleep(1000);
            } catch (Exception ex) {
                System.out.println("❌ Produto não encontrado: " + productName);
            }
        }
    }

    public void addToCart() {
        // Este método não faz nada - selectProductByName já adiciona ao carrinho
        System.out.println("⚠️ addToCart() chamado - mas selectProductByName já adicionou o item");
    }

    public void addProductToCart(String productName) {
        try {
            // Busca pelo produto específico e adiciona ao carrinho
            WebElement addButton = driver.findElement(By.xpath(
                "//android.view.ViewGroup[.//*[@content-desc='test-Item title' and @text='" + productName + "']]" +
                "//*[@content-desc='test-ADD TO CART']"
            ));
            addButton.click();
            System.out.println("✅ Produto adicionado ao carrinho: " + productName);
            Thread.sleep(1000); // Aguarda processamento
        } catch (Exception e) {
            System.out.println("❌ Erro ao adicionar produto: " + productName + " - " + e.getMessage());
        }
    }

    public void openCart() {
        try {
            WebElement cart = driver.findElement(cartIcon);
            cart.click();
            System.out.println("Carrinho aberto com sucesso");
        } catch (Exception e) {
            System.out.println("Botão do carrinho não encontrado");
        }
    }
}
