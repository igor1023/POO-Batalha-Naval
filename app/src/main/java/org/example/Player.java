// Classe genérica para Jogador
// tanto para o humano quanto para o computador
package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.Draw;

public class Player {

    // Atributos do objeto
    private String nome;
    private boolean humano; // TRUE = HUMANO | FALSE = COMPUTADOR
    private Draw tela;
    private Grade navios;
    private Grade alvos;
    private List<Navio> frota;
    private List<Coordenada> coordenadasNavios; // coordenadas de cada navio
    private boolean[][] frotaNaviosFlag;
    // frotaNaviosFlag é uma matriz 10x10 que servirá de flag:
    // posicao com TRUE indica que tem um navio
    // posicao com FALSE indica que não há navio (ou que eventualmente
    // a posição pertencia a algum navio, mas foi atingida)

    private Estatisticas estatisticas;
    private List<Coordenada> tirosDisparados; //guarda tiros disparados

    static private final Constantes constante = new Constantes();
    static private Random aleatorio;

    // para objetos auxiliares
    public Player(){

    }

    public Player(boolean humano) {

        this(humano, humano ? "USER" : "COMPUTADOR");

    }

    public Player(boolean humano, String nome) {

        this.humano = humano;
        prepararJogador(nome);

    }

    private void prepararJogador(String nome) {

        this.nome = nome.toUpperCase();
        this.estatisticas = new Estatisticas();
        this.frotaNaviosFlag = new boolean[constante.LIMITE_TAB][constante.LIMITE_TAB]; // por padrao tudo é FALSE
        this.tirosDisparados = new ArrayList<Coordenada>();
        this.coordenadasNavios = new ArrayList<Coordenada>();

        if (this.humano) { // se for HUMANO

            this.frota = new ArrayList<Navio>();
            this.prepararTela();
            this.prepararGradeNavios();
            this.prepararGradeAlvos();

        } else { // se for COMPUTADOR

            aleatorio = new Random();
            this.frota = posicionarNaviosComputador();

        }

    }

    public void resetar() {

        // resetar tabuleiro
        for (int i = 0; i < constante.LIMITE_TAB; i++) 
            for (int j = 0; j < constante.LIMITE_TAB; j++) 
                this.frotaNaviosFlag[i][j] = false;
            
        // resetar/limpar as listas abaixo para serem
        // reutilizadas na nova partida
        this.frota.clear();
        this.coordenadasNavios.clear();
        this.tirosDisparados.clear();

        if (! this.humano) 
            this.frota = posicionarNaviosComputador();

    }

    private List<Navio> posicionarNaviosComputador() {

        // Este método é utilizado pelo COMPUTADOR

        List<Navio> navios = new ArrayList<Navio>();

        // começo do primeiro até o quinto navio
        for (int i = 1; i <= constante.QUANTIDADE_NAVIOS; i++) {

            Coordenada coNi;
            boolean orientacaoNi;

            // Vou sortear uma coordenada válida
            do {

                coNi = Coordenada.sorteiaCoordenada(constante.LIMITE_TAB, constante.LIMITE_TAB);

                orientacaoNi = aleatorio.nextBoolean();

            } while (!Navio.validaPosicoes(coNi, orientacaoNi, i, this.coordenadasNavios));

            // Se chegou aqui, a coordenada (e orientacao) é válida.
            // Então eu crio o navio, adiciono ele na lista de navios
            // do Playerm adiciono as coordenadas do navio na lista 
            // coordenadasNavios e, por fim, marco os navios na matriz 
            // boolean flag

            Navio navioNi = new Navio(i, orientacaoNi, coNi);
            navios.add(navioNi); // adiciona a lista FROTA

            for (Coordenada co : navioNi.getCoordenadas()) 
                this.coordenadasNavios.add(co);

            marcarNavios(navioNi); // adiciona na matriz flag
        }

        return navios;
    }

    public Estatisticas getEstatisticas() {

        return this.estatisticas;

    }

    private void prepararTela() {

        // Preparar tela do HUMANO

        this.tela = new Draw();
        this.tela.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        // Ajusta o tamanho da tela usando algum método do objeto Draw
        this.tela.setCanvasSize(constante.LARGURA, constante.ALTURA);

        // Ajusta a escala X usando algum outro método do objeto Draw
        this.tela.setXscale(constante.INICIO_ESCALA, constante.LARGURA);

        // Ajusta a escala Y usando ainda outro método do objeto Draw
        this.tela.setYscale(constante.INICIO_ESCALA, constante.ALTURA);

        this.tela.setTitle("");

    }

    public void prepararGradeNavios() {

        if (this.humano) { // se for HUMANO

            this.navios = new Grade(constante.X_GRADE_NAVIO, constante.Y_GRADE_NAVIO);
            
            // Coloquei valores arbitrários no método abaixo até ficar em uma posição
            // visualmente adequada.
            this.navios.escreverTexto(tela, "Navios", constante.LARGURA / 2 - 250, 
                                      constante.ALTURA - 75, constante.FONTE / 2);
            this.navios.desenharTabuleiro(tela);

        }

    }

