
package driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Properties;
import java.io.InputStream;

public class DriverManager {
    private static AndroidDriver driver;

    public static AndroidDriver get() throws Exception {
        if (driver == null) {
            // Configure HTTP client for Java 21 compatibility
            System.setProperty("webdriver.http.factory", "netty");
            
            // Check if Appium server is available
            if (!isAppiumServerRunning()) {
                throw new RuntimeException("Appium server is not running on localhost:4723. " +
                    "Please start Appium server before running tests or use 'mvn compile test-compile' to validate the build only.");
            }
            
            // Fechar app se estiver aberto para garantir estado limpo
            System.out.println("🧹 Verificando e fechando app se necessário...");
            closeAppIfRunning();
            
            Properties p = new Properties();
            try (InputStream is = DriverManager.class.getClassLoader().getResourceAsStream("appium.properties")) {
                p.load(is);
            }
            
            // Resolve property with default value
            String deviceName = resolveProperty(p.getProperty("deviceName"), "Android Emulator");
            String platformVersion = resolveProperty(p.getProperty("platformVersion"), "16");
            String appPackage = p.getProperty("appPackage");
            String appActivity = p.getProperty("appActivity");
            String noReset = p.getProperty("noReset");
            
            UiAutomator2Options opts = new UiAutomator2Options()
                    .setPlatformName(p.getProperty("platformName"))
                    .setAutomationName(p.getProperty("automationName"))
                    .setDeviceName(deviceName)
                    .setPlatformVersion(platformVersion)
                    .autoGrantPermissions();
                    
            if (appPackage != null && appActivity != null) {
                opts.setAppPackage(appPackage);
                opts.setAppActivity(appActivity);
            }
            
            if ("true".equals(noReset)) {
                opts.setNoReset(true);
            }
            driver = new AndroidDriver(URI.create("http://localhost:4723/wd/hub").toURL(), opts);
        }
        return driver;
    }
    
    private static boolean isAppiumServerRunning() {
        try {
            java.net.Socket socket = new java.net.Socket();
            socket.connect(new java.net.InetSocketAddress("localhost", 4723), 5000);
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private static String resolveProperty(String propertyValue, String defaultValue) {
        if (propertyValue != null && propertyValue.startsWith("${") && propertyValue.endsWith("}")) {
            // Format: ${property.name:defaultValue}
            String content = propertyValue.substring(2, propertyValue.length() - 1);
            if (content.contains(":")) {
                String[] parts = content.split(":", 2);
                String sysProp = System.getProperty(parts[0]);
                return sysProp != null ? sysProp : parts[1];
            }
        }
        return propertyValue != null ? propertyValue : defaultValue;
    }

    private static void closeAppIfRunning() {
        try {
            System.out.println("📱 Verificando se o app está rodando...");
            
            // Usar adb para verificar e fechar o app
            ProcessBuilder pb = new ProcessBuilder("adb", "shell", "am", "force-stop", "com.swaglabsmobileapp");
            Process process = pb.start();
            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                System.out.println("✅ App fechado com sucesso (se estava rodando)");
                
                // Aguardar um pouco para garantir que o app foi fechado
                Thread.sleep(2000);
                
                // Opcional: Limpar dados do app para garantir estado limpo
                System.out.println("🧹 Limpando dados do app...");
                ProcessBuilder pbClear = new ProcessBuilder("adb", "shell", "pm", "clear", "com.swaglabsmobileapp");
                Process processClear = pbClear.start();
                processClear.waitFor();
                System.out.println("✅ Dados do app limpos");
                
            } else {
                System.out.println("⚠️ Não foi possível fechar o app (pode não estar rodando)");
            }
            
        } catch (Exception e) {
            System.out.println("⚠️ Erro ao tentar fechar o app: " + e.getMessage());
            // Não falhar o teste por causa disso, apenas continuar
        }
    }
}
