package br.com.bhas.idenuncia.model.repositories;

import br.com.bhas.idenuncia.model.entities.Funcionario;
import br.com.bhas.idenuncia.model.entities.Setor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioRepository implements Repository<Funcionario, Integer>{
    public static final FuncionarioRepository instance = new FuncionarioRepository();

    private FuncionarioRepository() {}

    @Override
    public void create(Funcionario funcionario) throws SQLException {
        String sql = "insert into funcionario(nome, anoNascimento, genero, setor) values(?, ?, ?, ?)";

        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setString(1, funcionario.getNome());
        pstm.setInt(2, funcionario.getAnoNascimento());
        pstm.setString(3, funcionario.getGenero());
        pstm.setInt(4, funcionario.getSetor().getCodigo());

        pstm.execute();
    }

    @Override
    public Funcionario read(Integer k) throws SQLException {
        String sql  = "select * from funcionario where codigo = " + k;

        ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
        Funcionario funcionario = null;

        if (result.next()) {
            funcionario = new Funcionario();

            Setor setor = SetorRepository.instance.read(result.getInt("setor"));

            funcionario.setCodigo(k);
            funcionario.setNome(result.getString("nome"));
            funcionario.setGenero(result.getString("genero"));
            funcionario.setAnoNascimento(result.getInt("anoNascimento"));
            funcionario.setSetor(setor);
        }

        return funcionario;
    }

    @Override
    public List<Funcionario> readAll() throws SQLException {
        String sql  = "select * from funcionario";

        ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
        List<Funcionario> funcionarios = new ArrayList<>();

        while(result.next()) {
            Funcionario funcionario = new Funcionario();
            Setor setor = SetorRepository.instance.read(result.getInt("setor"));

            funcionario.setCodigo(result.getInt("codigo"));
            funcionario.setNome(result.getString("nome"));
            funcionario.setGenero(result.getString("genero"));
            funcionario.setAnoNascimento(result.getInt("anoNascimento"));
            funcionario.setSetor(setor);

            funcionarios.add(funcionario);
        }

        return funcionarios;
    }

    public List<Funcionario> readAll(Integer k) throws SQLException {
        String sql  = "select * from funcionario where setor = " + k;

        ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
        List<Funcionario> funcionarios = new ArrayList<>();

        while(result.next()) {
            Funcionario funcionario = new Funcionario();
            Setor setor = SetorRepository.instance.read(k);

            funcionario.setCodigo(result.getInt("codigo"));
            funcionario.setNome(result.getString("nome"));
            funcionario.setGenero(result.getString("genero"));
            funcionario.setAnoNascimento(result.getInt("anoNascimento"));
            funcionario.setSetor(setor);

            funcionarios.add(funcionario);
        }

        return funcionarios;
    }
}
