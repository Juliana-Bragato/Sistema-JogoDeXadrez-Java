package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import tabuleiro.Pecas;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.PecasXadrez.Peao;
import xadrez.PecasXadrez.Rainha;
import xadrez.PecasXadrez.Rei;
import xadrez.PecasXadrez.Torre;
import xadrez.PecasXadrez.Bispo;
import xadrez.PecasXadrez.Cavalo;

public class PartidaDeXadrez {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkMate;
    private PecaDeXadrez passanteVuneravel;
    private PecaDeXadrez promover;

    private List<Pecas> pecasNoTabuleiro = new ArrayList<>();
    private List<Pecas> pecasCapturada = new ArrayList<>();

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

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public PecaDeXadrez getPassanteVuneravel() {
        return passanteVuneravel;
    }

    public PecaDeXadrez getPromover() {
        return promover;
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
        Pecas pecaCapturada = FazerMover(origem, destino);

        if (testeCheck(jogadorAtual)) {
            desfazerMovimento(origem, destino, pecaCapturada);
            throw new ExcecaoDeXadrez("Você não pode se colocar em Xeque.");
        }

        PecaDeXadrez PecaMovida = (PecaDeXadrez) tabuleiro.pecas(destino);

        //Specialmove promotion
        promover = null;
        if(PecaMovida instanceof Peao){
            if((PecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0) || (PecaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7)){
                promover = (PecaDeXadrez)tabuleiro.pecas(destino);
                promover = substituirPecaPromovida ("Q");
            }
        }

        check = (testeCheck(oponente(jogadorAtual))) ? true : false;

        if (testeCheckMate(oponente(jogadorAtual))) {
            checkMate = true;
        } else {
            proximoTurno();
        }

        // #Specialmove en passant
        if (PecaMovida instanceof Peao
                && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
            passanteVuneravel = PecaMovida;
        } else {
            passanteVuneravel = null;
        }

        return (PecaDeXadrez) pecaCapturada;
    }

    public PecaDeXadrez substituirPecaPromovida(String type){
        if( promover == null){
            throw new IllegalStateException("Não á peça a ser promovida.");
        }
        if(!type.equals("B") && !type.equals("C") && !type.equals("T") & !type.equals("Q")){
           return promover;
        }

        Posicao pos = promover.getXadrezPosicao().toPosicao();
        Pecas p = tabuleiro.removePecas(pos);
        pecasNoTabuleiro.remove(p);

        PecaDeXadrez novaPeca = novaPeca(type, promover.getCor());
        tabuleiro.colocarPecas(novaPeca, pos);
        pecasNoTabuleiro.add(novaPeca);

        return novaPeca;
        
    }

    private PecaDeXadrez novaPeca(String type, Cor cor){
        if(type.equals("B")) return new Bispo(tabuleiro, cor);
         if(type.equals("C")) return new Cavalo(tabuleiro, cor);
          if(type.equals("Q")) return new Rainha(tabuleiro, cor);
           return new Torre(tabuleiro, cor);
    }

    private Pecas FazerMover(Posicao origem, Posicao destino) {
        PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removePecas(origem);
        p.acrescentarContagemMovimento();
        Pecas pecaCapturada = tabuleiro.removePecas(destino);
        tabuleiro.colocarPecas(p, destino);

        if (pecasCapturada != null) {
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturada.add(pecaCapturada);
        }

        // #specialmove castling kingside rook
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePecas(origemT);
            tabuleiro.colocarPecas(torre, destinoT);
            torre.acrescentarContagemMovimento();
        }

