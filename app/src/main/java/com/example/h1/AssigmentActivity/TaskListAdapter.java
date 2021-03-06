
package com.example.h1.AssigmentActivity;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.h1.ParseString;
import com.example.h1.R;
import com.example.h1.TaskDB.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.NoteViewHolder> {



    public static class NoteViewHolder extends RecyclerView.ViewHolder{
        private Context context;

        RelativeLayout containerView;
        TextView textView, progress;
        ImageButton plus, minus;
        ImageView checked;

        NoteViewHolder (View view){
            super(view);
            context = view.getContext();

            containerView = view.findViewById(R.id.task_row);
            textView = view.findViewById(R.id.task_row_text);
            plus = view.findViewById(R.id.addV);
            minus = view.findViewById(R.id.remV);
            progress = view.findViewById(R.id.progressPercent);
            checked = view.findViewById(R.id.checkedTask);

            containerView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    final Task current = (Task) containerView.getTag();
                    String spent = String.valueOf(current.spent);
                    String total = String.valueOf(current.total);
                    String message = String.join(" ", spent, "/",  total);
                    Toast.makeText(containerView.getContext(), message, Toast.LENGTH_SHORT).show();
                }
            });

            containerView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final Task current = (Task) containerView.getTag();
                    ShowPopUp popUpClass = new ShowPopUp();
                    popUpClass.showPopupWindow(v, current.id);
                    return true;
                }
            });


            plus.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    final Task current = (Task) containerView.getTag();
                    if (current.spent < current.total) {
                        TaskList.database.taskDAO().add(current.id);
                        int n = TaskList.database.taskDAO().getV(current.id);
                        int t = TaskList.database.taskDAO().getT(current.id);
                        progress.setText(ParseString.parseProgress(n, t));


                        //bar.setProgress(TaskList.database.taskDAO().getV(current.id));
                    }
                    //try{Thread.sleep(500);}catch(InterruptedException e){System.out.println(e);}
                    TaskList.adapter.reload();
                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    final Task current = (Task) containerView.getTag();
                    if (current.spent > 0) {
                        TaskList.database.taskDAO().substract(current.id);
                        int n = TaskList.database.taskDAO().getV(current.id);
                        int t = TaskList.database.taskDAO().getT(current.id);
                        progress.setText(ParseString.parseProgress(n, t));
                    }
                    //try{Thread.sleep(500);}catch(InterruptedException e){System.out.println(e);}
                    TaskList.adapter.reload();
                }
            });

        }
    }

    private List<Task> notes = new ArrayList<>();

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false);
        return new NoteViewHolder (view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Task current = notes.get(position);

        holder.textView.setText(current.contents);

        String p = ParseString.parseProgress(current.spent, current.total);
        holder.progress.setText(p);

        if (current.spent == current.total && current.spent != 0) {
            holder.progress.setVisibility(View.INVISIBLE);
            holder.plus.setVisibility(View.INVISIBLE);
            holder.minus.setVisibility(View.INVISIBLE);
            holder.checked.setVisibility(View.VISIBLE);
            //shit happens
        }

        holder.containerView.setTag(current);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void reload(){
        notes = TaskList.database.taskDAO().getAllNotes();
        notifyDataSetChanged();
        notifyItemRangeChanged(0, notes.size());
    }


}
