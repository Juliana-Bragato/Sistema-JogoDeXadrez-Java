package xadrez.PecasXadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

    private PartidaDeXadrez partidaDeXadrez;

    public Peao(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
        super(tabuleiro, cor);
        this.partidaDeXadrez = partidaDeXadrez;
    }

    @Override
    public boolean[][] possivelMovimento() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        if (getCor() == Cor.BRANCO) {
            p.setValores(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPecas(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() - 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPecas(p) && getTabuleiro().posicaoExistente(p2)
                    && !getTabuleiro().temPecas(p2) && getContagemMovimento() == 0) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExistente(p) && existePecaInimiga(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExistente(p) && existePecaInimiga(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // #Especialmove en passant white
            if (posicao.getLinha() == 3) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExistente(esquerda) && existePecaInimiga(esquerda)
                        && getTabuleiro().pecas(esquerda) == partidaDeXadrez.getPassanteVuneravel()) {
                    mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
                }
                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExistente(direita) && existePecaInimiga(direita)
                        && getTabuleiro().pecas(direita) == partidaDeXadrez.getPassanteVuneravel()) {
                    mat[direita.getLinha() - 1][direita.getColuna()] = true;
                }
            }
        } else

        {
            p.setValores(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPecas(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() + 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPecas(p) && getTabuleiro().posicaoExistente(p2)
                    && !getTabuleiro().temPecas(p2) && getContagemMovimento() == 0) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExistente(p) && existePecaInimiga(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExistente(p) && existePecaInimiga(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // #Especialmove en passant black
            if (posicao.getLinha() == 4) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExistente(esquerda) && existePecaInimiga(esquerda)
                        && getTabuleiro().pecas(esquerda) == partidaDeXadrez.getPassanteVuneravel()) {
                    mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
                }
                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExistente(direita) && existePecaInimiga(direita)
                        && getTabuleiro().pecas(direita) == partidaDeXadrez.getPassanteVuneravel()) {
                    mat[direita.getLinha() + 1][direita.getColuna()] = true;
                }
            }
        }
        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}