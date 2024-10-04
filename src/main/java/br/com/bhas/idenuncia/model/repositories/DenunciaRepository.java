package br.com.bhas.idenuncia.model.repositories;

import br.com.bhas.idenuncia.model.entities.DENUNCIAS;
import br.com.bhas.idenuncia.model.entities.Denuncia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DenunciaRepository implements Repository<Denuncia, Integer> {
    public static final DenunciaRepository instance = new DenunciaRepository();

    private DenunciaRepository() {}

    @Override
    public void create(Denuncia denuncia) throws SQLException {
        String sql = "insert into denuncia(tipo) values(?);";

        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setString(1, denuncia.getTipo().name());

        pstm.execute();
    }

    @Override
    public Denuncia read(Integer k) throws SQLException {
        String sql  = "select * from denuncia where codigo = " + k;

        ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
        Denuncia denuncia = null;

        if (result.next()) {
            denuncia = new Denuncia();
            denuncia.setCodigo(result.getInt("codigo"));
            denuncia.setTipo(DENUNCIAS.valueOf(result.getString("tipo")));
        }

        return denuncia;
    }

    @Override
    public List<Denuncia> readAll() throws SQLException {
        String sql  = "select * from denuncia order by codigo desc;";

        ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
        List<Denuncia> denuncias = new ArrayList<>();

        while(result.next()) {
            Denuncia denuncia = new Denuncia();

            denuncia.setTipo(DENUNCIAS.valueOf(result.getString("tipo")));
            denuncia.setCodigo(result.getInt("codigo"));

            denuncias.add(denuncia);
        }

        return denuncias;
    }
}
