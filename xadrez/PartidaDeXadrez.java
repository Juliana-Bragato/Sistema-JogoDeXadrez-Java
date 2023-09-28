package xadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.PecasXadrez.Rei;
import xadrez.PecasXadrez.Torre;

public class PartidaDeXadrez {
    
    private Tabuleiro tabuleiro;

    public PartidaDeXadrez(){
        tabuleiro = new Tabuleiro(8, 8);
        initialSetup();
    }

    public PecaDeXadrez[][] getPecas(){
        PecaDeXadrez[][] mat= new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i=0; i<tabuleiro.getLinhas(); i++){
            for (int j=0; j<tabuleiro.getColunas(); j++){
                mat[i][j] = (PecaDeXadrez) tabuleiro.pecas(i, j);
            }
        }
        return mat;
    }
    private void initialSetup(){
        tabuleiro.colocarPecas(new Torre(tabuleiro, Cor.BRANCO), new Posicao(2, 1));
        tabuleiro.colocarPecas(new Rei(tabuleiro, Cor.PRETO ), new Posicao(0, 4));
        tabuleiro.colocarPecas(new Rei(tabuleiro, Cor.BRANCO ), new Posicao(7, 4));
    }
}
