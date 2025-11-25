import java.math.BigDecimal;

public class TotalPedido {
    // relaciona frete e descontos, com o valor do pedido

    private BigDecimal valorFrete;
    private BigDecimal valorDesconto;


    public TotalPedido(BigDecimal valorFrete, BigDecimal valorDesconto) {
        this.valorFrete = valorFrete;
        this.valorDesconto = valorDesconto;
    }


    public BigDecimal getValorFrete() {
        return valorFrete;
    }


    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }


    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }


    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }


    public BigDecimal calcularTotal(BigDecimal subtotalItens) {

        if (subtotalItens == null) {
            
            subtotalItens = BigDecimal.ZERO;
        }

        return subtotalItens.add(valorFrete).subtract(valorDesconto);
    }

    
}
