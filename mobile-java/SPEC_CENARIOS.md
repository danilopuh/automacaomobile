# Especificação dos Cenários Mobile - Automação de Testes

## 📱 Visão Geral

Este documento especifica os cenários de teste automatizado para aplicação mobile **SauceLabs Demo App** utilizando:
- **Framework:** Cucumber BDD + Java
- **Ferramenta:** Appium + Selenium WebDriver
- **Build Tool:** Maven
- **Linguagem:** Java 21

---

## 🎯 Cenários Implementados e Funcionais

### **CENÁRIO 1: Validação de Login Simples**

#### **Objetivo:**
Validar que o sistema de autenticação está funcionando corretamente de forma rápida e eficiente.

#### **Pré-condições:**
- Servidor Appium rodando na porta 4723
- Emulador Android ou dispositivo físico conectado
- Aplicativo SauceLabs instalado (`com.swaglabsmobileapp`)

#### **Passos do Teste:**
```gherkin
Cenário: Validação de login simples
  Dado que estou autenticado (Java)
  Então o aplicativo é fechado (Java)
```

#### **Detalhes da Execução:**
1. **Inicialização:** Criação de sessão Appium
2. **Limpeza:** Fechamento e limpeza de dados do app
3. **Autenticação:** Login com credenciais válidas
   - **Usuário:** `standard_user`
   - **Senha:** `secret_sauce`
4. **Validação:** Verificação da tela de produtos
5. **Preparação:** Limpeza do carrinho
6. **Finalização:** Fechamento controlado do aplicativo

#### **Tempo Estimado:** ~40 segundos

#### **Critérios de Sucesso:**
- ✅ Login realizado com sucesso
- ✅ Tela de produtos carregada
- ✅ Aplicativo fechado sem erros
- ✅ Sessão limpa para próximo teste

---

### **CENÁRIO 2: Fluxo Completo de Compra - E-commerce**

#### **Objetivo:**
Validar todo o fluxo de compra desde a autenticação até a finalização do pedido.

#### **Pré-condições:**
- Servidor Appium rodando na porta 4723
- Emulador Android ou dispositivo físico conectado
- Aplicativo SauceLabs instalado
- Sessão anterior encerrada corretamente

#### **Passos do Teste:**
```gherkin
Cenário: Fluxo completo de compra - do produto ao checkout
  Dado que estou autenticado (Java)
  Quando adiciono um item no carrinho (Java)
  E clico no carrinho (Java)
  E clico no checkout (Java)
  E preencho os dados ficticios (Java)
  E clico em continue (Java)
  E rolo scroll para baixo e clico em finish (Java)
  E clico em back home (Java)
  Então fecho o aplicativo (Java)
```

#### **Detalhes da Execução:**

##### **1. Autenticação**
- Login com credenciais válidas
- Validação da tela de produtos

##### **2. Seleção de Produto**
- Produto selecionado: **"Sauce Labs Backpack"**
- Adição ao carrinho via botão "ADD TO CART"

##### **3. Carrinho**
- Abertura do carrinho de compras
- Validação do produto adicionado

##### **4. Checkout**
- Inicialização do processo de checkout
- Navegação para formulário de dados

##### **5. Preenchimento de Dados**
- **Nome:** João Silva
- **Sobrenome:** Silva  
- **CEP:** 01234-567
- Validação de campos obrigatórios

##### **6. Continuação**
- Clique no botão "CONTINUE"
- Navegação para resumo do pedido

##### **7. Finalização**
- Scroll para visualizar botão "FINISH"
- Clique no botão "FINISH"
- Confirmação do pedido

##### **8. Retorno**
- Clique no botão "BACK HOME"
- Retorno à tela inicial de produtos

##### **9. Encerramento**
- Fechamento controlado do aplicativo
- Limpeza da sessão

#### **Tempo Estimado:** ~3-4 minutos

