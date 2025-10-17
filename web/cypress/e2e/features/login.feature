
Feature: E-commerce Web (Cypress)

  Scenario: Cenário 1 - Validar login e fechar site
    Given que acesso a aplicação web
    And informo credenciais válidas
    Then devo ver a home responsiva
    And fecho o navegador

  Scenario: Cenário 2 - Adicionar e remover item do carrinho
    Given que estou logado na aplicação
    When adiciono um item no carrinho
    And abro o carrinho
    And removo o item do carrinho
    Then o carrinho deve estar vazio

  Scenario: Cenário 3 - Fluxo completo de compra
    Given que estou logado na aplicação
    When adiciono um item no carrinho
    And abro o carrinho
    And clico em checkout
    And preencho dados de entrega
    And clico em continue
    And finalizo a compra
    And clico em back home
    Then devo estar na home
