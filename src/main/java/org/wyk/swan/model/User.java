package org.wyk.swan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Entity
@Table(name = "user_")
public class User extends AbstractPersistable<Long> {

     @JsonProperty
     @Column(name = "username", unique = true, nullable = false, updatable = false)
     private String username;

     @JsonProperty("first_name")
     @Column(name = "first_name", length = 255, nullable = true)
     private String firstName;

     @JsonProperty("last_name")
     @Column(name="last_name", length = 255, nullable = true)
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
