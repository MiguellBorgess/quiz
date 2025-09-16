import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal que inicializa o jogo no terminal.
 * Responsável por coletar os dados iniciais e iniciar o fluxo do jogo.
 */
public class Main {
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        // Exibe o título do jogo
        System.out.println("╔════════════════════════════╗");
        System.out.println(" ║    🎉 QUIZ TERMINAL 🎉   ║");
        System.out.println("╚════════════════════════════╝");

        // Cadastro do Game Master
        System.out.print("Nome do Game Master: ");
        String nomeGM = lerNaoVazio();
        GameMaster gm = new GameMaster(nomeGM);

        // Cadastro da quantidade de jogadores (mínimo 2)
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

        // Cadastro dos jogadores
        List<Jogador> jogadores = new ArrayList<>();
        for (int i = 1; i <= qtdJogadores; i++) {
            System.out.print("Nome do jogador " + i + ": ");
            String nome = lerNaoVazio();
            jogadores.add(new Jogador(nome));
        }

        // Criação e início do jogo
        Jogo jogo = new Jogo(gm, jogadores);
        jogo.iniciar();
    }

    /**
     * Lê uma entrada do usuário garantindo que não esteja vazia.
     *
     * @return String não vazia
     */
    private static String lerNaoVazio() {
        while (true) {
            String s = SC.nextLine().trim();
            if (!s.isBlank()) return s;
            System.out.print("Entrada vazia. Digite novamente: ");
        }
    }
}
