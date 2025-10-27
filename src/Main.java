import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Bem-vindo ao Ordem Paranormal RPG (Versão Simplificada) ---");

        Menu menu = new Menu(scanner);
        String modoDeJogo = menu.escolherModoDeJogo();

        if (modoDeJogo.equals("historia")) {
            ModoHistoria.iniciar(scanner);
        } else if (modoDeJogo.equals("sandbox")) {
            Sandbox.iniciar(scanner);
        } else { // PvP ou PvM
            String modoDeRolagem = menu.escolherModoDeRolagem();
            GerenciadorDeCombate gerenciadorDeCombate = new GerenciadorDeCombate(scanner, modoDeRolagem);

            if (modoDeJogo.equals("pvp")) {
                System.out.println("\n--- CRIAÇÃO DO JOGADOR 1 ---");
                Personagem jogador1 = CriadorDePersonagem.criar(scanner, "Jogador 1", modoDeRolagem);
                jogador1.exibirFicha();

                System.out.println("\n--- CRIAÇÃO DO JOGADOR 2 ---");
                Personagem jogador2 = CriadorDePersonagem.criar(scanner, "Jogador 2", modoDeRolagem);
                jogador2.exibirFicha();

                gerenciadorDeCombate.iniciarCombatePvP(jogador1, jogador2);

            } else { // Modo PVM
                System.out.println("\n--- CRIAÇÃO DE PERSONAGEM ---");
                Personagem jogador = CriadorDePersonagem.criar(scanner, "Jogador", modoDeRolagem);
                jogador.exibirFicha();

                Inimigo inimigo = menu.escolherInimigo();
                gerenciadorDeCombate.iniciarCombatePvM(jogador, inimigo);
            }
        }

        scanner.close();
        System.out.println("\nPrograma finalizado.");
    }
}
