/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.utils;


import easyproject.service.UserService;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author victo
 */

public class SendMail extends Thread {
    
    @Autowired
    private UserService userService;


    
        String servidorSMTP = "smtp.gmail.com";
        String puerto = "587";
        String usuario = "easyproyectjsf@gmail.com";
        String password = "proyectoweb";
   
        String destino = null;
        String asunto = null;
        String mensaje = null;

    String email, nombre, message;

    public SendMail(String email, String nombre, String message) {
        this.destino = email;
        this.asunto = nombre;
        this.mensaje = message;
    }

  

    @Override
    public void run() {
        
          Properties props = new Properties();
 
          props.put("mail.debug", "true");
          props.put("mail.smtp.auth", true);
          props.put("mail.smtp.starttls.enable", true);
          props.put("mail.smtp.host", servidorSMTP);
          props.put("mail.smtp.port", puerto);

          Session session = Session.getInstance(props, null);

          try {
           MimeMessage message1 = new MimeMessage(session);
           
           message1.addRecipient(Message.RecipientType.TO, new InternetAddress(
             destino));
           message1.setSubject(asunto);
           message1.setSentDate(new Date());
           message1.setContent(mensaje,"text/html; charset=utf-8");
           

           Transport tr = session.getTransport("smtp");
           tr.connect(servidorSMTP, usuario, password);
           message1.saveChanges();   
           tr.sendMessage(message1, message1.getAllRecipients());
           tr.close();

          } catch (MessagingException e) {
           e.printStackTrace();
          }
           
        
            
    }

//    private UsuarioFacade lookupUsuarioFacadeBean() {
//        try {
//            Context c = new InitialContext();
//            return (UsuarioFacade) c.lookup("java:global/EasyProject/EasyProject-ejb/UsuarioFacade!EasyProject.ejb.UsuarioFacade");
//        } catch (NamingException ne) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//            throw new RuntimeException(ne);
//        }
//    }

}
