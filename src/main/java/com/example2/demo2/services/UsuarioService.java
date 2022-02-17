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

    public ArrayList<UsuarioModel> obtenerUsuario(){
        return (ArrayList<UsuarioModel>)usuarioRepository.findAll();
    }

    public void guardarUsuario(UsuarioModel user){
        usuarioRepository.save(user);
    }

    public Optional<UsuarioModel> obtenerPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public ArrayList<UsuarioModel> findByPrioridad(Integer prioridad){
        return usuarioRepository.findByPrioridad(prioridad);
    }

    public boolean eliminarUsuario(Long id){
        try{
            usuarioRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean verificarEmail(String email){
        List<UsuarioModel> users = new ArrayList<>();
        users=usuarioRepository.findByEmail(email);
        for(int i=0;i<users.size();i++){
            if(users.get(i).getEmail().equalsIgnoreCase(email)){
                return false;
            }
        }
        return true;
    }

}
