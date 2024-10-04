package br.com.bhas.idenuncia.controllers;


import br.com.bhas.idenuncia.model.bodies.FuncionarioBody;
import br.com.bhas.idenuncia.model.entities.Funcionario;
import br.com.bhas.idenuncia.model.entities.Setor;
import br.com.bhas.idenuncia.model.repositories.FuncionarioRepository;
import br.com.bhas.idenuncia.model.repositories.SetorRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {
    @GetMapping
    public List<Funcionario> list(HttpServletResponse response) {
        try {
            return FuncionarioRepository.instance.readAll();
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody FuncionarioBody body) {
        try {
            Setor setor = SetorRepository.instance.read(body.getSetor());
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(body.getNome());
            funcionario.setAnoNascimento(body.getAnoNascimento());
            funcionario.setSetor(setor);

            FuncionarioRepository.instance.create(funcionario);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch(SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
