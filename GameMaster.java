import java.util.Objects;

public class GameMaster extends Usuario {

    public GameMaster(String nome) {
        super(nome);
    }

    /**
     * Cria uma nova pergunta com 4 alternativas (a, b, c, d) e
     * a alternativa correta explicitada (letras 'a'..'d').
     */
    public Pergunta criarPergunta(String enunciado, String[] alternativas, char respostaCorreta) {
        Objects.requireNonNull(enunciado, "Enunciado não pode ser nulo");
        Objects.requireNonNull(alternativas, "Alternativas não podem ser nulas");

        if (alternativas.length != 4) {
            throw new IllegalArgumentException("Devem existir exatamente 4 alternativas (a, b, c, d).");
        }

        char letra = Character.toLowerCase(respostaCorreta);
        if (letra < 'a' || letra > 'd') {
            throw new IllegalArgumentException("A resposta correta deve ser uma letra entre 'a' e 'd'.");
        }

        return new Pergunta(enunciado, alternativas, letra);
    }
}