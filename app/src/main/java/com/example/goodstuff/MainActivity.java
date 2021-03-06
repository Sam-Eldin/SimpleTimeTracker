package com.example.goodstuff;

import android.app.AlertDialog;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProjectManager projectManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize the projectManager.
        projectManager = ProjectManager.projectManager;
        projectManager.textView = this.findViewById(R.id.TopTextID);
        projectManager.adapter.listdata = projectManager.firestoreHelper.getData();
        projectManager.textView.setText(String.valueOf(projectManager.totalHours));
        projectManager.adapter.notifyItemRangeInserted(0, projectManager.adapter.getItemCount());

        // Initialize the recycler view.
        recyclerView = findViewById(R.id.resycling);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(projectManager.adapter);

        // Set onClicklistener to the Add button.
        (this.findViewById(R.id.fab)).setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Number of hours to add");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String value = input.getText().toString();
                System.out.println(value);
                if (value.isEmpty()) {
                    Toast.makeText(view.getContext(), "Value is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                double inputValue = Double.parseDouble(value);
                projectManager.addNewDay(inputValue);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }
}