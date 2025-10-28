// representa um ritual que um ocultista pode usar, com regras de sucesso e falha.
public class Ritual extends Item implements Cloneable {
    private String efeito; // Removido o 'final' para permitir a modificação
    private final int sucessoMinimo;
    private final String penalidadeFalha;

    // construtor para criar um novo ritual com regras de combate.
    public Ritual(String nome, String descricao, String efeito, int sucessoMinimo, String penalidadeFalha) {
        super(nome, descricao, 1); // rituais sao unicos, entao a quantidade e sempre 1
        this.efeito = efeito;
        this.sucessoMinimo = sucessoMinimo;
        this.penalidadeFalha = penalidadeFalha;
    }

    // getters e setters
    public String getEfeito() { return efeito; }
    public void setEfeito(String efeito) { this.efeito = efeito; } // Novo setter
    public int getSucessoMinimo() { return sucessoMinimo; }
    public String getPenalidadeFalha() { return penalidadeFalha; }

    @Override
    public void exibirDetalhes() {
        System.out.println("\n--- " + getNome() + " ---");
        System.out.println("descricao: " + getDescricao());
        System.out.println("efeito: " + efeito);
        System.out.println("sucesso: " + sucessoMinimo + "+");
        System.out.println("penalidade por falha: " + penalidadeFalha);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
