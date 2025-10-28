import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CriadorDePersonagem {

    public static Personagem criar(Scanner scanner, String tituloJogador, String modoDeRolagem) {
        if (tituloJogador.equals("Jogador 1") || tituloJogador.equals("Jogador")) {
            scanner.nextLine(); 
        }

        System.out.print("\ndigite o nome do " + tituloJogador + ": ");
        String nome = scanner.nextLine();

        int nex = escolherNEX(scanner, modoDeRolagem);
        Classe classe = escolherClasse(scanner);
        Atributos atributos = distribuirAtributos(scanner, nex);

        // cria o personagem com a arma padrao e sem rituais iniciais por enquanto
        Personagem novoPersonagem = new Personagem(nome, nex, classe, atributos, null, new ArrayList<>());

        // atribui a arma padrao e a adiciona ao inventario
        Arma armaPadrao = atribuirArmaInicial(classe);
        novoPersonagem.getInventario().adicionarItem(armaPadrao);
        novoPersonagem.equiparArma(armaPadrao); // define como arma equipada

        // permite escolher uma arma adicional, se aplicavel
        escolherArmaAdicional(scanner, novoPersonagem);

        // aplica a progressao inicial baseada no nex
        GerenciadorDeProgressao.aplicarProgressao(scanner, novoPersonagem);

        return novoPersonagem;
    }

    public static Personagem criarSuperSoldado(Scanner scanner, String modoDeRolagem) {
        scanner.nextLine(); // consome a quebra de linha pendente do menu
        System.out.print("\ndigite o nome do super soldado: ");
        String nome = scanner.nextLine();

        int nex = 5; // nex fixo para o modo historia
        System.out.println("\nnex definido em " + nex + "% (nivel de exposicao paranormal inicial).");

        Classe classe = escolherClasse(scanner);
        System.out.println("--- cientista 2: especializacao de combate definida. agora, vamos calibrar os atributos fisicos e mentais. ---");
        Atributos atributos = distribuirAtributos(scanner, nex);

        Personagem novoPersonagem = new Personagem(nome, nex, classe, atributos, null, new ArrayList<>());

        Arma armaPadrao = atribuirArmaInicial(classe);
        novoPersonagem.getInventario().adicionarItem(armaPadrao);
        novoPersonagem.equiparArma(armaPadrao);

        escolherArmaAdicional(scanner, novoPersonagem);

        GerenciadorDeProgressao.aplicarProgressao(scanner, novoPersonagem);

        return novoPersonagem;
    }

    private static Arma atribuirArmaInicial(Classe classe) {
        if (classe instanceof Combatente) {
            System.out.println("voce recebeu um taco de beisebol como arma inicial.");
            return new Arma("Taco de Beisebol", "um taco de beisebol comum, mas confiavel. perfeito para quebrar cabecas.", "2d4", "2d4", 10, 20);
        } else if (classe instanceof Especialista) {
            System.out.println("voce recebeu uma besta como arma inicial.");
            return new Arma("Besta", "uma verdadeira besta enjaulada (mas fora da jaula).", "1d8", "12", 10, 19);
        } else if (classe instanceof Ocultista) {
            System.out.println("voce recebeu uma adaga ritualistica como arma inicial.");
            return new Arma("Adaga Ritualística", "uma adaga com uma aura sinistra, nao recomendo voce ficar observando ela por muito tempo!!", "1d6", "2d6", 10, 20);
        }
        return null; // nunca deve acontecer
    }

    private static void escolherArmaAdicional(Scanner scanner, Personagem personagem) {
        if (personagem.getClasse() instanceof Combatente) {
            System.out.println("\nescolha sua arma adicional:");
            System.out.println("1: machado (dano: 1d8, critico: 3d8 / 20+)");
            System.out.println("2: katana (dano: 1d10, critico: 19 / 19+)");

            while (true) {
                try {
                    System.out.print("digite o numero da arma: ");
                    int escolha = scanner.nextInt();
                    Arma armaEscolhida = null;
                    if (escolha == 1) {
                        armaEscolhida = new Arma("Machado", "versatil, capaz e feroz, esse eh o machado! com seu grande peso por conta de seu cabo feito de jacaranda, ele eh seu fiel companheiro para quebrar cranios de qualquer tipo de inimigo", "1d8", "3d8", 12, 20);
                    } else if (escolha == 2) {
                        armaEscolhida = new Arma("Katana", "voltando diretamente do shogunado a katana mesmo sendo fragil nao faz feio! uma espada leve agil e precisa para quem prefere mais um estilo samurai", "1d10", "19", 10, 19);
                    } else {
                        System.out.println("opcao invalida.");
                        continue;
                    }
                    personagem.getInventario().adicionarItem(armaEscolhida);
                    personagem.equiparArma(armaEscolhida); // equipa a arma escolhida
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("entrada invalida. por favor, digite um numero.");
                    scanner.next();
                }
            }
        } else if (personagem.getClasse() instanceof Especialista) {
            System.out.println("\nescolha sua arma adicional:");
            System.out.println("1: revolver (dano: 2d6, critico: 6d6 / 19+)");
            System.out.println("2: fuzil de caca (dano: 2d8, critico: 3d8 / 19+)");

            while (true) {
                try {
                    System.out.print("digite o numero da arma: ");
                    int escolha = scanner.nextInt();
                    Arma armaEscolhida = null;
                    if (escolha == 1) {
                        armaEscolhida = new Arma("Revólver", "antigo 38 que provavelmente ja esteve na cintura de algum forasteiro do velho oeste", "2d6", "6d6", 10, 19);
                    } else if (escolha == 2) {
                        armaEscolhida = new Arma("Fuzil de Caça", "preciso e versatil, uma hora mata javalis outra hora penetra o cranio dos seus inimigos", "2d8", "3d8", 12, 19);
                    } else {
                        System.out.println("opcao invalida.");
                        continue;
                    }
                    personagem.getInventario().adicionarItem(armaEscolhida);
                    personagem.equiparArma(armaEscolhida); // equipa a arma escolhida
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("entrada invalida. por favor, digite um numero.");
                    scanner.next();
                }
            }
        }
        // ocultistas nao escolhem arma adicional, ja que a adaga e a unica arma deles
    }

    private static Atributos distribuirAtributos(Scanner scanner, int nex) {
        System.out.println("\n--- distribuicao de atributos ---");
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

        System.out.println("voce tem " + pontos + " pontos para distribuir entre agilidade, forca, intelecto, presenca e vigor.");
        System.out.println("cada atributo comeca em 1 e o valor maximo e 6.");

        int agi = 1, forca = 1, inte = 1, pres = 1, vig = 1;

        while (pontos > 0) {
            System.out.println("\npontos restantes: " + pontos);
            System.out.println("1: agilidade (" + agi + ")");
            System.out.println("2: forca (" + forca + ")");
            System.out.println("3: intelecto (" + inte + ")");
            System.out.println("4: presenca (" + pres + ")");
            System.out.println("5: vigor (" + vig + ")");

            try {
                System.out.print("escolha um atributo para aumentar: ");
                int escolha = scanner.nextInt();
                System.out.print("quantos pontos (1-" + pontos + "): ");
                int valor = scanner.nextInt();

                if (valor <= 0 || valor > pontos) {
                    System.out.println("valor invalido.");
                    continue;
                }

                switch (escolha) {
                    case 1: if (agi + valor > 6) { System.out.println("atributo nao pode exceder 6."); continue; } agi += valor; break;
                    case 2: if (forca + valor > 6) { System.out.println("atributo nao pode exceder 6."); continue; } forca += valor; break;
                    case 3: if (inte + valor > 6) { System.out.println("atributo nao pode exceder 6."); continue; } inte += valor; break;
                    case 4: if (pres + valor > 6) { System.out.println("atributo nao pode exceder 6."); continue; } pres += valor; break;
                    case 5: if (vig + valor > 6) { System.out.println("atributo nao pode exceder 6."); continue; } vig += valor; break;
                    default: System.out.println("opcao invalida."); continue;
                }
                pontos -= valor;

            } catch (InputMismatchException e) {
                System.out.println("entrada invalida. por favor, digite um numero.");
                scanner.next();
            }
        }

        return new Atributos(agi, forca, inte, pres, vig);
    }

    private static Classe escolherClasse(Scanner scanner) {
        System.out.println("\nescolha a classe do seu personagem:");
        System.out.println("1: combatente");
        System.out.println("2: especialista");
        System.out.println("3: ocultista");
        while (true) {
            try {
                System.out.print("digite o numero da classe: ");
                int escolha = scanner.nextInt();
                switch (escolha) {
                    case 1: return new Combatente();
                    case 2: return new Especialista();
                    case 3: return new Ocultista();
                    default: System.out.println("opcao invalida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("entrada invalida. por favor, digite um numero.");
                scanner.next();
            }
        }
    }

    private static int escolherNEX(Scanner scanner, String modoDeRolagem) {
        System.out.println("\ndefina o nivel de exposicao paranormal (nex).");
        int[] nexOptions = {5, 15, 25, 30, 45, 50};
        
        System.out.println("escolha um dos niveis de nex disponiveis:");
        for (int i = 0; i < nexOptions.length; i++) {
            System.out.println((i + 1) + ": nex " + nexOptions[i] + "%");
        }

        while (true) {
            try {
                System.out.print("digite o numero da opcao de nex: ");
                int escolha = scanner.nextInt();
                if (escolha > 0 && escolha <= nexOptions.length) {
                    return nexOptions[escolha - 1];
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
