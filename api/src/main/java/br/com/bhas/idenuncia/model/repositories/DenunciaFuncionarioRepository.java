package br.com.bhas.idenuncia.model.repositories;

import br.com.bhas.idenuncia.model.entities.Denuncia;
import br.com.bhas.idenuncia.model.entities.DenunciaFuncionario;
import br.com.bhas.idenuncia.model.entities.Funcionario;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DenunciaFuncionarioRepository implements Repository<DenunciaFuncionario, Integer> {
    public static final DenunciaFuncionarioRepository instance = new DenunciaFuncionarioRepository();

    private DenunciaFuncionarioRepository() {}


    @Override
    public void create(DenunciaFuncionario denunciaFuncionario) throws SQLException {
        String sql = "insert into denuncia_funcionario(denuncia_codigo, funcionario_codigo, data_criacao) values(?, ?, current_date)";

        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setInt(1, denunciaFuncionario.getDenuncia().getCodigo());
        pstm.setInt(2, denunciaFuncionario.getFuncionario().getCodigo());

        pstm.execute();
    }

    @Override
    public DenunciaFuncionario read(Integer k) throws SQLException {
        String sql  = "select * from denuncia_funcionario where codigo = " + k;

        ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
        DenunciaFuncionario denunciaFuncionario = null;

        if (result.next()) {
            Denuncia denuncia = DenunciaRepository.instance.read(result.getInt("denuncia_codigo"));
            Funcionario funcionario = FuncionarioRepository.instance.read(result.getInt("funcionario_codigo"));

            denunciaFuncionario = new DenunciaFuncionario();

            denunciaFuncionario.setCodigo(result.getInt("codigo"));
            denunciaFuncionario.setDenuncia(denuncia);
            denunciaFuncionario.setFuncionario(funcionario);
            denunciaFuncionario.setDataCriacao(result.getDate("data_criacao"));
        }

        return denunciaFuncionario;
    }

    @Override
    public List<DenunciaFuncionario> readAll() throws SQLException {
        String sql  = "select * from denuncia_funcionario";

        ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
        List<DenunciaFuncionario> denuncias = new ArrayList<>();

        while (result.next()) {
            DenunciaFuncionario denunciaFuncionario = new DenunciaFuncionario();

            Denuncia denuncia = DenunciaRepository.instance.read(result.getInt("denuncia_codigo"));
            Funcionario funcionario = FuncionarioRepository.instance.read(result.getInt("funcionario_codigo"));

            denunciaFuncionario.setCodigo(result.getInt("codigo"));
            denunciaFuncionario.setDenuncia(denuncia);
            denunciaFuncionario.setFuncionario(funcionario);
            denunciaFuncionario.setDataCriacao(result.getDate("data_criacao"));

            denuncias.add(denunciaFuncionario);
        }

        return denuncias;
    }

    public List<DenunciaFuncionario> readAll(int setor, Date dataCriacao) throws SQLException {
        String sql = "select df.* from denuncia_funcionario df join funcionario f on df.funcionario_codigo = f.codigo join setor s on f.setor_codigo = s.codigo where s.codigo = " + setor;

        if (dataCriacao != null) {
            sql += " and df.data_criacao = '" + dataCriacao.toLocalDate() + "'";
        }

        ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
        List<DenunciaFuncionario> denuncias = new ArrayList<>();

        while (result.next()) {
            DenunciaFuncionario denunciaFuncionario = new DenunciaFuncionario();

            Denuncia denuncia = DenunciaRepository.instance.read(result.getInt("denuncia_codigo"));
            Funcionario funcionario = FuncionarioRepository.instance.read(result.getInt("funcionario_codigo"));

            denunciaFuncionario.setCodigo(result.getInt("codigo"));
            denunciaFuncionario.setDenuncia(denuncia);
            denunciaFuncionario.setFuncionario(funcionario);
            denunciaFuncionario.setDataCriacao(result.getDate("data_criacao"));

            denuncias.add(denunciaFuncionario);
        }

        return denuncias;
    }
}
