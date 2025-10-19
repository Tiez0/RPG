import java.util.Arrays;
import java.util.List;

/**
 * Representa a origem Atleta.
 * Concede perícias em Acrobacia e Atletismo e o poder "110%".
 */
public class Atleta extends Origem {

    @Override
    public List<String> getPericiasTreinadas() {
        return Arrays.asList("Acrobacia", "Atletismo");
    }

    @Override
    public String getPoder() {
        return "110%: Quando faz um teste de perícia usando Força ou Agilidade (exceto Luta e Pontaria) você pode gastar 2 PE para receber +5";
    }

    @Override
    public String getNome() {
        return "Atleta";
    }
}
