package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import driver.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import pages.LoginPage;
import pages.ProductsPage;
import pages.CartPage;

public class TestHooks {
    private static AndroidDriver driver;
    private static boolean isSessionInitialized = false;
    private static LoginPage login;
    private static ProductsPage products;
    private static CartPage cart;

    @Before
    public void setUp(Scenario scenario) throws Exception {
        System.out.println("🎬 INICIANDO CENÁRIO: " + scenario.getName());
        
        if (!isSessionInitialized) {
            System.out.println("🚀 Inicializando sessão única do Appium...");
            System.out.println("🧹 O app será fechado e limpo automaticamente pelo DriverManager");
            
            driver = DriverManager.get();
            login = new LoginPage(driver);
            products = new ProductsPage(driver);
            cart = new CartPage(driver);
            
            // Aguardar mais tempo o app carregar após limpeza
            System.out.println("⏳ Aguardando app carregar após limpeza...");
            Thread.sleep(8000); // Aumentar tempo de espera
            
            // Fazer login inicial sempre
            System.out.println("🔍 Verificando autenticação na sessão compartilhada...");
            
            // Verificar se já estamos logados
            if (products.loaded()) {
                System.out.println("✅ Já estamos logados na tela de produtos!");
            } else if (login.visible()) {
                System.out.println("🔑 Fazendo login inicial...");
                login.login("standard_user", "secret_sauce");
                Thread.sleep(5000); // Aguardar carregamento
                
                if (products.loaded()) {
                    System.out.println("✅ Login inicial bem-sucedido!");
                } else {
                    System.out.println("⚠️ Aviso: Login pode não ter funcionado, mas continuando...");
                }
            } else {
                System.out.println("⚠️ Estado inesperado durante inicialização");
                // Tentar aguardar um pouco mais
                Thread.sleep(3000);
                if (login.visible()) {
                    System.out.println("🔑 Fazendo login após aguardar...");
                    login.login("standard_user", "secret_sauce");
                    Thread.sleep(5000);
                }
            }
            
            isSessionInitialized = true;
        } else {
            System.out.println("♻️ Reutilizando sessão existente...");
            
            // Verificar se ainda estamos na tela correta
            if (driver != null && products != null && !products.loaded()) {
                System.out.println("🔄 Sessão existente não está na tela de produtos. Tentando navegar...");
                
                // Aguardar um pouco antes de tentar navegar
                Thread.sleep(3000);
                
                // Se estivermos na tela de login, fazer login novamente
                if (login.visible()) {
                    System.out.println("🔑 Re-fazendo login...");
                    login.login("standard_user", "secret_sauce");
                    Thread.sleep(5000);
                } else {
                    System.out.println("⚠️ Estado inesperado - não estamos em login nem produtos");
                    // Tentar pressionar back algumas vezes para voltar à tela principal
                    try {
                        for (int i = 0; i < 3; i++) {
                            driver.navigate().back();
                            Thread.sleep(2000);
                            if (products.loaded()) {
                                System.out.println("✅ Voltamos à tela de produtos usando navegação");
                                break;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("⚠️ Erro ao tentar navegar: " + e.getMessage());
                    }
                }
            }
        }
        
        // Verificar se estamos na tela de produtos antes de preparar carrinho
        if (products != null && products.loaded()) {
            // Limpar carrinho antes de cada cenário para garantir estado limpo
            System.out.println("🛒 Preparando carrinho para o cenário...");
            products.openCart(); // Ir para o carrinho
            Thread.sleep(2000);
            cart.clearCart(); // Limpar todos os itens
            driver.navigate().back(); // Voltar para produtos
            Thread.sleep(2000);
            System.out.println("✅ Carrinho preparado e voltamos para produtos");
        } else {
            System.out.println("⚠️ Não foi possível preparar carrinho - não estamos na tela de produtos");
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println("❌ CENÁRIO FALHOU: " + scenario.getName());
        } else {
            System.out.println("✅ CENÁRIO PASSOU: " + scenario.getName());
        }
        
        // Não fechar o driver aqui - manter sessão ativa
        System.out.println("🔄 Mantendo sessão ativa para próximo cenário...");
    }

    // Método estático para obter o driver atual
    public static AndroidDriver getDriver() {
        return driver;
    }

    // Método para finalizar a sessão (chamado manualmente se necessário)
    public static void closeSession() {
        if (driver != null) {
            System.out.println("🔚 Finalizando sessão do Appium...");
            driver.quit();
            driver = null;
            isSessionInitialized = false;
        }
    }

    // Método para marcar que a sessão acabou (app foi fechado)
    public static void markSessionAsEnded() {
        System.out.println("🔄 Marcando sessão como finalizada - próximo cenário será reinicializado");
        isSessionInitialized = false;
        // Não limpar o driver aqui pois ele ainda pode estar sendo usado
        // driver = null;
        // login = null;
        // products = null;
    }
}