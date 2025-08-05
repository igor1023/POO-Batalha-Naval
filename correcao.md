# Correção

(gostei da seção Conclusão do readme, parabéns)

- Implementação
  - código está no pacote org.example, o que não é boa prática. use pacotes com nomes significativos
  - ainda existem algumas constantes literais no código (cores, Navio.obtemTamanho)
  - por que algumas classes têm construtor público vazio? isso não é bom

## Diagrama de classes

- setas inválidas para diagramas UML
- multiplicidades ausentes
- membros estáticos precisam ser sublinhados
- nomes de classe no plural não estão certos: Estatisticas, Constantes
- uma classe de constantes é uma solução prática...
  - mas não é necessário que todo objeto tenha um atributo que referencia um objeto da classe Constantes
  - muita duplicação e espaço de memória usado
  - basta deixar todos os atributos estáticos e usar uma associação de dependência
- Associações ausentes:
  - se Grade.desenharNavio usa um objeto Navio, existe uma associção entre essas classes
  - se Grade.desenharCirculo usa um objeto Coordenada, existe uma associação entre essas classes
  - ao adicionar essas associações, fica claro o alto acoplamento da solução proposta
- se cada Partida possui 2 objetos Player, e cada objeto Player tem um objeto Estatisticas...
  - qual desses dois objetos Estatisticas é usado pela Partida para executar mostrar Estatisticas?
  - será que Estatisticas não deveria estar associado à Partida?

## Requisitos Funcionais
- [x] Posicionamento manual
- [x] Posicionamento aleatório
- [x] Disparo manual
- [x] Disparo aleatório
- [x] Interface gráfica em tempo real
- [x] Informações no terminal durante o jogo
- [x] Informar o vencedor da partida
- [x] Reiniciar partidas com alternação de jogador inicial
- [x] Estatísticas das partidas

## Requisitos Técnicos
- [x] Java + Gradle
- [x] Ausência de constantes literais
- [x] .gitignore apropriado
- [x] Instruções de uso no readme.md
- [x] Captura de tela no readme.md
- [x] Execução com ./gradlew run

## Nota

9.5
