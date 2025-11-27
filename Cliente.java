public class Cliente {
    private String id;
    private String nome;
    private String email;

    private java.util.List<Endereco> enderecos = new java.util.ArrayList<>();

    public Cliente(String nome, String id){
        this.id = id;
        this.nome = nome;
    }
    public Cliente(String id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }

    
    public void adicionarEndereco(Endereco e) {
        if (e != null) enderecos.add(e);
    }

    public java.util.List<Endereco> getEnderecos() { return java.util.Collections.unmodifiableList(enderecos); }

    public Endereco localizarEndereco(String enderecoId) {
        for (Endereco e : enderecos) {
            if (e.getId().equals(enderecoId)) return e;
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("Cliente[id=%s, nome=%s, email=%s]", id, nome, email);
    }
}
