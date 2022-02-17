package com.example2.demo2.repositories;

import com.example2.demo2.models.UsuarioModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Long> {
    public abstract ArrayList<UsuarioModel> findByPrioridad(Integer prioridad);
    public abstract List<UsuarioModel> findAllByEmail(String email);
    public abstract ArrayList<UsuarioModel> findByEmail(String email);

}
