// Importa o plugin Allure
import '@shelex/cypress-allure-plugin';

// Importa comandos customizados
import './commands';

// Configurações globais para BDD/Cucumber
before(() => {
  cy.log('🚀 Iniciando testes BDD com Page Object Model');
});

beforeEach(() => {
  // Configurações antes de cada teste
  cy.viewport('iphone-6');
});

after(() => {
  cy.log('✅ Testes BDD finalizados');
});
