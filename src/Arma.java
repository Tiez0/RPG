/**
 * Representa uma arma no RPG, com nome e dano.
 */
public class Arma {
    private final String nome;
    private final String dano;

    /**
     * Construtor para criar uma nova arma.
     *
     * @param nome O nome da arma (ex: Machado).
     * @param dano O dano da arma em formato de dado (ex: 1d8).
     */
    public Arma(String nome, String dano) {
        this.nome = nome;
        this.dano = dano;
    }

    public String getNome() {
        return nome;
    }

    public String getDano() {
        return dano;
    }

    @Override
    public String toString() {
        return nome + " (" + dano + ")";
    }
}
