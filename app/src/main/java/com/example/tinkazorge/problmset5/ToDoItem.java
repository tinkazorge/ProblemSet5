package com.example.tinkazorge.problmset5;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * This class saves one item.
 */
public class ToDoItem{
    // fields
    private String item;
    public Boolean completed;
    private String list_title;

    static final int READ_BLOCK_SIZE = 100;

   // constructor
   public ToDoItem (String itemArg, Boolean Done, String list_titleArg){
       item = itemArg;
       completed = false;
       list_title = list_titleArg;
   }
   // methods
    public void finished() {
        completed = true;
    }
    public String returnItem(){
            return (item);
    }
    public String returnTitle(){
        return list_title;
    }
}

