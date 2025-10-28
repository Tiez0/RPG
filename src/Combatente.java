import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Representa o arquétipo Brutamontes.
 * Focado na força bruta e no combate corporal, usando qualquer coisa como arma para esmagar os Perdidos.
 */
public class Combatente extends Classe {

    @Override
    public String getNome() {
        return "Brutamontes";
    }

    @Override
    public int getPVIniciais() {
        // Brutamontes são os mais resistentes, feitos para aguentar e distribuir punição.
        return 4;
    }

    @Override
    public List<String> getPericiasTreinadas() {
        List<String> pericias = new ArrayList<>();
        pericias.add("Briga");
        pericias.add("Tolerância");
        
        pericias.add("Habilidade à escolha 1");
        pericias.add("Habilidade à escolha 2");
        
        return pericias;
    }

    @Override
    public List<String> getProficiencias() {
        return Arrays.asList("Armas simples", "Armas de pancada", "Proteções improvisadas");
    }

    @Override
    public List<String> getHabilidades(int nex) {
        List<String> habilidades = new ArrayList<>();
        if (nex >= 5) {
            habilidades.add("Ataque Feroz");
        }
        if (nex >= 10) {
            habilidades.add("Técnica de Trilha de Brutamontes");
        }
        if (nex >= 15) {
            habilidades.add("Fúria Incontrolável");
        }
        if (nex >= 20) {
            habilidades.add("Pele Grossa (bônus de resistência)");
        }
        if (nex >= 25) {
            habilidades.add("Ataque Feroz (Aprimorado)");
        }

        return habilidades;
    }
}
