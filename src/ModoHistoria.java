import java.util.Scanner;

public class ModoHistoria {

    public static void iniciar(Scanner scanner, String modoDeRolagem) {
        System.out.println("\n--- modo historia ---");
        
        System.out.println("--- cientista 1: ...ele esta estabilizado. os sinais vitais estao normais. ---");
        System.out.println("--- cientista 2: excelente. vamos comecar a fase final do projeto 'super soldado'. ---");
        System.out.println("--- cientista 1: registrando... qual sera a especializacao de combate do sujeito? ---");

        Personagem jogador = CriadorDePersonagem.criarSuperSoldado(scanner, modoDeRolagem);
        
        System.out.println("\n--- a criacao foi um sucesso. o sujeito esta pronto. ---");
        jogador.exibirFicha();

        System.out.println("\nvoce acorda em um bunker escuro. uma luz vermelha de emergencia pisca intermitentemente.");
        System.out.println("uma dor de cabeca lancinante o atinge enquanto memorias fragmentadas passam por sua mente.");
        System.out.println("voce se levanta, sentindo uma forca incomum em seus musculos.");
        System.out.println("um som gutural ecoa pelo corredor. um zumbi de sangue cambaleia em sua direcao!");

        Inimigo zumbiDeSangue = new Inimigo("Zumbi de Sangue", 20, "1d6");
        GerenciadorDeCombate gerenciadorDeCombate = new GerenciadorDeCombate(scanner, modoDeRolagem);
        gerenciadorDeCombate.iniciarCombatePvM(jogador, zumbiDeSangue);

        if (jogador.estaVivo()) {
            System.out.println("\nvoce derrotou o zumbi de sangue. o caminho esta livre.");
            System.out.println("a vitoria desperta algo em voce. agora voce pode transcender no proximo combate.");
            jogador.habilitarTranscender();
            System.out.println("voce sai do bunker e a luz do sol ofusca sua visao por um instante.");
        } else {
            System.out.println("\nvoce foi derrotado. o experimento falhou.");
            return;
        }

        System.out.println("\nao explorar os arredores, voce se depara com um vao entre dois predios.");
        System.out.println("parece que a unica maneira de continuar e saltando.");
        System.out.println("\n--- teste de agilidade (parkour) ---");
        System.out.println("role 1d20. 1 e morte. 2-9 e dano. 10+ e sucesso.");

        int rolagemParkour = gerenciadorDeCombate.rolarDadoCentralizado("1d20");

        if (rolagemParkour == 1) {
            System.out.println("\nfalha critica! voce erra o salto e cai para a morte. sua jornada termina aqui.");
            System.out.println("\n--- fim de jogo ---");
            return;
        } else if (rolagemParkour < 10) {
            System.out.println("\nfalha! voce escorrega e cai, mas consegue se agarrar na beirada no ultimo segundo.");
            int danoQueda = gerenciadorDeCombate.rolarDadoCentralizado("1d6");
            System.out.println("voce se machuca na queda e sofre " + danoQueda + " de dano.");
            jogador.receberDano(danoQueda);
            if (!jogador.estaVivo()) {
                System.out.println("os ferimentos da queda foram demais para voce. sua jornada termina aqui.");
                System.out.println("\n--- fim de jogo ---");
                return;
            }
            System.out.println("com sua forca sobre-humana, voce se puxa para cima, ferido, mas vivo.");
        } else {
            System.out.println("\nsucesso! com um salto agil, voce atravessa o vao e aterrissa com seguranca do outro lado.");
        }

        System.out.println("\ncontinuando sua exploracao, voce avista um grupo de pessoas ao redor de uma fogueira.");
        System.out.println("eles o veem e, embora inicialmente desconfiados, um homem mais velho se aproxima.");
        System.out.println("\n--- lider dos sobreviventes: ei, voce! o que faz perambulando por ai sozinho? e perigoso.");
        System.out.println("--- lider dos sobreviventes: meu nome e boris. nos somos o que restou... estamos tentando sobreviver.");
        System.out.println("--- lider dos sobreviventes: voce parece forte. venha, junte-se a nos. voce pode descansar e comer algo.");
        System.out.println("\nvoce, ainda desorientado, aceita o convite e os segue ate um pequeno acampamento improvisado.");
        System.out.println("a noite cai, e o cansaço do dia finalmente o atinge. voce adormece perto da fogueira.");

        System.out.println("\nno meio da noite, um grito o desperta. um zumbi de sangue bestial, maior e mais feroz, ataca o acampamento!");
        Inimigo zumbiBestial = new Inimigo("Zumbi de Sangue Bestial", 35, "2d6");
        gerenciadorDeCombate.iniciarCombatePvM(jogador, zumbiBestial);

        if (jogador.estaVivo()) {
            System.out.println("\nvoce conseguiu derrotar a criatura! o acampamento esta a salvo, por enquanto.");
            System.out.println("a experiencia o deixou mais forte e voce podera transcender novamente.");
            jogador.habilitarTranscender();
        } else {
            System.out.println("\nvoce foi subjugado pela fera. quando tudo parecia perdido, boris o empurra para longe e se sacrifica para te salvar.");
            System.out.println("a visao de seu sacrificio desperta uma furia dentro de voce.");
            System.out.println("voce se sente mais poderoso e podera usar esse sentimento para transcender.");
            jogador.receberCura(jogador.getPontosDeVidaMaximos()); // revive o jogador
            jogador.habilitarTranscender();
        }

        System.out.println("\nna manha seguinte, o clima no acampamento e sombrio. voce se tornou parte do grupo, e eles precisam de sua ajuda.");
        System.out.println("um dos sobreviventes se aproxima: \"boris confiava em voce. ele estava investigando um grupo estranho que se instalou na igreja proxima. eles sao... suspeitos. precisamos que voce se infiltre e descubra o que estao tramando.\"");

        System.out.println("\n--- missao: infiltracao na igreja ---");
        System.out.println("como voce vai se aproximar do acampamento inimigo?");
        System.out.println("1: se infiltrar como um andarilho perdido (requer intelecto).");
        System.out.println("2: tirar as roupas e fingir uma crise de loucura (+2 de intelecto temporario).");

        int escolhaInfiltracao = 0;
        while (escolhaInfiltracao != 1 && escolhaInfiltracao != 2) {
            System.out.print("escolha sua abordagem: ");
            escolhaInfiltracao = scanner.nextInt();
        }

        int intelectoParaTeste = jogador.getAtributos().getIntelecto();
        if (escolhaInfiltracao == 2) {
            System.out.println("\nvoce se despe e comeca a agir erraticamente. uma abordagem ousada, mas que pode funcionar.");
            intelectoParaTeste += 2;
        } else {
            System.out.println("\nvoce se aproxima com cautela, tentando parecer apenas mais um sobrevivente perdido.");
        }

        System.out.println("\n--- teste de intelecto (enganacao) ---");
        System.out.println("voce precisa de um resultado 10 ou maior para convence-los.");
        System.out.println("voce rolara " + intelectoParaTeste + "d20 e usara o melhor resultado.");

        int melhorRolagem = 0;
        for (int i = 0; i < intelectoParaTeste; i++) {
            int rolagem = gerenciadorDeCombate.rolarDadoCentralizado("1d20");
            if (rolagem > melhorRolagem) {
                melhorRolagem = rolagem;
            }
        }
        System.out.println("melhor resultado da rolagem: " + melhorRolagem);

        if (melhorRolagem >= 10) {
            System.out.println("\nsucesso! sua atuacao e convincente. os guardas baixam as armas.");
            if (escolhaInfiltracao == 2) {
                System.out.println("--- guarda: coitado, a solidao deve te ter afetado. venha, nos vamos te ajudar. temos um lugar seguro.");
            } else {
                System.out.println("--- guarda: um andarilho? voce deu sorte de nos encontrar. venha, participe do nosso convivio. voce vai gostar.");
            }
        } else {
            System.out.println("\nfalha! eles nao parecem convencidos, mas sua aparencia (ou atuacao) os deixa intrigados.");
            System.out.println("--- guarda: hmm, nao sei... ha algo estranho em voce. mas o lider vai querer ve-lo. levem-no para a igreja.");
        }

        System.out.println("\nquerendo ou nao, voce e levado para dentro da igreja. o ar e pesado e um canto baixo ecoa pelas paredes.");
        
        System.out.println("\nAo ser levado para dentro, um dos guardas aponta para uma porta lateral.");
        System.out.println("--- Guarda: Antes de ver o mestre, prove seu valor. Sobreviva ao que ha la dentro.");
        System.out.println("Voce e empurrado para uma sala escura. A porta se tranca. Um rugido ecoa e um Zumbi de Sangue Bestial avança!");

        Inimigo zumbiBestial2 = new Inimigo("Zumbi de Sangue Bestial", 50, "1d8+2");
        gerenciadorDeCombate.iniciarCombatePvM(jogador, zumbiBestial2);

        if (jogador.estaVivo()) {
            System.out.println("\nA criatura cai por terra. A porta para a proxima sala se abre.");
            System.out.println("A vitória o fortalece. Você pode transcender novamente.");
            jogador.habilitarTranscender();
        } else {
            System.out.println("\nVocê foi derrotado. Seu corpo se torna mais um experimento para o culto.");
            System.out.println("\n--- Fim de Jogo ---");
            return;
        }

        System.out.println("\nVoce entra em uma camara fria. O cheiro de sangue e podridão é intenso.");
        System.out.println("No centro, uma massa disforme de corpos costurados se ergue. Uma Aberração de Carne!");

        Inimigo aberracaoDeCarne = new Inimigo("Aberração de Carne", 100, "2d10");
        gerenciadorDeCombate.iniciarCombatePvM(jogador, aberracaoDeCarne);

        if (jogador.estaVivo()) {
            System.out.println("\nA aberração se desfaz em uma poça de carne e sangue. O caminho para o salão principal está livre.");
            System.out.println("A vitória o fortalece. Você pode transcender novamente.");
            jogador.habilitarTranscender();
        } else {
            System.out.println("\nA criatura o domina e o absorve para sua massa disforme. Sua jornada termina aqui.");
            System.out.println("\n--- Fim de Jogo ---");
            return;
        }

        System.out.println("\nVoce finalmente chega ao salão principal da igreja.");
        System.out.println("no altar, uma figura imponente e grotesca se vira para voce. uma criatura que parece um minotauro de metal e carne.");
        System.out.println("a congregacao se ajoelha e grita em unissono: 'gloria a empapx!'");

        Inimigo empapx = new Inimigo("Empapx, o Touro de Ferro", 50, "2d8");
        gerenciadorDeCombate.iniciarCombatePvM(jogador, empapx);

        if (jogador.estaVivo()) {
            System.out.println("\ncom um golpe final, a criatura monstruosa desaba. o silencio chocado da congregacao e quebrado por seus proprios gritos de terror ao fugirem.");
            System.out.println("voce desmantelou o culto. a igreja esta silenciosa, exceto pelo eco de sua vitoria.");
            System.out.println("a vitoria final lhe concede o poder de transcender mais uma vez.");
            jogador.habilitarTranscender();
            System.out.println("\n--- fim da primeira parte ---");
        } else {
            System.out.println("\nvoce pisca, a luz forte da tela do computador o cega por um instante.");
            System.out.println("o som de um teclado e a voz grave de um homem preenchem a sala.");
            System.out.println("--- professor maligno: ...e e por isso que a heranca e um pilar fundamental da programacao orientada a objetos, senhor(a) " + jogador.getNome() + ". esta prestando atencao?");
            System.out.println("\nvoce olha ao redor. nao ha zumbis, nem bunkers, nem minotauros. apenas colegas de classe e um quadro branco cheio de codigo.");
            System.out.println("tudo nao passou de um sonho... ou sera que nao?");
            System.out.println("\n--- fim de jogo: o despertar ---");
        }
    }
}
