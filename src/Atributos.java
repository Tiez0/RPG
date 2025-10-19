/**
 * Representa os cinco atributos básicos de um personagem:
 * Agilidade, Força, Intelecto, Presença e Vigor.
 */
public class Atributos {
//LEMBRAR DE FAZER MAIS TARDE OS ATRIBUTOS DE ROLAGEM NEGATIVA!!!!!!!!!!!
    private final int agilidade;
    private final int forca;
    private final int intelecto;
    private final int presenca;
    private final int vigor;

    /**
     * Construtor para inicializar os atributos de um personagem.
     *
     * @param agilidade Define a coordenação motora, velocidade de reação e destreza.
     * @param forca     Determina a potência muscular e habilidade atlética.
     * @param intelecto Mede o raciocínio, memória e educação geral.
     * @param presenca  Define os sentidos, força de vontade e habilidades sociais.
     * @param vigor     Determina a saúde e resistência física.
     */
    public Atributos(int agilidade, int forca, int intelecto, int presenca, int vigor) {
        this.agilidade = agilidade;
        this.forca = forca;
        this.intelecto = intelecto;
        this.presenca = presenca;
        this.vigor = vigor;
    }

    // Getters para cada atributo

    public int getAgilidade() {
        return agilidade;
    }

    public int getForca() {
        return forca;
    }

    public int getIntelecto() {
        return intelecto;
    }

    public int getPresenca() {
        return presenca;
    }

    public int getVigor() {
        return vigor;
    }

    @Override
    public String toString() {
        return "Atributos:" +
                "\n  Agilidade=" + agilidade +
                "\n  Força=" + forca +
                "\n  Intelecto=" + intelecto +
                "\n  Presença=" + presenca +
                "\n  Vigor=" + vigor;
    }
}
