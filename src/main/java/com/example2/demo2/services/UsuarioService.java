package com.example2.demo2.services;

import com.example2.demo2.models.UsuarioModel;
import com.example2.demo2.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    /**
     * Obtenemos todos los usuarios
     * @return retorna una lista de usuarios
     */
    public ArrayList<UsuarioModel> obtenerUsuario(){
        return (ArrayList<UsuarioModel>)usuarioRepository.findAll();
    }

    /**
     * Guardamos un nuevo usuario
     * @param user Clase usuario.
     */
    public void guardarUsuario(UsuarioModel user){
        usuarioRepository.save(user);
    }

    /**
     * Obtenemos una instancia usuarioModel según su ID
     * @param id ID del usuario
     * @return un objeto UsuarioModel siempre y cuando exista uno con la ID especificada
     */
    public Optional<UsuarioModel> obtenerPorId(Long id){
        return usuarioRepository.findById(id);
    }

    /**
     * Implementación del método buscar todos los usuarios según su prioridad.
     * @param prioridad Número de prioridad del usuario.
     * @return Retorna una lista de usuarios que comparten la misma prioridad.
     */
    public ArrayList<UsuarioModel> findByPrioridad(Integer prioridad){
        return usuarioRepository.findByPrioridad(prioridad);
    }

    /**
     * Método para eliminar un usuario según la ID que se especifique.
     * @param id ID del usuario que se requiere eliminar.
     * @return retorna true si fue eliminado exitosamente, false si ocurrió un error.
     */
    public boolean eliminarUsuario(Long id){
        try{
            usuarioRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * Método para verificar que un Email no esté en uso.
     * @param email email de un usuario.
     * @return Si el email está disponible, se retorna true. Si no está disponible false.
     */
    public boolean verificarEmail(String email){
        List<UsuarioModel> users = new ArrayList<>();
        users=usuarioRepository.findAllByEmail(email);
        for(int i=0;i<users.size();i++){
            if(users.get(i).getEmail().equalsIgnoreCase(email)){
                return false;
            }
        }
        return true;
    }

    /**
     * Método para buscar un usuario según el email.
     * @param email email de un usuario.
     * @return retorna una lista de usuarios.
     */
    public ArrayList<UsuarioModel> obtenerPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

}
