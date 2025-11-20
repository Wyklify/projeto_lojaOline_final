public class Main {

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

        Sistema.getInstancia().definirPrecoPadrao("MAS-00000001", 50);

        double preco = Sistema.getInstancia().consultarPreco("MAS-00000001");
        System.out.println("Preço vigente do produto MAS-00000001: R$ " + preco);


         Sistema.getInstancia().mostrarProdutosComPreco();


    }
}