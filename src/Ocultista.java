import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Representa a classe Ocultista.
 * Um estudioso do paranormal que usa rituais e compreende os elementos.
 */
public class Ocultista extends Classe {

    @Override
    public String getNome() {
        return "Ocultista";
    }

    @Override
    public int getPVIniciais() {
        // Valor base de PV por nível, sem o bônus de Vigor
        return 2;
    }

    @Override
    public List<String> getPericiasTreinadas() {
        List<String> pericias = new ArrayList<>();
        pericias.add("Ocultismo");
        pericias.add("Vontade");
        
        // Número fixo de perícias, já que não há mais Intelecto
        pericias.add("Perícia à escolha 1");
        pericias.add("Perícia à escolha 2");
        pericias.add("Perícia à escolha 3");
        pericias.add("Perícia à escolha 4");

        return pericias;
    }

    @Override
    public List<String> getProficiencias() {
        return Arrays.asList("Armas simples");
    }

    @Override
    public List<String> getHabilidades(int nex) {
        List<String> habilidades = new ArrayList<>();
        if (nex >= 5) {
            habilidades.add("Escolhido pelo Outro Lado (1º círculo)");
        }
        if (nex >= 10) {
            habilidades.add("Habilidade de trilha");
        }
        if (nex >= 15) {
            habilidades.add("Poder de ocultista");
        }
        // A progressão continua...
        return habilidades;
    }
}
