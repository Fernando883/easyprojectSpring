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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author csalas
 */
@Component
@Scope("session")
public class ProjectBean implements Serializable{

    private String projectName;
    private String projectDescription;
    private String projectDirector;

    @Autowired
    private UserBean userBean;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    protected List<String> listUsersName;
    protected List<String> tempUsers;
    protected String search;
    protected boolean projectAdded;
    protected boolean editProject;
    protected boolean projectEdited;
    HashMap<String, User> users;

    /**
     * Creates a new instance of addProjectBean
     */
    public ProjectBean() {
    }
    private List<Project> projects;

    @PostConstruct
    public void init() {
        search = "";

        users = new HashMap<>();
        projectAdded = false;
        projectEdited = false;

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

    public List<String> getListUsersName() {
        return listUsersName;
    }

    public void setListUsersName(List<String> listUsersName) {
        this.listUsersName = listUsersName;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<String> getTempUsers() {
        return tempUsers;
    }

    public void setTempUsers(List<String> tempUsers) {
        this.tempUsers = tempUsers;
    }

    public boolean isProjectAdded() {
        return projectAdded;
    }

    public void setProjectAdded(boolean proyectoInsertado) {
        this.projectAdded = proyectoInsertado;
    }

    public boolean isEditProject() {
        return editProject;
    }

    public void setEditProject(boolean editProject) {
        this.editProject = editProject;
    }

    public String doEditableProject() {
        setEditProject(true);

        return "";
    }

    public boolean isProjectEdited() {
        return projectEdited;
    }

    public void setProjectEdited(boolean projectEdited) {
        this.projectEdited = projectEdited;
    }

    public List<Project> getProjects() {
        Collection<String> idprojects = userBean.getUser().getId_project();
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

    public String doGoProject(Project project) {
        userBean.setProjectSelected(project);
        return "ViewProjectPage";
    }

    public String doPrepareCreate() {

        listUsersName = new ArrayList<>();
        
        for (User user : userService.findAllUsers()) {
            listUsersName.add(user.getEmail());
            users.put(user.getEmail(), user);
        }

        listUsersName.remove(userBean.getUser().getEmail());
        tempUsers = new ArrayList<>();

        return "";
    }

    public List<String> completeName(String query) {
        List<String> results = new ArrayList<>();

        for (String nombre : this.listUsersName) {
            if (nombre.startsWith(query)) {
                results.add(nombre);

            }
        }
        return results;
    }

    public String doAddTempList() {

        if (!tempUsers.contains(search)) {
            tempUsers.add(search);
        }

        search = "";
        return null;
    }

    public String doCleanProject() {

        projectName = "";

        projectDescription = "";
        tempUsers = new ArrayList<>();

        return "";
    }

    public String doAddProject() {

        String message = "";
        
        Project project = new Project();
        project.setName(projectName);
        project.setDescription(projectDescription);
        project.setId_director(userBean.getUser().getId());
        tempUsers.add(userBean.getUser().getEmail());
        project.setEmailsUsers(tempUsers);       
        projectService.createProject(project);
               
        for (String key: tempUsers) {
            User tmp = users.get(key);
            tmp.getId_project().add(project.getId());
            userService.editUser(tmp);
        }
        projectName = "";

        projectDescription = "";
        tempUsers = new ArrayList<>();
        projectAdded = true;

        /*message = "has sido añadido al proyecto" + project.getName() + "por el usuario:" + userBean.getName();

       
        for (String email: tempUsers) {
        
            new SendMail(email, project.getName(), message).start();

            //mail.toString();
        }*/

        return "";
    }

    public String doGoToNewProject() {
        return "NewProjectPage";
    }
}
