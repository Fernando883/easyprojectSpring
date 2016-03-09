/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.repository;

import easyproject.collection.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author inftel10
 */
@Repository
public interface ProjectRepository extends MongoRepository<Project, String>{
    
    @Query("{'id': ?0 }")
    public Project findProjectsById(String id);
    
}
