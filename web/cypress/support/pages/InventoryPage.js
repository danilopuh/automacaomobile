/**
 * Page Object Model - Inventory Page
 * Representa a página de produtos (home) da aplicação SauceDemo
 */
class InventoryPage {
  // Seletores da página
  get inventoryList() { 
    return cy.get('.inventory_list', { timeout: 10000 }); 
  }
  
  get inventoryItems() { 
    return cy.get('.inventory_item'); 
  }
  
  get cartBadge() { 
    return cy.get('.shopping_cart_badge'); 
  }
  
  get cartLink() { 
    return cy.get('.shopping_cart_link'); 
  }
  
  get menuButton() { 
    return cy.get('#react-burger-menu-btn'); 
  }

  // Botões específicos de produtos
  get addBackpackButton() { 
    return cy.get('[data-test="add-to-cart-sauce-labs-backpack"]', { timeout: 10000 }); 
  }
  
  get addBikeLightButton() { 
    return cy.get('[data-test="add-to-cart-sauce-labs-bike-light"]'); 
  }
  
  get addBoltTShirtButton() { 
    return cy.get('[data-test="add-to-cart-sauce-labs-bolt-t-shirt"]'); 
  }

  // Ações da página
  /**
   * Verifica se a página de inventário está carregada
   */
  shouldBeVisible() {
    cy.log('✅ Validando página de produtos');
    cy.url({ timeout: 15000 }).should('include', '/inventory.html');
    this.inventoryList.should('be.visible');
    this.inventoryItems.should('have.length.greaterThan', 0);
    return this;
  }

  /**
   * Adiciona um produto específico ao carrinho
   * @param {string} product - Nome do produto (backpack, bike-light, bolt-t-shirt)
   */
  addProductToCart(product) {
    cy.log(`🛒 Adicionando ${product} ao carrinho`);
    
    switch(product.toLowerCase()) {
      case 'backpack':
        this.addBackpackButton.should('be.visible').click();
        break;
      case 'bike-light':
        this.addBikeLightButton.should('be.visible').click();
        break;
      case 'bolt-t-shirt':
        this.addBoltTShirtButton.should('be.visible').click();
        break;
      default:
        throw new Error(`Produto não encontrado: ${product}`);
    }
    
    // Aguarda um pouco para o badge do carrinho atualizar
    cy.wait(500);
    return this;
  }

  /**
   * Adiciona a mochila ao carrinho (produto padrão)
   */
  addBackpackToCart() {
    return this.addProductToCart('backpack');
  }

  /**
   * Verifica o número de itens no carrinho
   * @param {number} count - Número esperado de itens
   */
  shouldHaveCartItems(count) {
    if (count > 0) {
      this.cartBadge.should('contain', count.toString());
    } else {
      this.cartBadge.should('not.exist');
    }
    return this;
  }

  /**
   * Clica no carrinho para navegar
   */
  goToCart() {
    cy.log('🛒 Navegando para o carrinho');
    this.cartLink.click();
    cy.wait(1000);
    return this;
  }

  /**
   * Abre o menu hamburger
   */
  openMenu() {
    this.menuButton.click();
    return this;
  }

  /**
   * Realiza logout via menu
   */
  logout() {
    this.openMenu();
    cy.get('#logout_sidebar_link').click();
    return this;
  }
}

export default InventoryPage;