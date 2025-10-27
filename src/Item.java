// representa um item generico no rpg.
// esta e uma classe abstrata, servindo como base para armas, rituais, granadas, etc.
public abstract class Item {
    private String nome;
    private String descricao;
    private int quantidade;

    public Item(String nome, String descricao, int quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    // getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void adicionarQuantidade(int valor) {
        this.quantidade += valor;
    }

    public void removerQuantidade(int valor) {
        this.quantidade -= valor;
        if (this.quantidade < 0) {
            this.quantidade = 0;
        }
    }

    // metodo para exibir detalhes do item (sera sobrescrito pelas subclasses)
    public abstract void exibirDetalhes();

    @Override
    public String toString() {
        return nome + (quantidade > 1 ? " (x" + quantidade + ")" : "");
    }
}
