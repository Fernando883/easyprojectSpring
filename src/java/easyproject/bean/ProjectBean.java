/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.bean;

import easyproject.collection.Project;
import easyproject.collection.User;
import easyproject.collection.sub.Task;
import easyproject.service.ProjectService;
import easyproject.service.UserService;
import easyproject.utils.SendMail;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author csalas
 */
@Component
@Scope("view")
public class ProjectBean implements Serializable {

    private String projectName;
    private String projectDescription;
    private String projectDirector;
    private List<Project> projects;

    @Autowired
    private UserBean userBean;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    protected List<String> listUsersName;
    protected List<String> tempUsers;
    protected String search;
    protected boolean editProject;
    protected boolean projectEdited;
    protected boolean projectAdded;
    HashMap<String, User> users;
    private String message = "Ha intentado eliminar un usuario asignado a una tarea";
    private boolean show = false;
    private Map<String, Boolean> checked = new HashMap<>();

    List<Project> userProjects;

    /**
     * Creates a new instance of addProjectBean
     */
    public ProjectBean() {
    }

    @PostConstruct
    public void init() {
        search = "";

        users = new HashMap<>();
        projectEdited = false;

        userProjects = new ArrayList<>();

        for (String idProject : userBean.getUser().getId_project()) {
            userProjects.add(projectService.findProjectById(idProject));
        }

        loadProjects();
        loadUserProjects();

    }

    private void loadProjects() {
        userBean.setUser(userService.findByEmail(userBean.getUser().getEmail()));
        Collection<String> idprojects = userBean.getUser().getId_project();
        projects = new ArrayList<>();
        if (idprojects != null) {

            for (String idproject : idprojects) {
                projects.add(projectService.findProjectById(idproject));
            }
        }
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

    public boolean isEditProject() {
        return editProject;
    }

    public void setEditProject(boolean editProject) {
        this.editProject = editProject;
    }

    public Map<String, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<String, Boolean> checked) {
        this.checked = checked;
    }

//    public String doEditableProject() {
//        setEditProject(true);
//
//        return "";
//    }
    public boolean isProjectAdded() {
        return projectAdded;
    }

    public void setProjectAdded(boolean projectAdded) {
        this.projectAdded = projectAdded;
    }

    public boolean isProjectEdited() {
        return projectEdited;
    }

    public void setProjectEdited(boolean projectEdited) {
        this.projectEdited = projectEdited;
    }

    public String getUserName(String id) {
        User userDirector = userService.findUsersById(id);
        return userDirector.getName();
    }

    public String getUserNameByEmail(String email) {
        User user = userService.findByEmail(email);
        return user.getName();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String doGoProject(Project project) {
        userBean.setProjectSelected(project);
        return "ViewProjectPage";
    }

    public String doPrepareEdit() {
        listUsersName = new ArrayList<>();
        List<User> allUsers = userService.findAllUsers();
        for (User allUser : allUsers) {
            listUsersName.add(allUser.getEmail());
        }
        listUsersName.remove(userBean.getUser().getEmail());

        for (String email : userBean.getProjectSelected().getEmailsUsers()) {
            listUsersName.remove(email);
        }

        tempUsers = new ArrayList<>();

        return "";
    }

    public String doEditProject() {

        //Lo que había antes más los nuevos
        List<String> memberProject = (List<String>) userBean.getProjectSelected().getEmailsUsers();
        if (tempUsers != null) {
            for (String userString : tempUsers) {
                if (!memberProject.contains(userString)) {
                    memberProject.add(userString);
                }
            }
        }

        Iterator it = checked.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            it.remove(); // avoids a ConcurrentModificationException
            if (pair.getValue().equals(true)) {

                String user = (String) pair.getKey();
                boolean find = false;

                if (userBean.getProjectSelected().getListTasks().size() > 0) {
                    for (Task task : userBean.getProjectSelected().getListTasks()) {
                        if (task.getEmailsUsers().contains(user)) {
                            find = true;
                            show = true;
                            break;
                        }
                    }
                }
                if (!find) {
                    userBean.getProjectSelected().getEmailsUsers().remove((String) pair.getKey());
                }
            }
        }

        userBean.getProjectSelected().setEmailsUsers(memberProject);

        if (projectName.length() != 0) {
            userBean.getProjectSelected().setName(projectName);

        }

        if (projectDescription.length() != 0) {
            userBean.getProjectSelected().setDescription(projectName);

        }

        projectService.editProject(userBean.getProjectSelected());

        projectName = "";
        projectDescription = "";
        tempUsers = new ArrayList<>();
        projectEdited = true;
        return "";
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

        String message1 = "";

        Project project = new Project();
        project.setName(projectName);
        project.setDescription(projectDescription);
        project.setId_director(userBean.getUser().getId());
        tempUsers.add(userBean.getUser().getEmail());
        project.setEmailsUsers(tempUsers);
        projectService.createProject(project);

        for (String key : tempUsers) {
            User tmp = users.get(key);
            tmp.getId_project().add(project.getId());
            userService.editUser(tmp);
        }

        message1 = "Has sido añadido al proyecto " + project.getName() + ". El "
                + "director del proyecto es: " + userBean.getName();

        for (String email : tempUsers) {

            new SendMail(email, project.getName(), message1).start();

        }
        projectDescription = "";
        tempUsers = new ArrayList<>();
        projectName = "";
        loadProjects();
        return "";
    }

    public String doGoToNewProject() {
        return "NewProjectPage";
    }

    public int calculaPosicionProject(Project proyecto) {
        if (this.projects == null) {
            return 0;
        }

        return this.projects.indexOf(proyecto) % 9;
    }

    public String findUserByEmail(String email_director) {
        User user = userService.findUsersByEmail(email_director);
        return user.getId();
    }

    public String doDeleteProject(Project project) {
        userBean.getUser().getId_project().remove((String) project.getId());
        userService.editUser(userBean.getUser());

        projectService.deleteProject(project);
        loadProjects();

        return "";
    }

    public void loadUserProjects() {
        //load user projects for
        userProjects = new ArrayList<>();

        for (String idProject : userBean.getUser().getId_project()) {
            userProjects.add(projectService.findProjectById(idProject));
        }
    }

    public List<Project> getUserProjects() {

        return userProjects;

    }

}
