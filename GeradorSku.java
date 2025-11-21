import java.util.concurrent.atomic.AtomicInteger;

public class GeradorSku {

    private final AtomicInteger contador = new AtomicInteger(0);

    public String gerarProximoSku() {

        int atual = contador.incrementAndGet();

        return String.format("PROD-%08d",atual);
    }

}
