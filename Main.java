public class Main {

    public static void main(String[] args) {
        Sistema sistema = Sistema.getInstancia();

        // Cadastrar categoria
        sistema.cadastrarCategoria("Masculina");

        // Cadastra produtos (IDs gerados internamente como PROD-00000001...)
        sistema.cadastrarProduto("Camisa", "GG", "Branca", "Camisa Algodão", "Masculina");
        sistema.cadastrarProduto("Camisa", "M", "Preta", "Camisa Polo", "Masculina");
        sistema.cadastrarProduto("Calça", "42", "Azul", "Calça Jeans", "Masculina");

        // Define preços (usando os SKUs gerados)
        sistema.definirPrecoPadrao("PROD-00000001", 49.90);
        sistema.definirPrecoPadrao("PROD-00000002", 59.90);
        sistema.definirPrecoPadrao("PROD-00000003", 99.90);

        // Adiciona ao estoque
        // cria um estoque para os testes e adiciona produtos nele
        sistema.criarEstoque("MAIN");
        sistema.adiconarProdutoEstoque("MAIN", "PROD-00000001", 10);
        sistema.adiconarProdutoEstoque("MAIN", "PROD-00000002", 5);
        sistema.adiconarProdutoEstoque("MAIN", "PROD-00000003", 3);

        // --- Testes antigos (comentados para manter histórico) ---
        // // Teste do carrinho (usa preço vigente via tabela de preços)
        // sistema.adicionarAoCarrinho("PROD-00000001", 2);
        // sistema.adicionarAoCarrinho("PROD-00000002", 1);
        //
        // sistema.mostrarCarrinhoDetalhado();
        //
        // // Mostrar preços vigentes
        // sistema.mostrarProdutosComPreco();
        // ---------------------------------------------------------

        // Novo teste: simula fluxo de compra por cliente (adiciona, mostra, finaliza e verifica estoque)
        // Cria/obtém o cliente via o singleton `Sistema` (uso explícito do singleton)
        Cliente cliente = sistema.criarCliente("C001", "João Silva", "joao@example.com");

        // adiciona dois endereços ao cliente e associa um ao carrinho
        Endereco e1 = new Endereco("ADDR1", "Rua das Flores", "123", "São Paulo", "01000-000", "Apto 12");
        Endereco e2 = new Endereco("ADDR2", "Avenida Central", "500", "São Paulo", "01001-000", "Loja");
        cliente.adicionarEndereco(e1);
        cliente.adicionarEndereco(e2);

        // cria/obtém carrinho do cliente e seleciona o endereço 1
        Carrinho cartCliente = sistema.criarCarrinhoParaCliente(cliente.getId());
        cartCliente.setEndereco(e1);

        sistema.adicionarAoCarrinhoParaCliente(cliente.getId(), "PROD-00000001", 2);
        sistema.adicionarAoCarrinhoParaCliente(cliente.getId(), "PROD-00000003", 1);
        sistema.adicionarAoCarrinhoParaCliente(cliente.getId(), "ae2we21e2-1321", 3); // SKU inexistente
        sistema.mostrarCarrinhoDetalhadoParaCliente(cliente.getId());

        sistema.finalizarCompraParaCliente(cliente.getId());

        // Exibir estoque e carrinho após finalização (do cliente)
        sistema.mostrarEstoque();
        sistema.mostrarCarrinhoDetalhadoParaCliente(cliente.getId());


        System.out.println("----------------------------------------------");

        // Teste de remoção no carrinho (AGORA VAI)
        // adicionando so para testar a remoção
        sistema.adicionarAoCarrinhoParaCliente(cliente.getId(), "PROD-00000001", 3);
        sistema.adicionarAoCarrinhoParaCliente(cliente.getId(), "PROD-00000002", 2);
        System.out.println("  Após adicionar itens para teste de remoção  ");
        sistema.mostrarCarrinhoDetalhadoParaCliente(cliente.getId());

        // Remover 1 unidade do PROD-00000001 (reduzir quantidade)
        boolean removidoParcial = Sistema.getInstancia().getOrCreateCarrinho(cliente.getId()).removerProdutoDoCarrinho("PROD-00000001", 1);
        System.out.println("Remoção parcial PROD-00000001 (1 unidade): " + removidoParcial);
        sistema.mostrarCarrinhoDetalhadoParaCliente(cliente.getId());

        // Remover mais unidades do que existe no PROD-00000002 (deve remover o item completamente)
        boolean removidoTotal = Sistema.getInstancia().getOrCreateCarrinho(cliente.getId()).removerProdutoDoCarrinho("PROD-00000002", 5);
        System.out.println("Remoção completa PROD-00000002 (quantidade maior que existente): " + removidoTotal);
        sistema.mostrarCarrinhoDetalhadoParaCliente(cliente.getId());


    }
}