// HomeActivity.java
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
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
        homeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, homeTaskList);
        homeListView.setAdapter(homeAdapter);

        // Add sample tasks (replace with actual task data)
        homeTaskList.add("Task 1 - Incomplete");
        homeTaskList.add("Task 2 - Complete");

        // TODO: Populate homeTaskList with actual task data and completion status
    }
}

