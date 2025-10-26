import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CriadorDePersonagem {

    public static Personagem criar(Scanner scanner, String tituloJogador, String modoDeRolagem) {
        if (tituloJogador.equals("Jogador 1") || tituloJogador.equals("Jogador")) {
            scanner.nextLine(); 
        }

        System.out.print("\nDigite o nome do " + tituloJogador + ": ");
        String nome = scanner.nextLine();

        int nex = escolherNEX(scanner);
        Classe classe = escolherClasse(scanner);
        Atributos atributos = distribuirAtributos(scanner, nex);

        Arma arma = null;
        Ritual ritual = null;

        if (classe instanceof Combatente || classe instanceof Especialista) {
            arma = escolherArma(scanner, classe, nex);
        } else if (classe instanceof Ocultista) {
            arma = new Arma("Adaga Ritualística", "1d6", "2d6", 10, 20);
            ritual = escolherRitual(scanner, nex);
        }

        return new Personagem(nome, nex, classe, atributos, arma, ritual);
    }

    private static Atributos distribuirAtributos(Scanner scanner, int nex) {
        System.out.println("\n--- Distribuição de Atributos ---");
        int pontos;
        switch (nex) {
            case 5: pontos = 4; break;
            case 15: pontos = 5; break;
            case 25: pontos = 6; break;
            case 30: pontos = 7; break;
            case 45: pontos = 8; break;
            case 50: pontos = 10; break;
            default: pontos = 0;
        }

        System.out.println("Você tem " + pontos + " pontos para distribuir entre Agilidade, Força, Intelecto, Presença e Vigor.");
        System.out.println("Cada atributo começa em 1 e o valor máximo é 6.");

        int agi = 1, forca = 1, inte = 1, pres = 1, vig = 1;

        while (pontos > 0) {
            System.out.println("\nPontos restantes: " + pontos);
            System.out.println("1: Agilidade (" + agi + ")");
            System.out.println("2: Força (" + forca + ")");
            System.out.println("3: Intelecto (" + inte + ")");
            System.out.println("4: Presença (" + pres + ")");
            System.out.println("5: Vigor (" + vig + ")");

            try {
                System.out.print("Escolha um atributo para aumentar: ");
                int escolha = scanner.nextInt();
                System.out.print("Quantos pontos (1-" + pontos + "): ");
                int valor = scanner.nextInt();

                if (valor <= 0 || valor > pontos) {
                    System.out.println("Valor inválido.");
                    continue;
                }

                switch (escolha) {
                    case 1: if (agi + valor > 6) { System.out.println("Atributo não pode exceder 6."); continue; } agi += valor; break;
                    case 2: if (forca + valor > 6) { System.out.println("Atributo não pode exceder 6."); continue; } forca += valor; break;
                    case 3: if (inte + valor > 6) { System.out.println("Atributo não pode exceder 6."); continue; } inte += valor; break;
                    case 4: if (pres + valor > 6) { System.out.println("Atributo não pode exceder 6."); continue; } pres += valor; break;
                    case 5: if (vig + valor > 6) { System.out.println("Atributo não pode exceder 6."); continue; } vig += valor; break;
                    default: System.out.println("Opção inválida."); continue;
                }
                pontos -= valor;

            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida.");
                scanner.next();
            }
        }

        return new Atributos(agi, forca, inte, pres, vig);
    }

    private static Arma escolherArma(Scanner scanner, Classe classe, int nex) {
        if (classe instanceof Combatente) {
            System.out.println("\nComo um Combatente, escolha sua arma inicial:");
            System.out.println("1: Machado (Acerto: 12+, Crítico: 20)");
            System.out.println("2: Katana (Acerto: 10+, Crítico: 19+)");
            if (nex >= 50) System.out.println("3: Ereshkigal (Acerto: 2+, Crítico: 15+)");

            while (true) {
                try {
                    System.out.print("Digite o número da arma: ");
                    int escolha = scanner.nextInt();
                    switch (escolha) {
                        case 1: return new Arma("Machado", "1d8", "3d8", 12, 20);
                        case 2: return new Arma("Katana", "1d10", "19", 10, 19);
                        case 3: if (nex >= 50) return new Arma("Ereshkigal", "2d12", "4d12", 2, 15);
                        default: System.out.println("Opção inválida.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida.");
                    scanner.next();
                }
            }
        } else if (classe instanceof Especialista) {
            System.out.println("\nComo um Especialista, escolha sua arma inicial:");
            System.out.println("1: Besta (Acerto: 11+, Crítico: 20)");
            System.out.println("2: Revólver (Acerto: 10+, Crítico: 19+)");
            System.out.println("3: Fuzil de Caça (Acerto: 12+, Crítico: 19+)");
            if (nex >= 50) System.out.println("4: Fuzil de Precisão Abutre (Acerto: 12+, Crítico: 18+)");

            while (true) {
                try {
                    System.out.print("Digite o número da arma: ");
                    int escolha = scanner.nextInt();
                    switch (escolha) {
                        case 1: return new Arma("Besta", "1d8", "19", 11, 20);
                        case 2: return new Arma("Revólver", "2d6", "6d6", 10, 19);
                        case 3: return new Arma("Fuzil de Caça", "2d8", "4d8", 12, 19);
                        case 4: if (nex >= 50) return new Arma("Fuzil de Precisão Abutre", "2d10", "10d10", 12, 18);
                        default: System.out.println("Opção inválida.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida.");
                    scanner.next();
                }
            }
        }
        return null;
    }

    private static Classe escolherClasse(Scanner scanner) {
        System.out.println("\nEscolha a classe do seu personagem:");
        System.out.println("1: Combatente");
        System.out.println("2: Especialista");
        System.out.println("3: Ocultista");
        while (true) {
            try {
                System.out.print("Digite o número da classe: ");
                int escolha = scanner.nextInt();
                switch (escolha) {
                    case 1: return new Combatente();
                    case 2: return new Especialista();
                    case 3: return new Ocultista();
                    default: System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida.");
                scanner.next();
            }
        }
    }

    private static Ritual escolherRitual(Scanner scanner, int nex) {
        System.out.println("\nComo um Ocultista, você recebe uma Adaga Ritualística (1d6) e escolhe seu ritual principal:");
        List<Ritual> rituaisDisponiveis = RitualData.getRitualsDisponiveis(nex);

        for (int i = 0; i < rituaisDisponiveis.size(); i++) {
            Ritual r = rituaisDisponiveis.get(i);
            System.out.println((i + 1) + ": " + r.getNome() + " (" + r.getDescricao() + ")");
        }

        while (true) {
            try {
                System.out.print("Digite o número do ritual: ");
                int escolha = scanner.nextInt();
                if (escolha > 0 && escolha <= rituaisDisponiveis.size()) {
                    return rituaisDisponiveis.get(escolha - 1);
                } else {
                    System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
            }
        }
    }

    private static int escolherNEX(Scanner scanner) {
        System.out.println("\nDefina o Nível de Exposição Paranormal (NEX).");
        int[] nexOptions = {5, 15, 25, 30, 45, 50};
        
        System.out.println("Escolha um dos níveis de NEX disponíveis:");
        for (int i = 0; i < nexOptions.length; i++) {
            System.out.println((i + 1) + ": NEX " + nexOptions[i] + "%");
        }

        while (true) {
            try {
                System.out.print("Digite o número da opção de NEX: ");
                int escolha = scanner.nextInt();
                if (escolha > 0 && escolha <= nexOptions.length) {
                    return nexOptions[escolha - 1];
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
