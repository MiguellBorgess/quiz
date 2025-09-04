import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Jogo {
    private static final Scanner SC = new Scanner(System.in);

    private final GameMaster gameMaster;
    private final List<Jogador> jogadores;
    private int rodadaAtual = 0; // máximo 5

    public Jogo(GameMaster gameMaster, List<Jogador> jogadores) {
        this.gameMaster = Objects.requireNonNull(gameMaster, "GameMaster não pode ser nulo");
        if (jogadores == null || jogadores.size() < 2) {
            throw new IllegalArgumentException("Devem existir pelo menos 2 jogadores.");
        }
        this.jogadores = new ArrayList<>(jogadores);
    }

    public void iniciar() {
        System.out.println("\n==== INÍCIO DO JOGO ====");
        for (int r = 1; r <= 5; r++) {
            this.rodadaAtual = r;
            System.out.println("\n---- RODADA " + r + " ----");
            System.out.println("(GM " + gameMaster.getNome() + ") Cadastre 3 perguntas:");

            Rodada rodada = new Rodada(jogadores);

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

            // Executa a rodada: todos os jogadores respondem às MESMAS 3 perguntas sequencialmente. :contentReference[oaicite:2]{index=2}
            rodada.iniciar();
        }

        exibirResultadoFinal();
    }

    private static String lerLinhaObrigatoria(String prompt) {
        System.out.print(prompt);
        while (true) {
            String s = SC.nextLine().trim();
            if (!s.isBlank()) return s;
            System.out.print("Entrada vazia. Tente novamente: ");
        }
    }

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
     * Critério final:
     * 1) Maior pontuação total; 2) Menor número de pulos totais.
     * Se ainda empatar, retorna lista com >1 jogadores (empate completo). :contentReference[oaicite:3]{index=3}
     */
    public List<Jogador> verificarVencedorFinal() {
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
        if (candidatos.size() == 1) return candidatos;

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
        return finalistas; // pode ter 1 (vencedor) ou >1 (empate completo)
    }

    public void exibirResultadoFinal() {
        System.out.println("\n==== RESULTADO FINAL ====");
        jogadores.stream()
                .sorted(Comparator.comparingInt(Jogador::getPontuacaoTotal).reversed())
                .forEach(j -> System.out.printf("- %s | Pontos: %d | Pulos totais: %d%n",
                        j.getNome(), j.getPontuacaoTotal(), j.getPulosTotal()));

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



    public int getRodadaAtual() {
        return rodadaAtual;
    }
}