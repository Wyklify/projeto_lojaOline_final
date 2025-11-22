public class Categoria {
    
    private String nome;
    private String prefixoSKU;

    public static final Categoria SEM_CATEGORIA = new Categoria("Sem categoria", "SEM");

    public Categoria(String nome, String prefixoSKU) {

        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome de categoria inválido");
        if (prefixoSKU == null || prefixoSKU.isBlank()) throw new IllegalArgumentException("Prefixo inválido");

        this.nome = nome.trim().toLowerCase();
        this.prefixoSKU = prefixoSKU.trim().toUpperCase();
        
    }



    public String getNome() {
        return nome;
    }



    public String getPrefixoSKU() {
        return prefixoSKU;
    }




    @Override
    public String toString() {
        
        return String.format("%s (SKU: %s)", nome.substring(0, 1).toUpperCase() + nome.substring(1), prefixoSKU);
    }
    
}
