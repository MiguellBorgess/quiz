import java.util.Locale;

public class Pergunta {
    private final String enunciado;
    private final String[] alternativas; // índice 0 -> 'a', 1 -> 'b', 2 -> 'c', 3 -> 'd'
    private final char respostaCorreta;   // 'a'..'d'

    public Pergunta(String enunciado, String[] alternativas, char respostaCorreta) {
        if (enunciado == null || enunciado.isBlank()) {
            throw new IllegalArgumentException("Enunciado inválido.");
        }
        if (alternativas == null || alternativas.length != 4) {
            throw new IllegalArgumentException("Devem existir exatamente 4 alternativas.");
        }
        for (String alt : alternativas) {
            if (alt == null || alt.isBlank()) {
                throw new IllegalArgumentException("Alternativa vazia/inválida.");
            }
        }
        char letra = Character.toLowerCase(respostaCorreta);
        if (letra < 'a' || letra > 'd') {
            throw new IllegalArgumentException("A resposta correta deve ser 'a', 'b', 'c' ou 'd'.");
        }

        this.enunciado = enunciado;
        this.alternativas = alternativas.clone();
        this.respostaCorreta = letra;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String[] getAlternativas() {
        return alternativas.clone();
    }

    public char getRespostaCorreta() {
        return respostaCorreta;
    }

    /** Retorna true se a resposta (a..d) for correta. */
    public boolean verificarResposta(char resposta) {
        return Character.toLowerCase(resposta) == respostaCorreta;
    }

    /**
     * Verifica se a resposta é válida:
     * - 'a', 'b', 'c', 'd' (alternativas)
     * - OU um comando de pulo. Adotaremos 'p' como comando de pulo.
     *   (O PDF fala em “comando de pulo” mas não fixa o caractere; usamos 'p'.) :contentReference[oaicite:1]{index=1}
     */
    public boolean isRespostaValida(char resposta) {
        char r = Character.toLowerCase(resposta);
        return (r >= 'a' && r <= 'd') || r == 'p';
    }

    /** Utilitário para mapear 'a'..'d' para índice 0..3. Retorna -1 se inválido. */
    public static int indiceDaLetra(char letra) {
        char r = Character.toLowerCase(letra);
        if (r < 'a' || r > 'd') return -1;
        return r - 'a';
    }

    /** Representação amigável para exibir no terminal. */
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