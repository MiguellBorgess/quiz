import java.util.Locale;

/**
 * Representa uma pergunta de múltipla escolha com 4 alternativas ('a' a 'd') e uma resposta correta.
 */
public class Pergunta {
    // Texto da pergunta
    private final String enunciado;

    // Vetor com as 4 alternativas. Índices: 0 → 'a', 1 → 'b', 2 → 'c', 3 → 'd'
    private final String[] alternativas;

    // Letra da alternativa correta ('a', 'b', 'c' ou 'd')
    private final char respostaCorreta;

    /**
     * Construtor da classe Pergunta.
     * Valida os dados de entrada e inicializa os atributos.
     *
     * @param enunciado Texto da pergunta
     * @param alternativas Vetor com 4 alternativas
     * @param respostaCorreta Letra da alternativa correta ('a' a 'd')
     */
    public Pergunta(String enunciado, String[] alternativas, char respostaCorreta) {
        // Validação do enunciado
        if (enunciado == null || enunciado.isBlank()) {
            throw new IllegalArgumentException("Enunciado inválido.");
        }

        // Validação do vetor de alternativas
        if (alternativas == null || alternativas.length != 4) {
            throw new IllegalArgumentException("Devem existir exatamente 4 alternativas.");
        }

        // Validação de cada alternativa individualmente
        for (String alt : alternativas) {
            if (alt == null || alt.isBlank()) {
                throw new IllegalArgumentException("Alternativa vazia/inválida.");
            }
        }

        // Validação da resposta correta (deve ser 'a', 'b', 'c' ou 'd')
        char letra = Character.toLowerCase(respostaCorreta);
        if (letra < 'a' || letra > 'd') {
            throw new IllegalArgumentException("A resposta correta deve ser 'a', 'b', 'c' ou 'd'.");
        }

        // Inicialização dos atributos
        this.enunciado = enunciado;
        this.alternativas = alternativas.clone(); // Clona para evitar alterações externas
        this.respostaCorreta = letra;
    }

    // Retorna o enunciado da pergunta
    public String getEnunciado() {
        return enunciado;
    }

    // Retorna uma cópia das alternativas
    public String[] getAlternativas() {
        return alternativas.clone();
    }

    // Retorna a letra da resposta correta
    public char getRespostaCorreta() {
        return respostaCorreta;
    }

    /**
     * Verifica se a resposta fornecida está correta.
     *
     * @param resposta Letra da resposta fornecida
     * @return true se for igual à resposta correta
     */
    public boolean verificarResposta(char resposta) {
        return Character.toLowerCase(resposta) == respostaCorreta;
    }

    /**
     * Verifica se a resposta fornecida é válida.
     * Aceita:
     * - 'a', 'b', 'c', 'd' (alternativas)
     * - 'p' como comando de pulo
     *
     * @param resposta Letra da resposta fornecida
     * @return true se for válida
     */
    public boolean isRespostaValida(char resposta) {
        char r = Character.toLowerCase(resposta);
        return (r >= 'a' && r <= 'd') || r == 'p';
    }

    /**
     * Converte uma letra ('a' a 'd') para o índice correspondente (0 a 3).
     *
     * @param letra Letra da alternativa
     * @return Índice correspondente ou -1 se inválida
     */
    public static int indiceDaLetra(char letra) {
        char r = Character.toLowerCase(letra);
        if (r < 'a' || r > 'd') return -1;
        return r - 'a';
    }

    /**
     * Retorna uma representação amigável da pergunta e suas alternativas.
     *
     * @return String formatada para exibição no terminal
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(enunciado).append(System.lineSeparator());

        char letra = 'a';
        for (String alt : alternativas) {
            sb.append("  ").append(letra).append(") ").append(alt).append(System.lineSeparator());
            letra++;
        }

        return sb.toString();
    }
}
