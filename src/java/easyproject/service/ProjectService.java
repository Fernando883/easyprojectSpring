/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.service;

import easyproject.collection.Project;
import easyproject.collection.sub.Task;
import easyproject.repository.ProjectRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author inftel10
 */
@Component
@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository repository;

    public ProjectRepository getRepository() {
        return repository;
    }
    public void createProject(Project p) {
        repository.save(p);
    }
    
    public Project findProjectById(String id){
        return repository.findOne(id);
    }
    
    public List<Task> findAllTaskById(String idProject){
        return repository.findAllTaskById(idProject);
    }
    
    public List<Project> findAllProject() {
        return repository.findAll();
    }
    
    public void deleteProject(Project p) {
        repository.delete(p);
    }
    
    public void editProject (Project  p) {
        repository.save(p);
    }
}
