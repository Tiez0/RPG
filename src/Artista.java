import java.util.Arrays;
import java.util.List;

/**
 * Representa a origem Artista.
 * Concede perícias em Artes e Enganação e o poder "Magnum Opus".
 */
public class Artista extends Origem {

    @Override
    public List<String> getPericiasTreinadas() {
        return Arrays.asList("Artes", "Enganação");
    }

    @Override
    public String getPoder() {
        return "Magnum Opus: Você é famoso por uma de suas obras. Uma vez por missão, pode determinar que um personagem envolvido em uma cena de interação o reconheça. Você recebe +5 em testes de Presença e de perícias baseadas em Presença contra aquele personagem. A critério do mestre, pode receber esses bônus em outras situações nas quais seria reconhecido.";
    }

    @Override
    public String getNome() {
        return "Artista";
    }
}
