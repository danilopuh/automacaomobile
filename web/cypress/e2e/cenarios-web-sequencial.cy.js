// 🌐 Cenários Web - Page Object Model
import { LoginPage, InventoryPage, CartPage, CheckoutPage } from '../support/pages/index.js';

describe('🌐 Testes Web - E-commerce', () => {
  const loginPage = new LoginPage();
  const inventoryPage = new InventoryPage();
  const cartPage = new CartPage();
  const checkoutPage = new CheckoutPage();

  it('🎯 Execução Completa: Todos os Cenários em Sequência', () => {
    cy.log('🚀 Iniciando execução de todos os cenários em sequência');
    
    // ==================== CENÁRIO 1: LOGIN ====================
    cy.log('📝 CENÁRIO 1: Login e Validação da Home');
    cy.log('→ Fazendo login com Page Objects');
    
    loginPage.visit().loginWithValidCredentials();
    inventoryPage.shouldBeVisible();
    
    // Validações do login
    cy.get('.inventory_list').should('be.visible');
    cy.get('.inventory_item').should('have.length.greaterThan', 0);
    cy.url().should('include', '/inventory.html');
    
    cy.log('✅ CENÁRIO 1 CONCLUÍDO - Login realizado com sucesso!');
    cy.log('');
    
    // ==================== CENÁRIO 2: CARRINHO ====================
    cy.log('🛒 CENÁRIO 2: Operações do Carrinho');
    cy.log('→ Adicionando produto ao carrinho');
    
    inventoryPage.addBackpackToCart().shouldHaveCartItems(1);
    cy.log('✅ Produto adicionado ao carrinho');
    
    cy.log('→ Navegando para página do carrinho');
    inventoryPage.goToCart();
    cartPage.shouldBeVisible();
    cy.log('✅ Navegação para carrinho funcionando');
    
    cy.log('→ Testando remoção de produto');
    cartPage.removeBackpack().shouldBeEmpty();
    cy.log('✅ Remoção de produto funcionando');
    
    cy.log('→ Preparando para checkout (adicionando produto novamente)');
    cartPage.continueShopping();
    inventoryPage.shouldBeVisible();
    inventoryPage.addBackpackToCart();
    
    cy.log('✅ CENÁRIO 2 CONCLUÍDO - Operações do carrinho validadas!');
    cy.log('');
    
    // ==================== CENÁRIO 3: CHECKOUT ====================
    cy.log('💳 CENÁRIO 3: Processo Completo de Checkout');
    cy.log('→ Iniciando processo de checkout');
    
    inventoryPage.goToCart();
    cartPage.proceedToCheckout();
    
    cy.log('→ Preenchendo informações pessoais (Step 1)');
    checkoutPage
      .shouldBeOnStepOne()
      .fillDefaultPersonalInfo()
      .continue();
    cy.log('✅ Step 1 do checkout concluído');
    
    cy.log('→ Revisão e finalização (Step 2)');
    checkoutPage
      .shouldBeOnStepTwo()
      .finish()
      .shouldShowSuccess();
    cy.log('✅ Step 2 do checkout concluído');
    
    cy.log('→ Retornando para home');
    checkoutPage.backToHome();
    inventoryPage.shouldBeVisible();
    
    cy.log('✅ CENÁRIO 3 CONCLUÍDO - Checkout completo validado!');
    cy.log('');
    
    // ==================== VALIDAÇÃO FINAL ====================
    cy.log('🎯 VALIDAÇÃO FINAL');
    cy.log('→ Verificando estado final da aplicação');
    
    cy.get('.inventory_list').should('be.visible');
    cy.get('.inventory_item').should('have.length.greaterThan', 0);
    cy.url().should('include', '/inventory.html');
    
    cy.log('🎉 TODOS OS 3 CENÁRIOS EXECUTADOS COM SUCESSO!');
    cy.log('✅ CENÁRIO 1: Login funcionando com Page Objects');
    cy.log('✅ CENÁRIO 2: Carrinho funcionando com Page Objects');
    cy.log('✅ CENÁRIO 3: Checkout funcionando com Page Objects');
    cy.log('✅ Fluxo completo de e-commerce validado!');
  });
});