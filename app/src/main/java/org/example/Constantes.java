// Classe com declaração de constantes

package org.example;

public class Constantes {

    // Constantes relacionadas a GRADE
    public final int LARGURA = 1000;
    public final int ALTURA = 600;
    public final int FONTE = 30;
    public final double PIXEL = 40.0;
    public final int INICIO_ESCALA = 0;
    public final int LIMITE_TAB = 10; // Limite do tabuleiro (10 x 10)

    // Representam as coordenadas onde serão desenhadas
    // as grades no tabuleiro
    public final int X_GRADE_NAVIO = 50;
    public final int Y_GRADE_NAVIO = 100; 
    public final int X_GRADE_ALVOS = 550;
    public final int Y_GRADE_ALVOS = 100; 
    
    // Constantes relacionadas a NAVIO
    public final int QUANTIDADE_NAVIOS = 5;
    public final String[] NAVIOS = {"P", "E", "C", "S", "N"};
    public final String[] NOMES_NAVIOS = {"Porta-aviões", "Encouraçado", "Cruzador",
                                          "Submarino", "Contratorpedeiro"};

    // apenas para instanciar um objeto da classe Constantes                                          
    public Constantes(){

    }

}