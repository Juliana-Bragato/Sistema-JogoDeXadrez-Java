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

        while (true) {
            try {
                UI.clearScreen();
                UI.printTabuleiro(partidaDeXadrez.getPecas());
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
            } catch (ExcecaoDeXadrez e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }
}