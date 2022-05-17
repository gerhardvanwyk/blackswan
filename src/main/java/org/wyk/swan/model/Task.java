package org.wyk.swan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String error;

    @JsonIgnore
    @Transient // DATAJPA-622
    @Override
    public boolean isNew() {
        return super.isNew();
    }

    public Task() {
        this(null);
    }

    public Task(Long id){
        this.setId(id);
    }

}
