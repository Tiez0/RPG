import java.util.ArrayList;
import java.util.List;

// representa um personagem simplificado do rpg, com nome, classe, e inventario.
public class Personagem {

    private final String nome;
    private int nex; // removido final para permitir progressao
    private final Classe classe;
    private final Atributos atributos;
    private final Inventario inventario;
    private Arma armaEquipada; // para rastrear a arma atualmente equipada

    // status de combate
    private int pontosDeVidaAtuais;
    private int pontosDeVidaMaximos; // removido final para permitir atualizacao
    private boolean armaTravada = false;

    // efeitos de status (ex: cineraria)
    private int cinerariaDanoTurnos = 0;
    private int cinerariaDebuffTurnos = 0;

    public Personagem(String nome, int nex, Classe classe, Atributos atributos, Arma armaInicial, List<Ritual> rituaisIniciais) {
        this.nome = nome;
        this.nex = nex;
        this.classe = classe;
        this.atributos = atributos;
        this.inventario = new Inventario();
        if (armaInicial != null) {
            this.inventario.adicionarItem(armaInicial);
            this.armaEquipada = armaInicial;
        }
        if (rituaisIniciais != null) {
            for (Ritual r : rituaisIniciais) {
                this.inventario.adicionarItem(r);
            }
        }

        recalcularStatus();
        this.pontosDeVidaAtuais = this.pontosDeVidaMaximos;
    }

    // --- metodos de progressao ---

    public void setNex(int novoNex) {
        if (novoNex > this.nex) {
            int vidaAntes = this.pontosDeVidaAtuais;
            this.nex = novoNex;
            recalcularStatus();
            int vidaGanha = this.pontosDeVidaMaximos - vidaAntes;
            receberCura(vidaGanha);

            System.out.println("\n" + this.nome + " transcendeu! novo nex: " + this.nex + "%");
            if (vidaGanha > 0) {
                System.out.println("voce se sente mais forte e recuperou " + vidaGanha + " pv!");
            }
        }
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

        this.pontosDeVidaMaximos = vidaBase + bonusVigor + bonusCombatente;
    }

    public void adicionarRitual(Ritual novoRitual) {
        if (this.classe instanceof Ocultista) {
            this.inventario.adicionarItem(novoRitual);
            System.out.println(this.nome + " aprendeu o ritual: " + novoRitual.getNome());
        }
    }

    public void equiparArma(Arma novaArma) {
        this.armaEquipada = novaArma;
        System.out.println(this.nome + " agora empunha: " + novaArma.getNome());
    }

    // --- metodos de combate ---

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

    // --- metodos para efeitos de status ---

    public void aplicarCineraria() {
        this.cinerariaDanoTurnos = 5;
        this.cinerariaDebuffTurnos = 6;
        System.out.println(this.nome + " comeca a ser consumido por cinzas!");
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

    // --- metodos para arma travada ---

    public boolean isArmaTravada() {
        return armaTravada;
    }

    public void setArmaTravada(boolean travada) {
        this.armaTravada = travada;
    }

    public void destravarArma() {
        this.armaTravada = false;
    }

    // --- getters ---

    public String getNome() { return nome; }
    public int getPontosDeVidaAtuais() { return pontosDeVidaAtuais; }
    public int getPontosDeVidaMaximos() { return pontosDeVidaMaximos; }
    public Classe getClasse() { return classe; }
    public Atributos getAtributos() { return atributos; }
    public Inventario getInventario() { return inventario; }
    public int getNex() { return nex; }

    public Arma getArmaEquipada() {
        return armaEquipada;
    }

    public List<Arma> getArmasNoInventario() {
        List<Arma> armas = new ArrayList<>();
        for (Item item : inventario.getItens()) {
            if (item instanceof Arma) {
                armas.add((Arma) item);
            }
        }
        return armas;
    }

    public List<Ritual> getRituais() {
        List<Ritual> rituais = new ArrayList<>();
        for (Item item : inventario.getItens()) {
            if (item instanceof Ritual) {
                rituais.add((Ritual) item);
            }
        }
        return rituais;
    }

    // exibe a ficha simplificada do personagem.
    public void exibirFicha() {
        System.out.println("\n--- ficha do personagem ---");
        System.out.println("nome: " + nome);
        System.out.println("nex: " + nex + "%");
        System.out.println("classe: " + classe.getNome());

        System.out.println("\n--- atributos ---");
        System.out.println(atributos);

        System.out.println("\n--- status ---");
        System.out.println("pv: " + pontosDeVidaAtuais + " / " + pontosDeVidaMaximos);

        System.out.println("\n--- equipamentos e rituais ---");
        if (armaEquipada != null) {
            System.out.println("arma equipada: " + armaEquipada);
        } else {
            System.out.println("arma: desarmado");
        }
        List<Ritual> rituais = getRituais();
        if (rituais != null && !rituais.isEmpty()) {
            System.out.println("rituais conhecidos:");
            for (Ritual r : rituais) {
                System.out.println("- " + r.getNome());
            }
        }
        System.out.println("---------------------------");
    }
}
