/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.bean;

import easyproject.service.UserService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author macbookpro
 */
@Component
@Scope("request")
public class EmailBean {
    
    @Autowired
    private UserBean userBean;
    @Autowired
    private UserService userService;
    
    private String desteny;
    private String subject;
    private String message;
    private boolean  sendEmail;
    
    /**
     * Creates a new instance of EmailBean
     */
    public EmailBean() {
    }
    
    @PostConstruct
    public void init(){
        sendEmail = false;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getDesteny() {
        return desteny;
    }

    public void setDesteny(String desteny) {
        this.desteny = desteny;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }
    
    
    
    
    public String doSendEmail(){
        userService.sendEmail(userBean.getName(), userBean.getEmail() ,desteny, subject, message);
        desteny="";
        subject="";
        message="";
        sendEmail = true;
        return "";
    }
   
    
}
