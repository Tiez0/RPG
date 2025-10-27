import java.util.ArrayList;
import java.util.List;

/**
 * Representa um personagem simplificado do RPG, com nome, classe, arma e rituais.
 */
public class Personagem {

    private final String nome;
    private int nex; // Removido final para permitir progressão
    private final Classe classe;
    private final Atributos atributos;
    private Arma arma; // Removido final para permitir trocas
    private List<Ritual> rituais; // Alterado para uma lista de rituais

    // Status de combate
    private int pontosDeVidaAtuais;
    private int pontosDeVidaMaximos; // Removido final para permitir atualização
    private boolean armaTravada = false;

    // Efeitos de status (ex: Cinerária)
    private int cinerariaDanoTurnos = 0;
    private int cinerariaDebuffTurnos = 0;

    public Personagem(String nome, int nex, Classe classe, Atributos atributos, Arma arma, List<Ritual> rituais) {
        this.nome = nome;
        this.nex = nex;
        this.classe = classe;
        this.atributos = atributos;
        this.arma = arma;
        this.rituais = rituais != null ? new ArrayList<>(rituais) : new ArrayList<>();

        recalcularStatus();
        this.pontosDeVidaAtuais = this.pontosDeVidaMaximos;
    }

    // --- Métodos de Progressão ---

    public void aumentarNex(int aumento) {
        this.nex += aumento;
        System.out.println("\n" + this.nome + " transcendeu! Novo NEX: " + this.nex + "%");
        recalcularStatus();
    }

    public void recalcularStatus() {
        int vidaBase = classe.getPVIniciais();
        int bonusVigor;
        if (classe instanceof Combatente) {
            bonusVigor = atributos.getVigor() * 10;
        } else {
            bonusVigor = atributos.getVigor() * 7;
        }

        int bonusCombatente = 0;
        if (classe instanceof Combatente && nex >= 30) {
            bonusCombatente = 15;
        }

        int vidaAntiga = this.pontosDeVidaMaximos;
        this.pontosDeVidaMaximos = vidaBase + bonusVigor + bonusCombatente;

        // Cura o personagem pelo aumento de vida, se houver
        if (this.pontosDeVidaMaximos > vidaAntiga) {
            int cura = this.pontosDeVidaMaximos - vidaAntiga;
            receberCura(cura);
            System.out.println(this.nome + " sentiu-se mais forte e recuperou " + cura + " PV!");
        }
    }

    public void adicionarRitual(Ritual novoRitual) {
        if (this.classe instanceof Ocultista) {
            this.rituais.add(novoRitual);
            System.out.println(this.nome + " aprendeu o ritual: " + novoRitual.getNome());
        }
    }

    public void setArma(Arma novaArma) {
        this.arma = novaArma;
        System.out.println(this.nome + " agora empunha: " + novaArma.getNome());
    }

    // --- Métodos de Combate ---

    public void receberDano(int dano) {
        this.pontosDeVidaAtuais -= dano;
        if (this.pontosDeVidaAtuais < 0) {
            this.pontosDeVidaAtuais = 0;
        }
    }

    public void receberCura(int cura) {
        this.pontosDeVidaAtuais += cura;
        if (this.pontosDeVidaAtuais > this.pontosDeVidaMaximos) {
            this.pontosDeVidaAtuais = this.pontosDeVidaMaximos;
        }
    }

    public boolean estaVivo() {
        return this.pontosDeVidaAtuais > 0;
    }

    // --- Métodos para Efeitos de Status ---

    public void aplicarCineraria() {
        this.cinerariaDanoTurnos = 5;
        this.cinerariaDebuffTurnos = 6;
        System.out.println(this.nome + " começa a ser consumido por cinzas!");
    }

    public void processarEfeitosDeStatus() {
        if (this.cinerariaDanoTurnos > 0) {
            this.cinerariaDanoTurnos--;
        }
        if (this.cinerariaDebuffTurnos > 0) {
            this.cinerariaDebuffTurnos--;
        }
    }

    public int getCinerariaDanoTurnos() {
        return cinerariaDanoTurnos;
    }

    public boolean estaDebuffado() {
        return cinerariaDebuffTurnos > 0;
    }

    // --- Métodos para Arma Travada ---

    public boolean isArmaTravada() {
        return armaTravada;
    }

    public void setArmaTravada(boolean travada) {
        this.armaTravada = travada;
    }

    public void destravarArma() {
        this.armaTravada = false;
    }

    // --- Getters ---

    public String getNome() { return nome; }
    public int getPontosDeVidaAtuais() { return pontosDeVidaAtuais; }
    public int getPontosDeVidaMaximos() { return pontosDeVidaMaximos; }
    public Classe getClasse() { return classe; }
    public Atributos getAtributos() { return atributos; }
    public Arma getArma() { return arma; }
    public List<Ritual> getRituais() { return rituais; }
    public int getNex() { return nex; }

    /**
     * Exibe a ficha simplificada do personagem.
     */
    public void exibirFicha() {
        System.out.println("\n--- Ficha do Personagem ---");
        System.out.println("Nome: " + nome);
        System.out.println("NEX: " + nex + "%");
        System.out.println("Classe: " + classe.getNome());

        System.out.println("\n--- Atributos ---");
        System.out.println(atributos);

        System.out.println("\n--- Status ---");
        System.out.println("PV: " + pontosDeVidaAtuais + " / " + pontosDeVidaMaximos);

        System.out.println("\n--- Equipamentos e Rituais ---");
        if (arma != null) {
            System.out.println("Arma: " + arma);
        } else {
            System.out.println("Arma: Desarmado");
        }
        if (rituais != null && !rituais.isEmpty()) {
            System.out.println("Rituais Conhecidos:");
            for (Ritual r : rituais) {
                System.out.println("- " + r.getNome());
            }
        }
        System.out.println("---------------------------");
    }
}
