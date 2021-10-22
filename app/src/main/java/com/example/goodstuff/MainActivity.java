package com.example.goodstuff;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

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

        projectManager = ProjectManager.projectManager;

        projectManager.textView = this.findViewById(R.id.TopTextID);

        recyclerView = findViewById(R.id.resycling);

        projectManager.adapter.listdata = projectManager.firestoreHelper.getData();

        projectManager.textView.setText(String.valueOf(projectManager.totalHours));

        projectManager.adapter.notifyItemRangeInserted(0, projectManager.adapter.getItemCount());

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(projectManager.adapter);


        (this.findViewById(R.id.fab)).setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Number of hours to add");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String value = input.getText().toString();
                System.out.println(value);
                if (value.isEmpty()) {
                    return;
                }
                int inputValue = Integer.parseInt(value);

                projectManager.addNewDay(inputValue);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }


    public static class Collections {
        public static final String USERS = "users";
    }

    public static class Users {
        public static final String Sam = "Sam";
    }

    public static class Fields {
        public static final String Dates = "Dates";
    }
}