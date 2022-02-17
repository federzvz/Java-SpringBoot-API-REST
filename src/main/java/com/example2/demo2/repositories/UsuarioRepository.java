package com.example2.demo2.repositories;

import com.example2.demo2.models.UsuarioModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Interface dónde se implementarán los métodos vacíos.
 */
@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Long> {
    /**
     * Declaración de método vació para buscar una lista de usuarios según su prioridad.
     * @param prioridad Número indicador de la prioridad del usuario.
     * @return retorna una lista de usuarios.
     */
    public abstract ArrayList<UsuarioModel> findByPrioridad(Integer prioridad);

    /**
     * Declaración de método vacío para recorrer todos los usuarios y verificar si un email ya está en uso
     * @param email corresponde al email de un usuario.
     * @return retorna una lista de usuarios.
     */
    public abstract List<UsuarioModel> findAllByEmail(String email);

    /**
     * Declaración de método vacío para obtener todos los usuarios según su email.
     * @param email corresponde al email del usuario.
     * @return retorna una lista de usuarios.
     */
    public abstract ArrayList<UsuarioModel> findByEmail(String email);

}
