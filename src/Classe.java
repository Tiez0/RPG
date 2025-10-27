// representa a classe de um personagem, como combatente, especialista ou ocultista.
// define os pontos de vida iniciais.
// esta e uma classe abstrata, o que significa que ela nao pode ser instanciada diretamente.
// ela serve como um modelo para outras classes (combatente, especialista, ocultista)
// que vao herdar seus metodos e implementar suas proprias versoes.
public abstract class Classe {

    // retorna o nome da classe.
    public abstract String getNome();

    // retorna os pontos de vida (pv) base que o personagem ganha por nivel de nex.
    public abstract int getPVIniciais();

}
