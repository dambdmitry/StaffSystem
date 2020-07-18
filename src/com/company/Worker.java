package com.company;

public class Worker {
    private int id;
    private String name;

    public Worker(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Worker(){}

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return id + " " + name;
    }

    @Override
    public boolean equals(Object obj){
       Worker worker = (Worker) obj;
       return this.id == worker.getId() && this.name.equals(worker.getName());
    }
}
