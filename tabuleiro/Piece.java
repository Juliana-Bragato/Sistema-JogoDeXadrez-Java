package tabuleiro;

public class Piece {

    protected Position posicao;
    private Board tabuleiro;

    public Piece(Board tabuleiro) {
        this.tabuleiro = tabuleiro;
        posicao = null;
    }

    protected Board getTabuleiro() {
        return this.tabuleiro;
    }

}
