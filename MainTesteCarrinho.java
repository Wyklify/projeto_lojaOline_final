public class MainTesteCarrinho {
    public static void main(String[] args) {
         // Preparação mínima: criar categoria, produtos, preços e preencher tabela de estoque
        Sistema.getInstancia().cadastrarCategoria("Teste");
        Sistema.getInstancia().cadastrarProduto("Produto A", "P", "Vermelho", "Produto A desc", "Teste");
        Sistema.getInstancia().cadastrarProduto("Produto B", "M", "Azul", "Produto B desc", "Teste");

        Sistema.getInstancia().definirPrecoPadrao("PROD-00000001", 10.0);
        Sistema.getInstancia().definirPrecoPadrao("PROD-00000002", 20.0);

        // popular a tabela de estoque para permitir finalização
        Sistema.getInstancia().adiconarProdutoEstoque("PROD-00000001", 5);
        Sistema.getInstancia().adiconarProdutoEstoque("PROD-00000002", 2);

        // Cria cliente e carrinho (cliente local; Sistema usado como singleton)
        Cliente cliente = new Cliente("CART01", "Cliente Teste", "teste@example.com");
        Sistema.getInstancia().criarCarrinhoParaCliente(cliente.getId());

        // Ações no carrinho (usando singleton diretamente)
        Sistema.getInstancia().adicionarAoCarrinhoParaCliente(cliente.getId(), "PROD-00000001", 2);
        Sistema.getInstancia().adicionarAoCarrinhoParaCliente(cliente.getId(), "PROD-00000002", 1);

        System.out.println("-- Carrinho após adições --");
        Sistema.getInstancia().mostrarCarrinhoDetalhadoParaCliente(cliente.getId());

        // Remover uma unidade do primeiro produto
        boolean removed = Sistema.getInstancia().getOrCreateCarrinho(cliente.getId()).removerProdutoDoCarrinho("PROD-00000001", 1);
        System.out.println("Removido 1 unidade PROD-00000001: " + removed);
        Sistema.getInstancia().mostrarCarrinhoDetalhadoParaCliente(cliente.getId());

        // Finalizar compra do cliente
        Sistema.getInstancia().finalizarCompraParaCliente(cliente.getId());

        System.out.println("-- Estoque após finalização --");
        Sistema.getInstancia().mostrarEstoque();

        System.out.println("-- Carrinho do cliente após finalização --");
        Sistema.getInstancia().mostrarCarrinhoDetalhadoParaCliente(cliente.getId());

    }
}
