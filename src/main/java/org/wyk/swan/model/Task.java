package org.wyk.swan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Entity
@Data
public class Task extends AbstractPersistable<Long> {

    @JsonIgnore
    private Long userId;

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    @Transient
    @JsonProperty
    private String error;


    public Task() {
        this(null);
    }

    public Task(Long id){
        this.setId(id);
    }

}
