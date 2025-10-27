import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public String escolherModoDeJogo() {
        System.out.println("\nescolha o modo de jogo:");
        System.out.println("1: pvp (jogador vs jogador)");
        System.out.println("2: pvm (jogador vs maquina)");
        System.out.println("3: modo historia");
        System.out.println("4: modo sandbox");

        while (true) {
            try {
                System.out.print("digite o numero do modo: ");
                int escolha = scanner.nextInt();
                if (escolha == 1) return "pvp";
                if (escolha == 2) return "pvm";
                if (escolha == 3) return "historia";
                if (escolha == 4) return "sandbox";
                System.out.println("opcao invalida. por favor, escolha 1, 2, 3 ou 4.");
            } catch (InputMismatchException e) {
                System.out.println("entrada invalida. por favor, digite um numero.");
                scanner.next();
            }
        }
    }

    public String escolherModoDeRolagem() {
        System.out.println("\ncomo voce prefere rolar os dados?");
        System.out.println("1: modo classico (voce rola seus dados e insere o valor)");
        System.out.println("2: via terminal (o programa rola os dados para voce)");

        while (true) {
            try {
                System.out.print("digite o numero do modo: ");
                int escolha = scanner.nextInt();
                if (escolha == 1) {
                    return "classico";
                } else if (escolha == 2) {
                    return "terminal";
                } else {
                    System.out.println("opcao invalida. por favor, escolha 1 ou 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("entrada invalida. por favor, digite um numero.");
                scanner.next();
            }
        }
    }

    public Inimigo escolherInimigo() {
        System.out.println("\nescolha o inimigo que voce ira enfrentar:");
        Inimigo[] inimigos = {
            new Inimigo("Zumbi de Sangue", 30, "1d6"),
            new Inimigo("Zumbi de Sangue Bestial", 50, "1d8+2"),
            new Inimigo("MINOTAURO", 100, "2d8"),
            new Inimigo("Aberracao de Carne", 150, "2d10"),
            new Inimigo("O Diabo", 250, "3d12"),
            new Inimigo("Carnical Preto da Morte", 200, "3d10+5")
        };

        for (int i = 0; i < inimigos.length; i++) {
            System.out.println((i + 1) + ": " + inimigos[i].getNome());
        }

        while (true) {
            try {
                System.out.print("digite o numero do inimigo: ");
                int escolha = scanner.nextInt();
                if (escolha > 0 && escolha <= inimigos.length) {
                    return inimigos[escolha - 1];
                } else {
                    System.out.println("opcao invalida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("entrada invalida. por favor, digite um numero.");
                scanner.next();
            }
        }
    }
}
