package com.example.goodstuff;

import android.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
    public ArrayList<MyListData> listdata = new ArrayList<>();

    public MyListAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position > listdata.size() - 1)
            return;
        final MyListData myListData = listdata.get(position);
        holder.Day.setText(myListData.getDay());
        holder.Date.setText(myListData.getDate());
        holder.Hours.setText(String.valueOf(myListData.getHours()));
        holder.index = holder.getAdapterPosition();
        holder.relativeLayout.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Enter Number of hours to add");

            final EditText input = new EditText(view.getContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("Set", (dialog, which) -> {
                String value = input.getText().toString();
                System.out.println(value);
                if (value.isEmpty()) {
                    Toast.makeText(view.getContext(), "Value is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                int inputValue = Integer.parseInt(value);
                int pos = holder.getAdapterPosition();
                ProjectManager.projectManager.updateDay(pos, inputValue);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });
        holder.relativeLayout.setOnLongClickListener(view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Delete Item")
                    .setMessage("Are you sure you want to delete this day?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        ProjectManager.projectManager.removeDay(holder.getAdapterPosition());
                    }).setNegativeButton(android.R.string.no, null).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return this.listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView Day;
        public final TextView Date;
        public final TextView Hours;
        public final RelativeLayout relativeLayout;
        public int index;

        public ViewHolder(View itemView) {
            super(itemView);
            this.Day = itemView.findViewById(R.id.Day);
            this.Date = itemView.findViewById(R.id.Date);
            this.Hours = itemView.findViewById(R.id.Hours);
            this.relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
