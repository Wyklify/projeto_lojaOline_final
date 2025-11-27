import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private List<ItemCarrinho> itens = new ArrayList<>();
    private String id; // identifica o carrinho (por ex. id do cliente)

    public Carrinho(String id) {
        this.id = id;
    }

    // Construtor sem-args para compatibilidade (cria id genérico)
    public Carrinho() {
        this.id = "DEFAULT";
    }
    // O Carrinho guarda apenas intenção: produto + quantidade
    public void adicionarProduto(Produto produto, int quantidade) {
        if (produto == null || quantidade <= 0) return;

        // se já houver item para o produto, incrementa quantidade
        for (ItemCarrinho it : itens) {
            if (it.getProduto().getId().equals(produto.getId())) {
                it.setQuantidade(it.getQuantidade() + quantidade);
                return;
            }
        }

        ItemCarrinho item = new ItemCarrinho(produto, quantidade);
        this.itens.add(item);
    }

    public List<ItemCarrinho> getItensDetalhados() {
        return this.itens;
    }

    public void limpar() {
        this.itens.clear();
    }

    public String getId() {
        return this.id;
    }

    // Endereço selecionado para entrega (id referenciando um Endereco do cliente)
    private String enderecoId;

    public void setEnderecoId(String enderecoId) {
        this.enderecoId = enderecoId;
    }

    public String getEnderecoId() {
        return this.enderecoId;
    }

    public void setEndereco(Endereco endereco) {
        if (endereco != null) this.enderecoId = endereco.getId();
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
    //Convenience: remove by Produto object
    public boolean removerProdutoDoCarrinho(Produto produto, int quantidade) {
        if (produto == null) return false;
        return removerProdutoDoCarrinho(produto.getId(), quantidade);
    }

}
