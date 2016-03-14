package com.example.tinkazorge.problmset5;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class reads and writes files and returns an ArrayList.
 */
public class ToDoManager {

    // fields
    private String file;
    static final int READ_BLOCK_SIZE = 100;

    // constructor
    public ToDoManager(String fileArg){
        file = fileArg;
    }
    // method write text to file
    public void writeFile(View v, Context context) {

        // add-write text into file
        try {
            FileOutputStream fileout = context.openFileOutput("item.txt", Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(file);
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // method read text from file
    public ArrayList readFile(View v, Context context) {

        // create empty array
        ArrayList<String> arraylist = new ArrayList<String>();

        // open file
        try {
            FileInputStream fileIn = context.openFileInput("item.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            // read chars
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);

                // put strings in array
                s += readstring;
                arraylist.add(s);
            }
            // close the file
            InputRead.close();

        // diagnose exceptions
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arraylist;
    }

}

