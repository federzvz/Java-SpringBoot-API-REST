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

    /**
     * Método GET que obtiene todos los usuarios sin discriminación alguna.
     * @return retorna una lista de todos los usuarios.
     */
    @GetMapping()
    public ArrayList<UsuarioModel> obtenerUsuario(){
        return usuarioService.obtenerUsuario();
    }

    /**
     * Método POST para añadir un nuevo usuario al sistema.
     * @param user Instancia de tipo UsuarioModel.
     * @return Un código HTTP Response dependiendo de si todo está bien.
     */
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

    /**
     * Método GET que obtiene un usuario según su ID
     * @param id Corresponde al ID del usuario, siendo de tipo entero.
     * @return Retorna un objeto Usuario siempre y cuando exista el usuario de dicho ID.
     */
    @GetMapping(path ="/{id}")
    public Optional<UsuarioModel> obtenerUsuarioPorId(@PathVariable("id") Long id){
        try {
            return this.usuarioService.obtenerPorId(id);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Método GET que obtiene los usuarios según su prioridad.
     * @param prioridad Especifica la prioridad del usuario.
     * @return Retorna una lista de Usuarios que comparten la misma prioridad.
     */
    @GetMapping("/query")
    public ArrayList<UsuarioModel> obtenerUsuarioPorPrioridad(@RequestParam("prioridad") Integer prioridad){
        return this.usuarioService.findByPrioridad(prioridad);
    }

    /**
     * Método DELTE que elimina un usuario según su ID
     * @param id ID del usuario a borrar.
     * @return Retorna un mensaje de éxito o de error.
     */
    @DeleteMapping(path ="/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id){
        if(usuarioService.eliminarUsuario(id)){
            return "Se ha eliminado el usuario con id: "+id;
        }
        return "No se ha podido eliminar el usuario con id: "+id;
    }

    /**
     * Método GET que obtiene el usuario según el email
     * @param email email del usuario que se requiere buscar
     * @return retorna una lista de Usuarios con ese email, no debería de retornar más de uno pero se deja la opción.
     */
    @GetMapping("/email")
    public ArrayList<UsuarioModel> obtenerUsuarioPorEmail(@RequestParam("email") String email){
        return this.usuarioService.obtenerPorEmail(email);
    }

    /**
     * Controlador de error cuando se haga el mappeo a la variable PATH("/error")
     * @return retorna un código HTTP RESPONSE, esto es modificable
     */
    @RequestMapping(value = PATH)
    public ResponseEntity<String> error() {
        return new ResponseEntity<String>("No se ha encontrado ninguna página web para URL especificada.",HttpStatus.NOT_FOUND);
    }

    public String getErrorPath() {
        return PATH;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioModel> updateTutorial(@PathVariable("id") long id, @RequestBody UsuarioModel usuarioParam) {
        Optional<UsuarioModel> usuarioData = usuarioService.obtenerPorId(id);

        if (usuarioData.isPresent()) {
            UsuarioModel _usuario = usuarioData.get();
            _usuario.setNombre(usuarioParam.getNombre());
            _usuario.setEmail(usuarioParam.getEmail());
            _usuario.setEdad(usuarioParam.getEdad());
            _usuario.setPrioridad(usuarioParam.getPrioridad());
            _usuario.setTelefono(usuarioParam.getTelefono());
            _usuario.setSexo(usuarioParam.getSexo());
            usuarioService.guardarUsuario(_usuario);
            return new ResponseEntity<UsuarioModel>(_usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
