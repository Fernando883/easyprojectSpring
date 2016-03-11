/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.bean;

import easyproject.collection.sub.Comment;
import easyproject.service.ProjectService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author victo
 */
@Component
@Scope("request")
public class CommentBean {
    
//    @EJB
//    private FicheroFacade ficheroFacade;
//    @EJB
//    private ComentarioFacade comentarioFacade;

    @Autowired
    private UserBean userBean;
    @Autowired
    private ProjectService projectService;
    
    private String message;
    
    
    protected Part file;

    /**
     * Creates a new instance of CommentBean
     */
    public CommentBean() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String Message) {
        this.message = Message;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
    
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    

    public String doSaveComment() {

        Comment comment = new Comment();
        comment.setCommentText(message);
        comment.setId(String.valueOf(System.currentTimeMillis()));
        
        comment.setUserName(userBean.getUser().getName());
        
        int indexOf = userBean.getProjectSelected().getListTasks().indexOf(userBean.getTaskSelected());
        userBean.getProjectSelected().getListTasks().get(indexOf).getComments().add(comment);
        
        projectService.editProject(userBean.getProjectSelected());
        
        message="";
        return "";
    }
    
    public String doUpdateFile() throws IOException{
        String path = "/Users/csalas/NetBeansProjects/easyprojectSpring/web/uploaded/";
        String urlPath = "http://localhost:8080/easyprojectSpring/web/uploaded/";
        String fileName = String.valueOf(System.currentTimeMillis()) + getFilename(file);
        Comment comment = new Comment();
        message = "Ha subido el fichero: <a href='"+ urlPath + fileName+"'>"+ fileName +"</a>";
        
        file.write(getFilename(file));
        comment.setCommentText(message);
        comment.setId(String.valueOf(System.currentTimeMillis()));
        comment.setUserName(userBean.getUser().getName());
        
        int indexOf = userBean.getProjectSelected().getListTasks().indexOf(userBean.getTaskSelected());
        userBean.getProjectSelected().getListTasks().get(indexOf).getComments().add(comment);

        
        File dowloadFile = new File("/Applications/NetBeans/glassfish-4.1/glassfish/domains/domain1/generated/jsp/SpringMongoJSF/"+ getFilename(file));
	File newFile = new File(path+ getFilename(file));
	Path sourcePath = dowloadFile.toPath();
	Path newtPath = newFile.toPath();
	Files.copy(sourcePath, newtPath, REPLACE_EXISTING); 
        
        userBean.getProjectSelected().getListTasks().get(indexOf).getFiles().add(urlPath+ getFilename(file));
        
        projectService.editProject(userBean.getProjectSelected());
        
        
        message="";
        return "";
    }
    
    public String downloadFile(String text) throws IOException {
        String file = text.substring(22);
	
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("http://localhost:8080/SpringMongoJSF2/uploaded/"+file);
	
        return "";
    }
    
    public static String getFilename(Part part){
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if(cd.trim().startsWith("filename")){
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }
    
    public String getDate(String dateString) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date(new Long(dateString));
        
        return formatter.format(date);
    }

}
