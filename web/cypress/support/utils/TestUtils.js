/**
 * Utilitários para testes
 * Funções auxiliares e constantes compartilhadas
 */

// Dados de teste padrão
export const TEST_DATA = {
  users: {
    standard: {
      username: 'standard_user',
      password: 'secret_sauce'
    },
    locked: {
      username: 'locked_out_user', 
      password: 'secret_sauce'
    },
    problem: {
      username: 'problem_user',
      password: 'secret_sauce'
    }
  },
  
  customer: {
    firstName: 'João',
    lastName: 'Silva', 
    postalCode: '01234-567'
  },
  
  products: {
    backpack: 'Sauce Labs Backpack',
    bikeLight: 'Sauce Labs Bike Light',
    boltTshirt: 'Sauce Labs Bolt T-Shirt'
  }
};

// Timeouts padrão
export const TIMEOUTS = {
  short: 5000,
  medium: 10000,
  long: 30000
};

// URLs da aplicação
export const URLS = {
  base: '/',
  login: '/',
  inventory: '/inventory.html',
  cart: '/cart.html',
  checkoutStepOne: '/checkout-step-one.html',
  checkoutStepTwo: '/checkout-step-two.html',
  checkoutComplete: '/checkout-complete.html'
};

/**
 * Aguarda um tempo específico
 * @param {number} ms - Milissegundos para aguardar
 */
export function wait(ms = 1000) {
  cy.wait(ms);
}

/**
 * Gera um timestamp único para usar em dados de teste
 */
export function generateTimestamp() {
  return Date.now().toString();
}

/**
 * Configura viewport responsivo padrão
 */
export function setMobileViewport() {
  cy.viewport('iphone-6');
}

/**
 * Configura viewport desktop
 */
export function setDesktopViewport() {
  cy.viewport(1280, 720);
}

/**
 * Log personalizado com emoji
 * @param {string} emoji - Emoji para identificar o log
 * @param {string} message - Mensagem do log
 */
export function logStep(emoji, message) {
  cy.log(`${emoji} ${message}`);
}