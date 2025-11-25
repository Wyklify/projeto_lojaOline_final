public class Produto {

    private String id;
    private String nome;
    private String tamanho;
    private String cor;
    private String descricao;

    private Categoria categoria;

    public Produto(String id2, String nome, String tamanho, String cor, String descricao, Categoria categoria) {
        this.id = id2;
        this.nome = nome;
        this.tamanho = tamanho;
        this.cor = cor;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }    

    public String getNome() {
        return nome;
    }

    public String getTamanho() {
        return tamanho;
    }

    public String getCor() {
        return cor;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return String.format(
            "===============================%n" + 
                "Código: %s%n" +
                        "Nome: %s%n" +
                        "Tamanho: %s%n" +
                        "Cor: %s%n" +
                        "Descrição: %s%n" +
                        "Categoria: %s%n" + 
                        
            "===============================",
                id, nome, tamanho, cor, descricao, categoria);
    }

    public int getPreco() {

        throw new UnsupportedOperationException("Unimplemented method 'getPreco'");
    }

}