    public void prepararGradeAlvos() {

        if (this.humano) { // se for HUMANO

            this.alvos = new Grade(constante.X_GRADE_ALVOS, constante.Y_GRADE_ALVOS);

            // Coloquei valores arbitrários no método abaixo até ficar em uma posição
            // visualmente adequada.
            this.navios.escreverTexto(tela, "Alvos", (constante.LARGURA / 2 + 250),
                                     constante.ALTURA - 75, constante.FONTE / 2);

            this.alvos.desenharTabuleiro(tela);

        }
    }

    public Coordenada escolherAlvo() {

        if (!  this.humano) { // se for COMPUTADOR

            Coordenada co;
            // enquanto a coordenada alvo sorteada estiver na lista de tiros
            // ja disparados, eu sorteio outra coordenada. Se for uma coordenada
            // inédita, eu a adiciono nesta lista e retorno esta coordenada.

            do {

                // ja gera uma posicao que nao ultrapassa os limites
                co = Coordenada.sorteiaCoordenada(constante.LIMITE_TAB, constante.LIMITE_TAB);

            } while (tiroRepetiu(co));

            this.tirosDisparados.add(co);

            return co;
        }

        return null;

    }

    public void mostrarTabuleiro() {

        for (Navio n : this.frota) {
            this.navios.desenharNavio(tela, n);
        }

        this.tela.show();

    }

    public String getNome() {

        return this.nome;

    }

    public Grade getGradeNavios() {

        return this.navios;

    }

    public Grade getGradeAlvos() {

        return this.alvos;

    }

    public List<Navio> getFrota() {

        return this.frota;

    }

    public String getNomeNavioQueAfundou(Coordenada coord) {

        // para cada navio da minha frota
        // vou verificar cada coordenada de cada navio,
        // se alguma coordenada de algum navio for igual a coordenada alvo
        // eu dou um tiro no navio e vejo se ele afundou:
        // se afundou, retorno o nome dele
        for (Navio n : this.getFrota()) {

            for (Coordenada co : n.getCoordenadas()) {

                if (Coordenada.coordenadasIguais(co, coord)) {

                    // o navio sofre um tiro e verifico se ele afundou
                    n.sofrerTiro(coord);

                    return n.afundou() ? n.getNome() : null;

                }
            }

        }

        return null;
    }

    public List<Coordenada> getCoordenadasFrota() {

        return this.coordenadasNavios;

    }

    public Draw getTela() {

        return this.tela;

    }

    public void adicionarNavio(Navio n) {

        this.frota.add(n);

        for (Coordenada co : n.getCoordenadas()) 
            this.coordenadasNavios.add(co);

        this.navios.desenharNavio(tela, n);
        marcarNavios(n);
    }

    private void marcarNavios(Navio n) {

        for (Coordenada co : n.getCoordenadas())
            frotaNaviosFlag[co.getLinha()][co.getColuna()] = true;

    }

    // acertou um disparo
    public boolean acertou(Coordenada co, Player player) {

        if (this.humano) { // se for HUMANO (desenho na grade NAVIOS)

            if (frotaNaviosFlag[co.getLinha()][co.getColuna()]) {

                this.navios.desenharCirculoCinza(tela, navios, co);
                this.frotaNaviosFlag[co.getLinha()][co.getColuna()] = false; //nao tem mais navio nesta pos
                return true; // o Computador acertou o tiro

            }

            // o Computador errou o tiro
            this.alvos.desenharCirculoAzul(tela, navios, co);

            return false;

        } else { // se for COMPUTADOR (desenho na grade ALVOS)

            // se for verdadeiro o IF, significa
            // que há um navio nesta posicao
            if (this.frotaNaviosFlag[co.getLinha()][co.getColuna()]) {

                this.frotaNaviosFlag[co.getLinha()][co.getColuna()] = false;
                player.getGradeAlvos().desenharCirculoCinza(player.getTela(), player.getGradeAlvos(), co);
                return true;

            }

            player.getGradeAlvos().desenharCirculoAzul(player.getTela(), player.getGradeAlvos(), co);
            return false;

        }

    }

    // perderá quando toda a matriz de navio for FALSE
    public boolean perdeu() {

        for (int i = 0; i < constante.LIMITE_TAB; i++) {

            for (int j = 0; j < constante.LIMITE_TAB; j++) {

                // se for verdadeiro o IF, significa
                // que um navio nesta posicao
                if (this.frotaNaviosFlag[i][j])
                    return false;

            }

        }

        return true;

    }

    public boolean tiroRepetiu(Coordenada co) {

        // Aqui eu verifico se CO é uma coordenada
        // de um tiro já disparado

        for (Coordenada c : this.tirosDisparados) {

            if (Coordenada.coordenadasIguais(co, c)) {
                return true;
            }

        }

        return false;

    }

    public void registrarTiro(Coordenada co) {

        this.tirosDisparados.add(co);

    }

}
