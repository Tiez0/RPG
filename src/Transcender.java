import java.util.Scanner;

public class Transcender {

    private static final int[] MARCOS_NEX = {5, 15, 25, 30, 45, 50};

    // avanca o personagem para o proximo marco de nex e aplica as progressoes.
    // @param personagem o personagem que ira transcender.
    // @param scanner o scanner para entrada do usuario, necessario para a escolha de rituais.
    public static void evoluir(Personagem personagem, Scanner scanner) {
        int nexAtual = personagem.getNex();
        int proximoNex = nexAtual;

        // encontra o proximo marco de nex na sequencia
        for (int marco : MARCOS_NEX) {
            if (marco > nexAtual) {
                proximoNex = marco;
                break;
            }
        }

        // se ja estiver no nex maximo, nao faz nada
        if (proximoNex == nexAtual) {
            System.out.println("\n" + personagem.getNome() + " ja atingiu o nex maximo!");
            return;
        }

        // define o novo nex e aplica as progressoes
        personagem.setNex(proximoNex);
        GerenciadorDeProgressao.aplicarProgressao(scanner, personagem);
    }
}
