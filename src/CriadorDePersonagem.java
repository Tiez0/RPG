import java.util.ArrayList;
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

        Arma armaInicial = atribuirArmaInicial(classe);
        List<Ritual> rituaisIniciais = new ArrayList<>();

        Personagem novoPersonagem = new Personagem(nome, nex, classe, atributos, armaInicial, rituaisIniciais);

        gerenciarProgressaoArma(scanner, novoPersonagem);
        gerenciarProgressaoRitual(scanner, novoPersonagem);

        return novoPersonagem;
    }

    private static Arma atribuirArmaInicial(Classe classe) {
        if (classe instanceof Combatente) {
            System.out.println("Você recebeu um Taco de Beisebol como arma inicial.");
            return new Arma("Taco de Beisebol", "2d4", "2d4", 10, 20);
        } else if (classe instanceof Especialista) {
            System.out.println("Você recebeu uma Besta como arma inicial.");
            return new Arma("Besta", "1d8", "12", 10, 19);
        } else if (classe instanceof Ocultista) {
            System.out.println("Você recebeu uma Adaga Ritualística como arma inicial.");
            return new Arma("Adaga Ritualística", "1d6", "2d6", 10, 20);
        }
        return null; // Nunca deve acontecer
    }

    private static void gerenciarProgressaoArma(Scanner scanner, Personagem personagem) {
        // Lógica para Combatente
        if (personagem.getClasse() instanceof Combatente) {
            if (personagem.getNex() >= 30) {
                // Se a arma atual não for o Taco de Beisebol, transforma em "do Outro Lado"
                if (!personagem.getArma().getNome().equals("Taco de Beisebol")) {
                    personagem.getArma().transformarDoOutroLado();
                }
            }
            if (personagem.getNex() >= 50) {
                System.out.println("Você atingiu NEX 50! Recebeu a arma Ereshkigal!");
                personagem.setArma(new Arma("Ereshkigal", "2d12", "4d12", 2, 15));
            }
        }
        // Lógica para Especialista
        else if (personagem.getClasse() instanceof Especialista) {
            if (personagem.getNex() >= 15) {
                personagem.getArma().reduzirCritico(1);
            }
            if (personagem.getNex() >= 30) {
                personagem.getArma().reduzirCritico(1); // Mais 1 de redução
                personagem.getArma().transformarDoOutroLado();
            }
            if (personagem.getNex() >= 50) {
                System.out.println("Você atingiu NEX 50! Recebeu o Fuzil de Precisão Abutre!");
                personagem.setArma(new Arma("Fuzil de Precisão Abutre", "2d10", "10d10", 12, 18));
            }
        }
    }

    private static void gerenciarProgressaoRitual(Scanner scanner, Personagem personagem) {
        if (personagem.getClasse() instanceof Ocultista) {
            int rituaisParaEscolher = 0;
            if (personagem.getNex() >= 5) rituaisParaEscolher = 1;
            if (personagem.getNex() >= 15) rituaisParaEscolher = 2;
            if (personagem.getNex() >= 30) rituaisParaEscolher = 3;

            for (int i = 0; i < rituaisParaEscolher; i++) {
                System.out.println("\nEscolha seu ritual (Ritual " + (i + 1) + "):");
                List<Ritual> rituaisDisponiveis = RitualData.getRitualsDisponiveis(personagem.getNex());
                // Remover rituais já aprendidos para não escolher repetido
                rituaisDisponiveis.removeAll(personagem.getRituais());

                if (rituaisDisponiveis.isEmpty()) {
                    System.out.println("Não há mais rituais disponíveis para aprender neste NEX.");
                    break;
                }

                for (int j = 0; j < rituaisDisponiveis.size(); j++) {
                    Ritual r = rituaisDisponiveis.get(j);
                    System.out.println((j + 1) + ": " + r.getNome() + " (" + r.getDescricao() + ")");
                }

                while (true) {
                    try {
                        System.out.print("Digite o número do ritual: ");
                        int escolha = scanner.nextInt();
                        if (escolha > 0 && escolha <= rituaisDisponiveis.size()) {
                            personagem.adicionarRitual(rituaisDisponiveis.get(escolha - 1));
                            break;
                        } else {
                            System.out.println("Opção inválida.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida. Por favor, digite um número.");
                        scanner.next();
                    }
                }
            }

            // Cinerária automática no NEX 50
            if (personagem.getNex() >= 50) {
                boolean jaTemCineraria = false;
                for (Ritual r : personagem.getRituais()) {
                    if (r.getNome().equals("Cinerária")) {
                        jaTemCineraria = true;
                        break;
                    }
                }
                if (!jaTemCineraria) {
                    Ritual cineraria = new Ritual("Cinerária", "Dano contínuo e debuff.", "Dano contínuo", 15, "2d6");
                    personagem.adicionarRitual(cineraria);
                    System.out.println("Você atingiu NEX 50! Aprendeu o ritual Cinerária automaticamente!");
                }
            }
        }
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
