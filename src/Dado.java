import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Uma classe utilitária para simular rolagens de dados de RPG.
 */
public class Dado {

    private static final Random random = new Random();

    /**
     * Rola dados com base em uma expressão no formato XdY+Z (ex: "2d6", "1d20+5").
     *
     * @param expressao A string que representa a rolagem do dado.
     * @return O resultado da rolagem, ou 0 se a expressão for inválida.
     */
    public static int rolar(String expressao) {
        // Padrão para extrair os números da expressão (ex: 3d8+3)
        Pattern pattern = Pattern.compile("(\\d+)d(\\d+)(?:\\+(\\d+))?");
        Matcher matcher = pattern.matcher(expressao.replaceAll("\\s", "")); // Remove espaços

        if (!matcher.matches()) {
            return 0; // Retorna 0 se a expressão não corresponder ao padrão
        }

        try {
            int numeroDeDados = Integer.parseInt(matcher.group(1));
            int facesDoDado = Integer.parseInt(matcher.group(2));
            int modificador = (matcher.group(3) != null) ? Integer.parseInt(matcher.group(3)) : 0;

            int total = 0;
            for (int i = 0; i < numeroDeDados; i++) {
                total += random.nextInt(facesDoDado) + 1; // +1 porque nextInt(N) vai de 0 a N-1
            }

            return total + modificador;

        } catch (NumberFormatException e) {
            return 0; // Em caso de erro de conversão
        }
    }
}
