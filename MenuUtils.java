public class MenuUtils {

    public static String centralizarTextos(String texto) {
        int largura = 100; // largura total da linha

        int antes = (largura - texto.length()) / 2;
        int depois = largura - texto.length() - antes;

        StringBuilder sb = new StringBuilder(largura);
        for (int i = 0; i < antes; i++)
            sb.append("=");
        sb.append(texto);
        for (int i = 0; i < depois; i++)
            sb.append("=");

        return sb.toString();
    }

    public static void limparTela() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void carregando(String mensagem) {
        // estados de pontos
        String[] pontos = { "   ", ".", "..", "..." };

        long inicio = System.currentTimeMillis();
        int i = 0;

        // anima por cerca de 1 segundo
        while (System.currentTimeMillis() - inicio < 1000) {
            // \r volta o cursor para o início da linha, permitindo sobrescrever
            System.out.print("\r" + mensagem + pontos[i % pontos.length]);
            System.out.flush();

            try {
                Thread.sleep(250); // troca a cada 250ms (4 passos = ~1s)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            i++;
        }

        // limpa a linha e finaliza com quebra
        System.out.print("\r" + mensagem + "..." + "   \n");
        System.out.flush();
    }

    

}
