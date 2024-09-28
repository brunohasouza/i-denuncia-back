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
        String sql  = "select * from setor where codigo = " + k;

        ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
        Setor setor = null;

        if (result.next()) {
            setor = new Setor();

            setor.setCodigo(k);
            setor.setCor(result.getString("cor"));
            setor.setNome(result.getString("nome"));
            setor.setDescricao(new String(result.getBytes("descricao")));
        }

        return setor;
    }

    @Override
    public List<Setor> readAll() throws SQLException {
        String sql  = "select * from setor";

        ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
        List<Setor> setores = new ArrayList<>();

        while(result.next()) {
            Setor setor = new Setor();

            setor.setCodigo(result.getInt("codigo"));
            setor.setCor(result.getString("cor"));
            setor.setNome(result.getString("nome"));
            setor.setDescricao(result.getString("descricao"));

            setores.add(setor);
        }

        return setores;
    }
}
