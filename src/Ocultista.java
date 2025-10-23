/**
 * Representa a classe Ocultista.
 * Um estudioso do paranormal que usa rituais, com menos pontos de vida.
 */
public class Ocultista extends Classe {

    @Override
    public String getNome() {
        return "Ocultista";
    }

    @Override
    public int getPVIniciais() {
        return 10;
    }

}
