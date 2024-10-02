package br.com.bhas.idenuncia.model.entities;

import java.util.HashMap;
import java.util.Map;

public class Denuncia {
    private int codigo;
    private DENUNCIAS tipo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public DENUNCIAS getTipo() {
        return tipo;
    }

    public void setTipo(DENUNCIAS tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        Map<DENUNCIAS, String> denuncias = new HashMap<>();

        denuncias.put(DENUNCIAS.ACIDENTE, "Acidente");
        denuncias.put(DENUNCIAS.ASSEDIO, "Assédio");
        denuncias.put(DENUNCIAS.FALHA_EQUIPAMENTO, "Falha de equipamento");
        denuncias.put(DENUNCIAS.FALHA_PESSOAL, "Falha de pessoal");
        denuncias.put(DENUNCIAS.FALTA_INSUMO, "Falta de insumo");

        return denuncias.get(tipo);
    }
}
