import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Representa a classe Especialista.
 * Focado em conhecimento, perícias e versatilidade para resolver problemas.
 */
public class Especialista extends Classe {

    @Override
    public String getNome() {
        return "Especialista";
    }

    @Override
    public int getPVIniciais() {
        // Valor base de PV por nível, sem o bônus de Vigor
        return 3;
    }

    @Override
    public List<String> getPericiasTreinadas() {
        List<String> pericias = new ArrayList<>();
        // Número fixo e generoso de perícias para manter a identidade da classe
        for (int i = 0; i < 8; i++) {
            pericias.add("Perícia à escolha " + (i + 1));
        }
        return pericias;
    }

    @Override
    public List<String> getProficiencias() {
        return Arrays.asList("Armas simples", "Proteções leves");
    }

    @Override
    public List<String> getHabilidades(int nex) {
        List<String> habilidades = new ArrayList<>();
        if (nex >= 5) {
            habilidades.add("Eclético: Pode usar uma perícia como se fosse treinado.");
            habilidades.add("Perito: Bônus em duas perícias escolhidas.");
        }
        if (nex >= 10) {
            habilidades.add("Habilidade de trilha");
        }
        if (nex >= 15) {
            habilidades.add("Poder de especialista");
        }
        // A progressão continua...
        return habilidades;
    }
}
