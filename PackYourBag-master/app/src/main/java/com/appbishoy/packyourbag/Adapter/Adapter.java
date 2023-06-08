package com.appbishoy.packyourbag.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appbishoy.packyourbag.CheckList;
import com.appbishoy.packyourbag.Constants.MyConstants;
import com.appbishoy.packyourbag.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder>{

    List<String> titles;
    List<Integer> images;
    LayoutInflater inflater;
    Activity activity;

    public Adapter(Context context, List<String> titles, List<Integer> images, Activity activity) {
        this.titles = titles;
        this.images = images;
        this.activity = activity;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.image.setImageResource(images.get(position));
        holder.linearLayout.setAlpha(1.0F);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(activity, "Clicked on card", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(v.getContext(), CheckList.class);
                intent.putExtra(MyConstants.HEADER_SMALL,titles.get(position));

                if(MyConstants.MY_SELECTIONS.equals(titles.get(position))){
                    intent.putExtra(MyConstants.SHOW_SMALL,MyConstants.FALSE_STRING);
                }else {
                    intent.putExtra(MyConstants.SHOW_SMALL,MyConstants.TRUE_STRING);
                }
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;
        LinearLayout linearLayout;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.iv_main_item);
            linearLayout = itemView.findViewById(R.id.linear_layout);
        }
    }
}
