import java.util.ArrayList;
import java.util.List;

// classe responsavel por fornecer dados sobre os rituais disponiveis no jogo.
public class RitualData {

    // retorna uma lista de rituais disponiveis com base no nivel de exposicao paranormal (nex).
    //
    // @param nex o nex do personagem.
    // @return uma lista de rituais.
    public static List<Ritual> getRitualsDisponiveis(int nex) {
        List<Ritual> rituais = new ArrayList<>();

        // rituais basicos, sempre disponiveis
        rituais.add(new Ritual("Cicatrização", "uma onda de cura vinda diretamente do outro lado.", "3d8+3", 10, "1d4"));
        rituais.add(new Ritual("Eco Espiral", "eco eco eco eco........", "Copia ataque", 12, "1d6"));
        rituais.add(new Ritual("Decadência", "decadencia, um forte ritual diretamente do outro lado", "2d8+2", 10, "1d4"));
        rituais.add(new Ritual("Ritual Vodum", "ritual utilizado por antigas tribos do norte da selva amazonica.", "2d6", 13, "2d4"));

        // rituais que exigem um nex minimo
        if (nex >= 50) {
            rituais.add(new Ritual("Cinerária", "uma onda de fumaca que surge e preenche o ambiente inteiro, como uma nuvem venenosa", "Dano contínuo", 15, "2d6"));
        }

        return rituais;
    }
}
