package com.codsoft.checknote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity<Gson> extends AppCompatActivity {

    private EditText taskInput;
    private ListView taskListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> taskList;

    private static final String PREFS_NAME = "MyTaskListPrefs";
    private static final String TASKS_KEY = "tasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskInput = findViewById(R.id.taskInput);
        taskListView = findViewById(R.id.taskListView);

        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(adapter);

        // Load tasks from SharedPreferences
        loadTasks();

        // Set item click listener for editing tasks
        taskListView.setOnItemClickListener((parent, view, position, id) -> editTask(position));

        // Set item long click listener for marking tasks as completed
        taskListView.setOnItemLongClickListener((parent, view, position, id) -> {
            showDeleteConfirmationDialog(position);
            return true;
        });
    }
    public void addTask(View view) {
        String taskTitle = taskInput.getText().toString();
        if (!taskTitle.isEmpty()) {
            Task newTask = new Task(taskTitle, false, false, false, null);
            taskList.add(newTask);
            adapter.notifyDataSetChanged();
            taskInput.setText("");
            // Save tasks to SharedPreferences
            saveTasks();
        }
    }
    private void editTask(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Task");

        final EditText input = new EditText(this);
        input.setText(taskList.get(position).replace("[Completed] ", ""));
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String updatedTask = input.getText().toString();
            if (!updatedTask.isEmpty()) {
                // Check if the task was completed before editing
                if (taskList.get(position).contains("[Completed]")) {
                    taskList.set(position, "[Completed] " + updatedTask);
                } else {
                    taskList.set(position, updatedTask);
                }
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public void deleteTask(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Task");
        builder.setMessage("Are you sure you want to delete this task?");
        taskList.remove(position);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            taskList.remove(position);
            adapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

        builder.show();
    }
    private boolean markTaskCompleted(int position) {
        String task = taskList.get(position);
        // Check if the task is completed
        if (task.startsWith("[Completed] ")) {
            // If already completed, mark as active
            taskList.set(position, task.substring("[Completed] ".length()));
        } else {
            // If active, mark as completed
            taskList.set(position, "[Completed] " + task);
        }
        adapter.notifyDataSetChanged();
        return true; // Consume the long click event
    }


    public void navigateToHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putStringArrayListExtra("tasks", taskList);
        startActivity(intent);
    }

    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Task");
        builder.setMessage("Are you sure you want to delete this task?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            deleteTask(position);
            adapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void saveTasks() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String jsonTasks = gson.toJson(taskList);

        editor.putString(TASKS_KEY, jsonTasks);
        editor.apply();
    }

    private void loadTasks() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String jsonTasks = preferences.getString(TASKS_KEY, "");

        if (!jsonTasks.isEmpty()) {
            Gson gson = new Gson();
            Type taskListType = new TypeToken<ArrayList<Task>>() {}.getType();
            taskList = gson.fromJson(jsonTasks, taskListType);
        } else {
            taskList = new ArrayList<>();
        }

        // Update the adapter with the new task list
        adapter.clear();
        adapter.addAll(taskList);
        adapter.notifyDataSetChanged();
    }

    // ...
}