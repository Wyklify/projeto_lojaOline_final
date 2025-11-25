import java.util.concurrent.atomic.AtomicInteger;

public class GeradorSku {

    private final AtomicInteger contadorProduto = new AtomicInteger(0);
    private final AtomicInteger contadorPedido = new AtomicInteger(0); 

    public String gerarProximoSkuProduto() {

        int atual = contadorProduto.incrementAndGet();

        return String.format("PROD-%08d",atual);
    }


    public String gerarProximoSkuPedido() {

        int atual = contadorPedido.incrementAndGet();

        return String.format("PED-%08d",atual);
    }


}
