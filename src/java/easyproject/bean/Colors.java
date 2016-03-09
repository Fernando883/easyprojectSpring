/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author inftel12
 */

@Component
@Scope("request")
public class Colors {

    /**
     * Creates a new instance of Colors
     */
    private List<String> colors;
    private int num = 9;

    
    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
    
    public Colors() {
        
        colors = new ArrayList<>();
        colors.add("bg-aqua");
        colors.add("bg-green");
        colors.add("bg-yellow");
        colors.add("bg-purple");
        colors.add("bg-blue");   
        colors.add("bg-danger");
        colors.add("bg-teal");
        colors.add("bg-orange");
        colors.add("bg-olive");

    }
    
    
    public String getColor (int i) {
        System.out.println("VAlor: " + i);
        return colors.get(i);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    
}
