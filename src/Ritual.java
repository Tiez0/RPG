/**
 * Representa um ritual que um Ocultista pode usar, com regras de sucesso e falha.
 */
public class Ritual {
    private final String nome;
    private final String descricao;
    private final String efeito;
    private final int sucessoMinimo;
    private final String penalidadeFalha;

    /**
     * Construtor para criar um novo ritual com regras de combate.
     *
     * @param nome            O nome do ritual.
     * @param descricao       A descrição completa do efeito do ritual.
     * @param efeito          A fórmula de dano ou cura (ex: 3d8+3).
     * @param sucessoMinimo   O valor mínimo no d20 para o ritual funcionar.
     * @param penalidadeFalha O dano que o ocultista sofre em caso de falha.
     */
    public Ritual(String nome, String descricao, String efeito, int sucessoMinimo, String penalidadeFalha) {
        this.nome = nome;
        this.descricao = descricao;
        this.efeito = efeito;
        this.sucessoMinimo = sucessoMinimo;
        this.penalidadeFalha = penalidadeFalha;
    }

    // Getters
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getEfeito() { return efeito; }
    public int getSucessoMinimo() { return sucessoMinimo; }
    public String getPenalidadeFalha() { return penalidadeFalha; }

    @Override
    public String toString() {
        return nome + " (" + descricao + ")";
    }
}
