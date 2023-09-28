package tabuleiro;

public class Board {

    private int linhas;
    private int colunas;
    private Piece[][] pecas;

    public Board(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Piece[linhas][colunas];
    }

    public int getLinhas() {
        return this.linhas;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public int getColunas() {
        return this.colunas;
    }

    public void setColunas(int colunas) {
        this.colunas = colunas;
    }

}
