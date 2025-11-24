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
        sistema.adiconarProdutoEstoque("PROD-00000001", 10);
        sistema.adiconarProdutoEstoque("PROD-00000002", 5);
        sistema.adiconarProdutoEstoque("PROD-00000003", 3);

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

        // Novo teste: simula fluxo de compra (adiciona, mostra, finaliza e verifica estoque)
        sistema.adicionarAoCarrinho("PROD-00000001", 2);
        sistema.adicionarAoCarrinho("PROD-00000003", 1);
        sistema.adicionarAoCarrinho("ae2we21e2-1321", 3);
        sistema.mostrarCarrinhoDetalhado();

        sistema.finalizarCompra();

        // Exibir estoque e carrinho após finalização
        sistema.mostrarEstoque();
        sistema.mostrarCarrinhoDetalhado();


        System.out.println("----------------------------------------------");

        // Teste de remoção no carrinho (AGORA VAI)
        // adicionando so para testar a remoção
        sistema.adicionarAoCarrinho("PROD-00000001", 3);
        sistema.adicionarAoCarrinho("PROD-00000002", 2);
        System.out.println("  Após adicionar itens para teste de remoção  ");
        sistema.mostrarCarrinhoDetalhado();

        // Remover 1 unidade do PROD-00000001 (reduzir quantidade)
        boolean removidoParcial = Sistema.getInstancia().getCarrinho().removerProdutoDoCarrinho("PROD-00000001", 1);
        System.out.println("Remoção parcial PROD-00000001 (1 unidade): " + removidoParcial);
        sistema.mostrarCarrinhoDetalhado();

        // Remover mais unidades do que existe no PROD-00000002 (deve remover o item completamente)
        boolean removidoTotal = Sistema.getInstancia().getCarrinho().removerProdutoDoCarrinho("PROD-00000002", 5);
        System.out.println("Remoção completa PROD-00000002 (quantidade maior que existente): " + removidoTotal);
        sistema.mostrarCarrinhoDetalhado();


    }
}