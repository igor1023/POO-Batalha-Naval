// Classe que representa uma Partida

package org.example;

import java.util.List;
import java.util.Scanner;

public class Partida {

    private final Constantes constante = new Constantes();
    private final Scanner teclado = new Scanner(System.in);
    private Player pc;
    private Player humano;
    private int numeroPartida = 0;

    public Partida(){
        
    }

    public void executar() {

        // pc representa o COMPUTADOR/ROBÔ
        // humano representa o JOGADOR PESSOA FÍSICA

        mostrarInstrucoes();        
        this.pc = new Player(false);
        Estatisticas.adicionarNaLista(pc);

        boolean jogarNovamente;

        do { 

            this.numeroPartida++;
            int numeroRodada = 1;

            this.humano = obterDadosJogador();
            mostraRodadaNoDraw(numeroPartida, numeroRodada);

            // reseto os navios e tiros do pc para casos em que se quer jogar de novo
            pc.resetar();

            posicionarNaviosHumano();

            // inicialmente ninguem perdeu
            boolean alguemPerdeu = false;

            // Eu fiz este IF abaixo pelo seguinte caso:
            // Quando o computador começa e ele acertou todos meus navios,
            // eu não tenho o direito de atirar depois dele após a queda de todos meus navios.
            // A partida já deve acabar...
            // Vamos supor que o computador começa atirando e ele afundou os meus navios, 
            // Ao final desta partida, o Computador terá realizado X tiros e o jogador X-1 tiros
            // e vice-versa

            // E eu também defini que em partida ímpares o computador começa.
            // Já a pessoa começará nas partidas pares.

            if(numeroPartida % 2 == 0){

                while(! alguemPerdeu){

                    mostraRodadaNoDraw(numeroPartida, numeroRodada);
                    System.out.println("|| Rodada " + numeroRodada);
                    alguemPerdeu = jogadorComeca(humano, pc, teclado);
                    
                    System.out.println("||=============================================");
                    numeroRodada++;

                }

            } else {

                while(! alguemPerdeu){

                    mostraRodadaNoDraw(numeroPartida, numeroRodada);
                    System.out.println("|| Rodada " + numeroRodada);
                    alguemPerdeu = computadorComeca(humano, pc, teclado);

                    System.out.println("||=============================================");
                    numeroRodada++;

                }
            }

            String vencedor = vencedor();
            mostrarVencedor(vencedor);
            Estatisticas.adicionarNaLista(humano);
            jogarNovamente = jogarDeNovo(teclado);

            humano.getTela().close();

        } while(jogarNovamente);

        mostrarEstatisticas();
    }

    public void encerrar(){
        
        System.out.println("|| Obrigado por jogar!! Até mais 👋");
        System.out.println("|| Encerrando...");
        teclado.close();

    }

    static private void mostrarInstrucoes(){
                               
        System.out.println("\n=========== Jogo - Batalha Naval ===========");
        System.out.println("|| Caso entrar com alguma coordenada incorreta (ultrapassa limite do tabuleiro");
        System.out.println("|| ou valores indevidos), o jogo lhe solciitará as coordenadas para o mesmo navio");
        System.out.println("|| novamente.");

    }

    private Player obterDadosJogador() {

        System.out.print("\n|| Pressione ENTER para iniciar o jogo...");
        teclado.nextLine();

        System.out.print("|| Entre com o seu nome: ");
        String nome = teclado.nextLine();

        return new Player(true, nome);
    }

    // para deixar a impressão no terminal mais intuitiva referente às coordenadas
    static private String intToAscii(int n){

        // a forma que encontrei de converter um numero para um char
        // maiusculo. Aritmética similar as que eu utilizava em C.
        char c = (char) ('A' + n);
        return String.valueOf(c);

    }

    private void mostraRodadaNoDraw(int p, int r) {

        humano.getTela().setTitle("Partida: " + p + " | Rodada: " + r);

    }

