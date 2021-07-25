package com.liammacpherson.api.model;

import javax.persistence.*;

@Entity
public class Item extends SerializableEntity {

    private long id;
    private String name;

    public void setId(long id) {
        this.id = id;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

}
