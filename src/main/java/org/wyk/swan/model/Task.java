package org.wyk.swan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Entity
@Data
public class Task extends AbstractPersistable<Long> {

    @JsonIgnore
    @Column(name = "userId")
    private Long userId;

    @JsonProperty
    @Column(name = "name")
    private String name;

    @JsonProperty
    @Column(name = "description", length=510)
    private String description;

    @JsonProperty("date_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "date_time" )
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
