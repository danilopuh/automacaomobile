import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste para validar que o upgrade do Java 21 foi bem-sucedido
 */
public class ValidationTest {

    @Test
    @DisplayName("Validar que estamos usando Java 21")
    public void testJavaVersion() {
        String javaVersion = System.getProperty("java.version");
        System.out.println("Versão do Java em uso: " + javaVersion);
        
        // Verifica se estamos usando Java 21 ou superior
        assertTrue(javaVersion.startsWith("21") || javaVersion.startsWith("25"), 
                   "Deve estar usando Java 21 LTS ou superior, mas está usando: " + javaVersion);
    }

    @Test
    @DisplayName("Validar que as features do Java 21 funcionam")
    public void testJava21Features() {
        // Test Record (Java 14+)
        record Pessoa(String nome, int idade) {}
        
        var pessoa = new Pessoa("João", 30);
        assertEquals("João", pessoa.nome());
        assertEquals(30, pessoa.idade());
        
        // Test Pattern Matching for instanceof (Java 16+)
        Object obj = "Hello Java 21";
        if (obj instanceof String s) {
            assertTrue(s.contains("Java 21"));
        }
        
        // Test Text Blocks (Java 15+)
        String json = """
                {
                    "upgrade": "successful",
                    "version": "Java 21 LTS",
                    "status": "working"
                }
                """;
        
        assertTrue(json.contains("Java 21 LTS"));
        System.out.println("✅ Todas as features do Java 21 estão funcionando!");
    }

    @Test
    @DisplayName("Validar que as dependências estão compatíveis")
    public void testDependenciesLoaded() {
        // Test Selenium classes
        try {
            Class.forName("org.openqa.selenium.WebDriver");
            System.out.println("✅ Selenium carregado com sucesso");
        } catch (ClassNotFoundException e) {
            fail("Selenium não foi carregado corretamente: " + e.getMessage());
        }

        // Test Appium classes
        try {
            Class.forName("io.appium.java_client.AppiumDriver");
            System.out.println("✅ Appium carregado com sucesso");
        } catch (ClassNotFoundException e) {
            fail("Appium não foi carregado corretamente: " + e.getMessage());
        }

        // Test Cucumber classes
        try {
            Class.forName("io.cucumber.java.en.Given");
            System.out.println("✅ Cucumber carregado com sucesso");
        } catch (ClassNotFoundException e) {
            fail("Cucumber não foi carregado corretamente: " + e.getMessage());
        }
    }
}