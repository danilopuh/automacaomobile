// 🌐 Cenários Web - Page Object Model
import { LoginPage, InventoryPage, CartPage, CheckoutPage } from '../support/pages/index.js';

describe('🌐 Testes Web - E-commerce', () => {
  const loginPage = new LoginPage();
  const inventoryPage = new InventoryPage();
  const cartPage = new CartPage();
  const checkoutPage = new CheckoutPage();

  // Esperar entre testes para evitar problemas de sessão
  beforeEach(() => {
    cy.wait(2000); // Espera 2 segundos entre cada teste
  });

  it('📝 CENÁRIO 1: Login e Validação da Home', () => {
    cy.log('🚀 Executando Cenário 1: Login e Validação');
    
    // Abrir página e fazer login
    loginPage.visit().loginWithValidCredentials();
    inventoryPage.shouldBeVisible();
    
    // Validações do login
    cy.get('.inventory_list').should('be.visible');
    cy.get('.inventory_item').should('have.length.greaterThan', 0);
    cy.url().should('include', '/inventory.html');
    
    cy.log('✅ CENÁRIO 1 CONCLUÍDO - Login e validação realizados com sucesso!');
    cy.log('🔄 Fechando página após Cenário 1');
  });

  it('🛒 CENÁRIO 2: Operações do Carrinho', () => {
    cy.log('🚀 Executando Cenário 2: Operações do Carrinho');
    
    // Limpar cookies e storage antes de abrir página
    cy.clearCookies();
    cy.clearLocalStorage();
    cy.log('→ Cookies e storage limpos');
    
    // Abrir página novamente e fazer login
    cy.log('→ Abrindo página novamente para Cenário 2');
    loginPage.visit().loginWithValidCredentials();
    inventoryPage.shouldBeVisible();
    
    // Adicionar produto ao carrinho
    inventoryPage.addBackpackToCart().shouldHaveCartItems(1);
    cy.log('✅ Produto adicionado ao carrinho');
    
    // Ir para carrinho
    inventoryPage.goToCart();
    cartPage.shouldBeVisible();
    cy.log('✅ Navegação para carrinho funcionando');
    
    // Testar remoção
    cartPage.removeBackpack().shouldBeEmpty();
    cy.log('✅ Remoção de produto funcionando');
    
    cy.log('✅ CENÁRIO 2 CONCLUÍDO - Operações do carrinho validadas!');
    cy.log('🔄 Fechando página após Cenário 2');
  });

  it('💳 CENÁRIO 3: Processo Completo de Checkout', () => {
    cy.log('🚀 Executando Cenário 3: Checkout Completo');
    
    // Limpar cookies e storage antes de abrir página
    cy.clearCookies();
    cy.clearLocalStorage();
    cy.log('→ Cookies e storage limpos');
    
    // Abrir página novamente e fazer login
    cy.log('→ Abrindo página novamente para Cenário 3');
    loginPage.visit().loginWithValidCredentials();
    inventoryPage.shouldBeVisible();
    
    // Adicionar produto e ir para checkout
    inventoryPage.addBackpackToCart().goToCart();
    cartPage.shouldBeVisible();
    cartPage.proceedToCheckout();
    
    // Step 1: Informações pessoais
    checkoutPage
      .shouldBeOnStepOne()
      .fillDefaultPersonalInfo()
      .continue();
    cy.log('✅ Step 1 do checkout concluído');
    
    // Step 2: Revisão e finalização
    checkoutPage
      .shouldBeOnStepTwo()
      .finish()
      .shouldShowSuccess();
    cy.log('✅ Step 2 do checkout concluído');
    
    // Voltar para home
    checkoutPage.backToHome();
    inventoryPage.shouldBeVisible();
    
    cy.log('✅ CENÁRIO 3 CONCLUÍDO - Checkout completo validado!');
    cy.log('🔄 Fechando página após Cenário 3');
  });
});