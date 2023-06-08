package com.appbishoy.packyourbag.Adapter;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appbishoy.packyourbag.Constants.MyConstants;
import com.appbishoy.packyourbag.Database.RoomDB;
import com.appbishoy.packyourbag.Models.Items;
import com.appbishoy.packyourbag.R;

import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListViewHolder>{

    Context context;
    List<Items> itemsList;
    RoomDB database;
    String show;

    public CheckListAdapter() {
    }

    public CheckListAdapter(Context context, List<Items> itemsList, RoomDB database, String show) {
        this.context = context;
        this.itemsList = itemsList;
        this.database = database;
        this.show = show;
        if(itemsList.size()==0)
            Toast.makeText(context.getApplicationContext(), "Nothing to show", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public CheckListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckListViewHolder(LayoutInflater.from(context).inflate(R.layout.check_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListViewHolder holder, int position) {
        holder.checkBox.setText(itemsList.get(position).getItemName());
        holder.checkBox.setChecked(itemsList.get(position).getChecked());

        if(MyConstants.FALSE_STRING.equals(show)){
            holder.button.setVisibility(View.GONE);
            holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_item));
        }else {
            if(itemsList.get(position).getChecked()){
                holder.layout.setBackgroundColor(Color.parseColor("#8e546f"));
            }else{
                holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_item));
            }
        }


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean check = holder.checkBox.isChecked();
                database.mainDao().checkUncheck(itemsList.get(position).getID(),check);
                if(MyConstants.FALSE_STRING.equals(show)){
                    itemsList = database.mainDao().getAllSelected(true);
                    notifyDataSetChanged();
                }else {
                    itemsList.get(position).setChecked(check);
                    notifyDataSetChanged();
                    Toast toastMessage = null;
                    if(toastMessage!=null)
                    {
                        toastMessage.cancel();
                    }
                    if(itemsList.get(position).getChecked()){
                        toastMessage = Toast.makeText(context,"( "+holder.checkBox.getText()+" ) Packed",Toast.LENGTH_LONG);
                    }else {
                        toastMessage = Toast.makeText(context,"( "+holder.checkBox.getText()+" ) Un-Packed",Toast.LENGTH_LONG);
                    }
                    toastMessage.show();
                }
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete ( "+itemsList.get(position).getItemName()+" )" )
                        .setMessage("Are you sure ?")
                        .setPositiveButton("Confire", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database.mainDao().delete(itemsList.get(position));
                                itemsList.remove(itemsList.get(position));
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        }).setIcon(R.drawable.ic_delete_forever)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}

class CheckListViewHolder extends RecyclerView.ViewHolder{

    LinearLayout layout;
    CheckBox checkBox;
    Button button;
    public CheckListViewHolder(@NonNull View itemView) {
        super(itemView);

        layout = itemView.findViewById(R.id.linear_layout_item);
        checkBox = itemView.findViewById(R.id.checkbox);
        button = itemView.findViewById(R.id.btn_delete);
    }
}
