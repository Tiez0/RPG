import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

public class GerenciadorDeCombate {

    private final Scanner scanner;
    private final String modoDeRolagem;

    public GerenciadorDeCombate(Scanner scanner, String modoDeRolagem) {
        this.scanner = scanner;
        this.modoDeRolagem = modoDeRolagem;
    }

    public void iniciarCombatePvP(Personagem j1, Personagem j2) {
        System.out.println("\n========================================");
        System.out.println("INÍCIO DO COMBATE: " + j1.getNome() + " vs " + j2.getNome());
        System.out.println("========================================");

        int round = 1;
        while (j1.estaVivo() && j2.estaVivo()) {
            System.out.println("\n--- Round " + round + " ---");
            System.out.println("Status: " + j1.getNome() + " [PV: " + j1.getPontosDeVidaAtuais() + "] | " + j2.getNome() + " [PV: " + j2.getPontosDeVidaAtuais() + "]");

            realizarTurnoJogador(j1, j2);
            if (!j2.estaVivo()) break;

            realizarTurnoJogador(j2, j1);
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

    public void iniciarCombatePvM(Personagem jogador, Inimigo inimigo) {
        System.out.println("\n========================================");
        System.out.println("INÍCIO DO COMBATE: " + jogador.getNome() + " vs " + inimigo.getNome());
        System.out.println("========================================");

        int round = 1;
        while (jogador.estaVivo() && inimigo.estaVivo()) {
            System.out.println("\n--- Round " + round + " ---");
            System.out.println("Status: " + jogador.getNome() + " [PV: " + jogador.getPontosDeVidaAtuais() + "] | " + inimigo.getNome() + " [PV: " + inimigo.getPontosDeVidaAtuais() + "]");

            realizarTurnoJogador(jogador, inimigo);
            if (!inimigo.estaVivo()) break;

            realizarTurnoInimigo(inimigo, jogador);
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

    private void realizarTurnoInimigo(Inimigo inimigo, Personagem jogador) {
        System.out.println("\nÉ o turno de " + inimigo.getNome() + ".");
        inimigo.processarEfeitosDeStatus();

        if (inimigo.getCinerariaDanoTurnos() > 0) {
            int danoCineraria = rolarDadoCentralizado("1d6");
            System.out.println("As cinzas queimam " + inimigo.getNome() + " por " + danoCineraria + " de dano.");
            inimigo.receberDano(danoCineraria);
            if (!inimigo.estaVivo()) return;
        }

        System.out.println(inimigo.getNome() + " ataca!");
        int dano = rolarDadoCentralizado(inimigo.getDano());

        if (inimigo.estaDebuffado()) {
            System.out.println(inimigo.getNome() + " está enfraquecido e causa metade do dano!");
            dano /= 2;
        }

        System.out.println(inimigo.getNome() + " causou " + dano + " de dano em " + jogador.getNome() + ".");
        jogador.receberDano(dano);
    }

    private void realizarTurnoJogador(Personagem atacante, Object alvo) {
        System.out.println("\nÉ o turno de " + atacante.getNome() + ".");
        atacante.destravarArma();
        atacante.processarEfeitosDeStatus();

        if (atacante.getCinerariaDanoTurnos() > 0) {
            int danoCineraria = rolarDadoCentralizado("1d6");
            System.out.println("As cinzas queimam " + atacante.getNome() + " por " + danoCineraria + " de dano.");
            atacante.receberDano(danoCineraria);
            if (!atacante.estaVivo()) return;
        }

        while (true) {
            System.out.println("1: Mochila");
            System.out.println("2: Atacar");
            if (atacante.getClasse() instanceof Ocultista && !atacante.getRituais().isEmpty()) {
                System.out.println("3: Usar Ritual");
            }
            int acao = 0;
            int maxOpcao = (atacante.getClasse() instanceof Ocultista && !atacante.getRituais().isEmpty()) ? 3 : 2;

            while (acao < 1 || acao > maxOpcao) {
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
                if (atacante.getRituais() != null && !atacante.getRituais().isEmpty()) {
                    System.out.println("Rituais:");
                    for (Ritual r : atacante.getRituais()) {
                        System.out.println("  - " + r.getNome());
                    }
                }
                System.out.println("------------------------");
            } else if (acao == 2) { // Ação de atacar
                if (atacante.isArmaTravada()) {
                    System.out.println("Sua arma está travada! Você perde o turno tentando consertá-la.");
                    return;
                }
                resolverAtaqueComArma(atacante, alvo);
                break; 
            } else if (acao == 3 && atacante.getClasse() instanceof Ocultista) {
                // Escolher qual ritual usar
                List<Ritual> rituaisDisponiveis = atacante.getRituais();
                if (rituaisDisponiveis.isEmpty()) {
                    System.out.println("Você não possui rituais para usar.");
                    continue;
                }

                System.out.println("\n--- Escolha um Ritual ---");
                for (int i = 0; i < rituaisDisponiveis.size(); i++) {
                    System.out.println((i + 1) + ": " + rituaisDisponiveis.get(i).getNome() + " (" + rituaisDisponiveis.get(i).getDescricao() + ")");
                }

                int escolhaRitual = 0;
                while (escolhaRitual < 1 || escolhaRitual > rituaisDisponiveis.size()) {
                    try {
                        System.out.print("Digite o número do ritual: ");
                        escolhaRitual = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida. Por favor, digite um número.");
                        scanner.next();
                    }
                }
                usarRitual(atacante, alvo, rituaisDisponiveis.get(escolhaRitual - 1));
                break;
            }
        }
    }

    private void resolverAtaqueComArma(Personagem atacante, Object alvo) {
        Arma arma = atacante.getArma();
        System.out.println("\n" + atacante.getNome() + " prepara um ataque com " + arma.getNome() + "!");

        int testeDeAtaque;
        int dadosParaRolar = 1;
        String atributoUsado = "";

        if (atacante.getClasse() instanceof Combatente) {
            dadosParaRolar = atacante.getAtributos().getForca();
            atributoUsado = "Força";
        } else if (atacante.getClasse() instanceof Especialista) {
            dadosParaRolar = atacante.getAtributos().getAgilidade();
            atributoUsado = "Agilidade";
        } else if (atacante.getClasse() instanceof Ocultista) {
            dadosParaRolar = atacante.getAtributos().getAgilidade();
            atributoUsado = "Agilidade";
        }

        if (dadosParaRolar > 1) {
            System.out.println("Faça seu teste de ataque com " + atributoUsado + " (" + dadosParaRolar + "d20).");
            int melhorRolagem = 0;
            for (int i = 0; i < dadosParaRolar; i++) {
                int rolagem = rolarDadoCentralizado("1d20");
                System.out.println("Rolagem " + (i + 1) + ": " + rolagem);
                if (rolagem > melhorRolagem) {
                    melhorRolagem = rolagem;
                }
            }
            testeDeAtaque = melhorRolagem;
            System.out.println("Melhor resultado: " + testeDeAtaque);
        } else {
            System.out.println("Faça seu teste de ataque (1d20).");
            testeDeAtaque = rolarDadoCentralizado("1d20");
        }

        if (testeDeAtaque == 1) {
            resolverFalhaCritica(atacante);
        } else if (testeDeAtaque >= arma.getCriticoMinimo()) {
            System.out.println("ACERTO CRÍTICO! Dano massivo!");
            int dano = rolarDadoCentralizado(arma.getDanoCritico());
            if (atacante.getClasse() instanceof Combatente && atacante.getNex() >= 15) {
                dano += 2;
                System.out.println("Bônus de Combatente (NEX 15+): +2 de dano!");
            }
            if (atacante.estaDebuffado()) {
                System.out.println(atacante.getNome() + " está enfraquecido e causa metade do dano!");
                dano /= 2;
            }
            aplicarDano(alvo, dano);
            System.out.println("Dano CRÍTICO causado: " + dano);
        } else if (testeDeAtaque >= arma.getAcertoMinimo()) {
            System.out.println("Acerto! Rolando o dano...");
            int dano = rolarDadoCentralizado(arma.getDano());
            if (atacante.getClasse() instanceof Combatente && atacante.getNex() >= 15) {
                dano += 2;
                System.out.println("Bônus de Combatente (NEX 15+): +2 de dano!");
            }
            if (atacante.estaDebuffado()) {
                System.out.println(atacante.getNome() + " está enfraquecido e causa metade do dano!");
                dano /= 2;
            }
            aplicarDano(alvo, dano);
            System.out.println("Dano causado: " + dano);
        } else {
            System.out.println("ERROU! O ataque não atingiu o alvo.");
        }
    }

    private void resolverFalhaCritica(Personagem atacante) {
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
            int danoProprio = rolarDadoCentralizado(atacante.getArma().getDano());
            atacante.receberDano(danoProprio);
            System.out.println(atacante.getNome() + " se atrapalha e recebe " + danoProprio + " de dano!");
        }
    }

    private void aplicarDano(Object alvo, int dano) {
        if (alvo instanceof Personagem) {
            ((Personagem) alvo).receberDano(dano);
        } else if (alvo instanceof Inimigo) {
            ((Inimigo) alvo).receberDano(dano);
        }
    }

    private void usarRitual(Personagem atacante, Object alvo, Ritual ritual) {
        System.out.println(atacante.getNome() + " conjura o ritual: " + ritual.getNome() + "!");

        int presenca = atacante.getAtributos().getPresenca();
        System.out.println("Faça seu teste de Presença para conjurar o ritual (" + presenca + "d20).");

        int melhorRolagem = 0;
        for (int i = 0; i < presenca; i++) {
            int rolagem = rolarDadoCentralizado("1d20");
            System.out.println("Rolagem " + (i + 1) + ": " + rolagem);
            if (rolagem > melhorRolagem) {
                melhorRolagem = rolagem;
            }
        }
        System.out.println("Melhor resultado: " + melhorRolagem);

        if (melhorRolagem >= ritual.getSucessoMinimo()) {
            System.out.println("Sucesso! O ritual " + ritual.getNome() + " é conjurado!");
            
            Matcher m = Pattern.compile("(\\d+d\\d+(?:\\+\\d+)?)|(Copia ataque)|(Dano contínuo)").matcher(ritual.getEfeito());
            if (m.find()) {
                String expressao = m.group(1);
                String efeitoEspecial = m.group(2) != null ? m.group(2) : m.group(3);

                if (expressao != null) {
                    int valor = rolarDadoCentralizado(expressao);
                    if (atacante.estaDebuffado() && !ritual.getNome().contains("Cicatrização")) {
                        System.out.println(atacante.getNome() + " está enfraquecido e causa metade do dano!");
                        valor /= 2;
                    }

                    if (ritual.getNome().contains("Cicatrização")) {
                        atacante.receberCura(valor);
                        System.out.println(atacante.getNome() + " curou " + valor + " pontos de vida!");
                    } else {
                        aplicarDano(alvo, valor);
                        System.out.println("Dano do ritual: " + valor);
                    }
                } else if (efeitoEspecial != null) {
                    if (ritual.getNome().equals("Cinerária")) {
                        if (alvo instanceof Personagem) {
                            ((Personagem) alvo).aplicarCineraria();
                        } else if (alvo instanceof Inimigo) {
                            ((Inimigo) alvo).aplicarCineraria();
                        }
                    } else {
                        System.out.println("Efeito especial do ritual: " + efeitoEspecial);
                    }
                }
            } else {
                System.out.println("O ritual " + ritual.getNome() + " não tem um efeito de combate direto simulável, mas foi conjurado com sucesso.");
            }

        } else {
            System.out.println("Falha! O ritual sai do controle!");
            int danoPenalidade = rolarDadoCentralizado(ritual.getPenalidadeFalha());
            System.out.println(atacante.getNome() + " sofre " + danoPenalidade + " de dano mental como penalidade.");
            atacante.receberDano(danoPenalidade);
        }
    }

    private int rolarDadoCentralizado(String expressao) {
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
}
