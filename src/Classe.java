/**
 * Representa a classe de um personagem, como Combatente, Especialista ou Ocultista.
 * Define os pontos de vida iniciais.
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

}