    private void posicionarNaviosHumano() {

        System.out.println("\n=============================================");
        System.out.println("|| VAMOS POSICIONAR SEUS NAVIOS");
        System.out.println("=============================================");

        // Vamos obter as coordenadas de cada um dos cinco navios
        for(int i = 1; i <= constante.QUANTIDADE_NAVIOS; i++){

            Coordenada coNi = new Coordenada();
            boolean orientacaoNi;
            // Aqui eu vou criar o navio se as coordenadas são válidas
            // de acordo com a orientacao

            do{

                System.out.println("|| Navio " + i + ": " + constante.NOMES_NAVIOS[i-1]);
                System.out.println("|| Quantidade de casas para este navio: " + Navio.getTamanhoNavio(i));
                String coordenada;

                do {

                    System.out.printf("|| Entre com a coordenada Inicial (Ex.: A0): ");
                    coordenada = teclado.nextLine();

                } while(! coordenadaInicialValida(coordenada));

                // Para garantir uma letra MAIUSCULA e evitar erros,
                // Vou pegar o primeiro caractere na string de entrada COORDENADA,
                // convertê-lo para maiusculo
                int linha = coordenada.toUpperCase().charAt(0) - 'A';
                int coluna = Integer.parseInt(coordenada.substring(1));

                coNi.setLinha(linha);
                coNi.setColuna(coluna);

                System.out.print("|| Orientação (h = horizontal, outro = vertical): ");
                String orientacao = teclado.nextLine();
                
                orientacaoNi = ! orientacao.toLowerCase().equals("h");

                System.out.println("=============================================");

            } while (! Navio.validaPosicoes(coNi, orientacaoNi, i, humano.getCoordenadasFrota()));

            Navio navioNi = new Navio(i, orientacaoNi, coNi);
            humano.adicionarNavio(navioNi);
        }
    }

    private void mostrarVencedor(String str) {
       
        humano.getTela().clear();
        humano.getGradeNavios().escreverTexto(humano.getTela(), "Vencedor: " + str, 
            constante.LARGURA / 2, constante.ALTURA / 2, constante.FONTE);

        if(humano.perdeu())
            System.out.println("|| Você perdeu... ☠️");
        else System.out.println("|| Você ganhou!!! 🥇");
    }

    private String vencedor() {

        if(humano.perdeu()){

            pc.getEstatisticas().incrementaVitorias();
            return pc.getNome();

        }

        humano.getEstatisticas().incrementaVitorias();

        return humano.getNome();
    }

    static private void mostrarEstatisticas(){

        List<Player> players = Estatisticas.getListaPlayers();

        for(Player p : players)
            System.out.println("|| " + p.getNome() + " - Vitórias: " + p.getEstatisticas().getNumeroVitorias());

        String str = maiorVencedor(players);
        System.out.println("|| Jogador com mais vitórias: " + str);

    }

    static private String maiorVencedor(List<Player> players){

        int i = -1;
        String str = "";
        Player pAux = new Player();
        for(Player p : players){

            if(p.getEstatisticas().getNumeroVitorias() > i){
                
                i = p.getEstatisticas().getNumeroVitorias();
                str = p.getNome() + " 🏆";
                pAux = p; // para verificar empate

            }
        }

        // neste ponto, tenho em STR o nome do jogador com mais vitorias
        // porém pode ocorrer de termos dois ou mais jogadores com a 
        // mesma quantidade de vitorias, então o verificarei abaixo

        // se existe empate de vitorias, quer dizer que jogadores de nomes diferentes
        // possuem o mesmo numero de vitorias
        for(Player p : players){

            if(p.getEstatisticas().getNumeroVitorias() == 
               pAux.getEstatisticas().getNumeroVitorias() && ! p.getNome().equals(pAux.getNome()))
                str = "empate 🤝";

        }

        return str;

    }

    static private boolean coordenadaInicialValida(String str){

        // No meu caso, vou somente verificar se a string é composta por alguma letra
        // e se o usuario entrou com pelo menos DOIS caracteres
        if(str.isEmpty() || str.isBlank() || str.length() < 2)
            return false;

        for(int i = 1; i < str.length(); i++){

            if(str.charAt(i) < '0' || str.charAt(i) > '9')
                return false;

        }

        return true;

    }

    static private boolean jogarDeNovo(Scanner teclado){

        String resposta;

        do { 
            
            System.out.printf("|| Deseja jogar novamente?\n");
            System.out.printf("|| \"S\" - Sim\n");
            System.out.printf("|| \"N\" - Não\n");
            System.out.printf("|| > ");

            resposta = teclado.nextLine();

        } while (! resposta.toUpperCase().equals("S") && ! resposta.toUpperCase().equals("N"));

        // retorna true se resposta for S = jogar novamente
        // retorna false se resposta for N (caso contrario) = nao quer mais jogar
        return resposta.equals("S");

    }

