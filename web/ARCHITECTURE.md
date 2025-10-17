# 📁 Estrutura Page Object Model - Projeto Web

## 🏗️ Arquitetura do Projeto

```
web/
├── cypress/
│   ├── e2e/
│   │   ├── features/          # Arquivos .feature (Gherkin/BDD)
│   │   │   └── login.feature
│   │   └── steps/             # Step definitions (Cucumber)
│   │       └── login.steps.js
│   ├── support/
│   │   ├── pages/             # 🆕 Page Object Model Classes
│   │   │   ├── LoginPage.js
│   │   │   ├── InventoryPage.js
│   │   │   ├── CartPage.js
│   │   │   ├── CheckoutPage.js
│   │   │   └── index.js       # Índice de exportações
│   │   ├── utils/             # 🆕 Utilitários e helpers
│   │   │   └── TestUtils.js
│   │   └── e2e.js            # Configurações globais
│   ├── screenshots/          # Screenshots de falhas
│   └── allure-results/       # Relatórios Allure
├── cypress.config.js         # Configuração do Cypress
└── package.json             # Dependências Node.js
```

## 🎯 Padrão Page Object Model (POM)

### 📋 Benefícios
- ✅ **Reutilização**: Métodos compartilhados entre testes
- ✅ **Manutenibilidade**: Centralização de seletores
- ✅ **Legibilidade**: Código mais limpo e expressivo  
- ✅ **Escalabilidade**: Fácil adição de novas páginas
- ✅ **Robustez**: Encapsulamento de timeouts e waits

### 🔧 Estrutura das Classes

#### 1. **LoginPage.js** - Página de Login
```javascript
class LoginPage {
  // Seletores (getters)
  get usernameField() { return cy.get('#user-name'); }
  get passwordField() { return cy.get('#password'); }
  
  // Ações (métodos)
  visit() { /* navega para login */ }
  login(user, pass) { /* realiza login */ }
  loginWithValidCredentials() { /* login padrão */ }
}
```

#### 2. **InventoryPage.js** - Página de Produtos (Home)
```javascript
class InventoryPage {
  // Seletores
  get inventoryList() { return cy.get('.inventory_list'); }
  get cartBadge() { return cy.get('.shopping_cart_badge'); }
  
  // Ações
  shouldBeVisible() { /* valida página carregada */ }
  addProductToCart(product) { /* adiciona ao carrinho */ }
  goToCart() { /* navega para carrinho */ }
}
```

#### 3. **CartPage.js** - Página do Carrinho
```javascript
class CartPage {
  // Seletores
  get cartItems() { return cy.get('.cart_item'); }
  get checkoutButton() { return cy.get('[data-test="checkout"]'); }
  
  // Ações
  shouldBeVisible() { /* valida página do carrinho */ }
  removeProduct(product) { /* remove do carrinho */ }
  proceedToCheckout() { /* vai para checkout */ }
}
```

#### 4. **CheckoutPage.js** - Páginas de Checkout
```javascript
class CheckoutPage {
  // Seletores Step One
  get firstNameField() { return cy.get('[data-test="firstName"]'); }
  
  // Seletores Step Two  
  get finishButton() { return cy.get('[data-test="finish"]'); }
  
  // Ações
  fillPersonalInfo(name, last, postal) { /* preenche dados */ }
  continue() { /* próxima etapa */ }
  finish() { /* finaliza compra */ }
}
```

## 🔄 Utilização nos Step Definitions

### ❌ Antes (sem Page Objects)
```javascript
Given('que acesso a aplicação web', () => {
  cy.viewport('iphone-6');
  cy.visit('/', { timeout: 30000 });
  cy.log('🌐 Acessando aplicação web');
});

When('informo credenciais válidas', () => {
  cy.get('#user-name', { timeout: 10000 }).type('standard_user');
  cy.get('#password').type('secret_sauce');
  cy.get('#login-button').click();
});
```

### ✅ Depois (com Page Objects)
```javascript
import { LoginPage, InventoryPage } from '../../support/pages/index.js';

const loginPage = new LoginPage();
const inventoryPage = new InventoryPage();

Given('que acesso a aplicação web', () => {
  loginPage.visit();
});

When('informo credenciais válidas', () => {
  loginPage.loginWithValidCredentials();
});
```

## 🛠️ Arquivo de Utilitários (TestUtils.js)

### 📊 Dados de Teste
```javascript
export const TEST_DATA = {
  users: {
    standard: { username: 'standard_user', password: 'secret_sauce' }
  },
  customer: { firstName: 'João', lastName: 'Silva', postalCode: '01234-567' }
};
```

### ⏱️ Timeouts Padronizados
```javascript
export const TIMEOUTS = {
  short: 5000,
  medium: 10000, 
  long: 30000
};
```

### 🔗 URLs da Aplicação
```javascript
export const URLS = {
  login: '/',
  inventory: '/inventory.html',
  cart: '/cart.html'
};
```

## 🚀 Comandos de Execução

```bash
# Instalar dependências
npm install

# Executar todos os testes
npm test

# Executar em modo interativo
npx cypress open

# Executar específico cenário
npx cypress run --spec "cypress/e2e/features/login.feature"
```

## 📝 Cenários de Teste Implementados

### 🔐 Cenário 1: Login e Logout
- ✅ Acessa aplicação web
- ✅ Realiza login com credenciais válidas
- ✅ Valida home responsiva
- ✅ Finaliza sessão

### 🛒 Cenário 2: Operações do Carrinho
- ✅ Login na aplicação
- ✅ Adiciona item ao carrinho
- ✅ Abre carrinho
- ✅ Remove item do carrinho
- ✅ Valida carrinho vazio

### 💳 Cenário 3: Fluxo Completo de Compra
- ✅ Login na aplicação
- ✅ Adiciona item ao carrinho
- ✅ Procede para checkout
- ✅ Preenche dados de entrega
- ✅ Finaliza compra
- ✅ Retorna à home

## 🎨 Vantagens da Nova Estrutura

### 🔄 **Reutilização**
- Métodos `loginWithValidCredentials()` usados em múltiplos cenários
- Validações `shouldBeVisible()` padronizadas
- Navegação encapsulada em métodos específicos

### 🛡️ **Robustez**
- Timeouts configurados nos seletores críticos
- Waits estratégicos após ações importantes
- Tratamento de erros encapsulado

### 📖 **Legibilidade**
- Steps mais expressivos e autoexplicativos
- Separação clara entre lógica de teste e interação com página
- Documentação integrada nos métodos

### 🔧 **Manutenibilidade**
- Seletores centralizados nas classes Page Object
- Mudanças na UI requerem alterações apenas nos Page Objects
- Fácil adição de novos métodos e validações

## 🏆 Boas Práticas Implementadas

1. **Nomenclatura Clara**: Métodos autodescritivos
2. **Encapsulamento**: Lógica de página isolada
3. **Fluent Interface**: Métodos retornam `this` para chaining
4. **Timeouts Inteligentes**: Configurados onde necessário
5. **Logs Contextuais**: Informações úteis para debug
6. **Modularização**: Separação por responsabilidade
7. **Exportação Centralizada**: Arquivo index.js para imports

Esta estrutura garante testes mais robustos, fáceis de manter e expandir! 🚀