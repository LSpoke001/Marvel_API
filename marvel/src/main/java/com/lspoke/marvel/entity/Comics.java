package com.lspoke.marvel.entity;

import javax.persistence.*;

@Entity
@Table(name = "comics")
public class Comics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "character_id")
    private Character character;

    public Comics() {
    }

    public Comics(int id, String title, Character character) {
        this.id = id;
        this.title = title;
        this.character = character;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