    // método a ser chamada em partidas pares
    static private boolean jogadorComeca(Player player, Player pc, Scanner teclado){

        Coordenada coordJog = new Coordenada();

        do { 
            
            String coordenada;

            do {

                System.out.printf("|| Entre com a coordenada Inicial (Ex.: A0): ");
                coordenada = teclado.nextLine();

            } while(! coordenadaInicialValida(coordenada));
            
            // Para garantir uma letra MAIUSCULA e evitar erros,
            // Vou pegar o primeiro caractere na string de entrada COORDENADA,
            // convertê-lo para maiusculo
            int linha = coordenada.toUpperCase().charAt(0) - 'A';
            coordJog.setLinha(linha);

            // A coluna pega o resto da entrada e converto para numero                
            int coluna = Integer.parseInt(coordenada.substring(1));
            coordJog.setColuna(coluna);

        } while (player.tiroRepetiu(coordJog) || ! Coordenada.coordenadaValida(coordJog));

        player.registrarTiro(coordJog);

        if(pc.acertou(coordJog, player)){
         
            System.out.println("|| Você acertou");

            String navioQueAfundou = pc.getNomeNavioQueAfundou(coordJog);
            if(navioQueAfundou != null)
                System.out.println("|| Você afundou o " + navioQueAfundou + " do COMPUTADOR! 😎");
            
            if(pc.perdeu())
                return true;

        } else System.out.println("|| Você errou");
            
        Coordenada coordPC = pc.escolherAlvo();
        System.out.println("|| Jogada COMPUTADOR: " + (intToAscii(coordPC.getLinha())) + coordPC.getColuna());

        if(player.acertou(coordPC, pc)){ // true = pc acertou
         
            System.out.println("|| COMPUTADOR acertou");
            String navioQueAfundou = player.getNomeNavioQueAfundou(coordPC);

            if(navioQueAfundou != null)
                System.out.println("|| O COMPUTADOR afundou o seu navio " + navioQueAfundou + " 😱");

            if(player.perdeu())
                return true;

        } else System.out.println("|| COMPUTADOR errou");

        System.out.printf("|| Pressione ENTER para jogar a próxima rodada.");        
        teclado.nextLine();

        return false;
    }

    // método a ser chamada em partidas ímpares
    static private boolean computadorComeca(Player player, Player pc, Scanner teclado){

        Coordenada coordPC = pc.escolherAlvo();
        System.out.println("|| Jogada COMPUTADOR: " + (intToAscii(coordPC.getLinha())) + coordPC.getColuna());
        
        if(player.acertou(coordPC, pc)){ // true = pc acertou

            System.out.println("|| COMPUTADOR acertou");
            String navioQueAfundou = player.getNomeNavioQueAfundou(coordPC);

            if(navioQueAfundou != null)
                System.out.println("|| O COMPUTADOR afundou o seu navio " + navioQueAfundou + " 😱");

            if(player.perdeu())
                return true;

        } else System.out.println("|| COMPUTADOR errou");

        Coordenada coordJog = new Coordenada();

        do { 
            
            String coordenada;

            do {

                System.out.printf("|| Entre com a coordenada Inicial (Ex.: A0): ");
                coordenada = teclado.nextLine();

            } while(! coordenadaInicialValida(coordenada));
            
            // Para garantir uma letra MAIUSCULA e evitar erros,
            // Vou pegar o primeiro caractere na string de entrada COORDENADA,
            // convertê-lo para maiusculo
            int linha = coordenada.toUpperCase().charAt(0) - 'A';
            coordJog.setLinha(linha);

            // A coluna pega o resto da entrada e converto para numero                
            int coluna = Integer.parseInt(coordenada.substring(1));
            coordJog.setColuna(coluna);

        } while (player.tiroRepetiu(coordJog) || ! Coordenada.coordenadaValida(coordJog));

        player.registrarTiro(coordJog);

        if(pc.acertou(coordJog, player)){
         
            System.out.println("|| Você acertou");

            String navioQueAfundou = pc.getNomeNavioQueAfundou(coordJog);
            
            if(navioQueAfundou != null)
                System.out.println("|| Você afundou o " + navioQueAfundou + " do COMPUTADOR! 😎");

            if(pc.perdeu())
                return true;

        } else System.out.println("|| Você errou");

        System.out.printf("|| Pressione ENTER para jogar a próxima rodada.");
        teclado.nextLine();

        return false;
    }

}