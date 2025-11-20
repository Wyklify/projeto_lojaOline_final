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

    public void adicionarPreco(Preco preco) {

        precos.add(preco);
    }

    public double obterPrecoVigente(LocalDate hoje) {

        // verifica se tem promocao ativa hoje

        Preco melhorPromocao = null;

        for (Preco preco : precos) {

            if (preco.temVigencia() && preco.estaVigente(hoje)) {

                if (melhorPromocao == null || preco.getValor() < melhorPromocao.getValor()) {

                    melhorPromocao = preco;
                }
            }
        }

        if (melhorPromocao != null) {

            return melhorPromocao.getValor();
            
        }

        // se não houver promoção, procura o preco padrão

        for (Preco preco : precos) {
            
            if (!preco.temVigencia()) {
                
                return preco.getValor();
            }
        }

        // não se não tiver preço
        throw new RuntimeException("Nehum preço encontrado para o produto " + produtoId);
    }

}
