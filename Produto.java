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


    





    
    
}
