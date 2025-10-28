import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GerenciadorDeProgressao {

    public static void aplicarProgressao(Scanner scanner, Personagem personagem) {
        // gerencia a progressao de armas baseada no nex (transformacoes e desbloqueios automaticos)
        gerenciarProgressaoArma(scanner, personagem);
        // gerencia a progressao de rituais
        gerenciarProgressaoRitual(scanner, personagem);
    }

    private static void gerenciarProgressaoArma(Scanner scanner, Personagem personagem) {
        // logica para combatente
        if (personagem.getClasse() instanceof Combatente) {
            // transformacao "do outro lado" no nex 30
            if (personagem.getNex() >= 30) {
                Arma armaAtual = personagem.getArmaEquipada();
                // verifica se a arma atual nao e o taco de beisebol e ainda nao foi transformada
                if (armaAtual != null && !armaAtual.getNome().equals("Taco de Beisebol") && !armaAtual.getNome().contains("do Outro Lado")) {
                    armaAtual.transformarDoOutroLado();
                }
            }
            // desbloqueio automatico da ereshkigal no nex 50
            if (personagem.getNex() >= 50) {
                Arma ereshkigal = new Arma("Ereshkigal", "nao confunda com um arco! essa poderosa faca de dois gumes eh uma reliquid importada diretamente do outro lado, permitindo ao portador dela um corte elegante e brutal", "2d12", "4d12", 2, 15);
                // verifica se o personagem ja nao possui a ereshkigal no inventario
                boolean jaPossui = false;
                for(Item item : personagem.getInventario().getItens()){
                    if(item instanceof Arma && item.getNome().equals(ereshkigal.getNome())){
                        jaPossui = true;
                        break;
                    }
                }
                if(!jaPossui) {
                    System.out.println("voce atingiu nex 50! recebeu a arma ereshkigal!");
                    personagem.getInventario().adicionarItem(ereshkigal);
                    personagem.equiparArma(ereshkigal); // equipa automaticamente
                }
            }
        }
        // logica para especialista
        else if (personagem.getClasse() instanceof Especialista) {
            // reducao de critico no nex 15
            if (personagem.getNex() >= 15) {
                // apenas reduz se ainda nao tiver sido reduzido para este nex
                Arma armaAtual = personagem.getArmaEquipada();
                if (armaAtual != null) {
                    // verifica se a arma atual e uma das que podem ter critico reduzido
                    if (armaAtual.getNome().equals("Besta") && armaAtual.getCriticoMinimo() == 19) {
                        armaAtual.reduzirCritico(1);
                    } else if (armaAtual.getNome().equals("Revólver") && armaAtual.getCriticoMinimo() == 19) {
                        armaAtual.reduzirCritico(1);
                    } else if (armaAtual.getNome().equals("Fuzil de Caça") && armaAtual.getCriticoMinimo() == 19) {
                        armaAtual.reduzirCritico(1);
                    }
                }
            }
            // reducao de critico e transformacao "do outro lado" no nex 30
            if (personagem.getNex() >= 30) {
                Arma armaAtual = personagem.getArmaEquipada();
                if (armaAtual != null) {
                    // apenas reduz se ainda nao tiver sido reduzido para este nex
                    if (armaAtual.getNome().equals("Besta") && armaAtual.getCriticoMinimo() == 18) {
                        armaAtual.reduzirCritico(1);
                    } else if (armaAtual.getNome().equals("Revólver") && armaAtual.getCriticoMinimo() == 18) {
                        armaAtual.reduzirCritico(1);
                    } else if (armaAtual.getNome().equals("Fuzil de Caça") && armaAtual.getCriticoMinimo() == 18) {
                        armaAtual.reduzirCritico(1);
                    }
                    // verifica se a arma atual ainda nao foi transformada
                    if (!armaAtual.getNome().contains("do Outro Lado")) {
                        armaAtual.transformarDoOutroLado();
                    }
                }
            }
            // desbloqueio automatico do fuzil de precisao abutre no nex 50
            if (personagem.getNex() >= 50) {
                Arma fuzilAbutre = new Arma("Fuzil de Precisão Abutre", "fuzil para os mais exigentes, portado originalmente por um verdadeiro abutre que nunca errou um misero tiro (mesmo tendo somente um braco!)", "2d10", "10d10", 12, 15);
                // verifica se o personagem ja nao possui o fuzil abutre no inventario
                boolean jaPossui = false;
                for(Item item : personagem.getInventario().getItens()){
                    if(item instanceof Arma && item.getNome().equals(fuzilAbutre.getNome())){
                        jaPossui = true;
                        break;
                    }
                }
                if(!jaPossui) {
                    System.out.println("voce atingiu nex 50! recebeu o fuzil de precisao abutre!");
                    personagem.getInventario().adicionarItem(fuzilAbutre);
                    personagem.equiparArma(fuzilAbutre); // equipa automaticamente
                }
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
                System.out.println("\nescolha seu ritual (ritual " + (i + 1) + "):");
                List<Ritual> rituaisDisponiveis = RitualData.getRitualsDisponiveis(personagem.getNex());
                // remover rituais ja aprendidos para nao escolher repetido
                rituaisDisponiveis.removeAll(personagem.getRituais());

                if (rituaisDisponiveis.isEmpty()) {
                    System.out.println("nao ha mais rituais disponiveis para aprender neste nex.");
                    break;
                }

                for (int j = 0; j < rituaisDisponiveis.size(); j++) {
                    Ritual r = rituaisDisponiveis.get(j);
                    System.out.println((j + 1) + ": " + r.getNome() + " (" + r.getDescricao() + ")");
                }

                while (true) {
                    try {
                        System.out.print("digite o numero do ritual: ");
                        int escolha = scanner.nextInt();
                        if (escolha > 0 && escolha <= rituaisDisponiveis.size()) {
                            personagem.adicionarRitual(rituaisDisponiveis.get(escolha - 1));
                            break;
                        } else {
                            System.out.println("opcao invalida.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("entrada invalida. por favor, digite um numero.");
                        scanner.next();
                    }
                }
            }

            // cineraria automatica no nex 50
            if (personagem.getNex() >= 50) {
                boolean jaTemCineraria = false;
                for (Ritual r : personagem.getRituais()) {
                    if (r.getNome().equals("Cinerária")) {
                        jaTemCineraria = true;
                        break;
                    }
                }
                if (!jaTemCineraria) {
                    Ritual cineraria = new Ritual("Cinerária", "uma onda de fumaca que surge e preenche o ambiente inteiro, como uma nuvem venenosa", "Dano contínuo", 15, "2d6");
                    personagem.adicionarRitual(cineraria);
                    System.out.println("voce atingiu nex 50! aprendeu o ritual cineraria automaticamente!");
                }
            }
        }
    }
}
