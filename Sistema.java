import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Menus.MenuUtils;

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

    private Map<String, Produto> produtos = new HashMap<>();
    private Map<String, Categoria> categorias = new HashMap<>();
    private Map<String, TabelaPreco> tabelasPreco = new HashMap<>();

    private Map<String, Estoque> estoques = new HashMap<>();
    private Map<String, Pedido> pedidos = new HashMap<>();
    private Map<String, Cliente> clientes = new HashMap<>();

    private Map<String, Cliente> clientes = new HashMap<>();

    private GeradorSku geradorSku = new GeradorSku();

    // inicializa a categoria com uma label padrão de sem categoria

    private void inicializarCategoriasPadrao() {

        categorias.put(Categoria.SEM_CATEGORIA.getPrefixoSKU(), Categoria.SEM_CATEGORIA);
    }

    // Nova tabela simples de estoque (quantidade por produto) usada pelo carrinho
    private java.util.Map<String, Integer> tabelaEstoque = new java.util.HashMap<>();

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

        return geradorSku.gerarProximoSkuProduto();

    }

    // na construção de menu pode obrigar o adm a cadastrar um prduto e vincular a
    // um estoque, caso seja necessário gerar alertar de produto não vinculado isso
    // aqui ajuda
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

    /**
     * Consulta a quantidade disponível na tabela de estoque (não acessa o objeto
     * `Estoque`).
     */
    public int consultarQuantidadeTabela(String produtoId) {
        return tabelaEstoque.getOrDefault(produtoId, 0);
    }

    /**
     * Decrementa a tabela de estoque na finalização. Retorna true se conseguiu
     * decrementar.
     */
    public boolean decrementarTabelaEstoque(String produtoId, int quantidade) {
        int atual = tabelaEstoque.getOrDefault(produtoId, 0);
        if (atual >= quantidade) {
            tabelaEstoque.put(produtoId, atual - quantidade);
            return true;
        }
        return false;
    }

    /**
     * Incrementa a tabela de estoque (uso para rollback).
     */
    public void incrementarTabelaEstoque(String produtoId, int quantidade) {
        int atual = tabelaEstoque.getOrDefault(produtoId, 0);
        tabelaEstoque.put(produtoId, atual + quantidade);
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

    public void mostrarEstoque(String nome) {

        Estoque estoque = estoques.get(nome);

        if (estoque == null) {

            System.out.println("Estoque não encontrado: " + nome);

            return;
        }

        System.out.println("======== Estoque: " + nome + " ========");
        estoque.exibirEstoque();

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

    public void consultarPrecoEm(String produtoId, LocalDate data) {

        Produto p = produtos.get(produtoId);

        if (p == null) {

            throw new IllegalArgumentException("Produto não encontrado: " + produtoId);
        }

        TabelaPreco tabela = tabelasPreco.get(produtoId);

        if (tabela == null) {

            throw new IllegalArgumentException("Produto sem tabela de preço" + produtoId);
        }

        double preco = tabela.obterPrecoVigente(data);

        System.out.printf("Preço vigente para %s em %s: R$ %.2f%n",
                produtoId, data, preco);

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

    // Bloco do carrinho

    // Mapa de carrinhos por cliente (cada cliente tem seu carrinho)
    private java.util.Map<String, Carrinho> carrinhos = new java.util.HashMap<>();

    // Retorna o carrinho "default" (compatibilidade)
    public Carrinho getCarrinho() {
        return getOrCreateCarrinho("DEFAULT");
    }

    // Retorna ou cria um carrinho associado ao clienteId
    public Carrinho getOrCreateCarrinho(String clienteId) {
        if (clienteId == null || clienteId.isEmpty())
            clienteId = "DEFAULT";
        return carrinhos.computeIfAbsent(clienteId, id -> new Carrinho(id));
    }

    // Cria explicitamente um carrinho para um cliente (se já existir, retorna o
    // existente)
    public Carrinho criarCarrinhoParaCliente(String clienteId) {
        return getOrCreateCarrinho(clienteId);
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

        // Usa o novo método seguro do Carrinho que recebe o preço unitário (cart
        // DEFAULT)
        this.getCarrinho().adicionarProdutoComPreco(p, quantidade, preco);
        System.out.printf("Adicionado ao carrinho: %s x%d (R$ %.2f each)%n", produtoId, quantidade, preco);
    }

    public void mostrarCarrinhoDetalhado() {
        System.out.println("================ CARRINHO =================");
        Carrinho cart = this.getCarrinho();
        if (cart.getItensDetalhados().isEmpty()) {
            System.out.println("Carrinho vazio.");
            return;
        }

        for (ItemCarrinho item : cart.getItensDetalhados()) {
            Produto pr = item.getProduto();
            System.out.printf("SKU: %s | Nome: %s | Qtde: %d%n",
                pr.getId(), pr.getNome(), item.getQuantidade());
        }
        System.out.println("Total: (calculado no Pedido)");
    }

    // Versões por cliente
    public void adicionarAoCarrinhoParaCliente(String clienteId, String produtoId, int quantidade) {
        Produto p = produtos.get(produtoId);

        if (p == null) {
            System.out.println("Produto não encontrado: " + produtoId);
            return;
        }
        Carrinho cart = getOrCreateCarrinho(clienteId);
        cart.adicionarProduto(p, quantidade);
        System.out.printf("Adicionado ao carrinho (cliente=%s): %s x%d%n", clienteId, produtoId, quantidade);
    }

    public void mostrarCarrinhoDetalhadoParaCliente(String clienteId) {
        Carrinho cart = getOrCreateCarrinho(clienteId);
        System.out.println("================ CARRINHO (cliente=" + clienteId + ") =================");
        if (cart.getItensDetalhados().isEmpty()) {
            System.out.println("Carrinho vazio.");
            return;
        }

        for (ItemCarrinho item : cart.getItensDetalhados()) {
            Produto pr = item.getProduto();
            System.out.printf("SKU: %s | Nome: %s | Qtde: %d%n",
                pr.getId(), pr.getNome(), item.getQuantidade());
        }
        System.out.println("Total: (calculado no Pedido)");
    }

    public void finalizarCompraParaCliente(String clienteId) {
        Carrinho cart = getOrCreateCarrinho(clienteId);
        var itens = cart.getItensDetalhados();
        if (itens.isEmpty()) {
            System.out.println("Carrinho vazio. Nada a finalizar.");
            return;
        }

        java.util.List<String> removidos = new java.util.ArrayList<>();

        for (ItemCarrinho it : itens) {
            String sku = it.getProduto().getId();
            int qtd = it.getQuantidade();

            boolean ok = decrementarTabelaEstoque(sku, qtd);
            if (!ok) {
                System.out.println("Estoque insuficiente para produto: " + sku + ". Finalização abortada para cliente "
                        + clienteId);
                for (String r : removidos) {
                    String[] parts = r.split(":");
                    String s = parts[0];
                    int q = Integer.parseInt(parts[1]);
                    incrementarTabelaEstoque(s, q);
                }
                return;
            }

            removidos.add(sku + ":" + qtd);
        }

        System.out.println("Compra finalizada para cliente " + clienteId + ".");
        cart.limpar();
    }

    /**
     * Finaliza a compra: verifica disponibilidade no estoque e decrementa as
     * quantidades.
     * Se algum item não puder ser atendido, desfaz as remoções já feitas e informa
     * o usuário.
     */
    public void finalizarCompra() {
        var itens = this.getCarrinho().getItensDetalhados();
        if (itens.isEmpty()) {
            System.out.println("Carrinho vazio. Nada a finalizar.");
            return;
        }

        // lista de itens que já foram reduzidos no estoque (produtoId, quantidade)
        java.util.List<String> removidos = new java.util.ArrayList<>();

        for (ItemCarrinho it : itens) {
            String sku = it.getProduto().getId();
            int qtd = it.getQuantidade();

            // usa a tabela simples de estoque em vez de acessar diretamente o objeto
            // Estoque
            boolean ok = decrementarTabelaEstoque(sku, qtd);
            if (!ok) {
                System.out.println("Estoque insuficiente para produto: " + sku + ". Finalização abortada.");
                // desfazer removidos na tabela
                for (String r : removidos) {
                    // r tem formato sku:quant
                    String[] parts = r.split(":");
                    String s = parts[0];
                    int q = Integer.parseInt(parts[1]);
                    incrementarTabelaEstoque(s, q);
                }
                return;
            }

            removidos.add(sku + ":" + qtd);
        }

        // Se chegou aqui, todos os itens foram removidos do estoque — cria um Pedido a partir do Carrinho
        Carrinho cart = this.getCarrinho();

        // cria um cliente padrão para pedidos finalizados sem cliente explícito
        Cliente clientePadrao = new Cliente("Consumidor", "DEFAULT");
        Pedido pedido = criarPedido(clientePadrao);

        // adiciona itens ao pedido consultando os preços vigentes
        for (ItemCarrinho it : itens) {
            String sku = it.getProduto().getId();
            int qtd = it.getQuantidade();
            Produto produto = produtos.get(sku);
            TabelaPreco tabela = tabelasPreco.get(sku);

            if (tabela == null) {
                System.out.println("Preço não definido para produto: " + sku + ". Finalização abortada.");
                // rollback do estoque
                for (String r : removidos) {
                    String[] parts = r.split(":");
                    String s = parts[0];
                    int q = Integer.parseInt(parts[1]);
                    incrementarTabelaEstoque(s, q);
                }
                // remove pedido criado
                pedidos.remove(pedido.getId());
                return;
            }

            double precoAtual = tabela.obterPrecoVigente(LocalDate.now());
            pedido.addItem(produto, qtd, precoAtual);
        }

        double total = pedido.calcularTotal().doubleValue();
        System.out.printf("Compra finalizada. Pedido ID: %s | Total: R$ %.2f%n", pedido.getId(), total);
        cart.limpar();
    }

    // BLOCO DE PEDDIDOS

    public void criarPedido(String idCliente) {

        Cliente cliente = clientes.get(idCliente);

        if (cliente == null) {
            System.out.println("Cliente não encontrado para o ID: " + idCliente);
            return;
        }

        Pedido pedido = criarPedido(cliente);

        System.out.println("Pedido criado com sucesso! ID: " + pedido.getId() + " | Cliente: " + cliente.getNome());

    }

    // A ideia é criar um pedido, encher ele de itens e depois pagar.
    public Pedido criarPedido(Cliente cliente) {

        String idPedido = geradorSku.gerarProximoSkuPedido();

        Pedido pedido = new Pedido(cliente, idPedido);

        pedidos.put(idPedido, pedido);

        return pedido;
    }

    // BLOCO DE CLIENTES

    public Cliente criarCliente(String id, String nome, String email) {
        if (id == null || id.isEmpty()) {
            System.out.println("ID de cliente inválido");
            return null;
        }
        Cliente existente = clientes.get(id);
        if (existente != null) {
            return existente;
        }
        Cliente c = new Cliente(id, nome, email);
        clientes.put(id, c);
        System.out.println("Cliente criado/registrado: " + id + " | " + nome);
        return c;
    }

    public Cliente getCliente(String id) {
        return clientes.get(id);
    }

    public void adicionarItemAoPedido(String idPedido, String idProduto, int qtd) {

        Pedido pedido = pedidos.get(idPedido);
        Produto produto = produtos.get(idProduto);
        TabelaPreco tabela = tabelasPreco.get(idProduto);

        if (pedido == null) {

            System.out.println("Pedido não encontrado: " + idPedido);
            return;
        }

        if (produto == null) {
            System.out.println("Produto não encontrado: " + idProduto);
            return;
        }
        if (tabela == null) {
            System.out.println("Tabela de preço não encontrada para produto: " + idProduto);
            return;
        }

        // Procura disponibilidade do item em todos os estoques

        String estoqueUsadoID = null;

        for (Map.Entry<String, Estoque> entrada : estoques.entrySet()) {

            String idEstoque = entrada.getKey();
            Estoque estoque = entrada.getValue();

            if (estoque.temProdutoNoEstoque(idProduto)) {

                boolean conseguiuRemoverEstoque = estoque.retirarProduto(idProduto, qtd);

                if (conseguiuRemoverEstoque) {
                    estoqueUsadoID = idEstoque;
                    break;
                }
            }
        }

        if (estoqueUsadoID == null) {

            System.out.printf("Produto %s não disponível em quantidade suficiente (%d) em nenhum estoque.%n", idProduto,
                    qtd);
            return;

        }

        double precoAtual = qtd * tabela.obterPrecoVigente(LocalDate.now());

        pedido.addItem(produto, qtd, precoAtual);

        System.out.printf("Item adicionado ao pedido %s: %s (Qtd: %d, Preço: R$ %.2f) retiado do estoque: %s%n",
                idPedido, produto.getNome(), qtd, precoAtual, estoqueUsadoID);

    }

    // Configurar fre e desconto no pedido

    public void configurarFreteDesconto(String idPedido, BigDecimal frete, BigDecimal desconto) {

        Pedido pedido = pedidos.get(idPedido);

        if (pedido != null) {
            pedido.configurarFreteDesconto(frete, desconto);

            System.out.println("Frete e desconto configurados para o pedido: " + idPedido);
        } else {

            System.out.println("Pedido não encontrado: " + idPedido);
        }

    }

    // registra o método de pagamento
    public void registrarPagamento(String idPedido, MetodoPagamento metodo) {

        Pedido pedido = pedidos.get(idPedido);

        if (pedido != null) {

            String idPagamento = "PAG-" + idPedido;
            pedido.registrarPagamento(idPagamento, metodo);

            pedido.setStatus(StatusPagamento.CONFIRMADO);
        } else {

            System.out.println("Pedido não encontrado: " + idPedido);
        }
    }

    // confirmar se o pagamento foi pago

    public void confirmarPagamento(String idPedido) {

        Pedido pedido = pedidos.get(idPedido);

        if (pedido != null) {

            pedido.confirmarPagamento();
        } else {

            System.out.println("Pedido não encontrado: " + idPedido);
        }
    }

    // Cancelar pagamento

    public void cancelarPagamento(String idPedido) {

        Pedido pedido = pedidos.get(idPedido);

        if (pedido != null) {
            pedido.cancelarPagamento();
        } else {

            System.out.println(" Pedido não encontrado: " + idPedido);
        }
    }

    // mostrar detalhes de um pedido

    public void exibirPedido(String idPedido) {

        Pedido pedido = pedidos.get(idPedido);

        if (pedido != null) {
            System.out.println("=== Detalhes do Pedido ===");
            System.out.println("ID: " + idPedido);
            System.out.println("Cliente: " + pedido.getCliente().getNome());
            System.out.println("Status: " + pedido.getStatus());
            System.out.println("Total: R$ " + pedido.calcularTotal());
        } else {
            System.out.println("Pedido não encontrado: " + idPedido);
        }

    }

    // BLOCO DE CADASTRO DE CLIENTE

    public void cadastrarCliente(String nome, String email, String cpf) {

        if (this.localizarCpf(cpf)) {

            System.out.println("Cliente já cadastrado");

        } else {

            String idCliente = geradorSku.gerarProximoSKUCliente();

            Cliente cliente = new Cliente(idCliente, nome, email, cpf);

            this.clientes.put(idCliente, cliente);

            System.out.println("Cliente cadastrado com sucesso! ID: " + idCliente + " | Nome: " + nome);

        }

    }

    private Boolean localizarCpf(String cpf) {

        for (Cliente cliente : this.clientes.values()) {

            if (cliente.getCpf().equals(cpf)) {
                return true;
            }
        }

        return false;
    }

    public void mostrarCliente(String idCliente) {

        MenuUtils.limparTela();

        Cliente cliente = clientes.get(idCliente);

        if (cliente == null) {

            System.out.println("Cliente não encontrado para o ID: " + idCliente);
            return;
        }

        System.out.println("=== Dados do Cliente ===");

        System.out.println("ID: " + cliente.getId());
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("Email: " + cliente.getEmail());

        System.out.println(MenuUtils.centralizarTextos(String.format("Endereço: %s ", cliente.getId())));

        if (!cliente.getEnderecos().isEmpty()) {

            for (Endereco enderecos : cliente.getEnderecos()) {

                System.out.println(enderecos);
            }
        } else {

            System.out.println("Nenhum endereço cadastrado");
        }

        System.out.println(MenuUtils.centralizarTextos(String.format("Pedidos: %s ", cliente.getId())));

        boolean temPedidos = false;

        for (Pedido pedido : pedidos.values()) {

            if (pedido.getCliente().getId().equals(idCliente)) {

                System.out.println(pedido);
                temPedidos = true;
            }
        }

        if (!temPedidos) {

            System.out.println("Nenhum pedido encontrado para este cliente");
        }

    }

    public void listarClientes() {

        if (this.clientes.isEmpty()) {

            System.out.println("Nenhum cliente cadastrado");

        } else {

            for (Map.Entry<String, Cliente> entrada : clientes.entrySet()) {

                Cliente cliente = entrada.getValue();

                System.out.println(" ID: " + entrada.getKey() + " | Nome: " + cliente.getNome());

            }

        }

    }

    // BLOCO DE EXIBIÇÃO - TRATA AS SAIDAS DO SISTEMA

    // exibir lista de nomes das categorias cadastradas

    public List<String> getNomesCategorias() {

        List<String> nomes = new ArrayList<>();

        for (Categoria categorias : categorias.values()) {

            nomes.add(categorias.getNome());
        }

        return nomes;
    }

}
