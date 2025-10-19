import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static String modoDeRolagem;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Bem-vindo ao Ordem Paranormal RPG (Versão Simplificada) ---");

        String modoDeJogo = escolherModoDeJogo(scanner);
        escolherModoDeRolagem(scanner);

        if (modoDeJogo.equals("pvp")) {
            System.out.println("\n--- CRIAÇÃO DO JOGADOR 1 ---");
            Personagem jogador1 = criarPersonagem(scanner, "Jogador 1");
            jogador1.exibirFicha();

            System.out.println("\n--- CRIAÇÃO DO JOGADOR 2 ---");
            Personagem jogador2 = criarPersonagem(scanner, "Jogador 2");
            jogador2.exibirFicha();

            iniciarCombatePvP(scanner, jogador1, jogador2);

        } else { // Modo PVM
            System.out.println("\n--- CRIAÇÃO DE PERSONAGEM ---");
            Personagem jogador = criarPersonagem(scanner, "Jogador");
            jogador.exibirFicha();

            Inimigo inimigo = escolherInimigo(scanner);
            iniciarCombatePvM(scanner, jogador, inimigo);
        }

        scanner.close();
        System.out.println("\nPrograma finalizado.");
    }

    private static Personagem criarPersonagem(Scanner scanner, String tituloJogador) {
        // Limpa o buffer do scanner
        if (tituloJogador.equals("Jogador 1") || tituloJogador.equals("Jogador")) {
            scanner.nextLine(); 
        }

        System.out.print("\nDigite o nome do " + tituloJogador + ": ");
        String nome = scanner.nextLine();

        int nex = escolherNEX(scanner);
        Classe classe = escolherClasse(scanner);

        Arma arma = null;
        Ritual ritual = null;

        if (classe instanceof Combatente || classe instanceof Especialista) {
            arma = escolherArma(scanner, classe, nex);
        } else if (classe instanceof Ocultista) {
            arma = new Arma("Adaga Ritualística", "1d6");
            ritual = escolherRitual(scanner, nex);
        }

        // Atributos foram removidos, então não há mais distribuição de pontos.

        // Construtor simplificado sem Atributos
        return new Personagem(nome, nex, classe, arma, ritual);
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

    private static Arma escolherArma(Scanner scanner, Classe classe, int nex) {
        if (classe instanceof Combatente) {
            System.out.println("\nComo um Combatente, escolha sua arma inicial:");
            System.out.println("1: Machado (Dano: 1d8)");
            System.out.println("2: Katana (Dano: 1d10)");
            if (nex >= 50) {
                System.out.println("3: Ereshkigal (Dano: 2d12) - Requer NEX 50%");
            }

            while (true) {
                try {
                    System.out.print("Digite o número da arma: ");
                    int escolha = scanner.nextInt();
                    switch (escolha) {
                        case 1: return new Arma("Machado", "1d8");
                        case 2: return new Arma("Katana", "1d10");
                        case 3:
                            if (nex >= 50) return new Arma("Ereshkigal", "2d12");
                        default: System.out.println("Opção inválida.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida.");
                    scanner.next();
                }
            }
        } else if (classe instanceof Especialista) {
            System.out.println("\nComo um Especialista, escolha sua arma inicial:");
            System.out.println("1: Besta (Dano: 1d8)");
            System.out.println("2: Revólver (Dano: 2d6)");
            System.out.println("3: Fuzil de Caça (Dano: 2d8)");
            if (nex >= 50) {
                System.out.println("4: Fuzil de Precisão Abutre (Dano: 2d10) - Requer NEX 50%");
            }

            while (true) {
                try {
                    System.out.print("Digite o número da arma: ");
                    int escolha = scanner.nextInt();
                    switch (escolha) {
                        case 1: return new Arma("Besta", "1d8");
                        case 2: return new Arma("Revólver", "2d6");
                        case 3: return new Arma("Fuzil de Caça", "2d8");
                        case 4:
                            if (nex >= 50) return new Arma("Fuzil de Precisão Abutre", "2d10");
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

    private static void iniciarCombatePvP(Scanner scanner, Personagem j1, Personagem j2) {
        System.out.println("\n========================================");
        System.out.println("INÍCIO DO COMBATE: " + j1.getNome() + " vs " + j2.getNome());
        System.out.println("========================================");

        int round = 1;
        while (j1.estaVivo() && j2.estaVivo()) {
            System.out.println("\n--- Round " + round + " ---");
            System.out.println("Status: " + j1.getNome() + " [PV: " + j1.getPontosDeVidaAtuais() + "] | " + j2.getNome() + " [PV: " + j2.getPontosDeVidaAtuais() + "]");

            realizarTurnoJogador(scanner, j1, j2);
            if (!j2.estaVivo()) break;

            realizarTurnoJogador(scanner, j2, j1);
            if (!j1.estaVivo()) break;

            round++;
        }

        System.out.println("\n--- FIM DE COMBATE ---");
        if (j1.estaVivo()) {
            System.out.println("O vencedor é: " + j1.getNome() + "!");
        } else {
            System.out.println("O vencedor é: " + j2.getNome() + "!");
        }
    }

    private static void iniciarCombatePvM(Scanner scanner, Personagem jogador, Inimigo inimigo) {
        System.out.println("\n========================================");
        System.out.println("INÍCIO DO COMBATE: " + jogador.getNome() + " vs " + inimigo.getNome());
        System.out.println("========================================");

        int round = 1;
        while (jogador.estaVivo() && inimigo.estaVivo()) {
            System.out.println("\n--- Round " + round + " ---");
            System.out.println("Status: " + jogador.getNome() + " [PV: " + jogador.getPontosDeVidaAtuais() + "] | " + inimigo.getNome() + " [PV: " + inimigo.getPontosDeVidaAtuais() + "]");

            realizarTurnoJogador(scanner, jogador, inimigo);
            if (!inimigo.estaVivo()) break;

            realizarTurnoInimigo(scanner, inimigo, jogador);
            if (!jogador.estaVivo()) break;

            round++;
        }

        System.out.println("\n--- FIM DE COMBATE ---");
        if (jogador.estaVivo()) {
            System.out.println("O vencedor é: " + jogador.getNome() + "!");
        } else {
            System.out.println("O vencedor é: " + inimigo.getNome() + "!");
        }
    }

    private static void realizarTurnoJogador(Scanner scanner, Personagem atacante, Object alvo) {
        System.out.println("\nÉ o turno de " + atacante.getNome() + ".");
        System.out.println("1: Atacar com Arma");
        if (atacante.getRitual() != null) {
            System.out.println("2: Usar Ritual Principal");
        }

        int escolha = 0;
        while (escolha != 1 && (atacante.getRitual() == null || escolha != 2)) {
            try {
                System.out.print("Escolha sua ação: ");
                escolha = scanner.nextInt();
                if (escolha != 1 && (atacante.getRitual() == null || escolha != 2)) {
                    System.out.println("Ação inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida.");
                scanner.next();
            }
        }

        if (escolha == 1) { // Atacar com Arma
            Arma arma = atacante.getArma();
            System.out.println(atacante.getNome() + " ataca com " + arma.getNome() + "!");
            int dano = rolarDadoCentralizado(scanner, arma.getDano());
            if (alvo instanceof Personagem) ((Personagem) alvo).receberDano(dano);
            if (alvo instanceof Inimigo) ((Inimigo) alvo).receberDano(dano);
            System.out.println("Dano causado: " + dano);
        } else { // Usar Ritual
            Ritual ritual = atacante.getRitual();
            System.out.println(atacante.getNome() + " conjura o ritual " + ritual.getNome() + "!");
            Matcher m = Pattern.compile("(\\d+d\\d+(?:\\+\\d+)?)").matcher(ritual.getDescricao());
            if (m.find()) {
                String expressao = m.group(1);
                int valor = rolarDadoCentralizado(scanner, expressao);
                if (ritual.getNome().contains("Cicatrização")) {
                    atacante.receberCura(valor);
                    System.out.println(atacante.getNome() + " curou " + valor + " pontos de vida!");
                } else {
                    if (alvo instanceof Personagem) ((Personagem) alvo).receberDano(valor);
                    if (alvo instanceof Inimigo) ((Inimigo) alvo).receberDano(valor);
                    System.out.println("Dano causado: " + valor);
                }
            } else {
                System.out.println("O ritual não tem um efeito de combate direto simulável.");
            }
        }
    }

    private static void realizarTurnoInimigo(Scanner scanner, Inimigo atacante, Personagem alvo) {
        System.out.println("\nÉ o turno de " + atacante.getNome() + ".");
        System.out.println(atacante.getNome() + " ataca!");
        int dano = Dado.rolar(atacante.getDano());
        alvo.receberDano(dano);
        System.out.println("Dano causado: " + dano);
    }

    private static int rolarDadoCentralizado(Scanner scanner, String expressao) {
        if (modoDeRolagem.equals("classico")) {
            while (true) {
                try {
                    System.out.print("Role " + expressao + " e insira o resultado: ");
                    return scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Digite o número do resultado.");
                    scanner.next();
                }
            }
        } else { // Modo Terminal
            int resultado = Dado.rolar(expressao);
            System.out.println("Rolando " + expressao + "... Resultado: " + resultado);
            return resultado;
        }
    }

    private static String escolherModoDeJogo(Scanner scanner) {
        System.out.println("\nEscolha o modo de jogo:");
        System.out.println("1: PvP (Jogador vs Jogador)");
        System.out.println("2: PvM (Jogador vs Máquina)");

        while (true) {
            try {
                System.out.print("Digite o número do modo: ");
                int escolha = scanner.nextInt();
                if (escolha == 1) return "pvp";
                if (escolha == 2) return "pvm";
                System.out.println("Opção inválida. Por favor, escolha 1 ou 2.");
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
            }
        }
    }

    private static void escolherModoDeRolagem(Scanner scanner) {
        System.out.println("\nComo você prefere rolar os dados?");
        System.out.println("1: Modo Clássico (você rola seus dados e insere o valor)");
        System.out.println("2: Via Terminal (o programa rola os dados para você)");

        while (true) {
            try {
                System.out.print("Digite o número do modo: ");
                int escolha = scanner.nextInt();
                if (escolha == 1) {
                    modoDeRolagem = "classico";
                    return;
                } else if (escolha == 2) {
                    modoDeRolagem = "terminal";
                    return;
                } else {
                    System.out.println("Opção inválida. Por favor, escolha 1 ou 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
            }
        }
    }
    private static Inimigo escolherInimigo(Scanner scanner) {
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
        System.out.println("1: Cicatrização (Cura 3d8+3 PV)");
        System.out.println("2: Eco Espiral (Copia o ataque de um alvo)");
        System.out.println("3: Decadência (Dano de 2d8+2)");
        System.out.println("4: Ritual Vodum (Sacrifica PV para causar 2d6 de dano)");
        if (nex >= 50) System.out.println("5: Cinerária (Dano contínuo e debuff) - Requer NEX 50%");

        while (true) {
            try {
                System.out.print("Digite o número do ritual: ");
                int escolha = scanner.nextInt();
                switch (escolha) {
                    case 1: return new Ritual("Cicatrização", "Cura 3d8+3 pontos de vida.");
                    case 2: return new Ritual("Eco Espiral", "Copia o ataque de um alvo.");
                    case 3: return new Ritual("Decadência", "Causa 2d8+2 de dano.");
                    case 4: return new Ritual("Ritual Vodum", "Sacrifica PV para causar 2d6 de dano.");
                    case 5: if (nex >= 50) return new Ritual("Cinerária", "Dano contínuo e debuff.");
                    default: System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida.");
                scanner.next();
            }
        }
    }
}
