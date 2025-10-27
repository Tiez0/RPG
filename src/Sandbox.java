import java.util.Scanner;

public class Sandbox {

    public static void iniciar(Scanner scanner) {
        System.out.println("\n--- MODO SANDBOX ---");

        Menu menu = new Menu(scanner);
        String modoDeRolagem = menu.escolherModoDeRolagem();
        GerenciadorDeCombate gerenciadorDeCombate = new GerenciadorDeCombate(scanner, modoDeRolagem);

        System.out.println("\n--- CRIAÇÃO DE PERSONAGEM ---");
        Personagem jogador = CriadorDePersonagem.criar(scanner, "Jogador", modoDeRolagem);
        jogador.exibirFicha();

        Inimigo maligno = new Inimigo("MALIGNO", 100, "10");
        System.out.println("\nUm MALIGNO surge das sombras para desafiá-lo!");

        gerenciadorDeCombate.iniciarCombatePvM(jogador, maligno);
    }
}
