package org.wyk.swan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

@Data
@Entity
public class User extends AbstractPersistable<Long> {

     @JsonProperty
     private String username;

     @JsonProperty("first_name")
     private String firstName;

     @JsonProperty("last_name")
     private String lastName;
}
