/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.bean;

import easyproject.collection.Project;
import easyproject.collection.User;
import easyproject.collection.sub.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import easyproject.service.UserService;
import java.io.Serializable;
import java.util.Collection;
import org.springframework.context.annotation.Scope;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author victo
 */



@Component
@Scope("session")
public class UserBean implements Serializable {
//    @EJB
//    private ProyectoFacade proyectoFacade;
//    @EJB
//    private TareaFacade tareaFacade;
//    @EJB
//    private UsuarioFacade usuarioFacade;
//
       @Autowired
    private UserService userService;



    private String email;
    private User user;
    private String name;
    private String image;
    private Collection <String> id_project;
    private Project projectSelected = null;
    protected Task taskSelected = null;

    public Collection<String> getId_project() {
        return id_project;
    }

    public void setId_project(Collection<String> id_project) {
        this.id_project = id_project;
    }




    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Project getProjectSelected() {
        return projectSelected;
    }

    public void setProjectSelected(Project projectSelected) {
        this.projectSelected = projectSelected;
    }

    public Task getTaskSelected() {
        return taskSelected;
    }

    public void setTaskSelected(Task taskSelected) {

        this.taskSelected = taskSelected;
    }
    
     public String doProfile(){
        return "Profile";
    }


    public String doLogin(){
        projectSelected = null;
        name = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("name");
        email = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("email");
        image = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("image");

        if (image.equals("")) {
            image = "https://freeiconshop.com/files/edd/person-flat.png";
        }

        if(userService.findByEmail(email) == null){
            user = new User (email, name);
            userService.createUser(user);

        }else{
            user=userService.findByEmail(email);
        }
        return "";

    }

    public String doSignOut(){
        HttpSession session =  (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session != null){
            session.invalidate();
        }
        user = new User();
        email="";
        image="";
        return "PageTitle";
    }

    public String doGoToMainPage () {
        return "MainPage";
    }



}
