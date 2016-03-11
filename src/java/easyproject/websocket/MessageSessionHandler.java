/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.websocket;


import com.google.gson.Gson;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import easyproject.model.Message;

@ApplicationScoped
public class MessageSessionHandler {
    
    //@Autowired
    //private ProjectService projectService;
    
    
    private final HashMap<String, ArrayList<Session>> projectSession = new HashMap<>();
    private final HashMap<String, ArrayList<Message>> projectMessages = new HashMap<>();

    
    @PostConstruct
    public void afterCreate() {
        System.out.println("DeviceSessionHandler created");
    }        
    
    public void addSession(String projectID, Session session) {
        // Recuperamos sesiones para un determinado proyecto
        Gson gson = new Gson();
        ArrayList<Session> sessions = projectSession.get(projectID);
        if (sessions == null) 
            sessions = new ArrayList<>();
        sessions.add(session);
        projectSession.put(projectID, sessions);
        
        
        // Recuperamos mensajes para un determinado proyecto
        ArrayList<Message> msgList = projectMessages.get(projectID);
        if (msgList == null) {
            // Comprobamos si hay mensajes en la BD de sesiones anteriores
            /*Project p = projectService.findProjectById(projectID);
            if (p.getChat() != null) {
                Type listType = new TypeToken<ArrayList<Message>>() {
                    }.getType();
                msgList = gson.fromJson(p.getChat(), listType);
            } else*/
                msgList = new ArrayList<>();
        }
        
        for (Message msg : msgList) {
            JsonObject addMessage = createAddMessage(msg);
            sendToSession(projectID, session, addMessage);
        }
    }

    public void removeSession(String projectID, Session session) {
        projectSession.remove(projectID);
    }
    
    /*public List getMsgList() {
        return new ArrayList<>(msgList);
    }*/

    public void addMessage(String projectID, Message message) {
        ArrayList<Message> msgList = projectMessages.get(projectID);
        Gson gson = new Gson();
        if (msgList == null) {
            // Comprobamos si hay mensajes en la BD
            /*Project p = projectService.findProjectById(projectID);
            if (p.getChat() != null) {
                Type listType = new TypeToken<ArrayList<Message>>() {
                    }.getType();
                msgList = gson.fromJson(p.getChat(), listType);
            } else*/
                msgList = new ArrayList<>();
        }
        msgList.add(message);
        projectMessages.put(projectID, msgList);
        
        // Actualizamos los mensajes de la BD
        String chatMessages = gson.toJson(msgList);
        /*Project p = projectService.findProjectById(projectID);
        p.setChat(chatMessages);
        projectService.editProject(p);*/
        
        JsonObject addMessage = createAddMessage(message);
        sendToAllConnectedSessions(projectID, addMessage);
    }
    

    private JsonObject createAddMessage(Message message) {
        JsonProvider provider = JsonProvider.provider();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("email", message.getUserEmail())
                .add("name", message.getUserName())
                .add("message", message.getMessage())
                .add("photoURL", message.getPhotoUrl())
                .add("timestamp", simpleDateFormat.format(message.getTimestamp()))
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(String projectID, JsonObject message) {
        ArrayList<Session> sessions = projectSession.get(projectID);
            for (Session session : sessions) {
                sendToSession(projectID, session, message);
            }
    }

    private void sendToSession(String projectID, Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            ArrayList<Session> sessions = projectSession.get(projectID);
            if (sessions != null)
                sessions.remove(session);
            Logger.getLogger(MessageSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}

