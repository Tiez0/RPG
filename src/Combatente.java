import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Representa a classe Combatente.
 * Focado em combate, com acesso a vários tipos de armas e técnicas de batalha.
 */
public class Combatente extends Classe {

    @Override
    public String getNome() {
        return "Combatente";
    }

    @Override
    public int getPVIniciais() {
        // Valor base de PV por nível, sem o bônus de Vigor
        return 4;
    }

    @Override
    public List<String> getPericiasTreinadas() {
        List<String> pericias = new ArrayList<>();
        pericias.add("Luta ou Pontaria (escolher 1)");
        pericias.add("Fortitude ou Reflexos (escolher 1)");
        
        // Número fixo de perícias, já que não há mais Intelecto
        pericias.add("Perícia à escolha 1");
        pericias.add("Perícia à escolha 2");
        
        return pericias;
    }

    @Override
    public List<String> getProficiencias() {
        return Arrays.asList("Armas simples", "Armas táticas", "Proteções leves");
    }

    @Override
    public List<String> getHabilidades(int nex) {
        List<String> habilidades = new ArrayList<>();
        if (nex >= 5) {
            habilidades.add("Ataque especial");
        }
        if (nex >= 10) {
            habilidades.add("Habilidade de trilha");
        }
        if (nex >= 15) {
            habilidades.add("Poder de combatente");
        }
        if (nex >= 20) {
            habilidades.add("Aumento de atributo (agora um bônus fixo ou poder)");
        }
        if (nex >= 25) {
            habilidades.add("Ataque especial (Aprimorado)");
        }
        // A progressão continua...
        return habilidades;
    }
}
