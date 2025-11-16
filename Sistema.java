import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Sistema {

    private static Sistema sistema;

    private Sistema() {

    }

    public static Sistema getInstancia() {

        if (sistema == null) {

            sistema = new Sistema();
        }

        return sistema;
    }

    private Map<String, Produto> produtos = new HashMap<>();
    private Map<String, Categoria> categorias = new HashMap<>();

    // declaração do estoque

    Estoque estoque1 = new Estoque();

    // cadastra um produto

    public void cadastrarProduto(String nome, String tamanho, String cor, String descricao, String categoria, int qtd) {

        // cria instancia de categoria
        Categoria categoriaProduto = localizarCareCategoria(categoria);

        // cria hash de id
        String id = gerarId();

        Produto produto = new Produto(id, nome, tamanho, cor, descricao, categoriaProduto);

        produtos.put(id, produto);

        estoque1.addItem(produto, qtd);        
        
    }

    private String gerarId() {

        return UUID.randomUUID().toString();

    }


    // Cadastra Categoria

    public void cadastrarCategoria(String categoria) {
        
        Categoria categoriaProduto = new Categoria(categoria);

        this.categorias.put(categoria, categoriaProduto);
    }

    private Categoria localizarCareCategoria(String categoria) {


        return this.categorias.get(categoria);

    }

}
