# 📱 Especificação dos Cenários - Formato Gherkin

## 🎯 Feature: Carrinho de Compras Mobile

### **Descrição:**
Testes automatizados para validação do fluxo de e-commerce no aplicativo SauceLabs Demo App, cobrindo desde autenticação até finalização de compra.

---

## 📋 Cenários Implementados

```gherkin
# language: pt
Funcionalidade: Carrinho (Java)
  
  Como um usuário do aplicativo SauceLabs
  Eu quero realizar operações de e-commerce
  Para validar que o sistema funciona corretamente

  Contexto:
    Dado que o servidor Appium está rodando na porta 4723
    E que o emulador Android está conectado
    E que o aplicativo SauceLabs está instalado

  # CENÁRIO 1: Teste Rápido de Autenticação
  Cenário: Validação de login simples
    Dado que estou autenticado (Java)
    Então o aplicativo é fechado (Java)

  # CENÁRIO 2: Fluxo Completo de E-commerce
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

---

## 🔧 Steps Definitions Mapeados

### **Given/Dado Steps:**
| Step | Implementação | Status |
|------|---------------|---------|
| `que estou autenticado (Java)` | `autenticadoJava()` | ✅ Funcionando |

### **When/Quando Steps:**
| Step | Implementação | Status |
|------|---------------|---------|
| `adiciono um item no carrinho (Java)` | `adicionoUmItemNoCarrinhoJava()` | ✅ Funcionando |

### **And/E Steps:**
| Step | Implementação | Status |
|------|---------------|---------|
| `clico no carrinho (Java)` | `clicoNoCarrinhoJava()` | ✅ Funcionando |
| `clico no checkout (Java)` | `clicoNoCheckoutJava()` | ✅ Funcionando |
| `preencho os dados ficticios (Java)` | `preenchoDadosFicticiosJava()` | ✅ Funcionando |
| `clico em continue (Java)` | `clicoEmContinueJava()` | ✅ Funcionando |
| `rolo scroll para baixo e clico em finish (Java)` | `roloScrollParaBaixoEClicoEmFinishJava()` | ✅ Funcionando |
| `clico em back home (Java)` | `clicoEmBackHomeJava()` | ✅ Funcionando |
| `fecho o aplicativo (Java)` | `fechoAplicativoJava()` | ✅ Funcionando |

### **Then/Então Steps:**
| Step | Implementação | Status |
|------|---------------|---------|
| `o aplicativo é fechado (Java)` | `entaoAplicativoEFechadoJava()` | ✅ Funcionando |

---

## 📊 Cenários Expandidos com Dados

### **Cenário 1 Detalhado:**
```gherkin
Cenário: Validação de login simples
  Dado que estou autenticado (Java)
    # Executa login com:
    # Usuário: standard_user
    # Senha: secret_sauce
    # Valida tela de produtos
  Então o aplicativo é fechado (Java)
    # Fecha app via ADB command
    # Limpa sessão para próximo teste
    
  # Tempo estimado: ~40 segundos
  # Taxa de sucesso: 100%
