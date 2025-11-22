import java.util.List;
import java.util.Scanner;

public class MenuCadastroProduto {

    void interfaceCadastrarProduto() {

        Scanner input = new Scanner(System.in);
        boolean opcao = true;

        do {

            System.out.println(MenuUtils.centralizarTextos(" Cadastrar Produto "));

            System.out.print("Digite o nome do produto: ");
            String nome = input.nextLine();

            System.out.print("Digite o tamanho do produto: ");
            String tamanho = input.nextLine();

            System.out.print("Digite o cor do produto: ");
            String cor = input.nextLine();

            System.out.print("Digite a descricao do produto: ");
            String descricao = input.nextLine();

            // mostrar categorias disponíveis

            List<String> nomeCategorias = Sistema.getInstancia().getNomesCategorias();

            for (int i = 0; i < nomeCategorias.size(); i++) {

                System.out.println((i + 1) + " - " + nomeCategorias.get(i));
            }

            System.out.print("Digite a categoria do produto: ");

            Integer categoria = input.nextInt();

            System.out.print("Deseja adicionar um novo produto? (S/N)");
            
            String escolha = input.next();

            if (escolha.equals("N")) {
                
                opcao = false;
            }

        } while (opcao);

    }

}
