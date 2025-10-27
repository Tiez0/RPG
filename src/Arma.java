// representa uma arma no rpg, com regras de dano, acerto e critico.
public class Arma extends Item {
    private final String dano;
    private final String danoCritico;
    private final int acertoMinimo;
    private int criticoMinimo; // removido final para a habilidade do especialista

    // construtor para criar uma nova arma com regras de combate.
    public Arma(String nome, String descricao, String dano, String danoCritico, int acertoMinimo, int criticoMinimo) {
        super(nome, descricao, 1); // armas sao unicas, entao a quantidade e sempre 1
        this.dano = dano;
        this.danoCritico = danoCritico;
        this.acertoMinimo = acertoMinimo;
        this.criticoMinimo = criticoMinimo;
    }

    // --- metodos de modificacao ---

    public void transformarDoOutroLado() {
        super.setNome(super.getNome() + " do Outro Lado");
        System.out.println("sua arma brilha com uma energia roxa e se transforma!");
    }

    public void reduzirCritico(int reducao) {
        this.criticoMinimo -= reducao;
        System.out.println("sua pericia com a arma aumentou! (critico: " + this.criticoMinimo + "+)");
    }

    // --- getters ---
    public String getDano() { return dano; }
    public String getDanoCritico() { return danoCritico; }
    public int getAcertoMinimo() { return acertoMinimo; }
    public int getCriticoMinimo() { return criticoMinimo; }

    @Override
    public void exibirDetalhes() {
        System.out.println("\n--- " + getNome() + " ---");
        System.out.println("descricao: " + getDescricao());
        System.out.println("dano: " + dano);
        System.out.println("critico: " + danoCritico + " / " + criticoMinimo + "+");
        System.out.println("acerto minimo: " + acertoMinimo);
    }
}
