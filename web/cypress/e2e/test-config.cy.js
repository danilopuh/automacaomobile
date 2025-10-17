// Arquivo de teste simples para verificar se o Cypress funciona
describe('Teste de Configuração', () => {
  it('deve carregar a página inicial', () => {
    cy.visit('https://www.saucedemo.com');
    cy.get('#user-name').should('be.visible');
  });
});