/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.bean;



import easyproject.collection.sub.Task;
import easyproject.service.ProjectService;
import easyproject.service.UserService;
import easyproject.utils.SendMail;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 *
 * @author victo
 */
@Component
@Scope("view")
public class TaskBean {

//    @EJB
//    private UsuarioFacade usuarioFacade;
//    @EJB
//    private TareaFacade tareaFacade;
    
    @Autowired
    private UserBean userBean;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    


    private String nameTask;
    private String statusTask;
    private String description;
    private Collection<Task> collectionTask;

    private String duration;
    protected boolean taskAdded;
    protected boolean taskEdited;

    protected List<String> listUsersName;
    protected List<String> tempUsers;
    protected String search;
    

    protected boolean viewTask = false;

    /**
     * Creates a new instance of TaskBean
     */
    public TaskBean() {
        tempUsers = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        taskAdded = false;
        viewTask = false;
        duration = null;
        listUsersName = new ArrayList<>();

        if (userBean.getProjectSelected() != null) {
            listUsersName = userBean.getProjectSelected().getEmailsUsers();
        }
        loadTask();
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public Collection<Task> getCollectionTask() {
        
        return collectionTask;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public boolean isTaskAdded() {
        return taskAdded;
    }

    public void setTaskAdded(boolean taskAdded) {
        this.taskAdded = taskAdded;
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

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public boolean isViewTask() {
        return viewTask;
    }

    public void setViewTask(boolean viewTask) {
        this.viewTask = viewTask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTaskEdited() {
        return taskEdited;
    }

    public void setTaskEdited(boolean taskEdited) {
        this.taskEdited = taskEdited;
    }
    

    public String doShowTaskDetail(Task task) {
        taskAdded = false;
        taskEdited = false;
        this.viewTask = true;
        this.userBean.taskSelected = task;

        return "";
    }

    public List<String> completeName(String query) {
        List<String> results = new ArrayList<>();

        for (String nombre : this.listUsersName) {
            if (!nombre.equals("") && nombre.startsWith(query)) {
                results.add(nombre);

            }
        }
        return results;
    }

    public String doAddTempList() {

        if (!tempUsers.contains(search) && !search.equals("")) {
            tempUsers.add(search);
        }

        search = "";
        return null;
    }

    public String doEditTask() {

        //Lo que había antes más los nuevos
        List<String> memberTask = (List<String>) userBean.taskSelected.getEmailsUsers();

        for (String userString : tempUsers) {
           if(!memberTask.contains(userString))
           { memberTask.add(userString);
           }
        }
        userBean.taskSelected.setEmailsUsers(memberTask);

        userBean.taskSelected.setStatus(statusTask);

       
        userBean.taskSelected.setDuration(duration);

        projectService.editProject(userBean.getProjectSelected());

        duration = null;
        tempUsers = new ArrayList<>();
        taskEdited = true;

        loadTask();
        return "";
    }

    public String doAddTask() {
        
        String message = "";

        
        Task task = new Task();

        task.setName(nameTask);

        
        if(tempUsers.size() > 0)
        {
            task.setDuration(duration);

            task.setStatus(statusTask);

            task.setDescription(description); 

            task.setEmailsUsers(tempUsers);

            task.setId(String.valueOf(System.currentTimeMillis()));

            userBean.getProjectSelected().getListTasks().add(task);

            projectService.editProject(userBean.getProjectSelected());
            taskAdded = true;
            
        }
        
        
        
        message = "Has sido añadido a la tarea " + task.getName() + " del proyecto: " + userBean.getProjectSelected().getName() + ". El "
                + "director del proyecto es: " + userBean.getName();
        
         
        for (String email : tempUsers) {
           
            new SendMail(email,task.getName(),message).start();
            

        }
        
        nameTask = "";
        description = "";
        duration = null;
        tempUsers = new ArrayList<>();
        loadTask();
        return "";
        
        
    }
    public void  loadTask(){
        
        taskAdded = false;
        taskEdited = false;
        collectionTask = new ArrayList<Task>();
        if (userBean.getProjectSelected() != null) {
            userBean.setUser(userService.findByEmail(userBean.getUser().getEmail()));
            
            collectionTask = projectService.findAllTaskById(userBean.getProjectSelected().getId());
        }
        
    }

}
