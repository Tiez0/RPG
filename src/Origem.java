import java.util.List;

/**
 * Representa a vida pregressa de um personagem antes de se envolver com o paranormal.
 * Define as perícias treinadas e um poder especial concedido pela origem.
 */
public abstract class Origem {

    /**
     * Retorna a lista de perícias treinadas concedidas por esta origem.
     *
     * @return Uma lista de strings com os nomes das perícias.
     */
    public abstract List<String> getPericiasTreinadas();

    /**
     * Retorna a descrição do poder especial concedido por esta origem.
     *
     * @return Uma string descrevendo o poder.
     */
    public abstract String getPoder();

    /**
     * Retorna o nome da origem.
     *
     * @return O nome da origem.
     */
    public abstract String getNome();

    @Override
    public String toString() {
        return getNome() + ": " + getPoder();
    }
}
