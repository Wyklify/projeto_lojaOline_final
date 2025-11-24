import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    // essa classe está virando um monstro, porém corrigir isso não é uma batalha que quero travar.

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

    //Bloco do carrinho
    
    // instância única do carrinho gerenciada pelo Sistema
    private Carrinho carrinho = new Carrinho();

    public Carrinho getCarrinho() {
        return this.carrinho;
    }

    // Adiciona ao carrinho consultando a tabela de preços (não altera Produto)
    public void adicionarAoCarrinho(String produtoId, int quantidade) {
        Produto p = produtos.get(produtoId);

        if (p == null) {
            System.out.println("Produto não encontrado: " + produtoId);
            return;
        }

        double preco;
        try {
            preco = consultarPreco(produtoId);
        } catch (Exception e) {
            System.out.println("Preço não definido para produto: " + produtoId);
            return;
        }

        // Usa o novo método seguro do Carrinho que recebe o preço unitário
        this.carrinho.adicionarProdutoComPreco(p, quantidade, preco);
        System.out.printf("Adicionado ao carrinho: %s x%d (R$ %.2f each)%n", produtoId, quantidade, preco);
    }

    public void mostrarCarrinhoDetalhado() {
        System.out.println("================ CARRINHO =================");
        if (this.carrinho.getItensDetalhados().isEmpty()) {
            System.out.println("Carrinho vazio.");
            return;
        }

        for (ItemCarrinho item : this.carrinho.getItensDetalhados()) {
            Produto pr = item.getProduto();
            System.out.printf("SKU: %s | Nome: %s | Qtde: %d | Unit: R$ %.2f | Subtotal: R$ %.2f%n",
                pr.getId(), pr.getNome(), item.getQuantidade(), item.getPrecoUnitario(), item.getQuantidade() * item.getPrecoUnitario());
        }

        System.out.printf("Total: R$ %.2f%n", this.carrinho.calcularTotalComPreco());
    }

    /**
     * Finaliza a compra: verifica disponibilidade no estoque e decrementa as quantidades.
     * Se algum item não puder ser atendido, desfaz as remoções já feitas e informa o usuário.
     */
    public void finalizarCompra() {
        var itens = this.carrinho.getItensDetalhados();
        if (itens.isEmpty()) {
            System.out.println("Carrinho vazio. Nada a finalizar.");
            return;
        }

        // lista de itens que já foram reduzidos no estoque (produtoId, quantidade)
        java.util.List<String> removidos = new java.util.ArrayList<>();

        for (ItemCarrinho it : itens) {
            String sku = it.getProduto().getId();
            int qtd = it.getQuantidade();

            boolean ok = estoque1.retirarProduto(sku, qtd);
            if (!ok) {
                System.out.println("Estoque insuficiente para produto: " + sku + ". Finalização abortada.");
                // desfazer removidos
                for (String r : removidos) {
                    // r tem formato sku:quant
                    String[] parts = r.split(":");
                    String s = parts[0];
                    int q = Integer.parseInt(parts[1]);
                    // re-adiciona ao estoque
                    Produto p = produtos.get(s);
                    if (p != null) {
                        estoque1.addItem(p, q);
                    }
                }
                return;
            }

            removidos.add(sku + ":" + qtd);
        }

        // Se chegou aqui, todos os itens foram removidos do estoque — gera resumo e limpa carrinho
        System.out.printf("Compra finalizada. Total: R$ %.2f%n", this.carrinho.calcularTotalComPreco());
        this.carrinho.limparComPreco();
    }

}
