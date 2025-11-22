import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sistema {

    private static Sistema sistema;

    private Sistema() {

        inicializarCategoriasPadrao();

    }

    public static Sistema getInstancia() {

        if (sistema == null) {

            sistema = new Sistema();
        }

        return sistema;
    }

    // essa classe está virando um monstro, porém corrigir isso não é uma batalha que quero travar.

    private Map<String, Produto> produtos = new HashMap<>();
    private Map<String, Categoria> categorias = new HashMap<>();
    private Map<String, TabelaPreco> tabelasPreco = new HashMap<>();

    private Map<String, Estoque> estoques = new HashMap<>();

    private GeradorSku geradorSku = new GeradorSku();


    // gambiarra para fazer categoria

    private void inicializarCategoriasPadrao() {

        categorias.put(Categoria.SEM_CATEGORIA.getPrefixoSKU(), Categoria.SEM_CATEGORIA);
    }

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
        String id = gerarIdProduto();

        Produto produto = new Produto(id, nome, tamanho, cor, descricao, categoriaProduto);

        produtos.put(id, produto);

        // Cria uma tabela de preco vazia para o produto no momento do cadastro

        if (!tabelasPreco.containsKey(id)) {

            tabelasPreco.put(id, new TabelaPreco(id));
        }

        System.out.println("Produto cadastrado com sucesso!");
        System.out.println(produto);

    }

    private String gerarIdProduto() {

        return geradorSku.gerarProximoSku();

    }

    // na construção de menu pode obrigar o adm a cadastrar um prduto e vincular a um estoque, caso seja necessário gerar alertar de produto não vinculado isso aqui ajuda
    public void mostrarProdutosNaoVinculadosAoEstoque() {

        System.out.println("Produtos sem estoque vinculado");

        for (Map.Entry<String, Produto> entrada : produtos.entrySet()) {

            String id = entrada.getKey();
            Produto produto = entrada.getValue();

            boolean vinculado = false;

            for (Estoque estoque : estoques.values()) {

                if (estoque.temProdutoNoEstoque(id)) {
                    
                    vinculado = true;
                    break;
                }
                
            }

            if (!vinculado) {

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

    // criar um novo estoque

    public void criarEstoque(String nome) {

        if (estoques.containsKey(nome)) {
            
            System.out.println("Estoque já existe" + nome);
            return;
        }

        estoques.put(nome, new Estoque());

        System.out.println("Estoque criado: " + nome);

    }

    public void mostrarEstoques() {

        for (Map.Entry<String, Estoque> entry :  estoques.entrySet()) {
            
            System.out.println("Estoque: " + entry.getKey());
            entry.getValue().exibirEstoque();
        }
    }

    // ADICIONAR PRODUTO NO ESTOQUE

    public void adiconarProdutoEstoque(String nomeEstoque, String id, int quantidade) {

        // mostrarProdutosNaoVinculadosAoEstoque();

        Estoque estoque = estoques.get(nomeEstoque);

        if (estoque == null) {
            System.out.println("Estoque não encontrado: " + nomeEstoque);
            return;    
        }

        Produto produto = produtos.get(id);

        if (produto == null) {
            
            System.out.println("Produto não encontrado: " + id);
            return;
        }

        estoque.addItem(produto, quantidade);

        System.out.printf("Produto %s adicionado ao estoque %s (qtd: %d) %n", id, nomeEstoque, quantidade);

    }

    public void diminuirProdutoEstoque(String nomeEstoque, String id, int quantidade) {

        Estoque estoque = estoques.get(nomeEstoque);

        if (estoque == null) {
            System.out.println("Estoque não encontrado: " + nomeEstoque);
            return;    
        }

        estoque.diminuirItem(id, quantidade);

    }

    // Visualizar estoque

    public void mostrarEstoque() {
        
        if (estoques.isEmpty()) {
            System.out.println("Nenhum estoque cadastrado");
            return;
        }

        for (Map.Entry<String, Estoque> entry : estoques.entrySet()) {
            
            System.out.println("======== Estoque: " + entry.getKey() + "========");
            entry.getValue().exibirEstoque();
        }
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


    // BLOCO DE EXIBIÇÃO - TRATA AS SAIDAS DO SISTEMA

    // exibir lista de nomes das categorias cadastradas

    public List<String> getNomesCategorias() {

        List<String> nomes = new ArrayList<>();

        for (Categoria categorias: categorias.values()) {
            
            nomes.add(categorias.getNome());
        }

        return nomes;
    }

}
