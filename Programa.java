import java.util.Scanner;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.XadrezPosicao;

public class Programa {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();

        while (true) {
            UI.printTabuleiro(partidaDeXadrez.getPecas());
            System.out.println();
            System.out.print("Posição de Origem: ");
            XadrezPosicao origem = UI.readXadrezPosicao(sc);

            System.out.println();
            System.out.print("Posição de Destino: ");
            XadrezPosicao destino = UI.readXadrezPosicao(sc);
            
            PecaDeXadrez pecaCapturada = partidaDeXadrez.performaceDeMovimento(origem, destino);
        }
    }
}