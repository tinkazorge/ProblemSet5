package com.example.tinkazorge.problmset5;


/**
 * This class saves one list.
 */
public class ToDoList {

    // fields
    private String list;
    public boolean completed;

    // constructor
    public ToDoList (String listArg, Boolean Done){
        list = listArg;
        completed = false;
    }
    // methods
    public void finished(){
        completed = true;
    }
    public String returnItem(){
        return (list);
    }
}

