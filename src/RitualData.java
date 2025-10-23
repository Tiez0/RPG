
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por fornecer dados sobre os rituais disponíveis no jogo.
 */
public class RitualData {

    /**
     * Retorna uma lista de rituais disponíveis com base no Nível de Exposição Paranormal (NEX).
     *
     * @param nex O NEX do personagem.
     * @return Uma lista de rituais.
     */
    public static List<Ritual> getRitualsDisponiveis(int nex) {
        List<Ritual> rituais = new ArrayList<>();

        // Rituais básicos, sempre disponíveis
        rituais.add(new Ritual("Cicatrização", "Cura 3d8+3 pontos de vida.", "3d8+3", 10, "1d4"));
        rituais.add(new Ritual("Eco Espiral", "Copia o ataque de um alvo.", "Copia ataque", 12, "1d6"));
        rituais.add(new Ritual("Decadência", "Causa 2d8+2 de dano.", "2d8+2", 10, "1d4"));
        rituais.add(new Ritual("Ritual Vodum", "Sacrifica PV para causar 2d6 de dano.", "2d6", 13, "2d4"));

        // Rituais que exigem um NEX mínimo
        if (nex >= 50) {
            rituais.add(new Ritual("Cinerária", "Dano contínuo e debuff.", "Dano contínuo", 15, "2d6"));
        }

        return rituais;
    }
}
