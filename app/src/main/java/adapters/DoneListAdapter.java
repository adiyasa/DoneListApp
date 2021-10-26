package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nauval.donelistapp.R;
import com.nauval.donelistapp.activities.AddActivity;

import java.util.ArrayList;

import DAO.DAODoneTask;
import models.DoneTaskModel;
import viewholders.DoneListVH;

public class DoneListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    ArrayList<DoneTaskModel> taskModelArrayList = new ArrayList<>();

    public DoneListAdapter(Context context){
        this.context = context;
    }
    public void setItem(ArrayList<DoneTaskModel> doneTaskModels){
        taskModelArrayList.addAll(doneTaskModels);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_done_list,parent,false);
        return new DoneListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DoneListVH vh = (DoneListVH)holder;
        DoneTaskModel model = taskModelArrayList.get(position);
        vh.tv_list.setText(model.getTask());
        vh.but_list.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context,vh.but_list);
            popupMenu.inflate(R.menu.menu_list);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.menu_edit:
                        Intent intent = new Intent(context, AddActivity.class);
                        intent.putExtra("EDIT", model);
                        context.startActivity(intent);
                        break;
                    case R.id.menu_remove:
                        DAODoneTask dao=new DAODoneTask();
                        dao.remove(model.getUid()).addOnSuccessListener(suc->
                        {
                            Toast.makeText(context, "Task is removed", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(holder.getAdapterPosition());
                            taskModelArrayList.remove(model);
                        }).addOnFailureListener(er->
                                Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show());
                        break;
                }return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return taskModelArrayList.size();
    }
}
