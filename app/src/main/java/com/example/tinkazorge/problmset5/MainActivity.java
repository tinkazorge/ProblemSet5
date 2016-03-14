package com.example.tinkazorge.problmset5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * To do list: In this class the user can add and remove lists using a long click. A toast will pop up that says "Removed". With a
 * short click the user can open the list to add items.
 */

public class MainActivity extends Activity {

    // declare widgets
    Button addButton;
    EditText list_edit;
    TextView listText;

    // create array to store values from edittext
    ArrayList<String> listInputArray;

    // create adapter
    ArrayAdapter<String> arrayAdapterMain;

    // create context
    Context context_lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find widgets by ID
        final ListView listview1 = (ListView) findViewById(R.id.list_view_shapes);
        Button addbutton = (Button) findViewById(R.id.add_button);
        final EditText list_edit = (EditText) findViewById(R.id.list_edit);
        final TextView listtext = (TextView) findViewById(R.id.todo_lists);

        // get text from edittext
        final String list_title = list_edit.getText().toString();

        // get context
        context_lists = MainActivity.this;

        // define arrayLists
        listInputArray = new ArrayList<String>();
        //arrayAdapterMain = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listInputArray);

        // set data behind listview
        //listview1.setAdapter(arrayAdapterMain);

        // when a user uses a short click
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // define next activity
                Intent individuallist = new Intent(MainActivity.this, IndividualListActivity.class);

                //push  input to next activity
                individuallist.putExtra("list_title", list_title);

                // start next activity
                startActivity(individuallist);
            }
        });

        // when a user uses a long click
        listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // delete string at position
                listInputArray.remove(listview1.getItemAtPosition(position));

                // get position and convert to string
                String completed = (listview1.getItemAtPosition(position)).toString();

                // set boolean finished to true
                ToDoList done= new ToDoList(completed, false);

                // notify the adapter of data change
                arrayAdapterMain.notifyDataSetChanged();

                // let toast pop up
                String text = "List completed!";
                Toast toast = Toast.makeText(context_lists, text, Toast.LENGTH_SHORT);
                toast.show();

                return true;
            }
        });

        // when the adddbutton is clicked
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get text from edittext
                String main_list = list_edit.getText().toString();

                // call ToDoList
                ToDoList newList = new ToDoList(main_list, false);
                String stored_list= newList.returnItem();

                // call WriteFile
                ToDoManager writeFile = new ToDoManager(stored_list);
                writeFile.writeFile(listview1, context_lists);

                // call readFile
                ToDoManager readFile = new ToDoManager(stored_list);
                ArrayList<String> readList = readFile.readFile(listview1, context_lists);

                // display array
                ArrayAdapter<String> arrayAdapterAfterSave1 = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_list_item_1, readList);

                listview1.setAdapter(arrayAdapterAfterSave1);

                // add item to the list
                readList.add(stored_list);

                 // update the list
                ((ArrayAdapter) ((ListView)findViewById(R.id.list_view_shapes)).getAdapter()).notifyDataSetChanged();

                // clear edittext
                list_edit.setText("");
            }
        });
    }
    //if the user can clicks anywhere in the screen
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //and the focus is in edittext
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    //clear focus
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }


}






