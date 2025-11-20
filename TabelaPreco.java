import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TabelaPreco {
    
    private String produtoId;
    private List<Preco> precos;

    public TabelaPreco(String id) {
        this.produtoId = id;
        this.precos = new ArrayList<>();
    }

    public void adiconarPreco(Preco preco) {

        precos.add(preco);
    }

    public double obterPrecoVigente(LocalDate hoje) {

        // verifica se tem promocao ativa hoje

        for (Preco preco : precos) {
            
            if (preco.temVigencia() && preco.estaVigente(hoje)) {
                
                return preco.getValor();
            }
        }

        throw new RuntimeException("Nehum preço encontrado para o produto" + produtoId);
    }

    
}
