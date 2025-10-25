import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * uma classe utilitaria para simular rolagens de dados de rpg ou retornar valores fixos.
 */
public class Dado {

    private static final Random random = new Random();

    /**
     * rola dados com base em uma expressao (ex: "2d6", "1d20+5") ou retorna um valor fixo.
     *
     * @param expressao a string que representa a rolagem do dado ou um numero fixo (ex: "19").
     * @return o resultado da rolagem/valor, ou 0 se a expressao for invalida.
     */
    public static int rolar(String expressao) {
        expressao = expressao.replaceAll("\\s", ""); // remove espacos

        // padrao para extrair os numeros da expressao (ex: 3d8+3)
        Pattern pattern = Pattern.compile("(\\d+)d(\\d+)(?:\\+(\\d+))?");
        Matcher matcher = pattern.matcher(expressao);

        if (matcher.matches()) {
            try {
                int numeroDeDados = Integer.parseInt(matcher.group(1));
                int facesDoDado = Integer.parseInt(matcher.group(2));
                int modificador = (matcher.group(3) != null) ? Integer.parseInt(matcher.group(3)) : 0;

                int total = 0;
                for (int i = 0; i < numeroDeDados; i++) {
                    total += random.nextInt(facesDoDado) + 1; // +1 porque nextint(n) vai de 0 a n-1
                }
                return total + modificador;
            } catch (NumberFormatException e) {
                return 0; // improvavel, mas seguro
            }
        } else {
            // se nao for uma expressao de dado, tenta converter para um numero fixo
            try {
                return Integer.parseInt(expressao);
            } catch (NumberFormatException e) {
                return 0; // retorna 0 se a expressao for totalmente invalida
            }
        }
    }
}
