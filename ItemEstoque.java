public class ItemEstoque {

    private int quantidade;
    private Produto produto;

    

    public ItemEstoque(Produto produto, int quantidade) {
        this.quantidade = quantidade;
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    

}
