import java.math.BigDecimal;

public class MainTestePedido {


    public static void main(String[] args) {


        Cliente cliente = new Cliente("joao", "123");
        
        Sistema.getInstancia().cadastrarCategoria("Masculina");

        Sistema.getInstancia().cadastrarProduto("Camisa", "GG", "Branca", "Camisa Algodão", "Masculina");

       // Sistema.getInstancia().consultarPreco("PROD-00000001");

       Sistema.getInstancia().definirPrecoPadrao("PROD-00000001", 10);

       Sistema.getInstancia().mostrarProdutosComPreco();

       Pedido pedido = Sistema.getInstancia().criarPedido(cliente);

       Sistema.getInstancia().adicionarItemAoPedido(pedido.getId(), "PROD-00000001", 1);

       Sistema.getInstancia().configurarFreteDesconto(pedido.getId(), BigDecimal.valueOf(10), BigDecimal.valueOf(5));
        
       Sistema.getInstancia().registrarPagamento(pedido.getId(), MetodoPagamento.PIX);
       
       Sistema.getInstancia().confirmarPagamento(pedido.getId());

       Sistema.getInstancia().exibirPedido(pedido.getId());

    }
    
}
