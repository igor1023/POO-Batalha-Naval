package org.example;

import java.util.ArrayList;
import java.util.List;

public class Navio {
    
    /*  
        Lista de navios do jogo Batalha Naval

        Navio            | Tamanho (casas) | Símbolo | Nº do navio para criar no código (App)
        Porta-aviões     |      5          |    P    |               1
        Encouraçado      |      4          |    E    |               2
        Cruzador         |      3          |    C    |               3
        Submarino        |      3          |    S    |               4
        Contratorpedeiro |      2          |    N    |               5
    */
    
    // Atributos do Objeto
    private String simbolo;
    private String nomeNavio;
    private int tamanho;
    private boolean orientacao; // true = vertical e false = horizontal
    private int quantidadeTirosSofridos;
    private List<Coordenada> coordenadasNavio;
    
    static private final Constantes constante = new Constantes();

    public Navio(int numeroSimbolo, boolean orientacao, Coordenada pos0){

        if(validaEntrada(numeroSimbolo)){

            this.simbolo = constante.NAVIOS[numeroSimbolo - 1];
            this.nomeNavio = constante.NOMES_NAVIOS[numeroSimbolo - 1];
            this.tamanho = obtemTamanho(simbolo);
            this.orientacao = orientacao;
            //this.coordenadasAtingidas = new ArrayList<Coordenada>();
            
        } else System.out.println("Entradas inválidas");

        this.quantidadeTirosSofridos = 0;
        this.coordenadasNavio = setPosicoes(pos0, orientacao, numeroSimbolo);
    }

    // Vou verificar se existe alguma posicao inválida
    // com base na posicao inicial inserida pelo usuario e a 
    // orientacao do objeto Navio
    // Este método é utilizado para verificar se posso ou não
    // posso criar um navio com tais coordenadas e orientação
    public static boolean validaPosicoes(Coordenada pos, boolean orientacao, int numeroSimbolo, List<Coordenada> coordenadas){

        String simbolo = constante.NAVIOS[numeroSimbolo - 1];

        if(orientacao) { // vertical
        
            for(int i = 0; i < obtemTamanho(simbolo); i++){

                Coordenada coord = new Coordenada(pos.getLinha() + i, pos.getColuna());
                if(! Coordenada.coordenadaValida(coord))   
                    return false;

                if(existeSobreposicao(coordenadas, coord))
                    return false;

            }
        
        } else { // horizontal

            for(int j = 0; j < obtemTamanho(simbolo); j++){

                Coordenada coord = new Coordenada(pos.getLinha(), pos.getColuna() + j);
                if(! Coordenada.coordenadaValida(coord))
                    return false;

                if(existeSobreposicao(coordenadas, coord))
                    return false;
                    
            }

        }

        return true;
    }

    private static boolean existeSobreposicao(List<Coordenada> coordenadas, Coordenada coord){

        // verificando sobreposicao: se alguma coordenada é igual
        // a uma outra coordenada existente
        for(Coordenada coAux : coordenadas){

            if(Coordenada.coordenadasIguais(coAux, coord))
                return true;
                        
            }
        
        return false;
    }

    public static int getTamanhoNavio(int len){

        String simbolo = constante.NAVIOS[len - 1];
        return obtemTamanho(simbolo);

    }

    public int getTamanho(){

        return this.tamanho;

    }

    private List<Coordenada> setPosicoes(Coordenada pos, boolean orientacao, int numeroSimbolo){

        String simbolo = constante.NAVIOS[numeroSimbolo - 1];
        List<Coordenada> coordenadasNovas = new ArrayList<Coordenada>();

        if(orientacao) { //vertical
        
            for(int i = 0; i < obtemTamanho(simbolo); i++){

                Coordenada co = new Coordenada(pos.getLinha() + i, pos.getColuna());
                coordenadasNovas.add(co);
                
            }
        
        } else { // horizontal

            for(int j = 0; j < obtemTamanho(simbolo); j++){

                Coordenada co = new Coordenada(pos.getLinha(), pos.getColuna() + j);
                coordenadasNovas.add(co);
            }

        }
        
        return coordenadasNovas;
    }

    public String getNome(){

        return this.nomeNavio;

    }

    public List<Coordenada> getCoordenadas(){

        return this.coordenadasNavio;

    }

    public void sofrerTiro(Coordenada coord){

        this.quantidadeTirosSofridos++;

    }

    public boolean afundou(){

        // se um navio de X posicoes sofrer X tiros, entao
        // significa que ele afundou
        return this.quantidadeTirosSofridos == this.tamanho;

    }

    private static boolean validaEntrada(int num){

        // está entre 1 e 5 para utilizarmos aquele vetor
        // constante NAVIOS
        return num >= 1 && num <= constante.QUANTIDADE_NAVIOS;

    }

    private static int obtemTamanho(String simbolo){

        return switch(simbolo){

            case "P" -> 5;
            case "E" -> 4;
            case "C" -> 3;
            case "S" -> 3;
            case "N" -> 2;
            default  -> 0; 

        };

    }

    public boolean getOrientacao(){

        return this.orientacao;

    }

    // public void desenhar(Draw tab, Grade grade){

    //     double x0 = grade.getCX();
    //     double y0 = grade.getCY();
    //     List<Coordenada> coord = getCoordenadas();

    //     for(int i = 0; i < this.tamanho; i++){

    //         double cx, cy;

    //         if(this.orientacao){ // true é vertical

    //             cx = x0 + coord.get(i).getColuna() * constante.PIXEL + (constante.PIXEL / 2.0);
    //             cy = y0 + (coord.get(i).getLinha()) * constante.PIXEL + (constante.PIXEL / 2.0);

    //         }
            
    //         else { // false é horizontal

    //             cx = x0 + (coord.get(i).getColuna()) * constante.PIXEL + (constante.PIXEL / 2.0);
    //             cy = y0 + coord.get(i).getLinha() * constante.PIXEL + (constante.PIXEL / 2.0);

    //         }

    //         tab.setPenColor(Color.red);
    //         tab.filledSquare(cx, cy, constante.PIXEL / 2.0);

    //         tab.setPenColor(Color.black);
    //         tab.square(cx, cy, constante.PIXEL / 2.0) ;
                        
    //     }

    // }

}