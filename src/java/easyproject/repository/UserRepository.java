/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.repository;

 
import easyproject.collection.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
 
 
@Repository
public interface UserRepository extends MongoRepository<User, String>{
    
    @Query("{'email': ?0 }")
    public User findUsersByEmail(String email);
    
    
    @Query("{'name':{$regex: '.*?0.*' }}")
    public List<User> findUsersByName(String name);
    

}