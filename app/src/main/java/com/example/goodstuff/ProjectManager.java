package com.example.goodstuff;


import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class ProjectManager {
    public static final ProjectManager projectManager = new ProjectManager();

    public final MyListAdapter adapter;
    public final FirestoreHelper firestoreHelper;

    TextView textView;
    public double totalHours = 0;
    private ProjectManager() {
        adapter = new MyListAdapter();
        firestoreHelper = new FirestoreHelper();
    }

    public void addNewDay(double hours) {
        Date current = new Date();
        this.firestoreHelper.addDay(hours);
        this.adapter.listdata.add(0, MyListData.construct(current, hours));
        this.adapter.notifyItemInserted(0);
        this.totalHours += hours;
        this.textView.setText(String.format("%.1f",this.totalHours));
    }

    public void updateDay(int index, double hours) {
        this.firestoreHelper.updateDay(index, hours);
        MyListData tempo = this.adapter.listdata.get(index);
        this.totalHours -= tempo.getHours();
        tempo.setHours(hours);
        this.totalHours += tempo.getHours();
        this.adapter.listdata.set(index, tempo);
        this.adapter.notifyItemChanged(index);
        this.textView.setText(String.format("%.1f",this.totalHours));
    }

    public void removeDay(int index) {
        this.totalHours -= this.adapter.listdata.get(index).getHours();
        this.firestoreHelper.remove(index);
        this.adapter.listdata.remove(index);
        this.adapter.notifyItemRemoved(index);
        this.textView.setText(String.format("%.1f",this.totalHours));
    }
}
