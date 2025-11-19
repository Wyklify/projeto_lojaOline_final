public class Main {

    public static void main(String[] args) {

        Sistema sistema = Sistema.getInstancia();

        // Cadastra categoria
        sistema.cadastrarCategoria("Masculina");

        // Cadastra produtos
        sistema.cadastrarProduto("Camisa", "GG", "Branca", "Camisa Algodão", "Masculina");
        sistema.cadastrarProduto("Camisa", "GG", "Branca", "Camisa Algodão", "Masculina");

        sistema.mostrarEstoque();

        // Adiciona ao estoque manualmente
        sistema.adiconarProdutoEstoque("MAS-00000001", 10);
        sistema.adiconarProdutoEstoque("MAS-00000002", 5);

        // Exibe estoque atual
        sistema.mostrarEstoque();

        // Diminui quantidade de um item
        sistema.estoque1.diminuirItem("MAS-00000002", 2);

        // Exibe estoque atualizado
        sistema.mostrarEstoque();

        // Cadastra novo produto sem adicionar ao estoque
        sistema.cadastrarProduto("Camisa", "GG", "Branca", "Camisa Algodão", "Masculina");

        // Exibe produtos sem estoque vinculado
        sistema.mostrarProdutosNaoVinculadosAoEstoque();
    }
}