/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.collection;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
 
@Document(collection="user")
public class User {
 
    @Id
    private String id;
    private String email;
    private String name;
    private Collection <String> id_project;
 
    public User() {
        id_project = new ArrayList<>();
    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public User(String id, String email, String name, Collection<String> id_project) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.id_project = id_project;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<String> getId_project() {
        return id_project;
    }

    public void setId_project(Collection<String> id_project) {
        this.id_project = id_project;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", name=" + name + ", id_project=" + id_project + '}';
    }
    
  
        
        
}