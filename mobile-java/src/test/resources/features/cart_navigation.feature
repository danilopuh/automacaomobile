
# language: pt
Funcionalidade: Carrinho (Java)
  
  Cenário: Validação de login simples
    Dado que estou autenticado (Java)
    Então o aplicativo é fechado (Java)
  
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
