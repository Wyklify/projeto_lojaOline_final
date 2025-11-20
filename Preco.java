import java.time.LocalDate;

public class Preco {

    private double valor;
    private LocalDate inicio;
    private LocalDate fim;

    // construtor preco padrao - por isso não tem incio e fim
    public Preco(double valor) {
        this.valor = valor;
        this.inicio = null;
        this.fim = null;
    }


    // contrutor para preco promocional
    public Preco(double valor, LocalDate inicio, LocalDate fim) {
        this.valor = valor;
        this.inicio = inicio;
        this.fim = fim;

        if ((inicio == null) != (fim == null)) {
            
            throw new IllegalArgumentException( "Para a promoção, inicio e fim devem ser ambos não-nulos ou ambos nulos");
        }

        if (inicio != null && fim.isBefore(inicio)) {
            
            throw new IllegalArgumentException("Data fim não pode ser antes da data início.");
        }
    }

    

    public double getValor() {
        return valor;
    }


    public boolean temVigencia() {

        return this.inicio != null && fim != null;
    }

    //metodo para verificar se o preco se tem promocao rolando
    public boolean estaVigente(LocalDate hoje) { 

        if (temVigencia()) {
            
            return !hoje.isBefore(inicio) && !hoje.isAfter(fim);
        }

        return true;
    }
    


    
}
