package xadrez.PecasXadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Bispo extends PecaDeXadrez {

    public Bispo(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean[][] possivelMovimento() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        // noroeste
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPecas(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() - 1, p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExistente(p) && existePecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // nordeste
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPecas(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() - 1, p.getColuna() + 1);

        }
        if (getTabuleiro().posicaoExistente(p) && existePecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // sudeste
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPecas(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() + 1, p.getColuna() + 1);

        }
        if (getTabuleiro().posicaoExistente(p) && existePecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // sudoeste
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPecas(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() + 1, p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExistente(p) && existePecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}
