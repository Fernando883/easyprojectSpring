/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.collection;

import easyproject.collection.sub.Task;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author inftel10
 */
@Document(collection="project")
public class Project {
    
    @Id
    private String id;
    private String name;
    private String id_director;
    private String description;
    private String chat; 
    private List<Task> listTasks;
    private List<String> idUsers;

    public Project(String id, String name, String id_director, String description, String chat, List<Task> listTasks, List<String> idUsers) {
        this.id = id;
        this.name = name;
        this.id_director = id_director;
        this.description = description;
        this.chat = chat;
        this.listTasks = listTasks;
        this.idUsers = idUsers;
    }
 
    public Project(String id, String name, String id_director, String description, String chat, List<Task> listTasks) {
        this.id = id;
        this.name = name;
        this.id_director = id_director;
        this.description = description;
        this.chat = chat;
        this.listTasks = listTasks;
    }

    public Project(String name, String id_director, String description, String chat, List<Task> listTasks) {
        this.name = name;
        this.id_director = id_director;
        this.description = description;
        this.chat = chat;
        this.listTasks = listTasks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_director() {
        return id_director;
    }

    public void setId_director(String id_director) {
        this.id_director = id_director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public List<Task> getListTasks() {
        return listTasks;
    }

    public void setListTasks(List<Task> listTasks) {
        this.listTasks = listTasks;
    }

    public List<String> getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(List<String> idUsers) {
        this.idUsers = idUsers;
    }
    
    

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", name=" + name + ", id_director=" + id_director + ", description=" + description + ", chat=" + chat + ", listTasks=" + listTasks + '}';
    }
    
    
    
}
