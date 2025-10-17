/**
 * Comandos customizados do Cypress para Page Object Model
 */

// Comando para fazer login rápido
Cypress.Commands.add('loginQuick', (username = 'standard_user', password = 'secret_sauce') => {
  cy.log(`🔐 Login rápido: ${username}`);
  cy.get('#user-name').type(username);
  cy.get('#password').type(password);
  cy.get('#login-button').click();
});

// Comando para aguardar carregamento da página
Cypress.Commands.add('waitForPageLoad', () => {
  cy.log('⏳ Aguardando carregamento da página...');
  cy.wait(1000);
});

// Comando para validar URL específica
Cypress.Commands.add('shouldBeOnPage', (path) => {
  cy.log(`📍 Validando página: ${path}`);
  cy.url().should('include', path);
});

// Comando para adicionar produto ao carrinho por data-test
Cypress.Commands.add('addToCartByDataTest', (dataTest) => {
  cy.log(`🛒 Adicionando produto: ${dataTest}`);
  cy.get(`[data-test="${dataTest}"]`).click();
});

// Comando para remover produto do carrinho por data-test
Cypress.Commands.add('removeFromCartByDataTest', (dataTest) => {
  cy.log(`🗑️ Removendo produto: ${dataTest}`);
  cy.get(`[data-test="${dataTest}"]`).click();
});

// Comando para verificar badge do carrinho
Cypress.Commands.add('shouldHaveCartBadge', (count) => {
  if (count > 0) {
    cy.get('.shopping_cart_badge').should('contain', count.toString());
    cy.log(`✅ Carrinho com ${count} item(s)`);
  } else {
    cy.get('.shopping_cart_badge').should('not.exist');
    cy.log('✅ Carrinho vazio');
  }
});

// Comando para navigation steps
Cypress.Commands.add('navigateToCart', () => {
  cy.log('🛒 Navegando para carrinho');
  cy.get('.shopping_cart_link').click();
  cy.url().should('include', '/cart.html');
});

// Comando para preencher checkout
Cypress.Commands.add('fillCheckoutInfo', (firstName = 'João', lastName = 'Silva', postalCode = '01234-567') => {
  cy.log('📝 Preenchendo dados de checkout');
  cy.get('[data-test="firstName"]').type(firstName);
  cy.get('[data-test="lastName"]').type(lastName);
  cy.get('[data-test="postalCode"]').type(postalCode);
});