#### **Critérios de Sucesso:**
- ✅ Autenticação bem-sucedida
- ✅ Produto adicionado ao carrinho
- ✅ Carrinho acessado corretamente
- ✅ Checkout iniciado sem erros
- ✅ Formulário preenchido com dados válidos
- ✅ Processo de pagamento simulado concluído
- ✅ Pedido finalizado com sucesso
- ✅ Retorno à tela inicial
- ✅ Aplicativo fechado sem erros

---

## 🔧 Configurações Técnicas

### **Dependências Maven:**
```xml
<dependencies>
    <dependency>
        <groupId>io.appium</groupId>
        <artifactId>java-client</artifactId>
        <version>8.5.1</version>
    </dependency>
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-java</artifactId>
        <version>7.15.0</version>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.2</version>
    </dependency>
</dependencies>
```

### **Configurações do Appium:**
```properties
platformName=ANDROID
automationName=UiAutomator2
deviceName=Android Emulator
platformVersion=16
appPackage=com.swaglabsmobileapp
appActivity=.SplashActivity
noReset=true
autoGrantPermissions=true
```

### **Estrutura de Arquivos:**
```
mobile-java/
├── src/test/java/
│   ├── driver/
│   │   └── DriverManager.java
│   ├── hooks/
│   │   └── TestHooks.java
│   ├── pages/
│   │   ├── LoginPage.java
│   │   ├── ProductsPage.java
│   │   ├── CartPage.java
│   │   └── CheckoutPage.java
│   ├── steps/
│   │   └── CommonSteps.java
│   └── runner/
│       └── RunCucumberTest.java
├── src/test/resources/
│   └── features/
│       └── cart_navigation.feature
└── pom.xml
```

---

## 📊 Métricas de Qualidade

### **Taxa de Sucesso:** 100% ✅
- **Cenário 1:** ✅ PASSOU
- **Cenário 2:** ✅ PASSOU

### **Cobertura de Teste:**
- **Autenticação:** ✅ Coberto
- **Navegação:** ✅ Coberto  
- **Seleção de Produtos:** ✅ Coberto
- **Carrinho de Compras:** ✅ Coberto
- **Processo de Checkout:** ✅ Coberto
- **Preenchimento de Formulários:** ✅ Coberto
- **Finalização de Pedido:** ✅ Coberto

### **Elementos Testados:**
- Campos de texto (login, formulários)
- Botões (ADD TO CART, CONTINUE, FINISH, etc.)
- Navegação entre telas
- Scroll e interações touch
- Validações de estado

---

## 🚀 Como Executar

### **Pré-requisitos:**
1. **Java 21** instalado
2. **Maven 3.9+** instalado
3. **Android SDK** configurado
4. **Appium 1.22.3+** instalado
5. **Emulador Android** ou dispositivo físico

### **Comandos de Execução:**

#### **Executar apenas validações:**
```bash
mvn test -Dtest=ValidationTest
```

#### **Executar todos os cenários:**
```bash
# Iniciar Appium em terminal separado
appium

# Executar testes
mvn test -Dtest=RunCucumberTest
```

#### **Executar cenário específico:**
```bash
# Para executar apenas um cenário, modifique o arquivo .feature
# ou use tags do Cucumber
```

---

## 📝 Observações Importantes

### **Sincronização:**
- Os cenários utilizam sessão compartilhada para eficiência
- Cada cenário limpa o estado antes da execução
- Tempos de espera são otimizados para estabilidade

### **Recuperação de Erros:**
- Sistema detecta quando app não está no estado esperado
- Recuperação automática via re-login
- Reinicialização inteligente quando necessário

### **Manutenabilidade:**
- Page Objects implementados para facilitar manutenção
- Steps reutilizáveis entre cenários
- Logs detalhados para debug

---

## 📅 Histórico de Versões

| Versão | Data | Alterações |
|--------|------|------------|
| 1.0 | 16/10/2025 | Implementação inicial dos cenários |
| 1.1 | 16/10/2025 | Correção de sincronização entre cenários |
| 1.2 | 17/10/2025 | Documentação completa dos specs |

---

**Status Atual:** ✅ **TODOS OS CENÁRIOS FUNCIONANDO CORRETAMENTE**

*Última atualização: 17 de outubro de 2025*