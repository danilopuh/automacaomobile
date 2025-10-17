/**
 * Page Object Model - Cart Page
 * Representa a página do carrinho de compras da aplicação SauceDemo
 */
class CartPage {
  // Seletores da página
  get cartItems() { 
    return cy.get('.cart_item'); 
  }
  
  get cartItemNames() { 
    return cy.get('.inventory_item_name'); 
  }
  
  get checkoutButton() { 
    return cy.get('[data-test="checkout"]', { timeout: 10000 }); 
  }
  
  get continueShoppingButton() { 
    return cy.get('[data-test="continue-shopping"]'); 
  }
  
  get removeBackpackButton() { 
    return cy.get('[data-test="remove-sauce-labs-backpack"]', { timeout: 10000 }); 
  }
  
  get removeBikeLightButton() { 
    return cy.get('[data-test="remove-sauce-labs-bike-light"]'); 
  }
  
  get cartBadge() { 
    return cy.get('.shopping_cart_badge'); 
  }

  // Ações da página
  /**
   * Verifica se está na página do carrinho
   */
  shouldBeVisible() {
    cy.log('🛒 Validando página do carrinho');
    cy.url({ timeout: 15000 }).should('include', '/cart.html');
    cy.get('.header_secondary_container').should('contain.text', 'Your Cart');
    return this;
  }

  /**
   * Remove um produto específico do carrinho
   * @param {string} product - Nome do produto (backpack, bike-light, bolt-t-shirt)
   */
  removeProduct(product) {
    cy.log(`🗑️ Removendo ${product} do carrinho`);
    
    switch(product.toLowerCase()) {
      case 'backpack':
        this.removeBackpackButton.click();
        break;
      case 'bike-light':
        this.removeBikeLightButton.click();
        break;
      default:
        throw new Error(`Produto não encontrado: ${product}`);
    }
    
    cy.wait(1000);
    return this;
  }

  /**
   * Remove a mochila do carrinho (produto padrão)
   */
  removeBackpack() {
    return this.removeProduct('backpack');
  }

  /**
   * Verifica se o carrinho está vazio
   */
  shouldBeEmpty() {
    cy.log('✅ Validando carrinho vazio');
    this.cartItems.should('not.exist');
    this.cartBadge.should('not.exist');
    return this;
  }

  /**
   * Verifica se o carrinho contém itens
   * @param {number} count - Número esperado de itens
   */
  shouldHaveItems(count) {
    this.cartItems.should('have.length', count);
    return this;
  }

  /**
   * Verifica se um produto específico está no carrinho
   * @param {string} productName - Nome do produto
   */
  shouldContainProduct(productName) {
    this.cartItemNames.should('contain', productName);
    return this;
  }

  /**
   * Procede para o checkout
   */
  proceedToCheckout() {
    cy.log('💳 Iniciando checkout');
    this.checkoutButton.click();
    cy.wait(1000);
    return this;
  }

  /**
   * Continua comprando (volta para inventário)
   */
  continueShopping() {
    this.continueShoppingButton.click();
    return this;
  }
}

export default CartPage;