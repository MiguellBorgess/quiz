import java.util.Scanner;

public class Jogador extends Usuario implements Respondente {
    private static final Scanner SC = new Scanner(System.in);

    private int acertosNaRodada;
    private int pulosNaRodada;
    private int pulosTotal;
    private int pontuacaoTotal;

    public Jogador(String nome) {
        super(nome);
        this.acertosNaRodada = 0;
        this.pulosNaRodada = 0;
        this.pulosTotal = 0;
        this.pontuacaoTotal = 0;
    }

    @Override
    public void responderPergunta(Pergunta pergunta) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_CYAN = "\u001B[36m";

        System.out.println();
        System.out.println(ANSI_CYAN + "Vez de: " + getNome() + ANSI_RESET);
        System.out.println(ANSI_YELLOW + pergunta + ANSI_RESET);


        char resposta;
        while (true) {
            System.out.print("Digite sua resposta (a/b/c/d) ou 'p' para pular: ");
            String linha = SC.nextLine().trim();
            if (linha.isEmpty()) continue;
            resposta = Character.toLowerCase(linha.charAt(0));
            if (pergunta.isRespostaValida(resposta)) break;
            System.out.println("Entrada inválida. Tente novamente.");
        }

        if (resposta == 'p') {
            boolean pulou = incrementarPulos(); // só pode 1 pulo por rodada
            if (!pulou) {
                System.out.println("Você já usou seu pulo nesta rodada. Responda a pergunta.");
                responderPergunta(pergunta); // força responder
                return;
            } else {
                System.out.println(getNome() + " optou por PULAR a pergunta.");
                return; // pulo não altera pontuação
            }
        }

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
        System.out.println("Pontuação total de " + getNome() + ": " + pontuacaoTotal);
    }

    public void incrementarAcertos() {
        acertosNaRodada++;
    }

    public int getAcertosNaRodada() {
        return acertosNaRodada;
    }

    public void resetarAcertos() {
        acertosNaRodada = 0;
    }

    /**
     * Tenta usar o pulo na rodada.
     * @return true se conseguiu pular; false se já tinha usado o pulo desta rodada.
     */
    public boolean incrementarPulos() {
        if (pulosNaRodada >= 1) {
            return false;
        }
        pulosNaRodada++;
        pulosTotal++;
        return true;
    }

    public void resetarPulos() {
        pulosNaRodada = 0;
    }

    public int getPulosTotal() {
        return pulosTotal;
    }

    public void incrementarPontuacao(int valor) {
        pontuacaoTotal += valor;
    }

    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    // Getters extras, se quiser expor tudo:
    public int getPulosNaRodada() { return pulosNaRodada; }
}