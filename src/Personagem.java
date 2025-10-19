import java.util.ArrayList;
import java.util.List;

/**
 * Representa um personagem simplificado do RPG, com nome, classe, arma e ritual.
 */
public class Personagem {

    private final String nome;
    private final int nex;
    private final Classe classe;
    private final Arma arma;
    private final Ritual ritual;

    // Status de combate
    private int pontosDeVidaAtuais;
    private final int pontosDeVidaMaximos;

    public Personagem(String nome, int nex, Classe classe, Arma arma, Ritual ritual) {
        this.nome = nome;
        this.nex = nex;
        this.classe = classe;
        this.arma = arma;
        this.ritual = ritual;

        // Cálculo de PV simplificado, sem atributos
        int niveis = nex / 5;
        this.pontosDeVidaMaximos = (classe.getPVIniciais() * niveis);
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

    // --- Getters ---

    public String getNome() { return nome; }
    public int getPontosDeVidaAtuais() { return pontosDeVidaAtuais; }
    public int getPontosDeVidaMaximos() { return pontosDeVidaMaximos; }
    public Classe getClasse() { return classe; }
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

        System.out.println("\n--- Perícias ---");
        // Chamada de perícias simplificada, sem Intelecto
        List<String> pericias = new ArrayList<>(classe.getPericiasTreinadas());
        System.out.println("Perícias Treinadas: " + pericias);

        System.out.println("\n--- Habilidades e Poderes ---");
        System.out.println("Proficiências: " + classe.getProficiencias());
        System.out.println("Habilidades de Classe: " + classe.getHabilidades(nex));
        System.out.println("---------------------------");
    }
}
