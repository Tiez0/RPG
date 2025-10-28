import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Representa o arquétipo Intuitivo.
 * Um sobrevivente que desenvolveu uma estranha sintonia com o "Eco", permitindo o uso de técnicas bizarras.
 */
public class Ocultista extends Classe {

    @Override
    public String getNome() {
        return "Intuitivo";
    }

    @Override
    public int getPVIniciais() {
        // Intuitivos são frágeis, focados mais na mente do que no corpo.
        return 2;
    }

    @Override
    public List<String> getPericiasTreinadas() {
        List<String> pericias = new ArrayList<>();
        pericias.add("Intuição"); // Perícia principal para entender o Eco e os Perdidos
        pericias.add("Vontade"); // Essencial para não enlouquecer
        
        pericias.add("Habilidade à escolha 1");
        pericias.add("Habilidade à escolha 2");
        pericias.add("Habilidade à escolha 3");
        pericias.add("Habilidade à escolha 4");

        return pericias;
    }

    @Override
    public List<String> getProficiencias() {
        return Arrays.asList("Armas improvisadas");
    }

    @Override
    public List<String> getHabilidades(int nex) {
        List<String> habilidades = new ArrayList<>();
        if (nex >= 5) {
            habilidades.add("Sussurros do Eco (1º círculo)");
        }
        if (nex >= 10) {
            habilidades.add("Técnica de Trilha de Sobrevivência");
        }
        if (nex >= 15) {
            habilidades.add("Conhecimento do Abismo");
        }
        // A progressão continua...
        return habilidades;
    }
}
