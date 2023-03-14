package com.example.tareamysql;

import java.util.Date;

public class Cliente {
    private int id;
    private String name;
    private int age;
    private Date date;


    public Cliente() {
    }

    public Cliente(int id, String name, int age, Date date) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.date = date;
    }

    public Cliente(String name, int age, Date date) {
        this.name = name;
        this.age = age;
        this.date = date;
    }

    public Cliente(String name, int age) {
        this.name = name;
        this.age = age;;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
