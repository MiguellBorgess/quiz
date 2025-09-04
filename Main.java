import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════╗");
        System.out.println(" ║    🎉 QUIZ TERMINAL 🎉   ║");
        System.out.println("╚════════════════════════════╝");


        // Game Master
        System.out.print("Nome do Game Master: ");
        String nomeGM = lerNaoVazio();
        GameMaster gm = new GameMaster(nomeGM);

        // Jogadores (mínimo 2)
        int qtdJogadores = 0;
        while (qtdJogadores < 2) {
            System.out.print("Quantidade de jogadores (>=2): ");
            String line = SC.nextLine().trim();
            try {
                qtdJogadores = Integer.parseInt(line);
                if (qtdJogadores < 2) {
                    System.out.println("É necessário pelo menos 2 jogadores.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            }
        }

        List<Jogador> jogadores = new ArrayList<>();
        for (int i = 1; i <= qtdJogadores; i++) {
            System.out.print("Nome do jogador " + i + ": ");
            String nome = lerNaoVazio();
            jogadores.add(new Jogador(nome));
        }

        // Cria e inicia o jogo (5 rodadas; perguntas inseridas pelo GM a cada rodada)
        Jogo jogo = new Jogo(gm, jogadores);
        jogo.iniciar(); // o método já pedirá as perguntas de cada rodada no terminal
    }

    private static String lerNaoVazio() {
        while (true) {
            String s = SC.nextLine().trim();
            if (!s.isBlank()) return s;
            System.out.print("Entrada vazia. Digite novamente: ");
        }
    }
}