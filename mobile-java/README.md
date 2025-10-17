# 📱 Mobile Automation - SauceLabs Demo App

## 🎯 Visão Geral

Projeto de automação de testes mobile para aplicativo SauceLabs Demo App utilizando **Cucumber BDD**, **Appium**, **Java 21** e **Maven**.

**Status Atual:** ✅ **FUNCIONANDO 100%** - Todos os cenários passando

---

## 📋 Documentação Disponível

| Documento | Descrição | Link |
|-----------|-----------|------|
| **Especificação Geral** | Visão completa dos cenários | [SPEC_CENARIOS.md](./SPEC_CENARIOS.md) |
| **Especificação Gherkin** | Cenários em formato BDD | [SPEC_CENARIOS_GHERKIN.md](./SPEC_CENARIOS_GHERKIN.md) |
| **Especificação Técnica** | Detalhes técnicos dos steps | [SPEC_STEPS_TECNICA.md](./SPEC_STEPS_TECNICA.md) |
| **Feature File** | Código Gherkin executável | [cart_navigation.feature](./src/test/resources/features/cart_navigation.feature) |

---

## 🚀 Quick Start

### **1. Pré-requisitos**
```bash
# Verificar instalações
java -version        # Java 21
mvn -version        # Maven 3.9+
appium --version    # Appium 1.22.3+
adb devices         # Android device/emulator
```

### **2. Executar Testes**
```bash
# Iniciar Appium (terminal separado)
appium

# Executar todos os cenários
mvn test -Dtest=RunCucumberTest

# Apenas validações rápidas
mvn test -Dtest=ValidationTest
```

### **3. Resultados**
- Logs detalhados no terminal
- Relatórios em `target/surefire-reports/`
- Screenshots em caso de falha (se configurado)

---

## 📱 Cenários Implementados

### ✅ **Cenário 1: Validação de Login Simples**
- **Tempo:** ~40 segundos
- **Objetivo:** Teste rápido de autenticação
- **Status:** 100% funcionando

### ✅ **Cenário 2: Fluxo Completo de E-commerce**  
- **Tempo:** ~3-4 minutos
- **Objetivo:** Teste completo do fluxo de compra
- **Status:** 100% funcionando

---

## 🏗️ Arquitetura do Projeto

```
mobile-java/
├── 📄 README.md                          # Este arquivo
├── 📄 SPEC_CENARIOS.md                   # Especificação geral
├── 📄 SPEC_CENARIOS_GHERKIN.md           # Especificação BDD
├── 📄 SPEC_STEPS_TECNICA.md              # Documentação técnica
├── 📄 pom.xml                            # Configuração Maven
├── 📁 src/test/java/
│   ├── 🚗 driver/
│   │   └── DriverManager.java            # Gerenciamento Appium
│   ├── 🪝 hooks/
│   │   └── TestHooks.java                # Setup/Teardown
│   ├── 📄 pages/
│   │   ├── LoginPage.java                # Page Object - Login
│   │   ├── ProductsPage.java             # Page Object - Produtos
│   │   ├── CartPage.java                 # Page Object - Carrinho
│   │   └── CheckoutPage.java             # Page Object - Checkout
│   ├── 👣 steps/
│   │   └── CommonSteps.java              # Step Definitions
│   └── 🏃 runner/
│       └── RunCucumberTest.java          # Test Runner
├── 📁 src/test/resources/
│   ├── 🥒 features/
│   │   └── cart_navigation.feature       # Feature Gherkin
│   └── appium.properties                 # Configurações Appium
└── 📁 target/                            # Resultados e relatórios
```

---

## 🔧 Tecnologias Utilizadas

| Tecnologia | Versão | Propósito |
|------------|--------|-----------|
| **Java** | 21 | Linguagem principal |
| **Maven** | 3.9.11 | Build tool |
| **Appium** | 1.22.3 | Automação mobile |
| **Selenium** | 4.11.0 | WebDriver |
| **Cucumber** | 7.15.0 | BDD Framework |
| **JUnit** | 5.10.2 | Test Framework |
| **Android SDK** | API 36 | Platform mobile |

