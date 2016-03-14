package com.example.tinkazorge.problmset5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * To do list: With this class the user can add tasks to a to do list and delete them
 * using a long click. A toast will pop up that says "Removed".
 */

public class IndividualListActivity extends Activity {

    // declare widgets
    Button addButton;
    EditText editItem;
    TextView itemText;

    // create array to store values from edittext
    ArrayList<String> item_input;

    // create adapters
    ArrayAdapter<String> arrayAdapterItems;

    // create context
    Context context_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_list);
        context_items = IndividualListActivity.this;
        item_input = new ArrayList<String>();
        arrayAdapterItems = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item_input);

        // find widgets by ID
        final ListView listview2 = (ListView) findViewById(R.id.list_view_shapes);
        Button addbutton = (Button) findViewById(R.id.add_button);
        final EditText item_edit = (EditText) findViewById(R.id.item_edit);;
        TextView itemtext = (TextView) findViewById(R.id.todo_items);

        // get text from edittext
        String main_item = item_edit.getText().toString();

        //get the intent that started the activity
        Intent individuallist = getIntent();

        // retrieve title from main activity
        Bundle list_titles = individuallist.getExtras();
        final String list_title = (String) list_titles.get("list_title");

        // set data behind listview
        listview2.setAdapter(arrayAdapterItems);

        // when a user uses a long click
        listview2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // delete string at position
                item_input.remove(listview2.getItemAtPosition(position));

                String completed = (listview2.getItemAtPosition(position)).toString();

                ToDoItem done = new ToDoItem(completed, true, list_title);

                // notify the adapter of data change
                arrayAdapterItems.notifyDataSetChanged();

                // let toast pop up
                String text = "Task completed!";
                Toast toast = Toast.makeText(context_items, text, Toast.LENGTH_SHORT);
                toast.show();

                return true;
            }
        });

        // when the adddbutton is clicked
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get text from edittext
                String main_item = item_edit.getText().toString();

                // call ToDoItem
                ToDoItem newItem = new ToDoItem(main_item, false, list_title);
                String stored_item= newItem.returnItem();

                // call WriteFile
                ToDoManager writeFile = new ToDoManager(stored_item);
                writeFile.writeFile(listview2, context_items);

                // call Readfile
                ToDoManager readFile = new ToDoManager(stored_item);
                ArrayList<String> readItem = readFile.readFile(listview2, context_items);

                // display array
                ArrayAdapter<String> arrayAdapterAfterSave2 = new ArrayAdapter<String>(
                IndividualListActivity.this, android.R.layout.simple_list_item_1, readItem);

                // set data behind listview
                listview2.setAdapter(arrayAdapterAfterSave2);

                // add item to the list
                readItem.add(stored_item);
                //update the list
                ((ArrayAdapter) ((ListView)findViewById(R.id.list_view_shapes)).getAdapter()).notifyDataSetChanged();

                // clear edittext
                item_edit.setText("");
            }
        });
    }

    // if the user can clicks anywhere in the screen
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            // and the focus is in edittext
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    // clear focus
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

}





