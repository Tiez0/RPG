import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- bem-vindo ao ordem paranormal rpg (versao simplificada) ---");

        Menu menu = new Menu(scanner);

        // primeiro, escolhe o modo de rolagem
        String modoDeRolagem = menu.escolherModoDeRolagem();

        // depois, escolhe o modo de jogo
        String modoDeJogo = menu.escolherModoDeJogo();

        if (modoDeJogo.equals("historia")) {
            ModoHistoria.iniciar(scanner, modoDeRolagem);
        } else if (modoDeJogo.equals("sandbox")) {
            Sandbox.iniciar(scanner);
        } else { // pvp ou pvm
            GerenciadorDeCombate gerenciadorDeCombate = new GerenciadorDeCombate(scanner, modoDeRolagem);

            if (modoDeJogo.equals("pvp")) {
                System.out.println("\n--- criacao do jogador 1 ---");
                Personagem jogador1 = CriadorDePersonagem.criar(scanner, "Jogador 1", modoDeRolagem);
                jogador1.exibirFicha();

                System.out.println("\n--- criacao do jogador 2 ---");
                Personagem jogador2 = CriadorDePersonagem.criar(scanner, "Jogador 2", modoDeRolagem);
                jogador2.exibirFicha();

                gerenciadorDeCombate.iniciarCombatePvP(jogador1, jogador2);

            } else { // modo pvm
                System.out.println("\n--- criacao de personagem ---");
                Personagem jogador = CriadorDePersonagem.criar(scanner, "Jogador", modoDeRolagem);
                jogador.exibirFicha();

                Inimigo inimigo = menu.escolherInimigo();
                gerenciadorDeCombate.iniciarCombatePvM(jogador, inimigo);
            }
        }

        scanner.close();
        System.out.println("\nprograma finalizado.");
    }
}
