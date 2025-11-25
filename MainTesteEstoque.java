public class MainTesteEstoque {


    public static void main(String[] args) {


        Sistema.getInstancia().mostrarEstoque();
        

        Sistema.getInstancia().criarEstoque("Estoque1");
        Sistema.getInstancia().criarEstoque("Estoque2");

        

        Sistema.getInstancia().mostrarEstoque();

        Sistema.getInstancia().cadastrarCategoria("Masculina");

        Sistema.getInstancia().cadastrarProduto("Camisa", "GG", "Branca", "Camisa Algodão", "Masculina");

        Sistema.getInstancia().adiconarProdutoEstoque("Estoque1", "PROD-00000001", 10);



        Sistema.getInstancia().mostrarEstoque("Estoque2");

        Sistema.getInstancia().mostrarEstoque("Estoque1");



    }
    
}
