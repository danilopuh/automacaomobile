// 🌐 Cenários Web - Page Object Model (Versão Unificada)
import { LoginPage, InventoryPage, CartPage, CheckoutPage } from '../support/pages/index.js';

describe('🌐 Testes Web - E-commerce', () => {
  const loginPage = new LoginPage();
  const inventoryPage = new InventoryPage();
  const cartPage = new CartPage();
  const checkoutPage = new CheckoutPage();

  describe('📝 CENÁRIO 1: Login e Validação', () => {
    it('deve fazer login e validar a página inicial', () => {
      cy.log('🚀 Iniciando Cenário 1: Login e Validação');
      
      loginPage.visit().loginWithValidCredentials();
      inventoryPage.shouldBeVisible();
      
      // Validações do login
      cy.get('.inventory_list').should('be.visible');
      cy.get('.inventory_item').should('have.length.greaterThan', 0);
      cy.url().should('include', '/inventory.html');
      
      cy.log('✅ CENÁRIO 1 CONCLUÍDO - Login realizado com sucesso!');
    });
  });

  describe('🛒 CENÁRIO 2: Operações do Carrinho', () => {
    it('deve adicionar e remover produtos do carrinho', () => {
      cy.log('🚀 Iniciando Cenário 2: Operações do Carrinho');
      
      // Adicionar produto ao carrinho
      inventoryPage.addBackpackToCart().shouldHaveCartItems(1);
      cy.log('✅ Produto adicionado ao carrinho');
      
      // Ir para página do carrinho
      inventoryPage.goToCart();
      cartPage.shouldBeVisible();
      cy.log('✅ Navegação para carrinho funcionando');
      
      // Testar remoção de produto
      cartPage.removeBackpack().shouldBeEmpty();
      cy.log('✅ Remoção de produto funcionando');
      
      // Voltar e adicionar produto para o próximo cenário
      cartPage.continueShopping();
      inventoryPage.shouldBeVisible();
      inventoryPage.addBackpackToCart().shouldHaveCartItems(1);
      
      cy.log('✅ CENÁRIO 2 CONCLUÍDO - Operações do carrinho validadas!');
    });
  });

  describe('💳 CENÁRIO 3: Processo de Checkout', () => {
    it('deve completar o processo de checkout', () => {
      cy.log('🚀 Iniciando Cenário 3: Processo de Checkout');
      
      // Ir para carrinho e proceder checkout
      inventoryPage.goToCart();
      cartPage.proceedToCheckout();
      
      // Step 1: Preencher informações pessoais
      cy.log('→ Preenchendo informações pessoais (Step 1)');
      checkoutPage
        .shouldBeOnStepOne()
        .fillDefaultPersonalInfo()
        .continue();
      cy.log('✅ Step 1 do checkout concluído');
      
      // Step 2: Revisão e finalização
      cy.log('→ Revisão e finalização da compra (Step 2)');
      checkoutPage
        .shouldBeOnStepTwo()
        .finish()
        .shouldShowSuccess();
      cy.log('✅ Step 2 do checkout concluído');
      
      // Voltar para home
      cy.log('→ Retornando para página inicial');
      checkoutPage.backToHome();
      inventoryPage.shouldBeVisible();
      
      cy.log('✅ CENÁRIO 3 CONCLUÍDO - Checkout completo validado!');
      
      // Validação final
      cy.get('.inventory_list').should('be.visible');
      cy.get('.inventory_item').should('have.length.greaterThan', 0);
      cy.url().should('include', '/inventory.html');
      
      cy.log('🎉 TODOS OS 3 CENÁRIOS EXECUTADOS COM SUCESSO!');
    });
  });
    cy.log('🚀 Iniciando execução completa dos 3 cenários');
    
    // ==================== CENÁRIO 1: LOGIN ====================
    cy.log('');
    cy.log('📝 CENÁRIO 1: Login e Validação da Home');
    cy.log('→ Fazendo login inicial');
    
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
    cy.log('→ Testando funcionalidades do carrinho');
    
    // Adicionar produto ao carrinho
    inventoryPage.addBackpackToCart().shouldHaveCartItems(1);
    cy.log('✅ Produto adicionado ao carrinho');
    
    // Ir para página do carrinho
    inventoryPage.goToCart();
    cartPage.shouldBeVisible();
    cy.log('✅ Navegação para carrinho funcionando');
    
    // Testar remoção de produto
    cartPage.removeBackpack().shouldBeEmpty();
    cy.log('✅ Remoção de produto funcionando');
    
    // Voltar e adicionar produto para o próximo cenário
    cartPage.continueShopping();
    inventoryPage.shouldBeVisible();
    inventoryPage.addBackpackToCart().shouldHaveCartItems(1);
    
    cy.log('✅ CENÁRIO 2 CONCLUÍDO - Operações do carrinho validadas!');
    cy.log('');
    
    // ==================== CENÁRIO 3: CHECKOUT ====================
    cy.log('💳 CENÁRIO 3: Processo Completo de Checkout');
    cy.log('→ Iniciando processo de checkout');
    
    // Ir para carrinho e proceder checkout
    inventoryPage.goToCart();
    cartPage.proceedToCheckout();
    
    // Step 1: Preencher informações pessoais
    cy.log('→ Preenchendo informações pessoais (Step 1)');
    checkoutPage
      .shouldBeOnStepOne()
      .fillDefaultPersonalInfo()
      .continue();
    cy.log('✅ Step 1 do checkout concluído');
    
    // Step 2: Revisão e finalização
    cy.log('→ Revisão e finalização da compra (Step 2)');
    checkoutPage
      .shouldBeOnStepTwo()
      .finish()
      .shouldShowSuccess();
    cy.log('✅ Step 2 do checkout concluído');
    
    // Voltar para home
    cy.log('→ Retornando para página inicial');
    checkoutPage.backToHome();
    inventoryPage.shouldBeVisible();
    
    cy.log('✅ CENÁRIO 3 CONCLUÍDO - Checkout completo validado!');
    cy.log('');
    
    // ==================== VALIDAÇÃO FINAL ====================
    cy.log('🎯 VALIDAÇÃO FINAL DE TODOS OS CENÁRIOS');
    cy.log('→ Verificando estado final da aplicação');
    
    cy.get('.inventory_list').should('be.visible');
    cy.get('.inventory_item').should('have.length.greaterThan', 0);
    cy.url().should('include', '/inventory.html');
    
    cy.log('');
    cy.log('🎉 EXECUÇÃO COMPLETA FINALIZADA COM SUCESSO!');
    cy.log('✅ CENÁRIO 1: Login e validação → OK');
    cy.log('✅ CENÁRIO 2: Operações do carrinho → OK');
    cy.log('✅ CENÁRIO 3: Checkout completo → OK');
    cy.log('✅ Todos os cenários executados em uma única sessão!');
  });
