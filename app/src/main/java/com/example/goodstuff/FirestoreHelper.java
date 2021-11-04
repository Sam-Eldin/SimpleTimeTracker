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
    public final FirebaseFirestore database; // firestore connection
    private final DocumentReference documentReference; // Sam document reference
    public ArrayList<Object> objectArrayList; // Dates array

    public FirestoreHelper() {
        this.database = FirebaseFirestore.getInstance();
        this.documentReference = database.collection(Collections.USERS).document(Users.Sam);
        objectArrayList = executeGet();
    }


    public void addDay(Double hours) {
        Map<Object, Object> map = new HashMap<>();
        map.put(DatesFields.HOURS, hours);
        map.put(DatesFields.DAY, new Date());
        objectArrayList.add(0, map);
        executeUpdate(objectArrayList);
        System.out.println("\n\n--------------------------\nAdded\n------------------------------\n\n");
    }

    public void updateDay(int index, Double newHours) {
        Map<Object, Object> map = (Map<Object, Object>) objectArrayList.get(index);
        map.put("hours", newHours);
        executeUpdate(objectArrayList);
        System.out.println("\n\n--------------------------\nUpdated\n------------------------------\n\n");
    }

    public void remove(int index) {
        objectArrayList.remove(index);
        executeUpdate(objectArrayList);
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
            double hours = (Double) mappy.get("hours");
            sum += hours;
            results.add(MyListData.construct(date, hours));
        }
        ProjectManager.projectManager.totalHours = sum;
        return results;
    }

    private void executeUpdate(ArrayList<Object> documentSnapshot) {
        Task<Void> task = documentReference.update(Fields.Dates, documentSnapshot);
        while (!task.isComplete()) {
        }
    }

    private ArrayList<Object> executeGet() {
        Task<DocumentSnapshot> task = documentReference.get();
        while (!task.isComplete()) {
        }
        return (ArrayList<Object>) Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getData()).get(Fields.Dates);
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

    public static class DatesFields {
        public static final String DAY = "day";
        public static final String HOURS = "hours";

    }
}


