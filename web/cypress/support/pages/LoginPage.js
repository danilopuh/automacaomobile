/**
 * Page Object Model - Login Page
 * Representa a página de login da aplicação SauceDemo
 */
class LoginPage {
  // Seletores da página
  get usernameField() { 
    return cy.get('#user-name', { timeout: 10000 }); 
  }
  
  get passwordField() { 
    return cy.get('#password'); 
  }
  
  get loginButton() { 
    return cy.get('#login-button'); 
  }
  
  get errorMessage() { 
    return cy.get('[data-test="error"]'); 
  }

  // Ações da página
  /**
   * Visita a página de login
   */
  visit() {
    cy.viewport(1280, 720);
    
    // Verifica se já estamos na página correta
    cy.url().then((url) => {
      if (!url.includes('saucedemo.com') || url.includes('/inventory.html')) {
        cy.visit('/', { 
          timeout: 30000,
          failOnStatusCode: false 
        });
      }
    });
    
    cy.log('🌐 Acessando página de login');
    return this;
  }

  /**
   * Realiza login com credenciais fornecidas
   * @param {string} username - Nome de usuário
   * @param {string} password - Senha
   */
  login(username, password) {
    cy.log(`🔐 Fazendo login com usuário: ${username}`);
    
    // Aguarda página carregar antes de interagir
    this.usernameField.should('be.visible').clear().type(username);
    this.passwordField.should('be.visible').clear().type(password);
    this.loginButton.should('be.enabled').click();
    
    // Aguarda redirecionamento
    cy.url({ timeout: 15000 }).should('include', '/inventory.html');
    return this;
  }

  /**
   * Realiza login com credenciais válidas padrão
   */
  loginWithValidCredentials() {
    return this.login('standard_user', 'secret_sauce');
  }

  /**
   * Verifica se há mensagem de erro
   */
  shouldHaveError() {
    this.errorMessage.should('be.visible');
    return this;
  }

  /**
   * Verifica se o login foi bem-sucedido (redirecionamento)
   */
  shouldRedirectToInventory() {
    cy.url().should('include', '/inventory.html');
    return this;
  }
}

export default LoginPage;