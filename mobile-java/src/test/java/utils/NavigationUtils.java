package utils;

import io.appium.java_client.android.AndroidDriver;
import pages.LoginPage;
import pages.ProductsPage;

public class NavigationUtils {
    
    public static boolean forceNavigateToProducts(AndroidDriver driver, int maxAttempts) {
        LoginPage login = new LoginPage(driver);
        ProductsPage products = new ProductsPage(driver);
        
        System.out.println("🎯 Forçando navegação para tela de produtos...");
        
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.println("🔄 Tentativa " + attempt + " de " + maxAttempts);
            
            try {
                // Verificar se já estamos na tela de produtos
                if (products.loaded()) {
                    System.out.println("✅ Já estamos na tela de produtos!");
                    return true;
                }
                
                // Se estivermos na tela de login, fazer login
                if (login.visible()) {
                    System.out.println("🔑 Fazendo login (tentativa " + attempt + ")...");
                    login.login("standard_user", "secret_sauce");
                    Thread.sleep(4000); // Aguardar mais tempo
                    
                    // Verificar se o login funcionou
                    if (products.loaded()) {
                        System.out.println("✅ Login bem-sucedido! Estamos na tela de produtos.");
                        return true;
                    }
                } else {
                    System.out.println("⚠️ Não estamos na tela de login nem de produtos. Aguardando...");
                    Thread.sleep(2000);
                }
                
                // Se chegou até aqui, não conseguimos navegar. Tentar novamente.
                if (attempt < maxAttempts) {
                    System.out.println("❌ Tentativa " + attempt + " falhou. Tentando novamente...");
                    Thread.sleep(1000);
                }
                
            } catch (Exception e) {
                System.out.println("⚠️ Erro na tentativa " + attempt + ": " + e.getMessage());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        System.out.println("❌ Não foi possível navegar para a tela de produtos após " + maxAttempts + " tentativas.");
        return false;
    }
}