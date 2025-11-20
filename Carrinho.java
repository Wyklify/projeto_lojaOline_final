import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private List<ItemCarrinho> itens = new ArrayList<>();
    private double total = 0.0;

    public void adicionarProduto(Produto produto, int quantidade) {
        if (produto == null || quantidade <= 0) {
            return;
        }

        ItemCarrinho item = new ItemCarrinho(produto, quantidade);
        this.itens.add(item);
        this.total += produto.getPreco() * quantidade;
    }

    public double calcularTotal() {
        return this.total;
    }

    public void limpar() {
        this.itens.clear();
        this.total = 0.0;
    }

    public List<ItemCarrinho> getItens() {
        return this.itens;
    }

}
