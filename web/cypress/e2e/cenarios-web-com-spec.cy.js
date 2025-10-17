// 🌐 Cenários Web - Page Object Model (Cenários Visíveis no Spec)
import { LoginPage, InventoryPage, CartPage, CheckoutPage } from '../support/pages/index.js';

describe('🌐 Testes Web - E-commerce', () => {
  const loginPage = new LoginPage();
  const inventoryPage = new InventoryPage();
  const cartPage = new CartPage();
  const checkoutPage = new CheckoutPage();

  // Fazer login apenas uma vez antes de todos os testes
  before(() => {
    cy.log('🚀 Fazendo login inicial para todos os cenários');
    loginPage.visit().loginWithValidCredentials();
    inventoryPage.shouldBeVisible();
  });

  describe('📝 CENÁRIO 1: Login e Validação', () => {
    it('deve validar que o login foi realizado com sucesso', () => {
      cy.log('🚀 Validando Cenário 1: Login e Validação');
      
      // Validações do login já realizado no before
      cy.get('.inventory_list').should('be.visible');
      cy.get('.inventory_item').should('have.length.greaterThan', 0);
      cy.url().should('include', '/inventory.html');
      
      cy.log('✅ CENÁRIO 1 CONCLUÍDO - Login validado com sucesso!');
    });
  });

  describe('🛒 CENÁRIO 2: Operações do Carrinho', () => {
    beforeEach(() => {
      // Verificar se ainda estamos logados, se não, fazer login novamente
      cy.url().then((url) => {
        if (!url.includes('/inventory.html')) {
          cy.log('→ Fazendo login novamente para o Cenário 2');
          loginPage.visit().loginWithValidCredentials();
        }
      });
      inventoryPage.shouldBeVisible();
    });

    it('deve adicionar e remover produtos do carrinho', () => {
      cy.log('🚀 Executando Cenário 2: Operações do Carrinho');
      
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
    beforeEach(() => {
      // Verificar se ainda estamos logados, se não, fazer login novamente
      cy.url().then((url) => {
        if (!url.includes('/inventory.html')) {
          cy.log('→ Fazendo login novamente para o Cenário 3');
          loginPage.visit().loginWithValidCredentials();
        }
      });
      inventoryPage.shouldBeVisible();
    });

    it('deve completar o processo de checkout', () => {
      cy.log('🚀 Executando Cenário 3: Processo de Checkout');
      
      // Adicionar produto ao carrinho para o checkout
      inventoryPage.addBackpackToCart().shouldHaveCartItems(1);
      
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
});