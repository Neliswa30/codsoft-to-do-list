package com.codsoft.checknote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ListView homeListView;
    private ArrayAdapter<String> homeAdapter;
    private ArrayList<String> homeTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeListView = findViewById(R.id.homeListView);

        // Initialize home task list
        homeTaskList = new ArrayList<>();
        if (getIntent().hasExtra("tasks")) {
            // Retrieve tasks from MainActivity
            homeTaskList = getIntent().getStringArrayListExtra("tasks");
        }

        homeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, homeTaskList);
        homeListView.setAdapter(homeAdapter);

        // Add sample tasks (replace with actual task data)
        homeTaskList.add("Task 1 - Incomplete");
        homeTaskList.add("Task 2 - Complete");

        // TODO: Populate homeTaskList with actual task data and completion status
    }
    // Add this method to handle adding tasks from MainActivity
    public void addHomeTask(String task) {
        homeTaskList.add(task);
        homeAdapter.notifyDataSetChanged();
    }
}
