package com.example2.demo2.controllers;

import com.example2.demo2.models.UsuarioModel;
import com.example2.demo2.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping()
    public ArrayList<UsuarioModel> obtenerUsuario(){
        return usuarioService.obtenerUsuario();
    }

    @PostMapping()
    public ResponseEntity<String> guardarUsuario(@RequestBody UsuarioModel user){
        if(!user.getSexo().equalsIgnoreCase("h") && !user.getSexo().equalsIgnoreCase("m")){
            return new ResponseEntity("El sexo "+user.getSexo()+" es inválido.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if(user.getEdad()>120 || user.getEdad()<1){
            return new ResponseEntity("La edad "+user.getEdad()+" es inválida.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try{
            usuarioService.guardarUsuario(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(path ="/{id}")
    public Optional<UsuarioModel> obtenerUsuarioPorId(@PathVariable("id") Long id){
        return this.usuarioService.obtenerPorId(id);
    }

    @GetMapping("/query")
    public ArrayList<UsuarioModel> obtenerUsuarioPorPrioridad(@RequestParam("prioridad") Integer prioridad){
        return this.usuarioService.findByPrioridad(prioridad);
    }

    @DeleteMapping(path ="/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id){
        if(usuarioService.eliminarUsuario(id)){
            return "Se ha eliminado el usuario con id: "+id;
        }
        return "No se ha podido eliminar el usuario con id: "+id;
    }
}
