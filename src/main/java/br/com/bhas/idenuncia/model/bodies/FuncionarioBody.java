package br.com.bhas.idenuncia.model.bodies;

public class FuncionarioBody {
    private String nome;
    private int setor;
    private int anoNascimento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSetor() {
        return setor;
    }

    public void setSetor(int setor) {
        this.setor = setor;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    @Override
    public String toString() {
        return "FuncionarioBody{" +
                "nome='" + nome + '\'' +
                ", setor=" + setor +
                ", anoNascimento=" + anoNascimento +
                '}';
    }
}
