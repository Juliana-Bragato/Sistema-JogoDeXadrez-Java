package xadrez;

import tabuleiro.Pecas;
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

}
