package xadrez.PecasXadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

    private PartidaDeXadrez partidaDeXadrez;

    public Rei(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
        super(tabuleiro, cor);
        this.partidaDeXadrez = partidaDeXadrez;
    }

    @Override
    public String toString() {
        return "R";
    }

    private boolean podeMover(Posicao posicao) {
        PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().pecas(posicao);
        return p == null || p.getCor() != getCor();
    }

    private boolean testTorreCastling(Posicao posicao) {
        PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().pecas(posicao);
        return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContagemMovimento() == 0;
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
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // nordeste
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // sudoeste
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // sudeste
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // #Movimento Especial Castling
        if (getContagemMovimento() == 0 && !partidaDeXadrez.getCheck()) {
            // #specialmove castling kingside rook
            Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if (testTorreCastling(posT1)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                if (getTabuleiro().pecas(p1) == null && getTabuleiro().pecas(p2) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true;

                }
            }
            // #specialmove castling queenside rook
            Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if (testTorreCastling(posT2)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
                if (getTabuleiro().pecas(p1) == null && getTabuleiro().pecas(p2) == null && getTabuleiro().pecas(p3) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() - 2] = true;

                }
            }
        }
        return mat;
    }
}
