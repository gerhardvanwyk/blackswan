package org.wyk.swan.model;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "default", query = "")
public class Task extends AbstractPersistable<Long> {

    public Task() {
        this(null);
    }

    public Task(Long id){
        this.setId(id);
    }

}
