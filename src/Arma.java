/**
 * Representa uma arma no RPG, com regras de dano, acerto e crítico.
 */
public class Arma {
    private String nome; // Removido final para permitir a transformação "do Outro Lado"
    private final String dano;
    private final String danoCritico;
    private final int acertoMinimo;
    private int criticoMinimo; // Removido final para a habilidade do Especialista

    /**
     * Construtor para criar uma nova arma com regras de combate.
     *
     * @param nome          O nome da arma (ex: Machado).
     * @param dano          O dano padrão da arma (ex: 1d8).
     * @param danoCritico   O dano em um acerto crítico (ex: 3d8 ou 19).
     * @param acertoMinimo  O valor mínimo no d20 para acertar.
     * @param criticoMinimo O valor mínimo no d20 para um acerto crítico.
     */
    public Arma(String nome, String dano, String danoCritico, int acertoMinimo, int criticoMinimo) {
        this.nome = nome;
        this.dano = dano;
        this.danoCritico = danoCritico;
        this.acertoMinimo = acertoMinimo;
        this.criticoMinimo = criticoMinimo;
    }

    // --- Métodos de Modificação ---

    public void transformarDoOutroLado() {
        this.nome += " do Outro Lado";
        System.out.println("Sua arma brilha com uma energia roxa e se transforma!");
    }

    public void reduzirCritico(int reducao) {
        this.criticoMinimo -= reducao;
        System.out.println("Sua perícia com a arma aumentou! (Crítico: " + this.criticoMinimo + "+)");
    }

    // --- Getters ---
    public String getNome() { return nome; }
    public String getDano() { return dano; }
    public String getDanoCritico() { return danoCritico; }
    public int getAcertoMinimo() { return acertoMinimo; }
    public int getCriticoMinimo() { return criticoMinimo; }

    @Override
    public String toString() {
        return nome + " (Dano: " + dano + " | Crítico: " + danoCritico + " / " + criticoMinimo + "+)";
    }
}
