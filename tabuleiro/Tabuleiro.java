package tabuleiro;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private Pecas[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 1 || colunas < 1) {
            throw new ExcecaoDeTabuleiro(
                    "Erro ao criar o tabuleiro: é necessário que tenha pelo menos 1 linha e 1 coluna. ");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Pecas[linhas][colunas];
    }

    public int getLinhas() {
        return this.linhas;
    }

    public int getColunas() {
        return this.colunas;
    }

    public Pecas pecas(int linhas, int colunas) {
        if (!posicaoExistente(linhas, colunas)) {
            throw new ExcecaoDeTabuleiro("Essa posição não existe no tabuleiro.");
        }
        return pecas[linhas][colunas];
    }

    public Pecas pecas(Posicao posicao) {
        if (!posicaoExistente(posicao)) {
            throw new ExcecaoDeTabuleiro("Essa posição não existe no tabuleiro.");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }

    public void colocarPecas(Pecas peca, Posicao posicao) {
        if (temPecas(posicao)) {
            throw new ExcecaoDeTabuleiro("Já existe uma peça nessa posição " + posicao);
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.posicao = posicao;
    }

    private boolean posicaoExistente(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public boolean posicaoExistente(Posicao posicao) {
        return posicaoExistente(posicao.getLinha(), posicao.getColuna());
    }

    public boolean temPecas(Posicao posicao) {
        if (!posicaoExistente(posicao)) {
            throw new ExcecaoDeTabuleiro("Essa posição não existe no tabuleiro.");
        }
        return pecas(posicao) != null;
    }
}
