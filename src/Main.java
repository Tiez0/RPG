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
            arma = new Arma("Adaga Ritualística", "1d6", "2d6", 10, 20);
            ritual = escolherRitual(scanner, nex);
        }

        // Construtor simplificado, sem Atributos ou Origem
        return new Personagem(nome, nex, classe, arma, ritual);
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

    // --- LÓGICA DE COMBATE E OUTROS MÉTODOS (sem alterações) ---
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
        atacante.destravarArma();

        System.out.println("1: Mochila");
        System.out.println("2: Atacar");
        int acao = 0;
        while (acao != 1 && acao != 2) {
            try {
                System.out.print("Escolha sua ação: ");
                acao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida.");
                scanner.next();
            }
        }

        if (acao == 1) {
            System.out.println("\n--- Mochila de " + atacante.getNome() + " ---");
            if (atacante.getArma() != null) System.out.println("- Arma: " + atacante.getArma());
            if (atacante.getRitual() != null) System.out.println("- Ritual: " + atacante.getRitual());
            System.out.println("------------------------");
            System.out.println("(Visualizar a mochila gasta o turno)");
            return;
        }

        if (atacante.isArmaTravada()) {
            System.out.println("Sua arma está travada! Você perde o turno tentando consertá-la.");
            return;
        }

        if (atacante.getClasse() instanceof Ocultista) {
            System.out.println("1: Atacar com Adaga Ritualística");
            System.out.println("2: Usar Ritual Principal");
            int tipoAtaque = 0;
            while (tipoAtaque != 1 && tipoAtaque != 2) {
                try {
                    System.out.print("Escolha o tipo de ataque: ");
                    tipoAtaque = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida.");
                    scanner.next();
                }
            }
            if (tipoAtaque == 1) {
                resolverAtaqueComArma(scanner, atacante, alvo);
            } else {
                usarRitual(scanner, atacante, alvo);
            }
        } else {
            resolverAtaqueComArma(scanner, atacante, alvo);
        }
    }

    private static void resolverAtaqueComArma(Scanner scanner, Personagem atacante, Object alvo) {
        Arma arma = atacante.getArma();
        System.out.println("\n" + atacante.getNome() + " prepara um ataque com " + arma.getNome() + "!");
        System.out.println("Faça seu teste de ataque (1d20).");
        int testeDeAtaque = rolarDadoCentralizado(scanner, "1d20");

        if (testeDeAtaque == 1) {
            resolverFalhaCritica(scanner, atacante);
        } else if (testeDeAtaque >= arma.getCriticoMinimo()) {
            System.out.println("ACERTO CRÍTICO! Dano massivo!");
            int dano = rolarDadoCentralizado(scanner, arma.getDanoCritico());
            aplicarDano(alvo, dano);
            System.out.println("Dano CRÍTICO causado: " + dano);
        } else if (testeDeAtaque >= arma.getAcertoMinimo()) {
            System.out.println("Acerto! Rolando o dano...");
            int dano = rolarDadoCentralizado(scanner, arma.getDano());
            aplicarDano(alvo, dano);
            System.out.println("Dano causado: " + dano);
        } else {
            System.out.println("ERROU! O ataque não atingiu o alvo.");
        }
    }

    private static void resolverFalhaCritica(Scanner scanner, Personagem atacante) {
        System.out.println("FALHA CRÍTICA! O ataque deu terrivelmente errado.");
        System.out.println("Escolha a consequência:");
        System.out.println("1: A arma trava e fica inutilizável no próximo turno.");
        System.out.println("2: O ataque se volta contra você.");
        int consequencia = 0;
        while (consequencia != 1 && consequencia != 2) {
            try {
                System.out.print("Escolha 1 ou 2: ");
                consequencia = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida.");
                scanner.next();
            }
        }
        if (consequencia == 1) {
            atacante.setArmaTravada(true);
            System.out.println("Sua arma travou!");
        } else {
            int danoProprio = rolarDadoCentralizado(scanner, atacante.getArma().getDano());
            atacante.receberDano(danoProprio);
            System.out.println(atacante.getNome() + " se atrapalha e recebe " + danoProprio + " de dano!");
        }
    }

    private static void aplicarDano(Object alvo, int dano) {
        if (alvo instanceof Personagem) {
            ((Personagem) alvo).receberDano(dano);
        } else if (alvo instanceof Inimigo) {
            ((Inimigo) alvo).receberDano(dano);
        }
    }

    private static void usarRitual(Scanner scanner, Personagem atacante, Object alvo) {
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
                aplicarDano(alvo, valor);
                System.out.println("Dano do ritual: " + valor);
            }
        } else {
            System.out.println("O ritual não tem um efeito de combate direto simulável.");
        }
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
