package org.wyk.swan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Data
@Entity
public class User extends AbstractPersistable<Long> {

     @JsonProperty
     @Column(unique = true, nullable = false, updatable = false)
     private String username;

     @JsonProperty("first_name")
     private String firstName;

     @JsonProperty("last_name")
     private String lastName;

     @Transient
     @JsonProperty
     private String error;

     public User(){
     }

     public User(Long  id){
          this.setId(id);
     }
}
