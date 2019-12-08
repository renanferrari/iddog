# IDDog

<img src="demo.gif" align="center" width=250>

## Introdução

Projeto desenvolvido como parte do [desafio iddog da IDwall](https://github.com/idwall/desafios-iddog). Consiste em um app que possui como principal funcionalidade a listagem de imagens de cachorros por raça.

## Decisões técnicas

O processo de tomada de decisão utilizado ao longo deste exercício foi pautado em compreender **o mínimo necessário** para se atingir os resultados esperados, considerando as limitações de tempo e o contexto ao qual o projeto se aplica.

Para isso, aspectos como a legibilidade e testabilidade do código foram bastante favorecidos em relação à outros aspectos como escabilidade e eficiência da solução, já que estes poderiam levar a otimizações prematuras baseadas em suposições. Além disso, também foi utilizada uma abordagem bastante centrada no usuário, o que levou à priorização de mudanças que causassem alterações visíveis no comportamento do aplicativo.

### Arquitetura

Para a arquitetura foi utilizado uma combinação entre MVVM para a camada de aplicação e visualização e uma arquitetura baseada em [Interaction Driven Design](https://vimeo.com/130256611) para a camada de domínio.

### UI

Para a interface, foi escolhida uma navegação por abas, dada que esta é a navegação mais indicada para grupos de conteúdo relacionados que estejam no mesmo nível de hierarquia, como explicado [na documentação de componentes](https://material.io/components/tabs/#usage) do Material Design.

## Bibliotecas

Das bibliotecas utilizadas no projeto, as que mais se destacam são as seguintes:

- **OkHttp** e **Retrofit**, para chamadas de rede. 
- **Koin**, para injeção de dependências.
- **Picasso**, para carregamento de imagens.
- **AndroidX Lifecycle**, para utilização de ViewModel e LiveData.
- **Mockito**, para a criação de mocks nos testes unitários.

## Instalação

Existem três formas de instalar esse projeto:

- Instalando o APK disponibilizado em [releases](https://github.com/renanferrari/iddog/releases).
- Usando a linha de comando: `./gradlew assembleDebug`.
- Usando o Android Studio.
