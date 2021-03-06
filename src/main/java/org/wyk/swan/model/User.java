package org.wyk.swan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
     @JsonInclude(JsonInclude.Include.NON_EMPTY)
     private String error;
     @JsonIgnore
     @Transient // DATAJPA-622
     @Override
     public boolean isNew() {
         return super.isNew();
     }

     public User(){
     }

     public User(Long  id){
          this.setId(id);
     }
}
