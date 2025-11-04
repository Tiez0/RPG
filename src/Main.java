import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static String modoDeRolagem;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("O mundo que você conhecia se foi. Engolido pelo 'Eco', um silêncio faminto que veio depois dos gritos.");
        System.out.println("Agora, apenas os destroços e os 'Perdidos' - os corpos vazios que andam - permanecem.");
        System.out.println("Você não é um herói. Você é um sobrevivente. E sobreviver é tudo o que importa.");
        System.out.println("------------------------------------------------------------------------------------");

        String modoDeJogo = escolherModoDeJogo(scanner);
        escolherModoDeRolagem(scanner);

        if (modoDeJogo.equals("pvp")) {
            System.out.println("\n--- REGISTRO DO SOBREVIVENTE 1 ---");
            Personagem jogador1 = criarPersonagem(scanner, "Sobrevivente 1");
            jogador1.exibirFicha();

            System.out.println("\n--- REGISTRO DO SOBREVIVENTE 2 ---");
            Personagem jogador2 = criarPersonagem(scanner, "Sobrevivente 2");
            jogador2.exibirFicha();

            iniciarCombatePvP(scanner, jogador1, jogador2);

        } else { // Modo PVM
            System.out.println("\n--- CRIAÇÃO DE SOBREVIVENTE ---");
            Personagem jogador = criarPersonagem(scanner, "Sobrevivente");
            jogador.exibirFicha();

            Inimigo inimigo = escolherInimigo(scanner);
            iniciarCombatePvM(scanner, jogador, inimigo);
        }

        scanner.close();
        System.out.println("\nFim da transmissão.");
    }

    private static Personagem criarPersonagem(Scanner scanner, String tituloJogador) {
        if (tituloJogador.equals("Sobrevivente 1") || tituloJogador.equals("Sobrevivente")) {
            scanner.nextLine(); 
        }

        System.out.print("\nInsira o nome do " + tituloJogador + ": ");
        String nome = scanner.nextLine();

        int nex = escolherNEX(scanner);
        Classe classe = escolherClasse(scanner);

        Arma arma = null;
        Ritual ritual = null;

        if (classe instanceof Combatente || classe instanceof Especialista) {
            arma = escolherArma(scanner, classe, nex);
        } else if (classe instanceof Ocultista) {
            arma = new Arma("Caco de Osso Afiado", "1d6", "2d6", 10, 20);
            ritual = escolherRitual(scanner, nex);
        }

        return new Personagem(nome, nex, classe, arma, ritual);
    }

    private static Arma escolherArma(Scanner scanner, Classe classe, int nex) {
        if (classe instanceof Combatente) {
            System.out.println("\nComo um Brutamontes, você se vira com o que tem. Escolha sua ferramenta:");
            System.out.println("1: Machado de Bombeiro (Acerto: 12+, Crítico: 20)");
            System.out.println("2: Facão Militar (Acerto: 10+, Crítico: 19+)");
            if (nex >= 50) System.out.println("3: Marreta \"Demolidora\" (Acerto: 2+, Crítico: 15+)");

            while (true) {
                try {
                    System.out.print("Digite o número da ferramenta: ");
                    int escolha = scanner.nextInt();
                    switch (escolha) {
                        case 1: return new Arma("Machado de Bombeiro", "1d8", "3d8", 12, 20);
                        case 2: return new Arma("Facão Militar", "1d10", "19", 10, 19);
                        case 3: if (nex >= 50) return new Arma("Marreta \"Demolidora\"", "2d12", "4d12", 2, 15);
                        default: System.out.println("Opção inválida.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida.");
                    scanner.next();
                }
            }
        } else if (classe instanceof Especialista) {
            System.out.println("\nComo um Batedor, o silêncio é seu amigo. Escolha sua arma:");
            System.out.println("1: Besta de Sucata (Acerto: 11+, Crítico: 20)");
            System.out.println("2: Revólver .38 (Acerto: 10+, Crítico: 19+)");
            System.out.println("3: Rifle de Ferrolho (Acerto: 12+, Crítico: 19+)");
            if (nex >= 50) System.out.println("4: Rifle \"Carniceiro\" .308 (Acerto: 12+, Crítico: 18+)");

            while (true) {
                try {
                    System.out.print("Digite o número da arma: ");
                    int escolha = scanner.nextInt();
                    switch (escolha) {
                        case 1: return new Arma("Besta de Sucata", "1d8", "19", 11, 20);
                        case 2: return new Arma("Revólver .38", "2d6", "6d6", 10, 19);
                        case 3: return new Arma("Rifle de Ferrolho", "2d8", "4d8", 12, 19);
                        case 4: if (nex >= 50) return new Arma("Rifle \"Carniceiro\" .308", "2d10", "10d10", 12, 18);
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
        System.out.println("O CONFRONTO COMEÇA: " + j1.getNome() + " vs " + j2.getNome());
        System.out.println("========================================");

        int round = 1;
        while (j1.estaVivo() && j2.estaVivo()) {
            System.out.println("\n--- Turno " + round + " ---");
            System.out.println("Condição: " + j1.getNome() + " [PV: " + j1.getPontosDeVidaAtuais() + "] | " + j2.getNome() + " [PV: " + j2.getPontosDeVidaAtuais() + "]");

            realizarTurnoJogador(scanner, j1, j2);
            if (!j2.estaVivo()) break;

            realizarTurnoJogador(scanner, j2, j1);
            if (!j1.estaVivo()) break;

            round++;
        }

        System.out.println("\n--- FIM DO CONFRONTO ---");
        if (j1.estaVivo()) {
            System.out.println("O sobrevivente é: " + j1.getNome() + ".");
        } else {
            System.out.println("O sobrevivente é: " + j2.getNome() + ".");
        }
    }

    private static void iniciarCombatePvM(Scanner scanner, Personagem jogador, Inimigo inimigo) {
        System.out.println("\n========================================");
        System.out.println("O CONFRONTO COMEÇA: " + jogador.getNome() + " vs " + inimigo.getNome());
        System.out.println("========================================");

        int round = 1;
        while (jogador.estaVivo() && inimigo.estaVivo()) {
            System.out.println("\n--- Turno " + round + " ---");
            System.out.println("Condição: " + jogador.getNome() + " [PV: " + jogador.getPontosDeVidaAtuais() + "] | " + inimigo.getNome() + " [PV: " + inimigo.getPontosDeVidaAtuais() + "]");

            realizarTurnoJogador(scanner, jogador, inimigo);
            if (!inimigo.estaVivo()) break;

            realizarTurnoInimigo(scanner, inimigo, jogador);
            if (!jogador.estaVivo()) break;

            round++;
        }

        System.out.println("\n--- FIM DO CONFRONTO ---");
        if (jogador.estaVivo()) {
            System.out.println(jogador.getNome() + " sobreviveu... por enquanto.");
        } else {
            System.out.println(jogador.getNome() + " se tornou mais um Perdido.");
        }
    }
    
    private static void realizarTurnoInimigo(Scanner scanner, Inimigo inimigo, Personagem jogador) {
        System.out.println("\nÉ a vez de " + inimigo.getNome() + ".");
        System.out.println(inimigo.getNome() + " ataca!");
        int dano = rolarDadoCentralizado(scanner, inimigo.getDano());
        jogador.receberDano(dano);
        System.out.println(inimigo.getNome() + " causou " + dano + " de dano em " + jogador.getNome() + ".");
    }

    private static void realizarTurnoJogador(Scanner scanner, Personagem atacante, Object alvo) {
        System.out.println("\nÉ a vez de " + atacante.getNome() + ".");
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
            if (atacante.getRitual() != null) System.out.println("- Técnica: " + atacante.getRitual());
            System.out.println("------------------------");
            System.out.println("(Remexer na mochila gasta seu turno. Tempo é vida.)");
            return;
        }

        if (atacante.isArmaTravada()) {
            System.out.println("Sua arma emperrou! Você perde o turno tentando consertá-la.");
            return;
        }

        if (atacante.getClasse() instanceof Ocultista) {
            System.out.println("1: Atacar com Caco de Osso");
            System.out.println("2: Usar Técnica de Sobrevivência");
            int tipoAtaque = 0;
            while (tipoAtaque != 1 && tipoAtaque != 2) {
                try {
                    System.out.print("Escolha sua abordagem: ");
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
        System.out.println("\n" + atacante.getNome() + " avança com " + arma.getNome() + "!");
        System.out.println("Faça seu teste de ataque (1d20).");
        int testeDeAtaque = rolarDadoCentralizado(scanner, "1d20");

        if (testeDeAtaque == 1) {
            resolverFalhaCritica(scanner, atacante);
        } else if (testeDeAtaque >= arma.getCriticoMinimo()) {
            System.out.println("GOLPE PERFEITO! Dano massivo!");
            int dano = rolarDadoCentralizado(scanner, arma.getDanoCritico());
            aplicarDano(alvo, dano);
            System.out.println("Dano CRÍTICO causado: " + dano);
        } else if (testeDeAtaque >= arma.getAcertoMinimo()) {
            System.out.println("Acertou! Rolando o dano...");
            int dano = rolarDadoCentralizado(scanner, arma.getDano());
            aplicarDano(alvo, dano);
            System.out.println("Dano causado: " + dano);
        } else {
            System.out.println("ERROU! O ataque se perde no vazio.");
        }
    }

    private static void resolverFalhaCritica(Scanner scanner, Personagem atacante) {
        System.out.println("DESASTRE! O ataque deu terrivelmente errado.");
        System.out.println("Escolha a consequência:");
        System.out.println("1: A arma emperra e fica inutilizável no próximo turno.");
        System.out.println("2: Você se desequilibra e o ataque se volta contra você.");
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
            System.out.println("Sua arma emperrou!");
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
        System.out.println(atacante.getNome() + " usa a técnica " + ritual.getNome() + "!");
        Matcher m = Pattern.compile("(\\d+d\\d+(?:\\+\\d+)?)").matcher(ritual.getDescricao());
        if (m.find()) {
            String expressao = m.group(1);
            int valor = rolarDadoCentralizado(scanner, expressao);
            if (ritual.getNome().contains("Adrenalina")) {
                atacante.receberCura(valor);
                System.out.println(atacante.getNome() + " recuperou " + valor + " pontos de vida!");
            } else {
                aplicarDano(alvo, valor);
                System.out.println("Dano da técnica: " + valor);
            }
        } else {
            System.out.println("A técnica não tem um efeito de combate direto simulável.");
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
        System.out.println("1: Sobrevivente vs Sobrevivente");
        System.out.println("2: Sobrevivente vs Perdido");

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
        System.out.println("\nComo você prefere encarar o destino?");
        System.out.println("1: Modo Clássico (você rola seus dados e insere o valor)");
        System.out.println("2: Via Terminal (o sistema rola os dados para você)");

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
        System.out.println("\nEscolha a ameaça que você irá enfrentar:");
        Inimigo[] inimigos = {
            new Inimigo("Errante Comum", 30, "1d6"),
            new Inimigo("Corredor Frenético", 50, "1d8+2"),
            new Inimigo("Bruto Inchado", 100, "2d8"),
            new Inimigo("Amálgama de Carne", 150, "2d10"),
            new Inimigo("O Sussurrante", 250, "3d12"),
            new Inimigo("Sombra Rastejante", 200, "3d10+5")
        };

        for (int i = 0; i < inimigos.length; i++) {
            System.out.println((i + 1) + ": " + inimigos[i].getNome());
        }

        while (true) {
            try {
                System.out.print("Digite o número da ameaça: ");
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
        System.out.println("\nO apocalipse te forçou a se adaptar. Quem você se tornou?");
        System.out.println("1: Brutamontes (Focado em combate corporal)");
        System.out.println("2: Batedor (Focado em armas a distância)");
        System.out.println("3: Intuitivo (Usa o conhecimento do 'Eco' para sobreviver)");
        while (true) {
            try {
                System.out.print("Digite o número do seu arquétipo: ");
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
        System.out.println("\nComo um Intuitivo, você carrega um Caco de Osso Afiado (1d6) e domina técnicas bizarras:");
        System.out.println("1: Injeção de Adrenalina (Cura 3d8+3 PV)");
        System.out.println("2: Mimetismo Traumático (Copia o ataque de um alvo)");
        System.out.println("3: Bomba de Ferrugem (Dano de 2d8+2)");
        System.out.println("4: Frenesi de Dor (Sacrifica PV para causar 2d6 de dano)");
        if (nex >= 50) System.out.println("5: Esporos da Podridão (Dano contínuo e debuff) - Requer NES 50%");

        while (true) {
            try {
                System.out.print("Digite o número da técnica: ");
                int escolha = scanner.nextInt();
                switch (escolha) {
                    case 1: return new Ritual("Injeção de Adrenalina", "Cura 3d8+3 pontos de vida.");
                    case 2: return new Ritual("Mimetismo Traumático", "Copia o ataque de um alvo.");
                    case 3: return new Ritual("Bomba de Ferrugem", "Causa 2d8+2 de dano.");
                    case 4: return new Ritual("Frenesi de Dor", "Sacrifica PV para causar 2d6 de dano.");
                    case 5: if (nex >= 50) return new Ritual("Esporos da Podridão", "Dano contínuo e debuff.");
                    default: System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida.");
                scanner.next();
            }
        }
    }

    private static int escolherNEX(Scanner scanner) {
        System.out.println("\nDefina seu Nível de Estresse (NES). Quanto mais alto, mais experiente... e mais perto do limite.");
        int[] nexOptions = {5, 15, 25, 30, 45, 50};
        
        System.out.println("Escolha um dos níveis de NES disponíveis:");
        for (int i = 0; i < nexOptions.length; i++) {
            System.out.println((i + 1) + ": NES " + nexOptions[i] + "%");
        }

        while (true) {
            try {
                System.out.print("Digite o número da opção de NES: ");
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
