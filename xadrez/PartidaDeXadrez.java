package xadrez;

import tabuleiro.Pecas;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.PecasXadrez.Rei;
import xadrez.PecasXadrez.Torre;

public class PartidaDeXadrez {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;

    public PartidaDeXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        jogadorAtual = Cor.BRANCO;
        initialSetup();
    }

    public int getTurno() {
        return turno;
    }

    public Cor getJogadorAtual() {
        return jogadorAtual;
    }

    public PecaDeXadrez[][] getPecas() {
        PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                mat[i][j] = (PecaDeXadrez) tabuleiro.pecas(i, j);
            }
        }
        return mat;
    }

    public boolean[][] possivelMovimento(XadrezPosicao posicaoDeDestino) {
        Posicao posicao = posicaoDeDestino.toPosicao();
        validadePosicaoDeOrigem(posicao);
        return tabuleiro.pecas(posicao).possivelMovimento();
    }

    public PecaDeXadrez performaceDeMovimento(XadrezPosicao posicaoDeOrigem, XadrezPosicao posicaoDeDestino) {
        Posicao origem = posicaoDeOrigem.toPosicao();
        Posicao destino = posicaoDeDestino.toPosicao();
        validadePosicaoDeOrigem(origem);
        validadePosicaoDeDestino(origem, destino);
        Pecas capituraDePeca = FazerMover(origem, destino);
        proximoTurno();
        return (PecaDeXadrez) capituraDePeca;
    }

    private Pecas FazerMover(Posicao origem, Posicao destino) {
        Pecas p = tabuleiro.removePecas(origem);
        Pecas pecaCapturada = tabuleiro.removePecas(destino);
        tabuleiro.colocarPecas(p, destino);
        return pecaCapturada;
    }

    private void validadePosicaoDeOrigem(Posicao posicao) {
        if (!tabuleiro.temPecas(posicao)) {
            throw new ExcecaoDeXadrez("Não existe peça na posição de origem");
        }

        if (jogadorAtual != ((PecaDeXadrez) tabuleiro.pecas(posicao)).getCor()) {
            throw new ExcecaoDeXadrez("A peça escolhida não é sua.");
        }

        if (!tabuleiro.pecas(posicao).umMovimentoPossivel()) {
            throw new ExcecaoDeXadrez("Não existe movimentos possíveis para a peça escolhida.");
        }
    }

    private void validadePosicaoDeDestino(Posicao origem, Posicao destino) {
        if (!tabuleiro.pecas(origem).possivelMovimento(destino)) {
            throw new ExcecaoDeXadrez("A peça escolhida não pode se mover para a posição de destino.");
        }
    }

    private void proximoTurno() {
        turno++;
        jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private void coloqueNovaPecas(char coluna, int linha, PecaDeXadrez peca) {
        tabuleiro.colocarPecas(peca, new XadrezPosicao(coluna, linha).toPosicao());
    }

    private void initialSetup() {
        coloqueNovaPecas('c', 1, new Torre(tabuleiro, Cor.BRANCO));
        coloqueNovaPecas('c', 2, new Torre(tabuleiro, Cor.BRANCO));
        coloqueNovaPecas('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        coloqueNovaPecas('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        coloqueNovaPecas('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        coloqueNovaPecas('d', 1, new Rei(tabuleiro, Cor.BRANCO));

        coloqueNovaPecas('c', 7, new Torre(tabuleiro, Cor.PRETO));
        coloqueNovaPecas('c', 8, new Torre(tabuleiro, Cor.PRETO));
        coloqueNovaPecas('d', 7, new Torre(tabuleiro, Cor.PRETO));
        coloqueNovaPecas('e', 7, new Torre(tabuleiro, Cor.PRETO));
        coloqueNovaPecas('e', 8, new Torre(tabuleiro, Cor.PRETO));
        coloqueNovaPecas('d', 8, new Rei(tabuleiro, Cor.PRETO));
    }
}
