import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

// gerencia o inventario de um personagem, incluindo itens, armas e rituais.
public class Inventario {
    private final List<Item> itens;

    public Inventario() {
        this.itens = new ArrayList<>();
    }

    // adiciona um item ao inventario. se o item ja existir, aumenta a quantidade.
    public void adicionarItem(Item novoItem) {
        for (Item item : itens) {
            // usa equals para verificar se o item ja existe (precisaremos implementar o equals em item/arma/ritual)
            if (item.equals(novoItem)) {
                item.adicionarQuantidade(novoItem.getQuantidade());
                return;
            }
        }
        itens.add(novoItem);
    }

    // remove um item do inventario.
    public void removerItem(Item itemParaRemover) {
        itens.remove(itemParaRemover);
    }

    // exibe o inventario e permite ao jogador ver os detalhes de um item.
    public void exibir(Scanner scanner) {
        if (itens.isEmpty()) {
            System.out.println("\nseu inventario esta vazio.");
            return;
        }

        // ordena os itens por nome
        Collections.sort(itens, Comparator.comparing(Item::getNome));

        while (true) {
            System.out.println("\n--- inventario ---");
            for (int i = 0; i < itens.size(); i++) {
                System.out.println((i + 1) + ": " + itens.get(i));
            }
            System.out.println("0: fechar inventario");

            System.out.print("\nescolha um item para ver os detalhes (ou 0 para sair): ");
            try {
                int escolha = scanner.nextInt();
                if (escolha == 0) {
                    break;
                } else if (escolha > 0 && escolha <= itens.size()) {
                    itens.get(escolha - 1).exibirDetalhes();
                } else {
                    System.out.println("opcao invalida.");
                }
            } catch (Exception e) {
                System.out.println("entrada invalida. por favor, digite um numero.");
                scanner.next();
            }
        }
    }

    public List<Item> getItens() {
        return itens;
    }
}
