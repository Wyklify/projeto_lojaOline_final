public class MainTesteCadastrarProduto {

    public static void main(String[] args) {

        // Cadastrar categoria

        Sistema.getInstancia().cadastrarCategoria("Masculina");

        // Cadastra produtos
        Sistema.getInstancia().cadastrarProduto("Camisa", "GG", "Branca", "Camisa Algodão", "Masculina");
        Sistema.getInstancia().cadastrarProduto("Camisa", "GG", "Branca", "Camisa Algodão", "Masculina");

        // Mostrar produtos sem estoque

       Sistema.getInstancia().mostrarProdutosNaoVinculadosAoEstoque();

        Sistema.getInstancia().mostrarEstoque();

        // Sistema.getInstancia().consultarPreco("MAS-00000001");

        // Adicionar preco

        Sistema.getInstancia().definirPrecoPadrao("PROD-00000002", 50);

        double preco = Sistema.getInstancia().consultarPreco("PROD-00000002");
        System.out.println("Preço vigente do produto PROD-00000002: R$ " + preco);


         Sistema.getInstancia().mostrarProdutosComPreco();


    }
}