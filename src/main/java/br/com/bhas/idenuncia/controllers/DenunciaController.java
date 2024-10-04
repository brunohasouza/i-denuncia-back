package br.com.bhas.idenuncia.controllers;

import br.com.bhas.idenuncia.model.bodies.DenunciaFuncionarioBody;
import br.com.bhas.idenuncia.model.entities.Denuncia;
import br.com.bhas.idenuncia.model.entities.DenunciaFuncionario;
import br.com.bhas.idenuncia.model.entities.Funcionario;
import br.com.bhas.idenuncia.model.repositories.DenunciaFuncionarioRepository;
import br.com.bhas.idenuncia.model.repositories.DenunciaRepository;
import br.com.bhas.idenuncia.model.repositories.FuncionarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/denuncias")
@CrossOrigin(origins = "*")
public class DenunciaController {
    @GetMapping
    public List<Denuncia> list() {
        try {
            return DenunciaRepository.instance.readAll();
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DenunciaFuncionarioBody body) {
        try {
            Denuncia denuncia = DenunciaRepository.instance.read(body.getDenuncia());
            Funcionario funcionario = FuncionarioRepository.instance.read(body.getFuncionario());
            DenunciaFuncionario denunciaFuncionario = new DenunciaFuncionario();
            denunciaFuncionario.setFuncionario(funcionario);
            denunciaFuncionario.setDenuncia(denuncia);

            DenunciaFuncionarioRepository.instance.create(denunciaFuncionario);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch(SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{setor}")
    public List<DenunciaFuncionario> listBySetor(@PathVariable("setor") int setor, @RequestParam(required = false) Date data) {
        try {
            return DenunciaFuncionarioRepository.instance.readAll(setor, data);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}
