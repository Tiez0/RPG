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
        System.out.println("inicio do combate: " + j1.getNome() + " vs " + j2.getNome());
        System.out.println("========================================");

        int round = 1;
        while (j1.estaVivo() && j2.estaVivo()) {
            System.out.println("\n--- round " + round + " ---");
            System.out.println("status: " + j1.getNome() + " [pv: " + j1.getPontosDeVidaAtuais() + "] | " + j2.getNome() + " [pv: " + j2.getPontosDeVidaAtuais() + "]");

            realizarTurnoJogador(j1, j2);
            if (!j2.estaVivo()) break;

            realizarTurnoJogador(j2, j1);
            if (!j1.estaVivo()) break;

            round++;
        }

        System.out.println("\n--- fim de combate ---");
        if (j1.estaVivo()) {
            System.out.println("o vencedor e: " + j1.getNome() + "!");
        } else {
            System.out.println("o vencedor e: " + j2.getNome() + "!");
        }
    }

    public void iniciarCombatePvM(Personagem jogador, Inimigo inimigo) {
        System.out.println("\n========================================");
        System.out.println("inicio do combate: " + jogador.getNome() + " vs " + inimigo.getNome());
        System.out.println("========================================");

        int round = 1;
        while (jogador.estaVivo() && inimigo.estaVivo()) {
            System.out.println("\n--- round " + round + " ---");
            System.out.println("status: " + jogador.getNome() + " [pv: " + jogador.getPontosDeVidaAtuais() + "] | " + inimigo.getNome() + " [pv: " + inimigo.getPontosDeVidaAtuais() + "]");

            realizarTurnoJogador(jogador, inimigo);
            if (!inimigo.estaVivo()) break;

            realizarTurnoInimigo(inimigo, jogador);
            if (!jogador.estaVivo()) break;

            round++;
        }

        System.out.println("\n--- fim de combate ---");
        if (jogador.estaVivo()) {
            System.out.println("o vencedor e: " + jogador.getNome() + "!");
        } else {
            System.out.println("o vencedor e: " + inimigo.getNome() + "!");
        }
    }

    private void realizarTurnoInimigo(Inimigo inimigo, Personagem jogador) {
        System.out.println("\ne o turno de " + inimigo.getNome() + ".");
        inimigo.processarEfeitosDeStatus();

        if (inimigo.getCinerariaDanoTurnos() > 0) {
            int danoCineraria = rolarDadoCentralizado("1d6");
            System.out.println("as cinzas queimam " + inimigo.getNome() + " por " + danoCineraria + " de dano.");
            inimigo.receberDano(danoCineraria);
            if (!inimigo.estaVivo()) return;
        }

        System.out.println(inimigo.getNome() + " ataca!");
        int dano = rolarDadoCentralizado(inimigo.getDano());

        if (inimigo.estaDebuffado()) {
            System.out.println(inimigo.getNome() + " esta enfraquecido e causa metade do dano!");
            dano /= 2;
        }

        System.out.println(inimigo.getNome() + " causou " + dano + " de dano em " + jogador.getNome() + ".");
        jogador.receberDano(dano);
    }

    private void realizarTurnoJogador(Personagem atacante, Object alvo) {
        System.out.println("\ne o turno de " + atacante.getNome() + ".");
        atacante.destravarArma();
        atacante.processarEfeitosDeStatus();

        if (atacante.getCinerariaDanoTurnos() > 0) {
            int danoCineraria = rolarDadoCentralizado("1d6");
            System.out.println("as cinzas queimam " + atacante.getNome() + " por " + danoCineraria + " de dano.");
            atacante.receberDano(danoCineraria);
            if (!atacante.estaVivo()) return;
        }

        while (true) {
            System.out.println("1: mochila");
            System.out.println("2: atacar");
            boolean isOcultista = atacante.getClasse() instanceof Ocultista && !atacante.getRituais().isEmpty();
            boolean isSandbox = (alvo instanceof Inimigo && ((Inimigo) alvo).getNome().equals("MALIGNO"));

            int maxOpcao = 2;
            if (isOcultista) {
                System.out.println("3: usar ritual");
                maxOpcao = 3;
            }
            if (isSandbox) {
                System.out.println((maxOpcao + 1) + ": transcender (evoluir nex)");
                maxOpcao++;
            }

            int acao = 0;
            while (acao < 1 || acao > maxOpcao) {
                try {
                    System.out.print("escolha sua acao: ");
                    acao = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("entrada invalida.");
                    scanner.next();
                }
            }

            if (acao == 1) {
                System.out.println("\n--- mochila de " + atacante.getNome() + " ---");
                // o inventario agora e um objeto, entao chamamos o metodo exibir dele
                atacante.getInventario().exibir(scanner);
            } else if (acao == 2) { // acao de atacar
                if (atacante.isArmaTravada()) {
                    System.out.println("sua arma esta travada! voce perde o turno tentando conserta-la.");
                    return;
                }

                List<Arma> armasDisponiveis = atacante.getArmasNoInventario();
                if (armasDisponiveis.isEmpty()) {
                    System.out.println("voce nao possui armas para atacar!");
                    continue; // permite escolher outra acao
                }

                Arma armaParaUsar = null;
                if (armasDisponiveis.size() == 1) {
                    armaParaUsar = armasDisponiveis.get(0);
                    System.out.println("atacando com: " + armaParaUsar.getNome());
                } else {
                    System.out.println("\n--- escolha uma arma para atacar ---");
                    for (int i = 0; i < armasDisponiveis.size(); i++) {
                        System.out.println((i + 1) + ": " + armasDisponiveis.get(i).getNome());
                    }
                    int escolhaArma = 0;
                    while (escolhaArma < 1 || escolhaArma > armasDisponiveis.size()) {
                        try {
                            System.out.print("digite o numero da arma: ");
                            escolhaArma = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("entrada invalida. por favor, digite um numero.");
                            scanner.next();
                        }
                    }
                    armaParaUsar = armasDisponiveis.get(escolhaArma - 1);
                }
                resolverAtaqueComArma(atacante, alvo, armaParaUsar);
                break; 
            } else if (isOcultista && acao == 3) {
                // escolher qual ritual usar
                List<Ritual> rituaisDisponiveis = atacante.getRituais();
                if (rituaisDisponiveis.isEmpty()) {
                    System.out.println("voce nao possui rituais para usar.");
                    continue;
                }

                System.out.println("\n--- escolha um ritual ---");
                for (int i = 0; i < rituaisDisponiveis.size(); i++) {
                    System.out.println((i + 1) + ": " + rituaisDisponiveis.get(i).getNome() + " (" + rituaisDisponiveis.get(i).getDescricao() + ")");
                }

                int escolhaRitual = 0;
                while (escolhaRitual < 1 || escolhaRitual > rituaisDisponiveis.size()) {
                    try {
                        System.out.print("digite o numero do ritual: ");
                        escolhaRitual = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("entrada invalida. por favor, digite um numero.");
                        scanner.next();
                    }
                }
                usarRitual(atacante, alvo, rituaisDisponiveis.get(escolhaRitual - 1));
                break;
            } else if (isSandbox && acao == maxOpcao) { // acao de transcender
                Transcender.evoluir(atacante, scanner);
                // apos transcender, o turno continua ou encerra? por enquanto, encerra.
                break;
            }
        }
    }

    private void resolverAtaqueComArma(Personagem atacante, Object alvo, Arma armaUsada) {
        System.out.println("\n" + atacante.getNome() + " prepara um ataque com " + armaUsada.getNome() + "!");

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
            System.out.println("faca seu teste de ataque com " + atributoUsado + " (" + dadosParaRolar + "d20).");
            int melhorRolagem = 0;
            for (int i = 0; i < dadosParaRolar; i++) {
                int rolagem = rolarDadoCentralizado("1d20");
                System.out.println("rolagem " + (i + 1) + ": " + rolagem);
                if (rolagem > melhorRolagem) {
                    melhorRolagem = rolagem;
                }
            }
            testeDeAtaque = melhorRolagem;
            System.out.println("melhor resultado: " + testeDeAtaque);
        } else {
            System.out.println("faca seu teste de ataque (1d20).");
            testeDeAtaque = rolarDadoCentralizado("1d20");
        }

        if (testeDeAtaque == 1) {
            resolverFalhaCritica(atacante);
        } else if (testeDeAtaque >= armaUsada.getCriticoMinimo()) {
            System.out.println("acerto critico! dano massivo!");
            int dano = rolarDadoCentralizado(armaUsada.getDanoCritico());
            if (atacante.getClasse() instanceof Combatente && atacante.getNex() >= 15) {
                dano += 2;
                System.out.println("bonus de combatente (nex 15+): +2 de dano!");
            }
            if (atacante.estaDebuffado()) {
                System.out.println(atacante.getNome() + " esta enfraquecido e causa metade do dano!");
                dano /= 2;
            }
            aplicarDano(alvo, dano);
            System.out.println("dano critico causado: " + dano);
        } else if (testeDeAtaque >= armaUsada.getAcertoMinimo()) {
            System.out.println("acerto! rolando o dano...");
            int dano = rolarDadoCentralizado(armaUsada.getDano());
            if (atacante.getClasse() instanceof Combatente && atacante.getNex() >= 15) {
                dano += 2;
                System.out.println("bonus de combatente (nex 15+): +2 de dano!");
            }
            if (atacante.estaDebuffado()) {
                System.out.println(atacante.getNome() + " esta enfraquecido e causa metade do dano!");
                dano /= 2;
            }
            aplicarDano(alvo, dano);
            System.out.println("dano causado: " + dano);
        } else {
            System.out.println("errou! o ataque nao atingiu o alvo.");
        }
    }

    private void resolverFalhaCritica(Personagem atacante) {
        System.out.println("falha critica! o ataque deu terrivelmente errado.");
        System.out.println("escolha a consequencia:");
        System.out.println("1: a arma trava e fica inutilizavel no proximo turno.");
        System.out.println("2: o ataque se volta contra voce.");
        int consequencia = 0;
        while (consequencia != 1 && consequencia != 2) {
            try {
                System.out.print("escolha 1 ou 2: ");
                consequencia = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("entrada invalida.");
                scanner.next();
            }
        }
        if (consequencia == 1) {
            atacante.setArmaTravada(true);
            System.out.println("sua arma travou!");
        } else {
            int danoProprio = rolarDadoCentralizado(atacante.getArmaEquipada().getDano());
            atacante.receberDano(danoProprio);
            System.out.println(atacante.getNome() + " se atrapalha e recebe " + danoProprio + " de dano!");
        }
    }

    private void aplicarDano(Object alvo, int dano) {
        if (alvo instanceof Personagem) {
            ((Personagem) alvo).receberDano(dano);
        }
        if (alvo instanceof Inimigo) {
            ((Inimigo) alvo).receberDano(dano);
        }
    }

    private void usarRitual(Personagem atacante, Object alvo, Ritual ritual) {
        System.out.println(atacante.getNome() + " conjura o ritual: " + ritual.getNome() + "!");

        int presenca = atacante.getAtributos().getPresenca();
        System.out.println("faca seu teste de presenca para conjurar o ritual (" + presenca + "d20).");

        int melhorRolagem = 0;
        for (int i = 0; i < presenca; i++) {
            int rolagem = rolarDadoCentralizado("1d20");
            System.out.println("rolagem " + (i + 1) + ": " + rolagem);
            if (rolagem > melhorRolagem) {
                melhorRolagem = rolagem;
            }
        }
        System.out.println("melhor resultado: " + melhorRolagem);

        if (melhorRolagem >= ritual.getSucessoMinimo()) {
            System.out.println("sucesso! o ritual " + ritual.getNome() + " e conjurado!");
            
            Matcher m = Pattern.compile("(\\d+d\\d+(?:\\+\\d+)?)|(copia ataque)|(dano continuo)").matcher(ritual.getEfeito());
            if (m.find()) {
                String expressao = m.group(1);
                String efeitoEspecial = m.group(2) != null ? m.group(2) : m.group(3);

                if (expressao != null) {
                    int valor = rolarDadoCentralizado(expressao);
                    if (atacante.estaDebuffado() && !ritual.getNome().contains("Cicatrização")) {
                        System.out.println(atacante.getNome() + " esta enfraquecido e causa metade do dano!");
                        valor /= 2;
                    }

                    if (ritual.getNome().contains("Cicatrização")) {
                        atacante.receberCura(valor);
                        System.out.println(atacante.getNome() + " curou " + valor + " pontos de vida!");
                    } else {
                        aplicarDano(alvo, valor);
                        System.out.println("dano do ritual: " + valor);
                    }
                } else if (efeitoEspecial != null) {
                    if (ritual.getNome().equals("Cinerária")) {
                        if (alvo instanceof Personagem) {
                            ((Personagem) alvo).aplicarCineraria();
                        } else if (alvo instanceof Inimigo) {
                            ((Inimigo) alvo).aplicarCineraria();
                        }
                    } else {
                        System.out.println("efeito especial do ritual: " + efeitoEspecial);
                    }
                }
            } else {
                System.out.println("o ritual " + ritual.getNome() + " nao tem um efeito de combate direto simulavel, mas foi conjurado com sucesso.");
            }

        } else {
            System.out.println("falha! o ritual sai do controle!");
            int danoPenalidade = rolarDadoCentralizado(ritual.getPenalidadeFalha());
            System.out.println(atacante.getNome() + " sofre " + danoPenalidade + " de dano mental como penalidade.");
            atacante.receberDano(danoPenalidade);
        }
    }

    private int rolarDadoCentralizado(String expressao) {
        if (modoDeRolagem.equals("classico")) {
            while (true) {
                try {
                    System.out.print("role " + expressao + " e insira o resultado: ");
                    return scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("entrada invalida. digite o numero do resultado.");
                    scanner.next();
                }
            }
        } else { // modo terminal
            int resultado = Dado.rolar(expressao);
            System.out.println("rolando " + expressao + "... resultado: " + resultado);
            return resultado;
        }
    }
}
