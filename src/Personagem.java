import java.util.ArrayList;
import java.util.List;

/**
 * Representa um sobrevivente no mundo pós-apocalíptico do "Eco".
 */
public class Personagem {

    private final String nome;
    private final int nex; // Internamente, ainda é NEX, mas para o jogador é NES
    private final Classe classe;
    private final Arma arma;
    private final Ritual ritual; // Representa uma Técnica de Sobrevivência

    // Status de combate
    private int pontosDeVidaAtuais;
    private final int pontosDeVidaMaximos;
    private boolean armaTravada = false; // Novo campo para controlar a arma

    public Personagem(String nome, int nex, Classe classe, Arma arma, Ritual ritual) {
        this.nome = nome;
        this.nex = nex;
        this.classe = classe;
        this.arma = arma;
        this.ritual = ritual;

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
    public Arma getArma() { return arma; }
    public Ritual getRitual() { return ritual; }
    public int getNex() { return nex; }

    /**
     * Exibe o registro do sobrevivente.
     */
    public void exibirFicha() {
        System.out.println("\n--- REGISTRO DO SOBREVIVENTE ---");
        System.out.println("Nome: " + nome);
        System.out.println("NES (Nível de Estresse): " + nex + "%");
        System.out.println("Arquétipo: " + classe.getNome());

        System.out.println("\n--- Condição ---");
        System.out.println("PV: " + pontosDeVidaAtuais + " / " + pontosDeVidaMaximos);

        System.out.println("\n--- Equipamento e Técnicas ---");
        if (arma != null) {
            System.out.println("Arma: " + arma);
        } else {
            System.out.println("Arma: Desarmado");
        }
        if (ritual != null) {
            System.out.println("Técnica Principal: " + ritual);
        }

        System.out.println("\n--- Habilidades de Sobrevivência ---");
        List<String> pericias = new ArrayList<>(classe.getPericiasTreinadas());
        System.out.println("Habilidades Treinadas: " + pericias);

        System.out.println("\n--- Aptidões e Vantagens ---");
        System.out.println("Proficiências: " + classe.getProficiencias());
        System.out.println("Habilidades de Arquétipo: " + classe.getHabilidades(nex));
        System.out.println("---------------------------");
    }
}
