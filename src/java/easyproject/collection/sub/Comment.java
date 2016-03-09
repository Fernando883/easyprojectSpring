/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.collection.sub;

import org.springframework.data.annotation.Id;

/**
 *
 * @author inftel10
 */
public class Comment {
    
    @Id
    private String id;
    private String commentText;
    private String date;

    public Comment(String id, String commentText, String date) {
        this.id = id;
        this.commentText = commentText;
        this.date = date;
    }

    public Comment(String commentText, String date) {
        this.commentText = commentText;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", commentText=" + commentText + ", date=" + date + '}';
    }
    
    
    
}
