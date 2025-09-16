import java.util.Objects;

/**
 * Representa o GameMaster (mestre do jogo), responsável por criar perguntas.
 * Herda de Usuario.
 */
public class GameMaster extends Usuario {

    /**
     * Construtor do GameMaster.
     *
     * @param nome Nome do GameMaster
     */
    public GameMaster(String nome) {
        super(nome);
    }

    /**
     * Cria uma nova pergunta com enunciado, 4 alternativas e a letra da resposta correta.
     *
     * @param enunciado Texto da pergunta
     * @param alternativas Vetor com 4 alternativas (a, b, c, d)
     * @param respostaCorreta Letra da alternativa correta ('a' a 'd')
     * @return Instância de Pergunta criada
     */
    public Pergunta criarPergunta(String enunciado, String[] alternativas, char respostaCorreta) {
        // Validações básicas para evitar valores nulos
        Objects.requireNonNull(enunciado, "Enunciado não pode ser nulo");
        Objects.requireNonNull(alternativas, "Alternativas não podem ser nulas");

        // Garante que existam exatamente 4 alternativas
        if (alternativas.length != 4) {
            throw new IllegalArgumentException("Devem existir exatamente 4 alternativas (a, b, c, d).");
        }

        // Valida se a letra da resposta correta está entre 'a' e 'd'
        char letra = Character.toLowerCase(respostaCorreta);
        if (letra < 'a' || letra > 'd') {
            throw new IllegalArgumentException("A resposta correta deve ser uma letra entre 'a' e 'd'.");
        }

        // Cria e retorna a nova pergunta
        return new Pergunta(enunciado, alternativas, letra);
    }
}
