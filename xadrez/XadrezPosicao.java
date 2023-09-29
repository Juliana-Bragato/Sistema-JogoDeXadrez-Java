package xadrez;

import tabuleiro.Posicao;

public class XadrezPosicao {

    private char coluna;
    private int linha;

    public XadrezPosicao(char coluna, int linha) {
        if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
            throw new ExcecaoDeXadrez("Erro ao instanciar a Posição de Xadrez. Só é válido os valores a1 até h8.");
        }
        this.coluna = coluna;
        this.linha = linha;
    }

    public char getColuna() {
        return this.coluna;
    }

    public int getLinha() {
        return this.linha;
    }

    protected Posicao toPosicao() {
        return new Posicao(8 - linha, coluna - 'a');
    }

    protected static XadrezPosicao Aposicao(Posicao posicao) {
        return new XadrezPosicao((char) ('a' - posicao.getColuna()), 8 - posicao.getLinha());
    }

    @Override
    public String toString() {
        return "" + coluna + linha;

    }
}
