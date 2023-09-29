package xadrez.PecasXadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

    public Rei(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "R";
    }

    private boolean podeMover(Posicao posicao) {
        PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().pecas(posicao);
        return p == null || p.getCor() != getCor();
    }

    @Override
    public boolean[][] possivelMovimento() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        // em Cima
        p.setValores(posicao.getLinha() - 1, posicao.getColuna());
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // em Baixo
        p.setValores(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // esquerda
        p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // direita
        p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

         // noroeste
        p.setValores(posicao.getLinha() -1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // nordeste
        p.setValores(posicao.getLinha() -1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // sudoeste
        p.setValores(posicao.getLinha() +1, posicao.getColuna() -1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // sudeste
        p.setValores(posicao.getLinha() +1, posicao.getColuna() +1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }


        return mat;
    }

}
