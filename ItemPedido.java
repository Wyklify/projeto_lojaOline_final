public class ItemPedido {
    
    private int quantidade;
    private Produto produto;
    private double precoUnitario;
    
      



    public ItemPedido(int quantidade, Produto produto, double precoUnitario) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.precoUnitario = precoUnitario;
    }



    public int getQuantidade() {
        return quantidade;
    }



    public Produto getProduto() {
        return produto;
    }



    public double getPrecoUnitario() {
        return precoUnitario;
    }


    

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }



    public void setProduto(Produto produto) {
        this.produto = produto;
    }



    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }



    public double calcularSubtotal() {

        return quantidade * precoUnitario;
    }
    
    
}
