import java.util.InputMismatchException;
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
        
        // Concede um ponto de atributo para ser distribuído
        System.out.println("\nVocê recebeu 1 ponto de atributo para distribuir!");
        distribuirPontoDeAtributo(scanner, personagem);

        GerenciadorDeProgressao.aplicarProgressao(scanner, personagem);
    }

    private static void distribuirPontoDeAtributo(Scanner scanner, Personagem personagem) {
        while (true) {
            System.out.println("\nEscolha um atributo para aumentar (limite de 6 pontos):");
            Atributos attrs = personagem.getAtributos();
            System.out.println("1: Agilidade (" + attrs.getAgilidade() + ")");
            System.out.println("2: Força (" + attrs.getForca() + ")");
            System.out.println("3: Intelecto (" + attrs.getIntelecto() + ")");
            System.out.println("4: Presença (" + attrs.getPresenca() + ")");
            System.out.println("5: Vigor (" + attrs.getVigor() + ")");

            try {
                System.out.print("Escolha uma opção: ");
                int escolha = scanner.nextInt();
                String atributoEscolhido = null;
                int valorAtual = 0;

                switch (escolha) {
                    case 1: atributoEscolhido = "agilidade"; valorAtual = attrs.getAgilidade(); break;
                    case 2: atributoEscolhido = "forca"; valorAtual = attrs.getForca(); break;
                    case 3: atributoEscolhido = "intelecto"; valorAtual = attrs.getIntelecto(); break;
                    case 4: atributoEscolhido = "presenca"; valorAtual = attrs.getPresenca(); break;
                    case 5: atributoEscolhido = "vigor"; valorAtual = attrs.getVigor(); break;
                    default: System.out.println("Opção inválida."); continue;
                }

                if (valorAtual >= 6) {
                    System.out.println("Este atributo já atingiu o valor máximo (6). Escolha outro.");
                    continue;
                }

                personagem.aumentarAtributo(atributoEscolhido, 1);
                System.out.println("\n" + atributoEscolhido.substring(0, 1).toUpperCase() + atributoEscolhido.substring(1) + " aumentado!");
                personagem.exibirFicha(); // Mostra a ficha atualizada
                break; // Sai do loop após a escolha

            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
            }
        }
    }
}
