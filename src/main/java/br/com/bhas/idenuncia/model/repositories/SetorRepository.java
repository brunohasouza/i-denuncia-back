package br.com.bhas.idenuncia.model.repositories;

import br.com.bhas.idenuncia.model.entities.Setor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SetorRepository implements Repository<Setor, Integer> {
    public static final SetorRepository instance = new SetorRepository();

    private SetorRepository() {}

    @Override
    public void create(Setor setor) throws SQLException {
        String sql = "insert into setor(nome, descricao, cor) values(?, ?, ?)";

        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setString(1, setor.getNome());
        pstm.setBytes(2, setor.getDescricao().getBytes());
        pstm.setString(3, setor.getCor());

        pstm.execute();
    }

    @Override
    public Setor read(Integer k) throws SQLException {
        String sql = "select " +
                "s.codigo," +
                " s.nome," +
                " s.cor," +
                " s.descricao, " +
                "count(distinct f.codigo) as funcionarios, " +
                "count(df.funcionario_codigo) as denuncias " +
                "from setor s " +
                "left join funcionario f on s.codigo = f.setor_codigo " +
                "left join denuncia_funcionario df on f.codigo = df.funcionario_codigo " +
                "where s.codigo = " + k + " " +
                "group by s.codigo, s.nome, s.cor, s.descricao" +
                " order by s.codigo desc;";

        ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
        Setor setor = null;

        if (result.next()) {
            setor = new Setor();

            setor.setCodigo(k);
            setor.setCor(result.getString("cor"));
            setor.setNome(result.getString("nome"));
            setor.setDescricao(new String(result.getBytes("descricao")));
            setor.setFuncionarios(result.getInt("funcionarios"));
            setor.setDenuncias(result.getInt("denuncias"));
        }

        return setor;
    }

    @Override
    public List<Setor> readAll() throws SQLException {
        String sql = "select " +
                "s.codigo," +
                " s.nome," +
                " s.cor," +
                " s.descricao, " +
                "count(distinct f.codigo) as funcionarios, " +
                "count(df.funcionario_codigo) as denuncias " +
                "from setor s " +
                "left join funcionario f on s.codigo = f.setor_codigo " +
                "left join denuncia_funcionario df on f.codigo = df.funcionario_codigo " +
                "group by s.codigo, s.nome, s.cor, s.descricao" +
                " order by s.codigo desc;";

        ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
        List<Setor> setores = new ArrayList<>();

        while(result.next()) {
            Setor setor = new Setor();

            setor.setCodigo(result.getInt("codigo"));
            setor.setCor(result.getString("cor"));
            setor.setNome(result.getString("nome"));
            setor.setDescricao(result.getString("descricao"));
            setor.setDenuncias(result.getInt("denuncias"));
            setor.setFuncionarios(result.getInt("funcionarios"));

            setores.add(setor);
        }

        return setores;
    }
}
