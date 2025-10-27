// representa a classe combatente.
// focado em combate, com mais pontos de vida.
public class Combatente extends Classe {

    @Override
    public String getNome() {
        return "Combatente";
    }

    @Override
    public int getPVIniciais() {
        return 15;
    }

}
