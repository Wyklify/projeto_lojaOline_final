package Menus;
import java.util.List;
import java.util.Scanner;

public class MenuManager {

    // ideia dessa classe é desenhar todos os menus

    void menuEstoque() {

    }

    

    public static void menuGeral() {

        MenuUtils.limparTela();

        
        Scanner input = new Scanner(System.in);

        int opcao;

        do {

            System.out.println(MenuUtils.centralizarTextos(" MENU PRINCIPAL "));
            System.out.println("1. Adicionar Categorias");
            System.out.println("2. Cadastrar um Produto");
            System.out.println("3. Definir preço do produto");
            System.out.println("0. Sair");

            System.out.println();

            System.out.print("Escolha: ");
            opcao = input.nextInt();

            input.nextLine();


            switch (opcao) {
                case 1:

                    MenuCadastroProduto.uiCadastrarProduto();
                    
                    break;
                
                case 2:

                    MenuCadastroProduto.uiCadastrarProduto();
                    
                    break;
                
                default :
                    break;
            }
            

        } while(true);
    }

    // utilitarios menu

   


    public static void main(String[] args) {
        
        MenuManager.menuGeral();

        

    }

}
