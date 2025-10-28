import java.util.Scanner;

public class Sandbox {

    public static void iniciar(Scanner scanner) {
        System.out.println("\n--- modo sandbox ---");

        Menu menu = new Menu(scanner);
        String modoDeRolagem = menu.escolherModoDeRolagem();
        GerenciadorDeCombate gerenciadorDeCombate = new GerenciadorDeCombate(scanner, modoDeRolagem);

        System.out.println("\n--- criacao de personagem ---");
        Personagem jogador = CriadorDePersonagem.criar(scanner, "Jogador", modoDeRolagem);
        jogador.exibirFicha();

        // Habilita a transcendência para o modo Sandbox
        jogador.habilitarTranscender();
        System.out.println("\n*** A energia do Sandbox flui em você. Você pode transcender neste combate. ***");

        Inimigo maligno = new Inimigo("MALIGNO", 100, "5");
        System.out.println("\num maligno surge das sombras para desafia-lo!");

        gerenciadorDeCombate.iniciarCombatePvM(jogador, maligno);
    }
}
