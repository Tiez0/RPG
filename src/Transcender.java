public class Transcender {

    /**
     * Aumenta o NEX de um personagem com base em sua classe.
     * @param personagem O personagem que irá transcender.
     */
    public static void evoluir(Personagem personagem) {
        if (personagem.getClasse() instanceof Combatente) {
            personagem.aumentarNex(5);
        } else if (personagem.getClasse() instanceof Especialista || personagem.getClasse() instanceof Ocultista) {
            personagem.aumentarNex(7);
        }

        // Futuramente, podemos adicionar aqui a lógica para aplicar os bônus de NEX (vida, dano, etc.)
    }
}
