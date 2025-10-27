// representa os cinco atributos basicos de um personagem:
// agilidade, forca, intelecto, presenca e vigor.
public class Atributos {
//lembrar de fazer mais tarde os atributos de rolagem negativa!!!!!!!!!!!
    private final int agilidade;
    private final int forca;
    private final int intelecto;
    private final int presenca;
    private final int vigor;

    // construtor para inicializar os atributos de um personagem.
    //
    // @param agilidade define a coordenacao motora, velocidade de reacao e destreza.
    // @param forca     determina a potencia muscular e habilidade atletica.
    // @param intelecto mede o raciocinio, memoria e educacao geral.
    // @param presenca  define os sentidos, forca de vontade e habilidades sociais.
    // @param vigor     determina a saude e resistencia fisica.
    public Atributos(int agilidade, int forca, int intelecto, int presenca, int vigor) {
        this.agilidade = agilidade;
        this.forca = forca;
        this.intelecto = intelecto;
        this.presenca = presenca;
        this.vigor = vigor;
    }

    // getters para cada atributo

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
