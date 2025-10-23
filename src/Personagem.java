/**
 * Representa um personagem simplificado do RPG, com nome, classe, arma e ritual.
 */
public class Personagem {

    private final String nome;
    private final int nex;
    private final Classe classe;
    private final Atributos atributos;
    private final Arma arma;
    private final Ritual ritual;

    // Status de combate
    private int pontosDeVidaAtuais;
    private final int pontosDeVidaMaximos;
    private boolean armaTravada = false;

    public Personagem(String nome, int nex, Classe classe, Atributos atributos, Arma arma, Ritual ritual) {
        this.nome = nome;
        this.nex = nex;
        this.classe = classe;
        this.atributos = atributos;
        this.arma = arma;
        this.ritual = ritual;

        int vidaBase = classe.getPVIniciais();
        int bonusVigor;
        if (classe instanceof Combatente) {
            bonusVigor = atributos.getVigor() * 10;
        } else {
            bonusVigor = atributos.getVigor() * 7;
        }
        this.pontosDeVidaMaximos = vidaBase + bonusVigor;
        this.pontosDeVidaAtuais = this.pontosDeVidaMaximos;
    }

    // --- Métodos de Combate ---

    public void receberDano(int dano) {
        this.pontosDeVidaAtuais -= dano;
        if (this.pontosDeVidaAtuais < 0) {
            this.pontosDeVidaAtuais = 0;
        }
    }

    public void receberCura(int cura) {
        this.pontosDeVidaAtuais += cura;
        if (this.pontosDeVidaAtuais > this.pontosDeVidaMaximos) {
            this.pontosDeVidaAtuais = this.pontosDeVidaMaximos;
        }
    }

    public boolean estaVivo() {
        return this.pontosDeVidaAtuais > 0;
    }

    // --- Métodos para Arma Travada ---

    public boolean isArmaTravada() {
        return armaTravada;
    }

    public void setArmaTravada(boolean travada) {
        this.armaTravada = travada;
    }

    public void destravarArma() {
        this.armaTravada = false;
    }

    // --- Getters ---

    public String getNome() { return nome; }
    public int getPontosDeVidaAtuais() { return pontosDeVidaAtuais; }
    public int getPontosDeVidaMaximos() { return pontosDeVidaMaximos; }
    public Classe getClasse() { return classe; }
    public Atributos getAtributos() { return atributos; }
    public Arma getArma() { return arma; }
    public Ritual getRitual() { return ritual; }
    public int getNex() { return nex; }

    /**
     * Exibe a ficha simplificada do personagem.
     */
    public void exibirFicha() {
        System.out.println("\n--- Ficha do Personagem ---");
        System.out.println("Nome: " + nome);
        System.out.println("NEX: " + nex + "%");
        System.out.println("Classe: " + classe.getNome());

        System.out.println("\n--- Atributos ---");
        System.out.println(atributos);

        System.out.println("\n--- Status ---");
        System.out.println("PV: " + pontosDeVidaAtuais + " / " + pontosDeVidaMaximos);

        System.out.println("\n--- Equipamentos e Rituais ---");
        if (arma != null) {
            System.out.println("Arma: " + arma);
        } else {
            System.out.println("Arma: Desarmado");
        }
        if (ritual != null) {
            System.out.println("Ritual Principal: " + ritual);
        }
        System.out.println("---------------------------");
    }
}
