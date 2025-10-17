
import { Given, When, Then } from '@badeball/cypress-cucumber-preprocessor';
import { LoginPage, InventoryPage, CartPage, CheckoutPage } from '../../support/pages/index.js';

// Instâncias dos Page Objects
const loginPage = new LoginPage();
const inventoryPage = new InventoryPage();
const cartPage = new CartPage();
const checkoutPage = new CheckoutPage();

// Steps básicos usando Page Objects
Given('que acesso a aplicação web', () => {
  loginPage.visit();
});

// Um único step para login - funciona tanto com When quanto Given/And
Given('informo credenciais válidas', () => {
  loginPage.loginWithValidCredentials();
});

// Step otimizado: faz login completo desde o início
Given('que estou logado na aplicação', () => {
  cy.log('🔄 Fazendo login completo...');
  loginPage.visit();
  loginPage.loginWithValidCredentials();
  inventoryPage.shouldBeVisible();
});

Then('devo ver a home responsiva', () => {
  inventoryPage.shouldBeVisible();
});

// Cenário 1 - Login e fechar
Then('fecho o navegador', () => {
  cy.log('🚪 Finalizando sessão');
  cy.log('✅ Sessão finalizada com sucesso');
});

// Cenário 2 - Carrinho (adicionar e remover) usando Page Objects
When('adiciono um item no carrinho', () => {
  inventoryPage.addBackpackToCart();
  inventoryPage.shouldHaveCartItems(1);
});

Given('abro o carrinho', () => {
  inventoryPage.goToCart();
  cartPage.shouldBeVisible();
});

Given('removo o item do carrinho', () => {
  cartPage.removeBackpack();
});

Then('o carrinho deve estar vazio', () => {
  cartPage.shouldBeEmpty();
});

// Cenário 3 - Fluxo completo de compra usando Page Objects
Given('clico em checkout', () => {
  cartPage.proceedToCheckout();
  checkoutPage.shouldBeOnStepOne();
});

Given('preencho dados de entrega', () => {
  checkoutPage.fillDefaultPersonalInfo();
});

Given('clico em continue', () => {
  checkoutPage.continue();
  checkoutPage.shouldBeOnStepTwo();
});

Given('finalizo a compra', () => {
  checkoutPage.finish();
  checkoutPage.shouldShowSuccess();
});

Given('clico em back home', () => {
  checkoutPage.backToHome();
});

Then('devo estar na home', () => {
  inventoryPage.shouldBeVisible();
});

// Cenário 2 - Carrinho (adicionar e remover) usando Page Objects
When('adiciono um item no carrinho', () => {
  inventoryPage.addBackpackToCart();
  inventoryPage.shouldHaveCartItems(1);
});

Given('abro o carrinho', () => {
  inventoryPage.goToCart();
  cartPage.shouldBeVisible();
});

Given('removo o item do carrinho', () => {
  cartPage.removeBackpack();
});

Then('o carrinho deve estar vazio', () => {
  cartPage.shouldBeEmpty();
});

// Cenário 3 - Fluxo completo de compra usando Page Objects
Given('clico em checkout', () => {
  cartPage.proceedToCheckout();
  checkoutPage.shouldBeOnStepOne();
});

Given('preencho dados de entrega', () => {
  checkoutPage.fillDefaultPersonalInfo();
});

Given('clico em continue', () => {
  checkoutPage.continue();
  checkoutPage.shouldBeOnStepTwo();
});

Given('finalizo a compra', () => {
  checkoutPage.finish();
  checkoutPage.shouldShowSuccess();
});

Given('clico em back home', () => {
  checkoutPage.backToHome();
});

Then('devo estar na home', () => {
  inventoryPage.shouldBeVisible();
});
