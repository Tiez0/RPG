import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Representa o arquétipo Batedor.
 * Focado em furtividade, precisão e exploração. Um Batedor elimina ameaças à distância e navega pelos destroços com cuidado.
 */
public class Especialista extends Classe {

    @Override
    public String getNome() {
        return "Batedor";
    }

    @Override
    public int getPVIniciais() {
        // Batedores são ágeis, mas não tão resistentes quanto os Brutamontes.
        return 3;
    }

    @Override
    public List<String> getPericiasTreinadas() {
        List<String> pericias = new ArrayList<>();
        // Batedores são versáteis e aprendem a lidar com muitas situações.
        for (int i = 0; i < 8; i++) {
            pericias.add("Habilidade à escolha " + (i + 1));
        }
        return pericias;
    }

    @Override
    public List<String> getProficiencias() {
        return Arrays.asList("Armas simples", "Armas de fogo", "Proteções leves");
    }

    @Override
    public List<String> getHabilidades(int nex) {
        List<String> habilidades = new ArrayList<>();
        if (nex >= 5) {
            habilidades.add("Adaptável: Pode usar uma habilidade como se fosse treinado.");
            habilidades.add("Olho de Águia: Bônus em duas perícias de precisão ou percepção.");
        }
        if (nex >= 10) {
            habilidades.add("Técnica de Trilha de Batedor");
        }
        if (nex >= 15) {
            habilidades.add("Vantagem de Batedor");
        }
        // A progressão continua...
        return habilidades;
    }
}
