import java.time.LocalDate;
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
    private Map<String, TabelaPreco> tabelasPreco = new HashMap<>();

    private GeradorSku geradorSku = new GeradorSku();

    // declaração do estoque

    Estoque estoque1 = new Estoque();

    // BLOCO DE CADASTRAR PRODUTO

    // cadastra um produto

    public void cadastrarProduto(String nome, String tamanho, String cor, String descricao, String categoria) {

        // apontador para categoria
        Categoria categoriaProduto = localizarCategoria(categoria);

        if (categoriaProduto == null) {
            System.out.println("Categoria não encontrada: " + categoria);
            return;
        }

        // cria hash de id
        String id = gerarId(categoriaProduto);

        Produto produto = new Produto(id, nome, tamanho, cor, descricao, categoriaProduto);

        produtos.put(id, produto);

        // Cria uma tabela de preco vazia para o produto no momento do cadastro

        if (!tabelasPreco.containsKey(id)) {

            tabelasPreco.put(id, new TabelaPreco(id));
        }

        System.out.println("Produto cadastrado com sucesso!");
        System.out.println(produto);

    }

    private String gerarId(Categoria categoria) {

        String prefixo = categoria.getPrefixoSKU();
        geradorSku.registrarPrefixo(prefixo);
        return geradorSku.gerarProximoSku(prefixo);

    }

    public void mostrarProdutosNaoVinculadosAoEstoque() {

        System.out.println("Produtos sem estoque vinculado");

        for (Map.Entry<String, Produto> entrada : produtos.entrySet()) {

            String id = entrada.getKey();
            Produto produto = entrada.getValue();

            if (!estoque1.temProdutoNoEstoque(id)) {

                System.out.printf("SKU: %s | Nome: %s | Categoria: %s%n",
                        produto.getId(),
                        produto.getNome(),
                        produto.getCategoria().getNome());

            }
        }

    }

    // Cadastra Categoria

    public void cadastrarCategoria(String categoria) {

        if (categorias.containsKey(normalizarNomeCategoria(categoria))) {

            System.out.println("Categoria já cadastrada: " + categoria);
            return;
        }

        String prefixoSku = categoria.substring(0, Math.min(3, categoria.length())).toUpperCase();

        Categoria categoriaProduto = new Categoria(categoria, prefixoSku);

        this.categorias.put(normalizarNomeCategoria(categoria), categoriaProduto);

        System.out.println("Categoria cadastrada com sucesso: " + categoria + " | Prefixo SKU: " + prefixoSku);
    }

    private Categoria localizarCategoria(String categoria) {

        return this.categorias.get(normalizarNomeCategoria(categoria));

    }

    private String normalizarNomeCategoria(String nome) {

        return nome.trim().toLowerCase();
    }

    // BLOCO ESTOQUE

    // ADICIONAR PRODUTO NO ESTOQUE

    public void adiconarProdutoEstoque(String id, int quantidade) {

        // mostrarProdutosNaoVinculadosAoEstoque();

        Produto p = this.produtos.get(id);

        if (p != null) {

            estoque1.addItem(p, quantidade);
            System.out.println("Estoque atualizado para o produto: " + id);
        } else {

            System.out.println("Produto não cadastrado" + id);
        }

    }

    // Visualizar estoque

    public void mostrarEstoque() {
        estoque1.exibirEstoque();
    }

    // BLOCO DE PRECO

    public void definirPrecoPadrao(String produtoId, double valor) {

        Produto p = produtos.get(produtoId);

        if (p == null) {

            System.out.println("Produto não encontrado: " + produtoId);
            return;
        }

        // Buscar tabela; se não existir, cria e coloca no map

        TabelaPreco tabela = tabelasPreco.get(produtoId);

        if (tabela == null) {

            tabela = new TabelaPreco(produtoId);
            tabelasPreco.put(produtoId, tabela);
        }

        tabela.adicionarPreco(new Preco(valor));
        System.out.println("Preço padrão definido para ID " + produtoId + ": R$ " + valor);

    }

    public void adicionarPromocao(String produtoId, double valor, LocalDate inicio, LocalDate fim) {

        // Verificar se o produto existe

        Produto p = produtos.get(produtoId);

        if (p == null) {

            System.out.println("Produto não encontrado: " + produtoId);
            return;
        }

        // Buscar tabela; se não existir, cria e coloca no map
        TabelaPreco tabela = tabelasPreco.get(produtoId);

        if (tabela == null) {

            tabela = new TabelaPreco(produtoId);
            tabelasPreco.put(produtoId, tabela);
        }

        tabela.adicionarPreco(new Preco(valor, inicio, fim));

        System.out.printf("Promoção adicionada para ID %s: R$ %.2f (%s até %s)%n",
                produtoId, valor, inicio, fim);

    }

    public double consultarPreco(String produtoID) {

        // Verificar se o produto existe

        Produto p = produtos.get(produtoID);

        if (p == null) {

            throw new IllegalArgumentException("Produto não entrcontado: " + produtoID);
        }

        // Verifica se a tabela de preco existe

        TabelaPreco tabela = tabelasPreco.get(produtoID);

        if (tabela == null) {

            throw new IllegalArgumentException("Produto sem tabela de preço" + produtoID);
        }

        return tabela.obterPrecoVigente(LocalDate.now());

    }

    public double consultarPrecoEm(String produtoId, LocalDate data) {

        Produto p = produtos.get(produtoId);

        if (p == null) {

            throw new IllegalArgumentException("Produto não encontrado: " + produtoId);
        }

        TabelaPreco tabela = tabelasPreco.get(produtoId);

        if (tabela == null) {

            throw new IllegalArgumentException("Produto sem tabela de preço" + produtoId);
        }

        return tabela.obterPrecoVigente(data);
    }

    public void mostrarProdutosComPreco() {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("Lista de Produtos com Preço Vigente");

        for (Map.Entry<String, Produto> entrada : produtos.entrySet()) {

            String id = entrada.getKey();
            Produto produto = entrada.getValue();
            String nome = produto.getNome();

            double preco;

            try {

                TabelaPreco tabela = tabelasPreco.get(id);
                preco = tabela.obterPrecoVigente(LocalDate.now());
                System.out.printf("ID: %s | Nome: %s | Preço: R$ %.2f%n", id, nome, preco);

            } catch (Exception e) {

                System.out.printf("ID: %s | Nome: %s | Preço: NÃO DEFINIDO%n", id, nome);
            }
        }

    }

}
