import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Classe principal que gerencia o fluxo do jogo de perguntas e respostas.
 * Envolve o GameMaster, os jogadores, e até 5 rodadas com 3 perguntas cada.
 */
public class Jogo {
    // Scanner compartilhado para entrada de dados no terminal
    private static final Scanner SC = new Scanner(System.in);

    private final GameMaster gameMaster; // Responsável por cadastrar perguntas
    private final List<Jogador> jogadores; // Lista de jogadores participantes
    private int rodadaAtual = 0; // Número da rodada atual (máximo 5)

    /**
     * Construtor do jogo.
     *
     * @param gameMaster Instância do GameMaster
     * @param jogadores Lista com pelo menos 2 jogadores
     */
    public Jogo(GameMaster gameMaster, List<Jogador> jogadores) {
        this.gameMaster = Objects.requireNonNull(gameMaster, "GameMaster não pode ser nulo");

        if (jogadores == null || jogadores.size() < 2) {
            throw new IllegalArgumentException("Devem existir pelo menos 2 jogadores.");
        }

        // Cria uma cópia da lista para evitar modificações externas
        this.jogadores = new ArrayList<>(jogadores);
    }

    /**
     * Inicia o jogo com até 5 rodadas.
     * Em cada rodada, o GameMaster cadastra 3 perguntas e os jogadores respondem.
     */
    public void iniciar() {
        System.out.println("\n==== INÍCIO DO JOGO ====");

        for (int r = 1; r <= 5; r++) {
            this.rodadaAtual = r;
            System.out.println("\n---- RODADA " + r + " ----");
            System.out.println("(GM " + gameMaster.getNome() + ") Cadastre 3 perguntas:");

            Rodada rodada = new Rodada(jogadores);

            // Cadastro de 3 perguntas pelo GameMaster
            for (int i = 1; i <= 3; i++) {
                System.out.println("📢 Cadastro de pergunta");
                System.out.println("\n[Cadastro] Pergunta " + i + " da Rodada " + r);

                String enunciado = lerLinhaObrigatoria("Enunciado: ");

                String[] alternativas = new String[4];
                alternativas[0] = lerLinhaObrigatoria("Alternativa a): ");
                alternativas[1] = lerLinhaObrigatoria("Alternativa b): ");
                alternativas[2] = lerLinhaObrigatoria("Alternativa c): ");
                alternativas[3] = lerLinhaObrigatoria("Alternativa d): ");

                char correta = lerAlternativaCorreta();

                Pergunta p = gameMaster.criarPergunta(enunciado, alternativas, correta);
                rodada.adicionarPergunta(p);
            }

            // Inicia a rodada: todos os jogadores respondem às mesmas 3 perguntas
            rodada.iniciar();
        }

        // Exibe o resultado final após as 5 rodadas
        exibirResultadoFinal();
    }

    /**
     * Lê uma linha obrigatória do terminal, garantindo que não esteja vazia.
     */
    private static String lerLinhaObrigatoria(String prompt) {
        System.out.print(prompt);
        while (true) {
            String s = SC.nextLine().trim();
            if (!s.isBlank()) return s;
            System.out.print("Entrada vazia. Tente novamente: ");
        }
    }

    /**
     * Lê a letra da alternativa correta, garantindo que seja 'a', 'b', 'c' ou 'd'.
     */
    private static char lerAlternativaCorreta() {
        System.out.print("Letra da alternativa correta (a/b/c/d): ");
        while (true) {
            String s = SC.nextLine().trim();
            if (!s.isEmpty()) {
                char c = Character.toLowerCase(s.charAt(0));
                if (c >= 'a' && c <= 'd') return c;
            }
            System.out.print("Inválido. Digite 'a', 'b', 'c' ou 'd': ");
        }
    }

    /**
     * Verifica quem é o(s) vencedor(es) do jogo com base nos critérios:
     * 1. Maior pontuação total
     * 2. Menor número de pulos
     * 3. Empate completo se ainda houver empate
     *
     * @return Lista com 1 ou mais jogadores vencedores
     */
    public List<Jogador> verificarVencedorFinal() {
        // Primeiro critério: maior pontuação
        int maxPontos = jogadores.stream()
                .mapToInt(Jogador::getPontuacaoTotal)
                .max()
                .orElse(Integer.MIN_VALUE);

        List<Jogador> candidatos = new ArrayList<>();
        for (Jogador j : jogadores) {
            if (j.getPontuacaoTotal() == maxPontos) {
                candidatos.add(j);
            }
        }

        // Se só há um com maior pontuação, ele é o vencedor
        if (candidatos.size() == 1) return candidatos;

        // Segundo critério: menor número de pulos
        int minPulos = candidatos.stream()
                .mapToInt(Jogador::getPulosTotal)
                .min()
                .orElse(Integer.MAX_VALUE);

        List<Jogador> finalistas = new ArrayList<>();
        for (Jogador j : candidatos) {
            if (j.getPulosTotal() == minPulos) {
                finalistas.add(j);
            }
        }

        return finalistas; // Pode haver empate completo
    }

    /**
     * Exibe o placar final e o(s) vencedor(es) do jogo.
     */
    public void exibirResultadoFinal() {
        System.out.println("\n==== RESULTADO FINAL ====");

        // Exibe todos os jogadores ordenados por pontuação decrescente
        jogadores.stream()
                .sorted(Comparator.comparingInt(Jogador::getPontuacaoTotal).reversed())
                .forEach(j -> System.out.printf("- %s | Pontos: %d | Pulos totais: %d%n",
                        j.getNome(), j.getPontuacaoTotal(), j.getPulosTotal()));

        // Determina e exibe o(s) vencedor(es)
        List<Jogador> vencedores = verificarVencedorFinal();
        if (vencedores.size() == 1) {
            System.out.println("\n🏆 Vencedor do jogo: " + vencedores.get(0).getNome());
        } else {
            System.out.println("\n🤝 Jogo terminou em EMPATE entre:");
            for (Jogador j : vencedores) {
                System.out.println(" - " + j.getNome());
            }
        }

        System.out.println("==== FIM ====");
    }

    // Getter para a rodada atual (pode ser útil para exibição ou lógica externa)
    public int getRodadaAtual() {
        return rodadaAtual;
    }
}
