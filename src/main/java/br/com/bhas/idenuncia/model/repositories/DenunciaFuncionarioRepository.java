package br.com.bhas.idenuncia.model.repositories;

import br.com.bhas.idenuncia.model.entities.Denuncia;
import br.com.bhas.idenuncia.model.entities.DenunciaFuncionario;
import br.com.bhas.idenuncia.model.entities.Funcionario;

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
        String sql = "insert into denuncia_funcionario(denuncia, funcionario) values(?, ?)";

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
            Denuncia denuncia = DenunciaRepository.instance.read(result.getInt("denuncia"));
            Funcionario funcionario = FuncionarioRepository.instance.read(result.getInt("funcionario"));

            denunciaFuncionario = new DenunciaFuncionario();

            denunciaFuncionario.setCodigo(result.getInt("codigo"));
            denunciaFuncionario.setDenuncia(denuncia);
            denunciaFuncionario.setFuncionario(funcionario);
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

            Denuncia denuncia = DenunciaRepository.instance.read(result.getInt("denuncia"));
            Funcionario funcionario = FuncionarioRepository.instance.read(result.getInt("funcionario"));

            denunciaFuncionario.setCodigo(result.getInt("codigo"));
            denunciaFuncionario.setDenuncia(denuncia);
            denunciaFuncionario.setFuncionario(funcionario);

            denuncias.add(denunciaFuncionario);
        }

        return denuncias;
    }
}
