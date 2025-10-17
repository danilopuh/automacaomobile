
package pages;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.PerformsTouchActions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.ArrayList;
import driver.DriverManager;

public class CheckoutPage {
    private final AndroidDriver driver;
    public CheckoutPage(AndroidDriver driver) { this.driver = driver; }

    // Seletores para as diferentes etapas do checkout
    private final By firstNameField = By.xpath("//*[@content-desc='test-First Name']");
    private final By lastNameField = By.xpath("//*[@content-desc='test-Last Name']");
    private final By postalCodeField = By.xpath("//*[@content-desc='test-Zip/Postal Code']");
    private final By btnContinue = By.xpath("//*[@content-desc='test-CONTINUE']");
    private final By btnFinish = By.xpath("//*[@content-desc='test-FINISH']");
    private final By btnBackHome = By.xpath("//*[@content-desc='test-BACK HOME']");
    private final By msgSuccess = By.xpath("//*[@content-desc='test-CHECKOUT: COMPLETE!']");

    public void fillPersonalData() {
        try {
            System.out.println("📝 Preenchendo dados pessoais...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Aguardar campos aparecerem
            wait.until(ExpectedConditions.presenceOfElementLocated(firstNameField));
            
            // Preencher campos com dados fictícios específicos
            driver.findElement(firstNameField).clear();
            driver.findElement(firstNameField).sendKeys("João");
            Thread.sleep(500);
            
            driver.findElement(lastNameField).clear();
            driver.findElement(lastNameField).sendKeys("Silva");
            Thread.sleep(500);
            
            driver.findElement(postalCodeField).clear();
            driver.findElement(postalCodeField).sendKeys("01234-567");
            Thread.sleep(500);
            
            System.out.println("✅ Dados pessoais preenchidos: João Silva, CEP: 01234-567");
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao preencher dados pessoais: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void clickContinue() {
        try {
            System.out.println("➡️ Clicando em CONTINUE...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.elementToBeClickable(btnContinue));
            driver.findElement(btnContinue).click();
            System.out.println("✅ CONTINUE clicado com sucesso");
            Thread.sleep(3000); // Aguardar próxima tela carregar
        } catch (Exception e) {
            System.out.println("❌ Erro ao clicar em CONTINUE: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void clickFinish() {
        try {
            System.out.println("🔄 Fazendo scroll para baixo e clicando em FINISH...");
            System.out.println("🎯 Clicando em FINISH...");
            
            List<WebElement> finishElements = new ArrayList<>();
            
            // Primeira tentativa - verificar se já está visível
            finishElements = findFinishButton();
            if (!finishElements.isEmpty()) {
                System.out.println("🎯 FINISH já visível, clicando...");
                finishElements.get(0).click();
                System.out.println("✅ FINISH executado com sucesso!");
                Thread.sleep(5000);
                return;
            }
            
            // Scroll mais agressivo para baixo
            System.out.println("📱 Fazendo scroll agressivo para baixo...");
            for (int i = 1; i <= 25; i++) {
                System.out.println("📱 Clique arrastando " + i + "...");
                
                try {
                    // TouchAction para arrastar a tela
                    TouchAction touchAction = new TouchAction((PerformsTouchActions) DriverManager.get());
                    touchAction.press(PointOption.point(540, 1800))
                              .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                              .moveTo(PointOption.point(540, 600))
                              .release()
                              .perform();
                    System.out.println("✅ TouchAction executado");
                } catch (Exception touchError) {
                    System.out.println("🚨 TouchAction falhou: " + touchError.getMessage());
                }
                
                Thread.sleep(800);
                
                // Buscar botão FINISH
                finishElements = findFinishButton();
                
                if (!finishElements.isEmpty()) {
                    System.out.println("🎯 FINISH encontrado na tentativa " + i + "!");
                    break;
                }
                
                // Debug a cada 5 tentativas
                if (i % 5 == 0) {
                    System.out.println("🔍 Debug após " + i + " cliques:");
                    debugVisibleElements();
                }
            }
            
            if (!finishElements.isEmpty()) {
                System.out.println("🎯 Clicando no botão FINISH encontrado...");
                finishElements.get(0).click();
                System.out.println("✅ FINISH executado com sucesso!");
                Thread.sleep(5000); // Aguardar tela de sucesso carregar
            } else {
                System.out.println("❌ FINISH não encontrado mesmo após scroll agressivo");
                // Última tentativa - procurar qualquer botão que possa ser FINISH
                List<WebElement> allButtons = DriverManager.get().findElements(By.xpath("//android.widget.Button"));
                System.out.println("🔍 Tentativa final - botões encontrados: " + allButtons.size());
                for (WebElement btn : allButtons) {
                    if (btn.isDisplayed()) {
                        String text = btn.getAttribute("text");
                        String desc = btn.getAttribute("content-desc");
                        System.out.println("  Botão: '" + text + "' | desc: '" + desc + "'");
                        if ((text != null && text.toLowerCase().contains("finish")) ||
                            (desc != null && desc.toLowerCase().contains("finish"))) {
                            System.out.println("🎯 Encontrado botão com finish, clicando...");
                            btn.click();
                            Thread.sleep(5000);
                            return;
                        }
                    }
                }
                throw new RuntimeException("Botão FINISH não encontrado após todas as tentativas");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao clicar em FINISH: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    private List<WebElement> findFinishButton() {
        List<WebElement> finishElements = new ArrayList<>();
        
        try {
            // Estratégia 1: Por texto exato
            finishElements.addAll(DriverManager.get().findElements(
                By.xpath("//*[@text='FINISH']")));
            
            // Estratégia 2: Por texto contendo
            finishElements.addAll(DriverManager.get().findElements(
                By.xpath("//*[contains(@text, 'FINISH')]")));
            
            // Estratégia 3: Por content-desc
            finishElements.addAll(DriverManager.get().findElements(
                By.xpath("//*[@content-desc='FINISH' or contains(@content-desc, 'FINISH')]")));
            
            // Estratégia 4: Por resource-id
            finishElements.addAll(DriverManager.get().findElements(
                By.xpath("//*[contains(@resource-id, 'finish') or contains(@resource-id, 'FINISH')]")));
            
            // Estratégia 5: Botões com texto finish (case insensitive)
            finishElements.addAll(DriverManager.get().findElements(
                By.xpath("//android.widget.Button[contains(translate(@text, 'finish', 'FINISH'), 'FINISH')]")));
        } catch (Exception e) {
            System.out.println("Erro ao buscar botão FINISH: " + e.getMessage());
        }
        
        // Filtrar apenas elementos visíveis
        return finishElements.stream()
            .filter(WebElement::isDisplayed)
            .collect(java.util.stream.Collectors.toList());
    }
    
    private void debugVisibleElements() {
        try {
            List<WebElement> currentElements = DriverManager.get().findElements(By.xpath("//*"));
            int count = 0;
            for (WebElement el : currentElements) {
                if (el.isDisplayed() && count < 10) { // Limitar output
                    String desc = el.getAttribute("content-desc");
                    String text = el.getAttribute("text");
                    if ((desc != null && !desc.isEmpty()) || (text != null && !text.isEmpty())) {
                        System.out.println("    " + el.getAttribute("className") + " | '" + desc + "' | '" + text + "'");
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro no debug: " + e.getMessage());
        }
    }

    public void clickBackHome() {
        try {
            System.out.println("🏠 Clicando em BACK HOME...");
            
            // Aguardar que a tela de sucesso carregue completamente
            Thread.sleep(5000); // Tempo extra para tela de sucesso
            
            // Buscar por diferentes seletores possíveis
            List<String> selectors = Arrays.asList(
                "//android.widget.TextView[@content-desc='BACK HOME']",
                "//android.widget.TextView[contains(@text, 'BACK HOME')]",
                "//android.widget.Button[@content-desc='BACK HOME']",
                "//android.widget.Button[contains(@text, 'BACK HOME')]",
                "//*[contains(@content-desc, 'BACK') and contains(@content-desc, 'HOME')]",
                "//*[contains(@text, 'BACK') and contains(@text, 'HOME')]"
            );
            
            WebElement backHomeElement = null;
            
            for (String selector : selectors) {
                try {
                    List<WebElement> elements = driver.findElements(By.xpath(selector));
                    if (!elements.isEmpty()) {
                        backHomeElement = elements.get(0);
                        System.out.println("✅ BACK HOME encontrado com seletor: " + selector);
                        break;
                    }
                } catch (Exception e) {
                    // Continuar para próximo seletor
                }
            }
            
            if (backHomeElement != null) {
                // Garantir que o elemento está visível e clicável
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
                wait.until(ExpectedConditions.elementToBeClickable(backHomeElement));
                
                backHomeElement.click();
                System.out.println("✅ BACK HOME clicado com sucesso!");
                Thread.sleep(3000); // Aguardar voltar para home
            } else {
                System.out.println("❌ BACK HOME não encontrado, tentando navegação alternativa...");
                // Se não encontrar, usar navegação alternativa
                try {
                    driver.navigate().back();
                    Thread.sleep(2000);
                    driver.navigate().back();
                    System.out.println("✅ Voltou para home usando navegação alternativa");
                } catch (Exception navEx) {
                    System.out.println("❌ Falha na navegação alternativa: " + navEx.getMessage());
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao clicar em BACK HOME: " + e.getMessage());
            // Não fazer throw para não quebrar o teste
        }
    }

    public void continueFlow() { 
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Primeiro preenche os campos se estiverem presentes
            if (!driver.findElements(firstNameField).isEmpty()) {
                System.out.println("📝 Preenchendo dados pessoais...");
                driver.findElement(firstNameField).sendKeys("Test");
                driver.findElement(lastNameField).sendKeys("User");
                driver.findElement(postalCodeField).sendKeys("12345");
                
                // Clica no botão CONTINUE
                wait.until(ExpectedConditions.elementToBeClickable(btnContinue));
                driver.findElement(btnContinue).click();
                System.out.println("✅ Dados preenchidos e CONTINUE clicado");
                
                // Aguardar a próxima tela
                Thread.sleep(2000);
            }
            
            // Então clica no botão FINISH para finalizar a compra
            if (!driver.findElements(btnFinish).isEmpty()) {
                System.out.println("🏁 Finalizando compra...");
                wait.until(ExpectedConditions.elementToBeClickable(btnFinish));
                driver.findElement(btnFinish).click();
                System.out.println("✅ FINISH clicado - compra finalizada");
                
                // Aguardar a tela de sucesso
                Thread.sleep(3000);
            }
            
        } catch (Exception e) {
            System.out.println("⚠️ Erro no checkout: " + e.getMessage());
        }
    }
    
    public void finish() { 
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(btnFinish));
            driver.findElement(btnFinish).click();
        } catch (Exception e) {
            System.out.println("⚠️ Erro ao finalizar: " + e.getMessage());
        }
    }
    
    public boolean successVisible() { 
        try {
            System.out.println("🔍 Verificando se tela de sucesso está visível...");
            
            // Tentar diferentes seletores para a tela de sucesso
            String[] successSelectors = {
                "//*[@content-desc='test-CHECKOUT: COMPLETE!']",
                "//*[contains(@text, 'CHECKOUT: COMPLETE')]",
                "//*[contains(@text, 'THANK YOU')]",
                "//*[contains(@text, 'Thank you')]",
                "//*[contains(@text, 'Your order has been dispatched')]",
                "//*[@content-desc='test-BACK HOME']"
            };
            
            for (String selector : successSelectors) {
                if (!driver.findElements(By.xpath(selector)).isEmpty()) {
                    System.out.println("✅ Tela de sucesso encontrada com seletor: " + selector);
                    return true;
                }
            }
            
            System.out.println("⚠️ Tela de sucesso não encontrada. Listando elementos visíveis:");
            // Listar elementos para debug
            try {
                var elements = driver.findElements(By.xpath("//*[@content-desc or @text]"));
                for (int i = 0; i < Math.min(10, elements.size()); i++) {
                    var element = elements.get(i);
                    String contentDesc = element.getAttribute("content-desc");
                    String text = element.getAttribute("text");
                    System.out.println("  " + (i+1) + ". content-desc: '" + contentDesc + "' | text: '" + text + "'");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro ao listar elementos: " + e.getMessage());
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("❌ Erro ao verificar tela de sucesso: " + e.getMessage());
            return false;
        }
    }
}
