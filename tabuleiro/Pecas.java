package tabuleiro;

public abstract class Pecas {

    protected Posicao posicao;
    private Tabuleiro tabuleiro;

    public Pecas(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        posicao = null;
    }

    protected Tabuleiro getTabuleiro() {
        return this.tabuleiro;
    }

    public abstract boolean[][] possivelMovimento();

    public boolean possivelMovimento(Posicao posicao) {
        return possivelMovimento()[posicao.getLinha()][posicao.getColuna()];
    }

    public boolean umMovimentoPossivel() {
        boolean[][] mat = possivelMovimento();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if (mat[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
