package org.example;

import java.util.ArrayList;
import java.util.List;

public class Estatisticas{

    // atributos do Objeto
    private int numeroVitorias;

    // Atributos da classe
    // pensando em um campeonato, com várias Partidas, fiz uma
    // lista que guarda todos os jogadores
    static final private List<Player> players = new ArrayList<Player>();

    public Estatisticas(){

        numeroVitorias = 0;

    }

    public void incrementaVitorias(){

        this.numeroVitorias++;

    }

    public int getNumeroVitorias(){

        return this.numeroVitorias;

    }

    static public void adicionarNaLista(Player p){

        // Como player p é criado no Do/While do App.java,
        // crio um cópia dele em pAux e adiciono pAux, caso 
        // seja uma outra pessoa jogando
        
        // Se o player jogar novamente e colocar o mesmo nome, significa
        // que é a mesma pessoa jogando. Entao as vitorias deste segundo jogador
        // ao jogar novamente sao transferidas/incrementadas ao objeto criado anteriormente
        // que já está nesta lista

        // Se for um nome diferente, significa que é uma outra pessoa jogando,
        // portanto, outro objeto jogador.
        
        for(Player player : players){

            if(p.getNome().equals(player.getNome())){

                player.getEstatisticas().numeroVitorias += p.getEstatisticas().numeroVitorias;
                return;
            }

        }

        // Se chegar aqui, é um outro Jogador, então adiciono ele na lista
        Player pAux = p;
        players.add(pAux);

    }

    static public List<Player> getListaPlayers(){

        return players;

    }

}