---

## 📊 Métricas de Qualidade

### **Execução Atual:**
- ✅ **Taxa de Sucesso:** 100%
- ✅ **Cenários Funcionais:** 2/2
- ✅ **Steps Implementados:** 8/8
- ✅ **Page Objects:** 4/4
- ✅ **Build Status:** SUCCESS

### **Cobertura de Teste:**
- ✅ Autenticação
- ✅ Navegação entre telas
- ✅ Adição de produtos ao carrinho
- ✅ Processo de checkout
- ✅ Preenchimento de formulários
- ✅ Finalização de compra
- ✅ Limpeza de sessão

---

## 🎭 Dados de Teste

### **Credenciais:**
- **Usuário:** `standard_user`
- **Senha:** `secret_sauce`

### **Produto Testado:**
- **Nome:** Sauce Labs Backpack
- **Seletor:** `test-Item title`

### **Dados Fictícios:**
- **Nome:** João
- **Sobrenome:** Silva
- **CEP:** 01234-567

---

## 🔍 Debugging e Logs

### **Logs Disponíveis:**
```bash
# Logs detalhados durante execução
🎬 INICIANDO CENÁRIO: [Nome do cenário]
🚀 Inicializando sessão única do Appium...
🔑 Fazendo login inicial...
✅ Login inicial bem-sucedido!
🛒 Produto adicionado ao carrinho: Sauce Labs Backpack
💳 Checkout iniciado com sucesso!
📝 Dados fictícios preenchidos!
🏁 FINISH executado com sucesso!
✅ CENÁRIO PASSOU: [Nome do cenário]
```

### **Relatórios:**
- **Surefire Reports:** `target/surefire-reports/`
- **Allure Results:** `target/allure-results/` (se configurado)

---

## 🚨 Troubleshooting

### **Problemas Comuns e Soluções:**

#### **1. Appium não conecta**
```bash
# Verificar se Appium está rodando
curl http://localhost:4723/wd/hub/status

# Restart Appium
pkill -f appium
appium
```

#### **2. Emulador não detectado**
```bash
# Listar dispositivos
adb devices

# Restart ADB
adb kill-server
adb start-server
```

#### **3. App não encontrado**
```bash
# Verificar se app está instalado
adb shell pm list packages | grep swaglabs

# Instalar app se necessário
adb install path/to/swaglabs.apk
```

#### **4. Testes falham esporadicamente**
- Aumentar tempos de espera em `TestHooks.java`
- Verificar se dispositivo tem recursos suficientes
- Limpar dados do app: `adb shell pm clear com.swaglabsmobileapp`

---

## 🔮 Roadmap Futuro

### **Melhorias Planejadas:**
- [ ] Implementar tags Cucumber (@smoke, @regression)
- [ ] Adicionar mais cenários de teste
- [ ] Integrar com Allure Reports
- [ ] Implementar execução paralela
- [ ] Adicionar testes de performance
- [ ] Configurar CI/CD pipeline

### **Cenários Adicionais:**
- [ ] Teste de logout/login
- [ ] Validação de produtos específicos
- [ ] Remoção de itens do carrinho
- [ ] Validação de campos obrigatórios
- [ ] Testes de diferentes usuários

---

## 📞 Suporte

### **Contato:**
- **Projeto:** Automação Mobile SauceLabs
- **Última Atualização:** 17 de outubro de 2025
- **Status:** ✅ Produção - Todos os cenários funcionando

### **Documentação Adicional:**
- Ver arquivos de especificação na raiz do projeto
- Consultar comentários no código fonte
- Verificar logs de execução para debugging

---

**🎉 PROJETO FUNCIONANDO 100% - PRONTO PARA USO! 🎉**