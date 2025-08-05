package org.example;

import java.awt.Color;
import java.util.List;

import edu.princeton.cs.algs4.Draw;

public class Grade{

    // Atributos do objeto
    private final double x0, y0;
    static private final Constantes constante = new Constantes();

    public Grade(double x, double y){

        this.x0 = x;
        this.y0 = y;

    }

    public double getCX(){

        return this.x0;

    }

    public double getCY(){

        return this.y0;

    }

    public void desenharTabuleiro(Draw grade){

        double cx, cy;

        for(int i = 0; i < constante.LIMITE_TAB; i++) {

            for(int j = 0; j < constante.LIMITE_TAB; j++){

                cy = y0 + i * constante.PIXEL + (constante.PIXEL / 2.0);
                cx = x0 + j * constante.PIXEL + (constante.PIXEL / 2.0);
                grade.setPenColor(Color.black); // Cor do tabuleiro é preto
                grade.square(cx, cy, (constante.PIXEL / 2.0));

            }
        }

        grade.setPenColor(Color.red); // A cor dos rótulos será vermelho
        
        inserirRotulos(grade);
    }

    private void inserirRotulos(Draw grade){

        char letra = 'A';
        char numero = '0';

        for(int i = 0; i < constante.LIMITE_TAB; i ++){
            
            double cx = this.x0 + i * constante.PIXEL + (constante.PIXEL / 2);
            // +20 pois draw posiciona square pelo seu centro cy
            // +20 representa a metade de cada lado do square (20 = metade de 40)
            // Multiplicar por 40 é o espaçamento entre cada square da grade

            grade.text(cx, this.y0 - 20, (numero++) + "", (int) (constante.PIXEL / 2)); // cast para nao rotacionar

        }

        for(int i = 0; i < constante.LIMITE_TAB; i ++){

            double cy = this.y0 + i * constante.PIXEL + (constante.PIXEL / 2);
            // +20 pois draw posiciona square pelo seu centro cy
            // +20 representa a metade de cada lado do square (20 = metade de 40)
            // Multiplicar por 40 é o espaçamento entre cada square da grade

            grade.text(this.x0 - (constante.PIXEL / 2), cy, (letra++) + "", (int) (constante.PIXEL / 2)); // cast para nao rotacionar

        }

        // Subtrair 20 em ambos grade.text para deslocar, respectivamente,
        // as letras para esquerda e os números para baixo;
        // isto é, deslocamento de 20 pixels para esquerda e para baixo.
        // Multiplicar por 40 é o espaçamento entre cada square da grade

    }

    private void desenharCirculo(Draw draw, Grade grade, Coordenada coord, Color cor){

        double x0 = grade.getCX(); // início em X da grade
        double y0 = grade.getCY(); // início em Y da grade

        double cx = x0 + coord.getColuna() * constante.PIXEL + (constante.PIXEL / 2.0); // coluna - eixo X
        double cy = y0 + coord.getLinha() * constante.PIXEL + (constante.PIXEL / 2.0);  // linha - eixo Y

        draw.setPenColor(cor);
        draw.filledCircle(cx, cy, constante.PIXEL / 4.0); // círculo menor

    }

    // marca erro no tabuleiro
    public void desenharCirculoAzul(Draw draw, Grade grade, Coordenada co) {
        
        desenharCirculo(draw, grade, co, Color.blue);

    }

    // marca acerto no tabuleiro
    public void desenharCirculoCinza(Draw draw, Grade grade, Coordenada co) {
       
        desenharCirculo(draw, grade, co, Color.gray);

    }

    public void escreverTexto(Draw draw, String str, int largura, int altura, int tamanhoFonte){

        draw.setPenColor(Color.black);
        draw.text((double) largura, (double) altura, str, tamanhoFonte);

    }

    public void desenharNavio(Draw tab, Navio n){

        double x0 = getCX();
        double y0 = getCY();
        List<Coordenada> coord = n.getCoordenadas();

        for(int i = 0; i < n.getTamanho(); i++){

            double cx, cy;

            if(n.getOrientacao()){ // true é vertical

                cx = x0 + coord.get(i).getColuna() * constante.PIXEL + (constante.PIXEL / 2.0);
                cy = y0 + (coord.get(i).getLinha()) * constante.PIXEL + (constante.PIXEL / 2.0);

            }
            
            else { // false é horizontal

                cx = x0 + (coord.get(i).getColuna()) * constante.PIXEL + (constante.PIXEL / 2.0);
                cy = y0 + coord.get(i).getLinha() * constante.PIXEL + (constante.PIXEL / 2.0);

            }

            tab.setPenColor(Color.red);
            tab.filledSquare(cx, cy, constante.PIXEL / 2.0);

            tab.setPenColor(Color.black);
            tab.square(cx, cy, constante.PIXEL / 2.0) ;
                        
        }

    }
}