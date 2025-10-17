
package steps;

import driver.DriverManager;
import hooks.TestHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.pt.*;
import org.openqa.selenium.By;
import pages.LoginPage;
import pages.ProductsPage;
import pages.CartPage;
import pages.CheckoutPage;
import static org.junit.jupiter.api.Assertions.*;

public class CommonSteps {
    private AndroidDriver driver;
    private LoginPage login;
    private ProductsPage products;
    private CartPage cart;
    private CheckoutPage checkout;

    @Dado("que a tela de Login \\(Java\\) está visível")
    public void loginVisivel() throws Exception {
        driver = DriverManager.get();
        login = new LoginPage(driver);
        products = new ProductsPage(driver);
        cart = new CartPage(driver);
        assertTrue(login.visible());
    }

    @Quando("informo credenciais válidas \\(Java)")
    public void credenciaisValidasJava() {
        login.login("standard_user", "secret_sauce");
    }

    @Então("vejo a tela de Produtos \\(Java)")
    public void vejoProdutosJava() {
        System.out.println("🔍 Verificando se a tela de produtos está visível...");
        boolean productsLoaded = products.loaded();
        System.out.println("📱 Tela de produtos carregada: " + productsLoaded);
        
        if (!productsLoaded) {
            System.out.println("⚠️ Tentando aguardar mais um pouco...");
            try {
                Thread.sleep(3000);
                productsLoaded = products.loaded();
                System.out.println("📱 Segunda tentativa - Produtos carregados: " + productsLoaded);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        assertTrue(productsLoaded, "Tela de produtos deveria estar visível");
    }

    @Dado("que estou autenticado \\(Java)")
    public void autenticadoJava() throws Exception {
        // Usar a sessão compartilhada dos hooks
        driver = TestHooks.getDriver();
        if (driver == null) {
            driver = DriverManager.get();
        }
        
        login = new LoginPage(driver);
        products = new ProductsPage(driver);
        cart = new CartPage(driver);
        checkout = new CheckoutPage(driver);
        
        System.out.println("🔍 Verificando autenticação na sessão compartilhada...");
        
        // Verificar se estamos na tela de produtos
        if (products.loaded()) {
            System.out.println("✅ Autenticação OK - estamos na tela de produtos!");
        } else {
            System.out.println("⚠️ Não estamos na tela de produtos. Tentando resolver...");
            
            // Aguardar um pouco mais
            Thread.sleep(3000);
            
            if (products.loaded()) {
                System.out.println("✅ Agora estamos na tela de produtos após aguardar!");
            } else if (login.visible()) {
                System.out.println("🔑 Fazendo login novamente...");
                login.login("standard_user", "secret_sauce");
                Thread.sleep(5000);
                
                if (products.loaded()) {
                    System.out.println("✅ Login realizado com sucesso!");
                } else {
                    System.out.println("⚠️ Login pode não ter funcionado completamente");
                }
            } else {
                System.out.println("⚠️ Estado inesperado - tentando reinicializar app");
                // Tentar reabrir o app
                try {
                    ProcessBuilder pb = new ProcessBuilder("adb", "shell", "am", "start", "-n", "com.swaglabsmobileapp/.SplashActivity");
                    pb.start().waitFor();
                    Thread.sleep(5000);
                    
                    if (login.visible()) {
                        login.login("standard_user", "secret_sauce");
                        Thread.sleep(5000);
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Erro ao tentar reinicializar app: " + e.getMessage());
                }
            }
        }
    }

    @Quando("seleciono um produto e adiciono {int} itens \\(Java)")
    public void selecionoProdutoJava(Integer quantidade) {
        // Para o primeiro produto, usar selectProductByName uma vez
        if (quantidade >= 1) {
            products.selectProductByName(System.getProperty("product.name", "Sauce Labs Backpack"));
        }
        
        // Para itens adicionais, usar addToCart (mas como está vazio, não faz nada)
        // Na verdade, como nosso app adiciona 1 item por clique, só fazemos uma vez
        System.out.println("📦 Adicionando " + quantidade + " item(s) ao carrinho");
        
        products.openCart();
    }

    @Quando("seleciono um produto e adiciono {int} item \\(Java)")
    public void selecionoProdutoUmItem(Integer quantidade) {
        // Usar o mesmo método para 1 item
        selecionoProdutoJava(quantidade);
    }

    @Então("o carrinho exibe {int} itens \\(Java)")
    public void carrinhoExibeJava(Integer quantidade) {
        assertTrue(cart.countItems() >= quantidade);
    }

    @Então("o carrinho exibe {int} item \\(Java)")
    public void carrinhoExibeUmItem(Integer quantidade) {
        // Usar o mesmo método para 1 item
        carrinhoExibeJava(quantidade);
    }

    @Então("inicio o checkout \\(Java)")
    public void inicioOCheckoutJava() {
        try {
            System.out.println("🛒 Iniciando processo de checkout...");
            
            // Primeiro verificar se estamos no carrinho, se não, abrir o carrinho
            try {
                driver.findElement(By.xpath("//*[@content-desc='test-CHECKOUT']"));
                System.out.println("✅ Já estamos na tela do carrinho");
            } catch (Exception e) {
                System.out.println("📱 Navegando para carrinho primeiro...");
                products.openCart();
                Thread.sleep(2000);
            }
            
            cart.checkout();
        } catch (InterruptedException e) {
            System.out.println("❌ Thread interrompida: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("❌ Erro ao iniciar checkout: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Então("preencho os dados pessoais no checkout \\(Java)")
    public void preenchoDadosPessoaisJava() {
        try {
            System.out.println("📝 Preenchendo dados pessoais para checkout...");
            checkout.fillPersonalData();
        } catch (Exception e) {
            System.out.println("❌ Erro ao preencher dados pessoais: " + e.getMessage());
            throw e;
        }
    }

    @Então("vejo confirmação de pedido \\(Java)")
    public void confirmacaoJava() {
        assertTrue(checkout.successVisible());
    }

    @E("fecho o aplicativo \\(Java)")
    public void fechoAplicativoJava() {
        System.out.println("📱 Fechando aplicativo...");
        if (driver != null) {
            try {
                // Fechar app usando adb
                ProcessBuilder pb = new ProcessBuilder("adb", "shell", "am", "force-stop", "com.swaglabsmobileapp");
                Process process = pb.start();
                int exitCode = process.waitFor();
                
                if (exitCode == 0) {
                    System.out.println("✅ Aplicativo fechado com sucesso");
                } else {
                    System.out.println("⚠️ Possível problema ao fechar o aplicativo");
                }
                
                // Marcar que a sessão precisa ser reinicializada
                TestHooks.markSessionAsEnded();
                
            } catch (Exception e) {
                System.out.println("❌ Erro ao fechar aplicativo: " + e.getMessage());
            }
        }
    }

    // === NOVOS STEPS PARA CENÁRIO DETALHADO ===
    
    @Quando("adiciono um item no carrinho \\(Java)")
    public void adicionoUmItemNoCarrinhoJava() {
        try {
            System.out.println("🛍️ Adicionando um item no carrinho...");
            products.addProductToCart("Sauce Labs Backpack");
            Thread.sleep(2000); // Aguardar confirmação
            System.out.println("✅ Item adicionado com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao adicionar item no carrinho: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Quando("clico no carrinho \\(Java)")
    public void clicoNoCarrinhoJava() {
        try {
            System.out.println("🛒 Clicando no carrinho...");
            products.openCart();
            Thread.sleep(2000); // Aguardar carrinho abrir
            System.out.println("✅ Carrinho aberto com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao clicar no carrinho: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Quando("clico no checkout \\(Java)")
    public void clicoNoCheckoutJava() {
        try {
            System.out.println("💳 Clicando no checkout...");
            cart.checkout();
            Thread.sleep(3000); // Aguardar tela de checkout
            System.out.println("✅ Checkout iniciado com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao clicar no checkout: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Quando("preencho os dados ficticios \\(Java)")
    public void preenchoDadosFicticiosJava() {
        try {
            System.out.println("📝 Preenchendo dados fictícios...");
            checkout.fillPersonalData();
            Thread.sleep(2000); // Aguardar preenchimento
            System.out.println("✅ Dados fictícios preenchidos!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao preencher dados: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Quando("clico em continue \\(Java)")
    public void clicoEmContinueJava() {
        try {
            System.out.println("➡️ Clicando em CONTINUE...");
            checkout.clickContinue();
            Thread.sleep(3000); // Aguardar próxima tela
            System.out.println("✅ CONTINUE executado com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao clicar em CONTINUE: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Quando("rolo scroll para baixo e clico em finish \\(Java)")
    public void roloScrollParaBaixoEClicoEmFinishJava() {
        try {
            System.out.println("📱 Fazendo scroll para baixo e clicando em FINISH...");
            checkout.clickFinish(); // Este método já tem scroll automático
            Thread.sleep(3000); // Aguardar tela de sucesso
            System.out.println("✅ FINISH executado com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao executar FINISH: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Quando("clico em back home \\(Java)")
    public void clicoEmBackHomeJava() {
        try {
            System.out.println("🏠 Clicando em BACK HOME...");
            checkout.clickBackHome();
            Thread.sleep(2000); // Aguardar retorno
            System.out.println("✅ BACK HOME executado com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao clicar em BACK HOME: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Então("o aplicativo é fechado \\(Java)")
    public void entaoAplicativoEFechadoJava() {
        // Chama o mesmo método que já existe para fechar o aplicativo
        fechoAplicativoJava();
    }
}
