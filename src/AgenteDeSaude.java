import java.util.Arrays;
import java.util.List;

/**
 * Representa a origem Agente de Saúde.
 * Concede perícias em Intuição e Medicina e o poder "Técnica Medicinal".
 */
public class AgenteDeSaude extends Origem {

    @Override
    public List<String> getPericiasTreinadas() {
        return Arrays.asList("Intuição", "Medicina");
    }

    @Override
    public String getPoder() {
        return "Técnica Medicinal: Sempre que cura um personagem, você adiciona seu Intelecto no total de PV curados.";
    }

    @Override
    public String getNome() {
        return "Agente de Saúde";
    }
}
