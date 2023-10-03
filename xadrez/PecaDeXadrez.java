package xadrez;

import tabuleiro.Pecas;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Pecas {

    private Cor cor;
    private int contagemMovimento;

    public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public int getContagemMovimento() {
        return contagemMovimento;
    }

    public void acrescentarContagemMovimento() {
        contagemMovimento++;
    }

    public void retirarContagemMovimento() {
        contagemMovimento--;
    }

    public XadrezPosicao getXadrezPosicao() {
        return XadrezPosicao.Aposicao(posicao);
    }

    protected boolean existePecaInimiga(Posicao posicao) {
        PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().pecas(posicao);
        return p != null && p.getCor() != cor;
    }

}
