/**
 * Índice dos Page Objects
 * Centraliza todas as importações das páginas
 */

import LoginPage from './LoginPage.js';
import InventoryPage from './InventoryPage.js';
import CartPage from './CartPage.js';
import CheckoutPage from './CheckoutPage.js';

// Exporta todas as páginas
export {
  LoginPage,
  InventoryPage,
  CartPage,
  CheckoutPage
};

// Também exporta como default para facilitar importação
export default {
  LoginPage,
  InventoryPage,
  CartPage,
  CheckoutPage
};