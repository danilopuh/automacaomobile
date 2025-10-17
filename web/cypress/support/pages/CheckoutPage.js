/**
 * Page Object Model - Checkout Pages
 * Representa as páginas de checkout da aplicação SauceDemo
 */
class CheckoutPage {
  // Seletores - Step One (Informações pessoais)
  get firstNameField() { 
    return cy.get('[data-test="firstName"]', { timeout: 10000 }); 
  }
  
  get lastNameField() { 
    return cy.get('[data-test="lastName"]'); 
  }
  
  get postalCodeField() { 
    return cy.get('[data-test="postalCode"]'); 
  }
  
  get continueButton() { 
    return cy.get('[data-test="continue"]'); 
  }
  
  get cancelButton() { 
    return cy.get('[data-test="cancel"]'); 
  }

  // Seletores - Step Two (Revisão)
  get finishButton() { 
    return cy.get('[data-test="finish"]', { timeout: 10000 }); 
  }
  
  get itemTotal() { 
    return cy.get('.summary_subtotal_label'); 
  }
  
  get tax() { 
    return cy.get('.summary_tax_label'); 
  }
  
  get total() { 
    return cy.get('.summary_total_label'); 
  }

  // Seletores - Complete (Confirmação)
  get completeHeader() { 
    return cy.get('.complete-header'); 
  }
  
  get backHomeButton() { 
    return cy.get('[data-test="back-to-products"]', { timeout: 10000 }); 
  }

  // Ações - Step One
  /**
   * Verifica se está na primeira etapa do checkout
   */
  shouldBeOnStepOne() {
    cy.log('📝 Validando checkout step one');
    cy.url().should('include', '/checkout-step-one.html');
    return this;
  }

  /**
   * Preenche as informações pessoais
   * @param {string} firstName - Primeiro nome
   * @param {string} lastName - Último nome  
   * @param {string} postalCode - Código postal
   */
  fillPersonalInfo(firstName, lastName, postalCode) {
    cy.log('📝 Preenchendo dados pessoais');
    this.firstNameField.type(firstName);
    this.lastNameField.type(lastName);
    this.postalCodeField.type(postalCode);
    return this;
  }

  /**
   * Preenche com dados padrão
   */
  fillDefaultPersonalInfo() {
    return this.fillPersonalInfo('João', 'Silva', '01234-567');
  }

  /**
   * Clica em continuar para próxima etapa
   */
  continue() {
    cy.log('➡️ Continuando para próxima etapa');
    this.continueButton.click();
    cy.wait(2000);
    return this;
  }

  /**
   * Cancela o checkout
   */
  cancel() {
    this.cancelButton.click();
    return this;
  }

  // Ações - Step Two  
  /**
   * Verifica se está na segunda etapa do checkout
   */
  shouldBeOnStepTwo() {
    cy.log('💰 Validando checkout step two');
    cy.url().should('include', '/checkout-step-two.html');
    return this;
  }

  /**
   * Finaliza a compra
   */
  finish() {
    cy.log('🎯 Finalizando compra');
    this.finishButton.click();
    cy.wait(1000);
    return this;
  }

  /**
   * Verifica o resumo da compra
   */
  shouldShowOrderSummary() {
    this.itemTotal.should('be.visible');
    this.tax.should('be.visible');
    this.total.should('be.visible');
    return this;
  }

  // Ações - Complete
  /**
   * Verifica se a compra foi finalizada com sucesso
   */
  shouldShowSuccess() {
    cy.log('✅ Validando compra finalizada');
    cy.url().should('include', '/checkout-complete.html');
    this.completeHeader.should('contain', 'Thank you for your order!');
    return this;
  }

  /**
   * Volta para a home
   */
  backToHome() {
    cy.log('🏠 Voltando para home');
    this.backHomeButton.click();
    cy.wait(1000);
    return this;
  }
}

export default CheckoutPage;