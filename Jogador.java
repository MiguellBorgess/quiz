import java.util.Scanner;

/**
 * Representa um jogador do jogo de perguntas e respostas.
 * Herda de Usuario e implementa a interface Respondente.
 */
public class Jogador extends Usuario implements Respondente {
    private static final Scanner SC = new Scanner(System.in);

    // Contadores de desempenho do jogador
    private int acertosNaRodada;
    private int pulosNaRodada;
    private int pulosTotal;
    private int pontuacaoTotal;

    /**
     * Construtor do jogador.
     *
     * @param nome Nome do jogador
     */
    public Jogador(String nome) {
        super(nome);
        this.acertosNaRodada = 0;
        this.pulosNaRodada = 0;
        this.pulosTotal = 0;
        this.pontuacaoTotal = 0;
    }

    /**
     * Método que permite ao jogador responder uma pergunta.
     * Trata entrada do usuário, validação, pulo e pontuação.
     *
     * @param pergunta A pergunta a ser respondida
     */
    @Override
    public void responderPergunta(Pergunta pergunta) {
        // Códigos ANSI para colorir o terminal
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_CYAN = "\u001B[36m";

        // Exibe o nome do jogador e a pergunta
        System.out.println();
        System.out.println(ANSI_CYAN + "Vez de: " + getNome() + ANSI_RESET);
        System.out.println(ANSI_YELLOW + pergunta + ANSI_RESET);

        // Lê e valida a resposta do jogador
        char resposta;
        while (true) {
            System.out.print("Digite sua resposta (a/b/c/d) ou 'p' para pular: ");
            String linha = SC.nextLine().trim();
            if (linha.isEmpty()) continue;
            resposta = Character.toLowerCase(linha.charAt(0));
            if (pergunta.isRespostaValida(resposta)) break;
            System.out.println("Entrada inválida. Tente novamente.");
        }

        // Trata o pulo de pergunta
        if (resposta == 'p') {
            boolean pulou = incrementarPulos(); // só pode pular uma vez por rodada
            if (!pulou) {
                System.out.println("Você já usou seu pulo nesta rodada. Responda a pergunta.");
                responderPergunta(pergunta); // força o jogador a responder
                return;
            } else {
                System.out.println(getNome() + " optou por PULAR a pergunta.");
                return; // pulo não altera pontuação
            }
        }

        // Verifica se a resposta está correta
        boolean correta = pergunta.verificarResposta(resposta);
        System.out.println("🤔 Verificando resposta...");
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        if (correta) {
            incrementarAcertos();
            incrementarPontuacao(+1);
            System.out.println(ANSI_GREEN + "✅ Resposta correta! (+1 ponto)" + ANSI_RESET);
        } else {
            incrementarPontuacao(-1);
            System.out.println(ANSI_RED + "❌ Resposta errada! (-1 ponto)" + ANSI_RESET);
        }

        // Exibe a pontuação atual do jogador
        System.out.println("Pontuação total de " + getNome() + ": " + pontuacaoTotal);
    }

    // Incrementa o número de acertos na rodada
    public void incrementarAcertos() {
        acertosNaRodada++;
    }

    // Retorna o número de acertos na rodada atual
    public int getAcertosNaRodada() {
        return acertosNaRodada;
    }

    // Reinicia o contador de acertos da rodada
    public void resetarAcertos() {
        acertosNaRodada = 0;
    }

    /**
     * Tenta usar o pulo na rodada.
     *
     * @return true se o pulo foi permitido; false se já foi usado
     */
    public boolean incrementarPulos() {
        if (pulosNaRodada >= 1) {
            return false;
        }
        pulosNaRodada++;
        pulosTotal++;
        return true;
    }

    // Reinicia o contador de pulos da rodada
    public void resetarPulos() {
        pulosNaRodada = 0;
    }

    // Retorna o total de pulos usados no jogo
    public int getPulosTotal() {
        return pulosTotal;
    }

    // Altera a pontuação total do jogador
    public void incrementarPontuacao(int valor) {
        pontuacaoTotal += valor;
    }

    // Retorna a pontuação total acumulada
    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    // Getter opcional para pulos na rodada atual
    public int getPulosNaRodada() {
        return pulosNaRodada;
    }
}
