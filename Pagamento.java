import java.math.BigDecimal;
import java.time.LocalDate;

public class Pagamento {

    private String id;
    private BigDecimal valorTotal;
    private MetodoPagamento metodoPagamento;
    private StatusPagamento statusPagamento;
    private LocalDate dataAcao;

    public Pagamento(String id, BigDecimal valorTotal, MetodoPagamento metodoPagamento) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.metodoPagamento = metodoPagamento;
        this.statusPagamento = StatusPagamento.PENDENTE;
        this.dataAcao = null;
    }

    public void confimarPagamento() {

        this.statusPagamento = StatusPagamento.CONFIRMADO;
        this.dataAcao = LocalDate.now();
    }

    public void cancelarPagamento() {
        this.statusPagamento = StatusPagamento.CANCELADO;
        this.dataAcao = LocalDate.now();
    }

    public void estornarPagamento() {
        this.statusPagamento = StatusPagamento.ESTORNADO;
        this.dataAcao = LocalDate.now();

    }

    public String getId() {
        return id;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public LocalDate getDataAcao() {
        return dataAcao;
    }

    

}
