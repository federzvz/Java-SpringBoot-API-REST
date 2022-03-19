# Java-SpringBoot-API-REST


# Se han agregado Pruebas Funcionales

  - PUT para modificar un elemento que se encuentra en la base de datos
  ```java
  @Test
    @DisplayName("PUT /usuario/1 - Success (Actualizar un usuario)")
    void testUpdateUsuarioModel() throws Exception {
        //Usuario a actualizar
        Optional<UsuarioModel> usuarioToUpdate = Optional.of(new UsuarioModel(1,"Fede","federzvz",1,null,null,null));
        //Usuario con sus nuevos valores actualizados
        UsuarioModel usuario = new UsuarioModel(1,"Fede","federzvz",1,"telNuevo","sexoNuevo",21);
        //Armamos nuestra prueba
        doReturn(usuarioToUpdate).when(service).obtenerPorId(1L);
        doReturn(usuario).when(service).guardarUsuario(any());

        //Convertimos nuestro
        String objToJson = new ObjectMapper().writeValueAsString(usuarioToUpdate);

        //Ejecutamos y comparamos si el resposne es OK(200)
        var response = mockMvc.perform(put("http://localhost:8080/usuario/update/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objToJson).header(HttpHeaders.IF_MATCH,"1")).andExpect(status().isOk());
    }
  ```
  - PUT para modificar un elemento que no se encuentra en la base de datos (not found)
  ```java
  @Test
    @DisplayName("PUT /usuario/1 - Error (Actualizar un usuario que no exista)")
    void testUpdateUsuarioNotFound() throws  Exception{
        UsuarioModel usuario1 = new UsuarioModel(69L,"nombreTest","emailTest",1,"telTest","sexoTest",28);
        doReturn(Optional.empty()).when(service).obtenerPorId(1l);

        mockMvc.perform(put("http://localhost:8080/usuario/update/{id}", 69L)
                        .header(HttpHeaders.IF_MATCH,"1")
                        .content(new ObjectMapper().writeValueAsString(usuario1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
  ```
  - GET para obtener un elemento por ID que se encuentra en la base de datos
  ```java
  @Test
    @DisplayName("GET /usuario/1 - success (Obtener un usuario meidante su ID)")
    void testGetUsuarioModelById() throws Exception {
        // Armar nuestra prueba de petición
        doReturn(Optional.empty()).when(service).obtenerPorId(1l);

        // Ejectuar el método GET para obtener por ID y esperar que el RESPONSE sea OK(200).
        mockMvc.perform(get("http://localhost:8080/usuario/1", 1L))
                .andExpect(status().isOk());
    }
  ```
  
  - GET para obtener varios elementos 
   ```java
   @Test
    @DisplayName("GET /usuario - success")
    void testGetUsuarioModel() throws Exception {
        UsuarioModel widget1 = new UsuarioModel(69,"nombreTest","emailTest",1,"telTest","sexoTest",28);
        UsuarioModel widget2 = new UsuarioModel(70,"nombreTest2","emailTest2",2,"telTest2","sexoTest2",29);
        //String nombre, String email, Integer prioridad, String telefono, String sexo, Integer edad
        doReturn(Lists.newArrayList(widget1, widget2)).when(service).obtenerUsuario();
        mockMvc.perform(get("http://localhost:8080/usuario"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(69)))
                .andExpect(jsonPath("$[0].nombre", is("nombreTest")))
                .andExpect(jsonPath("$[0].email", is("emailTest")))
                .andExpect(jsonPath("$[0].prioridad", is(1)))
                .andExpect(jsonPath("$[0].telefono", is("telTest")))
                .andExpect(jsonPath("$[0].sexo", is("sexoTest")))
                .andExpect(jsonPath("$[0].edad", is(28)))
                .andExpect(jsonPath("$[1].id", is(70)))
                .andExpect(jsonPath("$[1].nombre", is("nombreTest2")))
                .andExpect(jsonPath("$[1].email", is("emailTest2")))
                .andExpect(jsonPath("$[1].prioridad", is(2)))
                .andExpect(jsonPath("$[1].telefono", is("telTest2")))
                .andExpect(jsonPath("$[1].sexo", is("sexoTest2")))
                .andExpect(jsonPath("$[1].edad", is(29)));
    }
