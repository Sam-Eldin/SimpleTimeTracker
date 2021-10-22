package com.example.goodstuff;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"StatementWithEmptyBody", "unchecked"})
public class FirestoreHelper {
    public final FirebaseFirestore database;
    private final DocumentReference documentReference;

    public FirestoreHelper() {
        this.database = FirebaseFirestore.getInstance();
        this.documentReference = database.collection(MainActivity.Collections.USERS).document(MainActivity.Users.Sam);
    }


    public void addDay(int hours) {
        ArrayList<Object> documentSnapshot = executeGet();
        Map<Object, Object> map = new HashMap<>();
        map.put("hours", hours);
        map.put("day", new Date());
        documentSnapshot.add(0, map);
        executeUpdate(documentSnapshot);
        System.out.println("\n\n--------------------------\nAdded\n------------------------------\n\n");
    }

    public void updateDay(int index, int newHours) {
        ArrayList<Object> documentSnapshot = executeGet();
        Map<Object, Object> map = (Map<Object, Object>) documentSnapshot.get(index);
        map.put("hours", newHours);
        executeUpdate(documentSnapshot);
        System.out.println("\n\n--------------------------\nUpdated\n------------------------------\n\n");
    }

    public void deleteDay(int index) {
        ArrayList<Object> documentSnapshot = executeGet();
        documentSnapshot.remove(index);
        executeUpdate(documentSnapshot);
        System.out.println("\n\n--------------------------\nDeleted\n------------------------------\n\n");
    }

    public ArrayList<MyListData> getData() {
        long sum = 0;
        ArrayList<MyListData> results = new ArrayList<>();
        ArrayList<Object> data = executeGet();
        for (Object object : data) {
            Map<Object, Object> mappy = (Map<Object, Object>) object;

            Date date = ((Timestamp) Objects.requireNonNull(mappy.get("day"))).toDate();
            //noinspection ConstantConditions
            long hours = (long) mappy.get("hours");
            sum += hours;
            results.add(MyListData.construct(date, hours));
        }
        ProjectManager.projectManager.totalHours = sum;
        return results;
    }

    private void executeUpdate(ArrayList<Object> documentSnapshot) {
        Task<Void> task = documentReference.update(MainActivity.Fields.Dates, documentSnapshot);
        while (!task.isComplete()) {
        }
    }

    private ArrayList<Object> executeGet() {
        Task<DocumentSnapshot> task = documentReference.get();
        while (!task.isComplete()) {
        }
        return (ArrayList<Object>) Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getData()).get(MainActivity.Fields.Dates);
    }
}
