package tabuleiro;

public class Pecas {

    protected Posicao posicao;
    private Tabuleiro tabuleiro;

    public Pecas(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        posicao = null;
    }

    protected Tabuleiro getTabuleiro() {
        return this.tabuleiro;
    }

}