```

### **Cenário 2 Detalhado:**
```gherkin
Cenário: Fluxo completo de compra - do produto ao checkout
  Dado que estou autenticado (Java)
    # Login: standard_user / secret_sauce
    # Validação: Tela de produtos carregada
    
  Quando adiciono um item no carrinho (Java)
    # Produto: "Sauce Labs Backpack"
    # Ação: Clique em ADD TO CART
    # Validação: Produto adicionado
    
  E clico no carrinho (Java)
    # Seletor: test-Cart
    # Ação: Navegar para carrinho
    # Validação: Carrinho aberto
    
  E clico no checkout (Java)
    # Seletor: test-CHECKOUT
    # Ação: Iniciar processo checkout
    # Validação: Tela de dados carregada
    
  E preencho os dados ficticios (Java)
    # Nome: João
    # Sobrenome: Silva
    # CEP: 01234-567
    # Validação: Campos preenchidos
    
  E clico em continue (Java)
    # Seletor: test-CONTINUE
    # Ação: Avançar para resumo
    # Validação: Tela de resumo carregada
    
  E rolo scroll para baixo e clico em finish (Java)
    # Ação: Scroll para encontrar botão
    # Seletor: test-FINISH
    # Ação: Finalizar pedido
    # Validação: Pedido processado
    
  E clico em back home (Java)
    # Seletor: BACK HOME
    # Ação: Retornar à tela inicial
    # Validação: Tela produtos carregada
    
  Então fecho o aplicativo (Java)
    # ADB: force-stop com.swaglabsmobileapp
    # Limpeza: Sessão encerrada
    
  # Tempo estimado: ~3-4 minutos
  # Taxa de sucesso: 100%
```

---

## 🎭 Cenários de Teste Possíveis (Futuros)

### **Cenários Adicionais Sugeridos:**

```gherkin
Cenário: Validação de produto específico
  Dado que estou autenticado (Java)
  Quando clico no produto "Sauce Labs Bolt T-Shirt"
  Então vejo os detalhes do produto
  E o aplicativo é fechado (Java)

Cenário: Remoção de item do carrinho
  Dado que estou autenticado (Java)
  E adiciono um item no carrinho (Java)
  Quando clico no carrinho (Java)
  E removo o item do carrinho
  Então o carrinho está vazio
  E o aplicativo é fechado (Java)

Cenário: Validação de dados obrigatórios
  Dado que estou autenticado (Java)
  E adiciono um item no carrinho (Java)
  E clico no carrinho (Java)
  E clico no checkout (Java)
  Quando clico em continue sem preencher dados
  Então vejo mensagem de erro de campos obrigatórios
  E o aplicativo é fechado (Java)

Cenário: Logout e novo login
  Dado que estou autenticado (Java)
  Quando faço logout do aplicativo
  E faço login novamente
  Então estou na tela de produtos
  E o aplicativo é fechado (Java)
```

---

## 🚀 Como Executar os Cenários

### **Comando Maven:**
```bash
# Todos os cenários
mvn test -Dtest=RunCucumberTest

# Apenas validações
mvn test -Dtest=ValidationTest
```

### **Execução Individual:**
```bash
# Modificar o arquivo .feature para comentar cenários:
# Para executar apenas Cenário 1:
# Comentar linhas do Cenário 2 com #

# Para executar apenas Cenário 2:
# Comentar linhas do Cenário 1 com #
```

### **Com Tags (Futuro):**
```gherkin
@smoke
Cenário: Validação de login simples

@regression @e2e
Cenário: Fluxo completo de compra
```

---

## 📈 Métricas de Execução

| Cenário | Tempo Médio | Taxa Sucesso | Última Execução |
|---------|-------------|--------------|-----------------|
| Login Simples | ~40s | 100% ✅ | 17/10/2025 00:02 |
| Fluxo Completo | ~3-4min | 100% ✅ | 17/10/2025 00:02 |
| **TOTAL** | **~4-5min** | **100% ✅** | **Build Success** |

---

## 🔍 Troubleshooting

### **Problemas Comuns:**
1. **Appium não iniciado:** Verificar porta 4723
2. **Emulador desconectado:** Verificar `adb devices`
3. **App não instalado:** Verificar package `com.swaglabsmobileapp`
4. **Timeout:** Aumentar tempos de espera se necessário

### **Soluções Automáticas Implementadas:**
- ✅ Recuperação de sessão perdida
- ✅ Re-login automático quando necessário  
- ✅ Fallback para elementos alternativos
- ✅ Limpeza automática entre cenários

---

**Status Atual:** ✅ **TODOS OS CENÁRIOS ESPECIFICADOS E FUNCIONANDO**

*Especificação criada em: 17 de outubro de 2025*