import java.util.List;

/**
 * Representa a classe de um personagem, como Combatente, Especialista ou Ocultista.
 * Define os pontos de vida, perícias e habilidades iniciais.
 */
public abstract class Classe {

    /**
     * Retorna o nome da classe.
     */
    public abstract String getNome();

    /**
     * Retorna os Pontos de Vida (PV) base que o personagem ganha por nível de NEX.
     */
    public abstract int getPVIniciais();

    /**
     * Retorna a lista de perícias treinadas concedidas pela classe.
     * O número de perícias agora é fixo por classe.
     */
    public abstract List<String> getPericiasTreinadas();

    /**
     * Retorna a lista de proficiências da classe.
     */
    public abstract List<String> getProficiencias();

    /**
     * Retorna a lista de habilidades para um determinado Nível de Exposição Paranormal (NEX).
     * @param nex O NEX do personagem.
     * @return Uma lista de strings com as habilidades.
     */
    public abstract List<String> getHabilidades(int nex);

}
