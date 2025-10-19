import java.util.Arrays;
import java.util.List;

/**
 * Representa a origem Acadêmico.
 * Concede perícias em Ciências e Investigação e o poder "Saber é Poder".
 */
public class Academico extends Origem {

    @Override
    public List<String> getPericiasTreinadas() {
        return Arrays.asList("Ciências", "Investigação");
    }

    @Override
    public String getPoder() {
        return "Saber é Poder: Quando faz um teste usando Intelecto, você pode gastar 2 PE para receber +5 nesse teste.";
    }

    @Override
    public String getNome() {
        return "Acadêmico";
    }
}
