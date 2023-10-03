package xadrez;

import tabuleiro.Pecas;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Pecas {

    private Cor cor;

    public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public XadrezPosicao getXadrezPosicao(){
        return XadrezPosicao.Aposicao(posicao);
    }

    protected boolean existePecaInimiga(Posicao posicao){
        PecaDeXadrez p =(PecaDeXadrez)getTabuleiro().pecas(posicao);
        return p != null && p.getCor() != cor;
    }

}
