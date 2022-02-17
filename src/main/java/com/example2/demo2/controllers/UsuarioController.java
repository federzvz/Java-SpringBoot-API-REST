package com.example2.demo2.controllers;

import com.example2.demo2.models.UsuarioModel;
import com.example2.demo2.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController implements ErrorController {
    @Autowired
    UsuarioService usuarioService;
    static final String PATH ="/error";

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
            if(usuarioService.verificarEmail(user.getEmail())){
                usuarioService.guardarUsuario(user);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity("El email ingresado ya está registrado.", HttpStatus.CONFLICT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(path ="/{id}")
    public Optional<UsuarioModel> obtenerUsuarioPorId(@PathVariable("id") Long id){
        try {
            return this.usuarioService.obtenerPorId(id);
        }catch (Exception e){
            return null;
        }
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

    @GetMapping("/email")
    public ArrayList<UsuarioModel> obtenerUsuarioPorEmail(@RequestParam("email") String email){
        return this.usuarioService.obtenerPorEmail(email);
    }

    @RequestMapping(value = PATH)
    public ResponseEntity<String> error() {
        return new ResponseEntity<String>("No se ha encontrado ninguna página web para URL especificada.",HttpStatus.NOT_FOUND);
    }

    public String getErrorPath() {
        return PATH;
    }




}
