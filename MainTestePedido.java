import java.math.BigDecimal;
import java.time.LocalDate;

public class MainTestePedido {

    public static void main(String[] args) {

        Sistema.getInstancia().cadastrarCliente("João", "@gmail", "14784584715");

        Sistema.getInstancia().mostrarCliente("CLI-00000001");

        Sistema.getInstancia().cadastrarCategoria("Masculina");

        Sistema.getInstancia().cadastrarProduto("Camisa", "GG", "Branca", "Camisa Algodão", "Masculina");

        Sistema.getInstancia().criarEstoque("Estoque1");

        Sistema.getInstancia().adiconarProdutoEstoque("Estoque1", "PROD-00000001", 10);

        Sistema.getInstancia().adiconarProdutoEstoque("Estoque1", "PROD-00000001", 10);

        Sistema.getInstancia().mostrarEstoque("Estoque1");

        // // Sistema.getInstancia().consultarPreco("PROD-00000001");

        Sistema.getInstancia().definirPrecoPadrao("PROD-00000001", 10);

        Sistema.getInstancia().adicionarPromocao("PROD-00000001", 9, LocalDate.of(2025, 11, 28),
                LocalDate.of(2025, 11, 29));

        Sistema.getInstancia().consultarPrecoEm("PROD-00000001", LocalDate.of(2025, 11, 30));

        // Sistema.getInstancia().mostrarProdutosComPreco();

        // Sistema.getInstancia().listarClientes();

        Sistema.getInstancia().criarPedido("CLI-00000001");

        Sistema.getInstancia().adicionarItemAoPedido("PED-00000001", "PROD-00000001", 25);

        Sistema.getInstancia().adicionarItemAoPedido("PED-00000001", "PROD-00000001", 5);

        Sistema.getInstancia().mostrarEstoque("Estoque1");

        Sistema.getInstancia().configurarFreteDesconto("PED-00000001", BigDecimal.valueOf(10), BigDecimal.valueOf(5));

        Sistema.getInstancia().registrarPagamento("PED-00000001", MetodoPagamento.PIX);

       // Sistema.getInstancia().confirmarPagamento("PED-00000001");

        Sistema.getInstancia().exibirPedido("PED-00000001");

    }

}
