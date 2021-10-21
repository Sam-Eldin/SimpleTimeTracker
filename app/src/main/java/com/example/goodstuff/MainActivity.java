package com.example.goodstuff;

import android.app.AlertDialog;
import android.os.Bundle;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import android.text.InputType;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class MainActivity extends AppCompatActivity {


    TextView textView;
    long currentHours = 0;
    Map<String, Date> currentDates = null;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static class Collections {
        public static String USERS = "users";
    }

    private static class Users {
        public static String Sam = "Sam";
    }

    private static class Fields {
        public static String Dates = "Dates";
        public static String TotalHours = "Total Hours";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        textView = this.findViewById(R.id.TopTextID);

        db.collection(Collections.USERS).document(Users.Sam).get().addOnCompleteListener((tasl)-> {
            DocumentSnapshot value = tasl.getResult();

            assert value != null;
            Map<String, Object> qs = value.getData();
            assert qs != null;
            assert qs.get(Fields.TotalHours) != null;
            assert qs.get(Fields.Dates) != null;
            long totalHours = (long) qs.get(Fields.TotalHours);
            ArrayList<Map<String, Long>> dates = (ArrayList<Map<String, Long>>) qs.get(Fields.Dates);
            currentDates = null;
            assert dates != null;
            System.out.println(dates.get(0).get("hours"));
           /* dates.forEach((s, timestamp) -> {
                System.out.println(timestamp);
            });*/
            System.out.println(totalHours);
            currentHours = totalHours;
            textView.setText(String.valueOf(currentHours));
        });
        (this.findViewById(R.id.fab)).setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Number of hours to add");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String value = input.getText().toString();
                System.out.println(value);
                currentHours += Integer.parseInt(value);
                textView.setText(String.valueOf(currentHours));
                db.collection(Collections.USERS).document(Users.Sam).update(Fields.TotalHours, currentHours).addOnCompleteListener(task -> {
                    System.out.println("Updated result " + task.getResult());
                });
                /*currentDates.add(new Date());

                db.collection(Collections.USERS).document(Users.Sam).update(Fields.Dates, currentDates).addOnCompleteListener(task -> {
                    System.out.println("Updated result " + task.getResult());
                });*/
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}