import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public String escolherModoDeJogo() {
        System.out.println("\nEscolha o modo de jogo:");
        System.out.println("1: PvP (Jogador vs Jogador)");
        System.out.println("2: PvM (Jogador vs Máquina)");
        System.out.println("3: Modo História");
        System.out.println("4: Modo Sandbox");

        while (true) {
            try {
                System.out.print("Digite o número do modo: ");
                int escolha = scanner.nextInt();
                if (escolha == 1) return "pvp";
                if (escolha == 2) return "pvm";
                if (escolha == 3) return "historia";
                if (escolha == 4) return "sandbox";
                System.out.println("Opção inválida. Por favor, escolha 1, 2, 3 ou 4.");
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
            }
        }
    }

    public String escolherModoDeRolagem() {
        System.out.println("\nComo você prefere rolar os dados?");
        System.out.println("1: Modo Clássico (você rola seus dados e insere o valor)");
        System.out.println("2: Via Terminal (o programa rola os dados para você)");

        while (true) {
            try {
                System.out.print("Digite o número do modo: ");
                int escolha = scanner.nextInt();
                if (escolha == 1) {
                    return "classico";
                } else if (escolha == 2) {
                    return "terminal";
                } else {
                    System.out.println("Opção inválida. Por favor, escolha 1 ou 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
            }
        }
    }

    public Inimigo escolherInimigo() {
        System.out.println("\nEscolha o inimigo que você irá enfrentar:");
        Inimigo[] inimigos = {
            new Inimigo("Zumbi de Sangue", 30, "1d6"),
            new Inimigo("Zumbi de Sangue Bestial", 50, "1d8+2"),
            new Inimigo("MINOTAURO", 100, "2d8"),
            new Inimigo("Aberração de Carne", 150, "2d10"),
            new Inimigo("O Diabo", 250, "3d12"),
            new Inimigo("Carniçal Preto da Morte", 200, "3d10+5")
        };

        for (int i = 0; i < inimigos.length; i++) {
            System.out.println((i + 1) + ": " + inimigos[i].getNome());
        }

        while (true) {
            try {
                System.out.print("Digite o número do inimigo: ");
                int escolha = scanner.nextInt();
                if (escolha > 0 && escolha <= inimigos.length) {
                    return inimigos[escolha - 1];
                } else {
                    System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
            }
        }
    }
}
