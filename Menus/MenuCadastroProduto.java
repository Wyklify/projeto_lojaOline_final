package Menus;
import java.util.List;
import java.util.Scanner;

import Sistema;

public class MenuCadastroProduto {

    public static void uiCadastrarProduto() {

        MenuUtils.limparTela();

        Scanner input = new Scanner(System.in);
        boolean opcao = true;

        do {

            System.out.println(MenuUtils.centralizarTextos(" Cadastrar Produto "));

            System.out.print("Digite o nome do produto: ");
            String nome = input.nextLine();

            System.out.print("Digite o tamanho do produto: ");
            String tamanho = input.nextLine();

            System.out.print("Digite a cor do produto: ");
            String cor = input.nextLine();

            System.out.print("Digite a descricao do produto: ");
            String descricao = input.nextLine();

            // mostrar categorias disponíveis

            System.out.println();

            System.out.println(MenuUtils.centralizarTextos(" Categorias disponíveis "));

            String categoriaNome = null;
            Integer categoriaIndex;

            List<String> nomeCategorias = Sistema.getInstancia().getNomesCategorias();

            if (nomeCategorias.equals("Sem categoria") || nomeCategorias.isEmpty()) {

                System.out.println("Sem categorias cadastradas");

                System.out.print("Deseja cadastrar o produto sem categoria? (S/N)");

                String entrada = input.nextLine().toUpperCase().trim();

                if (entrada.equals("S")) {

                    categoriaNome = "Sem categoria";

                    Sistema.getInstancia().cadastrarProduto(nome, tamanho, cor, descricao, categoriaNome);

                } else {

                    MenuUtils.carregando("Encaminhado para o cadastro de categoria");
                    MenuCadastroCategoria.uiCadastrarCategoria();

                    break;
                }

            } else {

                for (int i = 0; i < nomeCategorias.size(); i++) {

                    System.out.println((i + 1) + " - " + nomeCategorias.get(i));
                }

                System.out.print("Digite a categoria do produto: ");

                categoriaIndex = input.nextInt();
                input.nextLine();

                categoriaNome = nomeCategorias.get(categoriaIndex - 1);

                

                Sistema.getInstancia().cadastrarProduto(nome, tamanho, cor, descricao, categoriaNome);

            }

            System.out.print("Deseja adicionar um novo produto? (S/N)");

            String escolha = input.nextLine().trim();

            if (escolha.equals("N")) {

                opcao = false;
            }

        } while (opcao);

        MenuUtils.carregando("Carregando menu principal");

        MenuManager.menuGeral();

    }

}
