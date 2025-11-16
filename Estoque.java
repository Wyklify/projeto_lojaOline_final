
import java.util.HashMap;
import java.util.Map;

public class Estoque {

    private Map<String, ItemEstoque> itens = new HashMap<>();

    public void addItem(Produto produto, int quantidade) {

        ItemEstoque itemEstoque = new ItemEstoque(produto, quantidade);

        this.itens.put(produto.getId(), itemEstoque);

    }

    public void diminuirItem(String codigo, int quantidade) {
        
        ItemEstoque item = itens.get(codigo);

        if (item != null && item.getQuantidade() >= quantidade) {
            
            item.setQuantidade(item.getQuantidade() - quantidade);
        } else {

            throw new IllegalArgumentException("Estoque insuficiente");
        }
        
        

    }

    

}
