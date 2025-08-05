// Classe que representa as coordendas do tabuleiro
// Como várias classes têm isso em comum, criei
// uma classe para representá-las

package org.example;

import java.util.Random;

public class Coordenada{
    
    private int coluna; // x
    private int linha; // y
    static private Random r = new Random();
    static private final Constantes constante = new Constantes();

    public Coordenada(){

        this(0, 0);

    }

    public Coordenada(int l, int c){

        this.linha = l;
        this.coluna = c;

    }

    public int getLinha(){

        return this.linha;
    
    }

    public int getColuna(){

        return this.coluna;
    
    }

    public void setLinha(int linha){

        this.linha = linha;

    }

    public void setColuna(int coluna){

        this.coluna = coluna;

    }

    static public Coordenada sorteiaCoordenada(int maxLinha, int maxColuna){

        return new Coordenada(r.nextInt(maxLinha), r.nextInt(maxColuna));

    }

    // retorna true se a coordenada estiver dentro do tabuleiro
    // retorna falso caso contrário
    static public boolean coordenadaValida(Coordenada co){

        // linha e coluna devem estar no intervalo [0, 9] ou [0, 10[
        return (co.getColuna() >= 0 && co.getColuna() < constante.LIMITE_TAB) && 
               (co.getLinha() >= 0 && co.getLinha() < constante.LIMITE_TAB);

    }

    // para comparar a coordenada objeto com outra coordenada
    public boolean ehIgual(Coordenada co){

        return this.getColuna() == co.getColuna() && this.getLinha() == co.getLinha();

    }

    // para comparar quaisquer coordenadas
    static public boolean coordenadasIguais(Coordenada coA, Coordenada coB){

        return coA.getLinha() == coB.getLinha() && coA.getColuna() == coB.getColuna();

    }
}