package br.com.bhas.idenuncia.controllers;

import br.com.bhas.idenuncia.model.entities.Funcionario;
import br.com.bhas.idenuncia.model.entities.Setor;
import br.com.bhas.idenuncia.model.repositories.FuncionarioRepository;
import br.com.bhas.idenuncia.model.repositories.SetorRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/setores")
@CrossOrigin(origins = "*")
public class SetorController {
    @GetMapping
    public List<Setor> list(HttpServletResponse response) {
        try {
            return SetorRepository.instance.readAll();
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Setor setor) {
        try {
            SetorRepository.instance.create(setor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Setor> find(@PathVariable("codigo") int codigo) {
        try {
            Setor setor = SetorRepository.instance.read(codigo);

            if (setor == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<Setor>(setor, HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{codigo}/funcionarios")
    public List<Funcionario> listBySetor(@PathVariable("codigo") int codigo) {
        try {
            return FuncionarioRepository.instance.readAll(codigo);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
