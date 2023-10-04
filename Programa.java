import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import xadrez.ExcecaoDeXadrez;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.XadrezPosicao;

public class Programa {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
        List<PecaDeXadrez> capturada = new ArrayList<>();

        while (!partidaDeXadrez.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.printPartida(partidaDeXadrez, capturada);
                System.out.println();
                System.out.print("Posição de Origem: ");
                XadrezPosicao origem = UI.readXadrezPosicao(sc);

                boolean[][] possivelMovimento = partidaDeXadrez.possivelMovimento(origem);
                UI.clearScreen();
                UI.printTabuleiro(partidaDeXadrez.getPecas(), possivelMovimento);
                System.out.println();
                System.out.print("Posição de Destino: ");
                XadrezPosicao destino = UI.readXadrezPosicao(sc);

                PecaDeXadrez pecaCapturada = partidaDeXadrez.performaceDeMovimento(origem, destino);

                if (pecaCapturada != null) {
                    capturada.add(pecaCapturada);
                }

                if (partidaDeXadrez.getPromover() != null) {
                    System.out.println(" Entre com a Peça a ser promovida (B/C/T/Q): ");
                    String type = sc.nextLine();
                    partidaDeXadrez.substituirPecaPromovida(type);
                }

            } catch (ExcecaoDeXadrez e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.clearScreen();
        UI.printPartida(partidaDeXadrez, capturada);
    }
}