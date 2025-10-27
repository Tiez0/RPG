/**
 * Representa um inimigo (monstro) no RPG.
 */
public class Inimigo {
    private final String nome;
    private int pontosDeVidaAtuais;
    private final int pontosDeVidaMaximos;
    private final String dano;

    // Efeitos de status (ex: Cinerária)
    private int cinerariaDanoTurnos = 0;
    private int cinerariaDebuffTurnos = 0;

    /**
     * Construtor para criar um novo inimigo.
     *
     * @param nome         O nome do inimigo (ex: Zumbi de Sangue).
     * @param pontosDeVida A quantidade de vida máxima do inimigo.
     * @param dano         O dano base do ataque do inimigo (ex: 1d6).
     */
    public Inimigo(String nome, int pontosDeVida, String dano) {
        this.nome = nome;
        this.pontosDeVidaMaximos = pontosDeVida;
        this.pontosDeVidaAtuais = pontosDeVida;
        this.dano = dano;
    }

    public void receberDano(int dano) {
        this.pontosDeVidaAtuais -= dano;
        if (this.pontosDeVidaAtuais < 0) {
            this.pontosDeVidaAtuais = 0;
        }
    }

    public boolean estaVivo() {
        return this.pontosDeVidaAtuais > 0;
    }

    // --- Métodos para Efeitos de Status ---

    public void aplicarCineraria() {
        this.cinerariaDanoTurnos = 5; // 1d6 de dano por 5 rodadas
        this.cinerariaDebuffTurnos = 6; // Metade do dano por 6 rodadas
        System.out.println(this.nome + " começa a ser consumido por cinzas!");
    }

    public void processarEfeitosDeStatus() {
        if (this.cinerariaDanoTurnos > 0) {
            this.cinerariaDanoTurnos--;
        }
        if (this.cinerariaDebuffTurnos > 0) {
            this.cinerariaDebuffTurnos--;
        }
    }

    public int getCinerariaDanoTurnos() {
        return cinerariaDanoTurnos;
    }

    public boolean estaDebuffado() {
        return cinerariaDebuffTurnos > 0;
    }

    // --- Getters ---

    public String getNome() {
        return nome;
    }

    public int getPontosDeVidaAtuais() {
        return pontosDeVidaAtuais;
    }

    public int getPontosDeVidaMaximos() {
        return pontosDeVidaMaximos;
    }

    public String getDano() {
        return dano;
    }

    @Override
    public String toString() {
        return nome + " (PV: " + pontosDeVidaAtuais + " / " + pontosDeVidaMaximos + ", Dano: " + dano + ")";
    }
}
