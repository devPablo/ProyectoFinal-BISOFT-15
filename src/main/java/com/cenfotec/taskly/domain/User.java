package com.cenfotec.taskly.domain;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;
import com.microsoft.azure.spring.data.cosmosdb.core.mapping.PartitionKey;
import org.springframework.data.annotation.Id;

@Document(collection = "Users")
public class User {
    @Id
    @PartitionKey
    private String username;
    private String password;
    private String nombre;
    private String apellidos;

    public User(String username, String password, String nombre, String apellidos) {
        this.setUsername(username);
        this.setPassword(password);
        this.setNombre(nombre);
        this.setApellidos(apellidos);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return "User:\n" +
                "Correo: " + getUsername() + '\n' +
                "Contraseña: " + getPassword() + '\n' +
                "Nombre: " + getNombre() + '\n' +
                "Apellidos: " + getApellidos();
    }
}