import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Pedido {

    private Cliente cliente;
    private Map<String, ItemPedido> itens = new HashMap<>();
    private String status;
    private String id;
    private LocalDate criacao;
    private LocalDate entrega;
    private TotalPedido totalPedido;
    private Pagamento pagamento;

    public Pedido(Cliente cliente, String id) {
        this.cliente = cliente;
        this.id = id;
        this.status = "ABERTO";
        this.criacao = LocalDate.now();
        this.totalPedido = new TotalPedido(BigDecimal.ZERO, BigDecimal.ZERO);
        this.pagamento = null;
    }

    public void addItem(Produto produto, int qtd, double precoUnitario) {

        String idProduto = produto.getId();
        ItemPedido itemExistente = itens.get(idProduto);

        if (itemExistente != null) {
            // se é diferente de null, já existe, então soma a quantidade
            int novaQuantidade = itemExistente.getQuantidade() + qtd;
            itemExistente.setQuantidade(novaQuantidade);
        } else { // Cria um novo item

            ItemPedido novoItem = new ItemPedido(qtd, produto, precoUnitario);
            itens.put(idProduto, novoItem);
        }
    }

    public void diminuirItem(String idProduto, int qtd) {

        ItemPedido item = itens.get(idProduto);

        if (item == null) {

            System.out.println("Item não encontrado no pedido");
            return;
        }

        if (item.getQuantidade() > qtd) {

            item.setQuantidade(item.getQuantidade() - qtd);
        } else {

            itens.remove(idProduto);
        }

    }

    public BigDecimal calcularTotal() {

        BigDecimal subTotalItens = BigDecimal.ZERO;

        for (ItemPedido item : itens.values()) {

            subTotalItens = subTotalItens.add(BigDecimal.valueOf(item.calcularSubtotal()));
        }

        return totalPedido.calcularTotal(subTotalItens);
    }

    // o valor do desconto e frete pode vir de uma regra de negócio externa esse
    // método aqui só funciona para receber esses valores para não dar null point
    public void configurarFreteDesconto(BigDecimal frete, BigDecimal desconto) {

        totalPedido.setValorFrete(frete);
        totalPedido.setValorDesconto(desconto);
    }

    public void registrarPagamento(String idPagamento, MetodoPagamento metodoPagamento) {

        BigDecimal valorTotal = calcularTotal();

        this.pagamento = new Pagamento(idPagamento, valorTotal, metodoPagamento);

        System.out.println("Pagamento registrado: " + metodoPagamento + " | Valor : R$ " + valorTotal);

    }

    public void confirmarPagamento() {

        if (pagamento != null) {

            this.pagamento.confimarPagamento();
            this.status = "CONFIRMADO";
            System.out.println("Pagamento confirmado. Pedido finalizado.");
        } else {
            System.out.println("Nenhum pagamento registrado para este pedido");
        }

    }

    public void cancelarPagamento() {

        if (pagamento != null) {
            pagamento.cancelarPagamento();
            this.status = "CANCELADO";
            System.out.println("Pagamento cancelado. Pedido cancelado.");
        } else {
            System.out.println("Nenhum pagamento registrado para este pedido.");
        }
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public String getId() {
        return id;
    }

    

    public String getStatus() {
        return status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    

    public void setStatus(StatusPagamento confirmado) {
        this.status = "confirmado";
    }

    @Override
    public String toString() {
        return String.format("ID = %s | Status Pagamento = %s", this.id, this.status);
    }

}
