/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.bean;

import easyproject.collection.Project;
import easyproject.collection.User;
import easyproject.service.ProjectService;
import easyproject.service.UserService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author csalas
 */
@Component
@Scope("request")
public class ProjectBean {

    private String projectName;
    private String projectDescription;
    private String projectDirector;

    @Autowired
    private UserBean userBean;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

//    protected List<String> listUsersName;
//    protected List<String> tempUsers;
//    protected String search;
//    protected boolean projectAdded;
//    protected boolean editProject;
    /**
     * Creates a new instance of addProjectBean
     */
    public ProjectBean() {
    }
    private List<Project> projects;

    @PostConstruct
    public void init() {
//        search = "";
//
//        userRemove = new ArrayList<>();
//        projectAdded = false;
//        projectEdited = false;

    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectDirector() {
        return projectDirector;
    }

    public void setProjectDirector(String projectDirector) {
        this.projectDirector = projectDirector;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

//    public List<String> getListUsersName() {
//        return listUsersName;
//    }
//
//    public void setListUsersName(List<String> listUsersName) {
//        this.listUsersName = listUsersName;
//    }
//
//    public String getSearch() {
//        return search;
//    }
//
//    public void setSearch(String search) {
//        this.search = search;
//    }
//
//    public List<String> getTempUsers() {
//        return tempUsers;
//    }
//
//    public void setTempUsers(List<String> tempUsers) {
//        this.tempUsers = tempUsers;
//    }
//
//    public boolean isProjectAdded() {
//        return projectAdded;
//    }
//    
//    public void setProjectAdded(boolean proyectoInsertado) {
//        this.projectAdded = proyectoInsertado;
//    }
//
//    public boolean isEditProject() {
//        return editProject;
//    }
//
//    public void setEditProject(boolean editProject) {
//        this.editProject = editProject;
//    }
//    public String doEditableProject () {
//        setEditProject(true);
//        
//        return "";
//    }
    public List<Project> getProjects() {
        List<String> idprojects = (List<String>) userBean.getId_project();
        projects = new ArrayList<>();
        if (idprojects != null) {

            for (String idproject : idprojects) {
                projects.add(projectService.findProjectById(idproject));
            }
        }
        return projects;

    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

//    public List<String> completeName(String query) {
//        List<String> results = new ArrayList<>();
//
//        for (String nombre : this.listUsersName) {
//            if (nombre.startsWith(query)) {
//                results.add(nombre);
//
//            }
//        }
//        return results;
//    }
    public String doGoProject(Project project) {
        userBean.setProjectSelected(project);
        return "ViewProjectPage";
    }

//    public String doAddTempList() {
//
//        if (!tempUsers.contains(search)) {
//            tempUsers.add(search);
//        }
//
//        search = "";
//        return null;
//    }
//
//    public String doCleanProject() {
//        
//        projectName = "";
//
//        projectDescription = "";
//        tempUsers = new ArrayList<>();
//
//        return "";
//    }
//
//    public String doAddProject() {
//
//        String email;
//        String message = "";
//
//        List<Usuario> memberProject = new ArrayList<>();
//
//        for (String userString : tempUsers) {
//            Usuario tmp = usuarioFacade.getUser(userString);
//            if (tmp != null) {
//                memberProject.add(tmp);
//            }
//        }
//
//        memberProject.add(userBean.getUser());
//        Proyecto project = new Proyecto();
//        project.setNombreP(projectName);
//        project.setDescripcion(projectDescription);
//        project.setDirector(userBean.getUser());
//        project.setUsuarioCollection(memberProject);
//
//        proyectoFacade.create(project);
//        proyectos.add(project);
//
//        projectName = "";
//
//        projectDescription = "";
//        tempUsers = new ArrayList<>();
//        projectAdded = true;
//
//        message = "has sido añadido al proyecto" + project.getNombreP() + "por el usuario:" + userBean.getName();
//
//        List<Usuario> usuario = (List<Usuario>) project.getUsuarioCollection();
//        for (Usuario usuario1 : usuario) {
//
//            email = usuario1.getEmail();
//            new SendMail(email, project.getNombreP(), message).start();
//
//            //mail.toString();
//        }
//
//        return null;
//    }
//
//    public String doGoToNewProject() {
//        return "NewProjectPage";
//    }
}
