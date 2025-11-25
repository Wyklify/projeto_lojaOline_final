public class Endereco {
    private String id;
    private String rua;
    private String numero;
    private String cidade;
    private String cep;
    private String complemento;

    public Endereco(String id, String rua, String numero, String cidade, String cep, String complemento) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.cep = cep;
        this.complemento = complemento;
    }

    public String getId() { return id; }
    public String getRua() { return rua; }
    public String getNumero() { return numero; }
    public String getCidade() { return cidade; }
    public String getCep() { return cep; }
    public String getComplemento() { return complemento; }

    @Override
    public String toString() {
        return String.format("[%s] %s, %s - %s (CEP: %s) %s", id, rua, numero, cidade, cep, complemento == null ? "" : complemento);
    }
}
