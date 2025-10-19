/**
 * Representa um ritual que um Ocultista pode usar.
 */
public class Ritual {
    private final String nome;
    private final String descricao;

    /**
     * Construtor para criar um novo ritual.
     *
     * @param nome      O nome do ritual (ex: Cicatrização).
     * @param descricao A descrição completa do efeito do ritual.
     */
    public Ritual(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return nome + ": " + descricao;
    }
}
