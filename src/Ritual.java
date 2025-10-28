/**
 * Representa uma Técnica de Sobrevivência que um Intuitivo pode usar, com consequências em caso de sucesso ou falha.
 */
public class Ritual {
    private final String nome;
    private final String descricao;

    /**
     * Construtor para criar uma nova técnica.
     *
     * @param nome      O nome da técnica (ex: "Injeção de Adrenalina").
     * @param descricao A descrição do que a técnica faz (ex: "Cura 3d8+3 pontos de vida.").
     */
    public Ritual(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }

    @Override
    public String toString() {
        return nome + " (" + descricao + ")";
    }
}
