import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rodada {
    private final List<Pergunta> perguntas = new ArrayList<>(3);
    private final List<Jogador> jogadores;

    public Rodada(List<Jogador> jogadores) {
        if (jogadores == null || jogadores.size() < 2) {
            throw new IllegalArgumentException("Devem existir pelo menos 2 jogadores.");
        }
        this.jogadores = Collections.unmodifiableList(new ArrayList<>(jogadores));
    }

    public void adicionarPergunta(Pergunta pergunta) {
        if (perguntas.size() >= 3) {
            throw new IllegalStateException("A rodada já possui 3 perguntas.");
        }
        perguntas.add(pergunta);
    }

    public void iniciar() {
        if (perguntas.size() != 3) {
            throw new IllegalStateException("Cada rodada precisa de exatamente 3 perguntas.");
        }

        // Regras de negócio: acertos/pulos reiniciam a cada rodada. :contentReference[oaicite:3]{index=3}
        for (Jogador j : jogadores) {
            j.resetarAcertos();
            j.resetarPulos();
        }

        // Todos os jogadores respondem às MESMAS perguntas, sequencialmente. :contentReference[oaicite:4]{index=4}
        for (Pergunta p : perguntas) {
            for (Jogador j : jogadores) {
                j.responderPergunta(p);
            }
        }

        // Ao final, podemos anunciar o vencedor da rodada (se houver)
        Jogador vencedor = verificarVencedor();
        if (vencedor == null) {
            System.out.println("\n==> Resultado da rodada: EMPATE\n");
        } else {
            System.out.println("\n==> Vencedor da rodada: " + vencedor.getNome() +
                    " (acertos: " + vencedor.getAcertosNaRodada() + ")\n");
        }
    }

    /**
     * Retorna o jogador com mais acertos NA RODADA, ou null se houver empate.
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

    public List<Pergunta> getPerguntas() {
        return Collections.unmodifiableList(perguntas);
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }
}