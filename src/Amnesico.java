import java.util.Arrays;
import java.util.List;

/**
 * Representa a origem Amnésico.
 * Concede duas perícias à escolha do mestre e o poder "Vislumbres do Passado".
 */
public class Amnesico extends Origem {

    @Override
    public List<String> getPericiasTreinadas() {
        // As duas perícias são escolhidas pelo mestre.
        return Arrays.asList("À escolha do mestre 1", "À escolha do mestre 2");
    }

    @Override
    public String getPoder() {
        return "Vislumbres do Passado: Uma vez por sessão, você pode fazer um teste de Intelecto (DT 10) para reconhecer pessoas ou lugares familiares, que tenha encontrado antes de perder a memória. Se passar, recebe 1d4 PE temporários e, a critério do mestre, uma informação útil.";
    }

    @Override
    public String getNome() {
        return "Amnésico";
    }
}
