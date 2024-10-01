package br.com.bhas.idenuncia.model.bodies;

public class DenunciaFuncionarioBody {
    private int denuncia;
    private int funcionario;

    public int getDenuncia() {
        return denuncia;
    }

    public void setDenuncia(int denuncia) {
        this.denuncia = denuncia;
    }

    public int getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(int funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public String toString() {
        return "DenunciaFuncionarioBody{" +
                "denuncia=" + denuncia +
                ", funcionario=" + funcionario +
                '}';
    }
}