        // #specialmove castling queenside rook
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePecas(origemT);
            tabuleiro.colocarPecas(torre, destinoT);
            torre.acrescentarContagemMovimento();
        }

        // #Specialmove en passant
        if (p instanceof Peao) {
            if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
                Posicao peaoPosicao;
                if (p.getCor() == Cor.BRANCO) {
                    peaoPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
                } else {
                    peaoPosicao = new Posicao(destino.getLinha() - 1, destino.getColuna());
                }
                pecaCapturada = tabuleiro.removePecas(peaoPosicao);
                pecasCapturada.add(pecaCapturada);
                pecasNoTabuleiro.remove(pecaCapturada);
            }
        }

        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Pecas pecaCapturada) {
        PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removePecas(destino);
        p.retirarContagemMovimento();
        tabuleiro.colocarPecas(p, origem);

        if (pecaCapturada != null) {
            tabuleiro.colocarPecas(pecaCapturada, destino);
            pecasCapturada.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }

        // #specialmove castling kingside rook
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePecas(destinoT);
            tabuleiro.colocarPecas(torre, origemT);
            torre.retirarContagemMovimento();
        }

        // #specialmove castling queenside rook
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePecas(destinoT);
            tabuleiro.colocarPecas(torre, origemT);
            torre.retirarContagemMovimento();
        }

        // #Specialmove en passant
        if (p instanceof Peao) {
            if (origem.getColuna() != destino.getColuna() && pecaCapturada == passanteVuneravel) {
                PecaDeXadrez peao = (PecaDeXadrez) tabuleiro.removePecas(destino);
                Posicao peaoPosicao;
                if (p.getCor() == Cor.BRANCO) {
                    peaoPosicao = new Posicao(3, destino.getColuna());
                } else {
                    peaoPosicao = new Posicao(4, destino.getColuna());
                }
                tabuleiro.colocarPecas(peao, peaoPosicao);
            }
        }

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

    private Cor oponente(Cor cor) {
        return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private PecaDeXadrez Rei(Cor cor) {
        List<Pecas> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
                .collect(Collectors.toList());
        for (Pecas p : list) {
            if (p instanceof Rei) {
                return (PecaDeXadrez) p;
            }
        }

        throw new IllegalStateException(" Não existe Rei " + cor + " no tabuleiro");
    }

    private boolean testeCheck(Cor cor) {
        Posicao reiPosicao = Rei(cor).getXadrezPosicao().toPosicao();
        List<Pecas> oponentePecas = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == oponente(cor))
                .collect(Collectors.toList());
        for (Pecas p : oponentePecas) {
            boolean[][] mat = p.possivelMovimento();
            if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testeCheckMate(Cor cor) {
        if (!testeCheck(cor)) {
            return false;
        }
        List<Pecas> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
                .collect(Collectors.toList());
        for (Pecas p : list) {
            boolean[][] mat = p.possivelMovimento();
            for (int i = 0; i < tabuleiro.getLinhas(); i++) {
                for (int j = 0; j < tabuleiro.getColunas(); j++) {
                    if (mat[i][j]) {
                        Posicao origem = ((PecaDeXadrez) p).getXadrezPosicao().toPosicao();
                        Posicao destino = new Posicao(i, j);
                        Pecas pecaCapturada = FazerMover(origem, destino);
                        boolean testeCheck = testeCheck(cor);
                        desfazerMovimento(origem, destino, pecaCapturada);
                        if (!testeCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void coloqueNovaPecas(char coluna, int linha, PecaDeXadrez peca) {
        tabuleiro.colocarPecas(peca, new XadrezPosicao(coluna, linha).toPosicao());
        pecasNoTabuleiro.add(peca);
    }

    private void initialSetup() {
        coloqueNovaPecas('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        coloqueNovaPecas('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        coloqueNovaPecas('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        coloqueNovaPecas('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
        coloqueNovaPecas('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
        coloqueNovaPecas('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        coloqueNovaPecas('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        coloqueNovaPecas('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        coloqueNovaPecas('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        coloqueNovaPecas('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        coloqueNovaPecas('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        coloqueNovaPecas('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        coloqueNovaPecas('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        coloqueNovaPecas('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        coloqueNovaPecas('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        coloqueNovaPecas('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

        coloqueNovaPecas('a', 8, new Torre(tabuleiro, Cor.PRETO));
        coloqueNovaPecas('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        coloqueNovaPecas('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        coloqueNovaPecas('d', 8, new Rainha(tabuleiro, Cor.PRETO));
        coloqueNovaPecas('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
        coloqueNovaPecas('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        coloqueNovaPecas('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
        coloqueNovaPecas('h', 8, new Torre(tabuleiro, Cor.PRETO));
        coloqueNovaPecas('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
        coloqueNovaPecas('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
        coloqueNovaPecas('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
        coloqueNovaPecas('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
        coloqueNovaPecas('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
        coloqueNovaPecas('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
        coloqueNovaPecas('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
        coloqueNovaPecas('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
    }
}
