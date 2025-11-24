import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private List<ItemCarrinho> itens = new ArrayList<>();

    // API legacy: Guarda preço 0.0
    public void adicionarProduto(Produto produto, int quantidade) {
        if (produto == null || quantidade <= 0) return;
        adicionarProdutoComPreco(produto, quantidade, 0.0);
    }

    // Novo método: adiciona produto registrando o preço unitário vigente
    public void adicionarProdutoComPreco(Produto produto, int quantidade, double precoUnitario) {
        if (produto == null || quantidade <= 0) return;

        ItemCarrinho item = new ItemCarrinho(produto, quantidade, precoUnitario);
        this.itens.add(item);
    }

    // Calcula total usando os preços armazenados nos itens
    public double calcularTotalComPreco() {
        double soma = 0.0;
        for (ItemCarrinho it : itens) {
            soma += it.getQuantidade() * it.getPrecoUnitario();
        }
        return soma;
    }

    // Compatibilidade: calcularTotal retorna total com preços.
    public double calcularTotal() {
        return calcularTotalComPreco();
    }

    public void limparComPreco() {
        this.itens.clear();
    }

    public List<ItemCarrinho> getItensDetalhados() {
        return this.itens;
    }

    /**
     * Remove quantidade de um produto do carrinho identificado pelo SKU.
     * Se a quantidade pedida for maior ou igual à do item, remove o item completamente.
     * Retorna true se algum item foi alterado/removido, false se o SKU não foi encontrado ou parâmetros inválidos.
     */
    public boolean removerProdutoDoCarrinho(String produtoId, int quantidade) {
        if (produtoId == null || produtoId.isEmpty() || quantidade <= 0) return false;

        for (int i = 0; i < itens.size(); i++) {
            ItemCarrinho it = itens.get(i);
            if (produtoId.equals(it.getProduto().getId())) {
                if (quantidade >= it.getQuantidade()) {
                    itens.remove(i);
                } else {
                    it.setQuantidade(it.getQuantidade() - quantidade);
                }
                return true;
            }
        }
        return false;
    }

    //Conveniência: vai remover pelo objeto Produto.
    //Olha Antonio, fiz sobrecarga.
    public boolean removerProdutoDoCarrinho(Produto produto, int quantidade) {
        if (produto == null) return false;
        return removerProdutoDoCarrinho(produto.getId(), quantidade);
    }

}
