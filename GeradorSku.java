import java.util.HashMap;
import java.util.Map;

public class GeradorSku {

    private final Map< String, Integer> proximoPrefixo = new HashMap<>();


    // so coloca um novo prefixo se ele não existir no map
    public void registrarPrefixo(String prefixo) {        

        proximoPrefixo.putIfAbsent(prefixo.toUpperCase(), 1);
    }
    
    public String gerarProximoSku (String prefixo) {

        String p = prefixo.toUpperCase().trim();

        Integer atual = proximoPrefixo.get(p);

        if (atual == null) {
            atual = 1;
        }
        
        String sku = p + "-" + String.format("%08d", atual);
        proximoPrefixo.put(p, atual + 1);

        return sku;
    }

}
