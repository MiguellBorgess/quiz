import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa uma rodada do jogo, contendo 3 perguntas e os jogadores participantes.
 * Cada jogador responde às mesmas perguntas, e ao final é possível determinar o vencedor da rodada.
 */
public class Rodada {
    // Lista de perguntas da rodada (máximo 3)
    private final List<Pergunta> perguntas = new ArrayList<>(3);

    // Lista imutável de jogadores participantes da rodada
    private final List<Jogador> jogadores;

    /**
     * Construtor da rodada.
     *
     * @param jogadores Lista de jogadores (mínimo 2)
     */
    public Rodada(List<Jogador> jogadores) {
        if (jogadores == null || jogadores.size() < 2) {
            throw new IllegalArgumentException("Devem existir pelo menos 2 jogadores.");
        }

        // Cria uma cópia imutável da lista de jogadores
        this.jogadores = Collections.unmodifiableList(new ArrayList<>(jogadores));
    }

    /**
     * Adiciona uma pergunta à rodada.
     * Só é permitido adicionar até 3 perguntas.
     *
     * @param pergunta Instância da pergunta a ser adicionada
     */
    public void adicionarPergunta(Pergunta pergunta) {
        if (perguntas.size() >= 3) {
            throw new IllegalStateException("A rodada já possui 3 perguntas.");
        }
        perguntas.add(pergunta);
    }

    /**
     * Inicia a rodada:
     * - Reinicia os acertos e pulos dos jogadores
     * - Cada jogador responde às mesmas 3 perguntas
     * - Exibe o vencedor da rodada (ou empate)
     */
    public void iniciar() {
        if (perguntas.size() != 3) {
            throw new IllegalStateException("Cada rodada precisa de exatamente 3 perguntas.");
        }

        // Reinicia os contadores de acertos e pulos dos jogadores
        for (Jogador j : jogadores) {
            j.resetarAcertos();
            j.resetarPulos();
        }

        // Todos os jogadores respondem às mesmas perguntas, uma por uma
        for (Pergunta p : perguntas) {
            for (Jogador j : jogadores) {
                j.responderPergunta(p);
            }
        }

        // Verifica e exibe o vencedor da rodada
        Jogador vencedor = verificarVencedor();
        if (vencedor == null) {
            System.out.println("\n=> Resultado da rodada: EMPATE\n");
        } else {
            System.out.println("\n=> Vencedor da rodada: " + vencedor.getNome() +
                    " (acertos: " + vencedor.getAcertosNaRodada() + ")\n");
        }
    }

    /**
     * Verifica o vencedor da rodada com base no número de acertos.
     *
     * @return Jogador com mais acertos ou null em caso de empate
     */
    public Jogador verificarVencedor() {
        int max = Integer.MIN_VALUE;
        Jogador vencedor = null;
        boolean empate = false;

        for (Jogador j : jogadores) {
            int a = j.getAcertosNaRodada();

            if (a > max) {
                max = a;
                vencedor = j;
                empate = false;
            } else if (a == max) {
                empate = true;
            }
        }

        return empate ? null : vencedor;
    }

    // Retorna uma lista imutável das perguntas da rodada
    public List<Pergunta> getPerguntas() {
        return Collections.unmodifiableList(perguntas);
    }

    // Retorna a lista imutável de jogadores da rodada
    public List<Jogador> getJogadores() {
        return jogadores;
    }
}
