
import java.util.HashMap;
import java.util.Map;

public class Estoque {

    private Map<String, ItemEstoque> itens = new HashMap<>();

    public void addItem(Produto produto, int quantidade) {

        String id = produto.getId();

        ItemEstoque itemExistente = localizarItem(id);

        if (itemExistente != null) {

            itemExistente.setQuantidade(itemExistente.getQuantidade() + quantidade);

        } else {

            ItemEstoque novoItem = new ItemEstoque(produto, quantidade);

            this.itens.put(id, novoItem);
        }

    }

    public void diminuirItem(String codigo, int quantidade) {

        ItemEstoque item = this.localizarItem(codigo);

        if (item == null) {

            System.out.println("Item nao encontrado no estoque");

        } else {

            if (item.getQuantidade() >= quantidade) {

                item.setQuantidade(item.getQuantidade() - quantidade);

            } else {
                System.out.println("Estoque insuficiente");
            }

        }

    }

    private ItemEstoque localizarItem(String codigo) {

        ItemEstoque item = itens.get(codigo);

        return item;

    }

    public boolean temProdutoNoEstoque(String codigo) {

        return itens.containsKey(codigo);
    }


    public void exibirEstoque() {

        if (itens.isEmpty()) {
            
            System.out.println("Estoque vazio.");
            return;
        }

        System.out.println("================ ESTOQUE ATUAL ================");

        for (ItemEstoque item : itens.values()) {

            Produto p = item.getProduto();
            System.out.printf(
                "ID: %s | Nome: %s | Tamanho: %s | Cor: %s | Categoria: %s | Quantidade: %d%n", 
                p.getId(),
                p.getNome(),
                p.getTamanho(),
                p.getCor(),
                deixarMaisculo(p.getCategoria().getNome()),
                item.getQuantidade()

            );
            
        }

    }

    private String deixarMaisculo( String texto) {

        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }

    /**
     * Tenta retirar `quantidade` do produto no estoque.
     * Retorna true se conseguiu remover a quantidade inteira, false caso contrário. */
    public boolean retirarProduto(String produtoId, int quantidade) {
        ItemEstoque item = this.localizarItem(produtoId);
        if (item == null) {
            return false;
        }

        if (item.getQuantidade() >= quantidade) {
            item.setQuantidade(item.getQuantidade() - quantidade);
            return true;
        }

        return false;
    }
}
