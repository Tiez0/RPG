/**
 * Representa um inimigo (monstro) no RPG.
 */
public class Inimigo {
    private final String nome;
    private int pontosDeVidaAtuais;
    private final int pontosDeVidaMaximos;
    private final String dano;

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
