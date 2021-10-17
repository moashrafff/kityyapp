package com.ashraff.cats_final;

import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.catViewHolder>  {

     private ArrayList<cat> cats ;
     private onCardItemClickListener listener;

    public recyclerViewAdapter(ArrayList<cat> cats , onCardItemClickListener listener) {
        this.cats = cats;
        this.listener = listener;
    }

    public ArrayList<cat> getCats() {
        return cats;
    }

    public void setCats(ArrayList<cat> cats) {
        this.cats = cats;
    }

    @NonNull
    @Override
    public catViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_card_view,null,false);
        catViewHolder catViewHolder = new catViewHolder(v);
        return catViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull catViewHolder holder, int position) {
        cat c = cats.get(position);
        if (c.getImage() != null && !c.getImage().isEmpty())
            holder.imageView.setImageURI(Uri.parse(c.getImage()));
        else
            holder.imageView.setImageResource(R.drawable.cat_1);
        holder.name.setText(c.getName());
        holder.color.setText(c.getColor());
        holder.color.setTextColor(Color.parseColor(c.getColor()));
        holder.eyeColor.setText(c.getEyeColor());
        holder.imageView.setTag(c.getId());



    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    class catViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name , color , eyeColor;

        public catViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cat_img);
            name = itemView.findViewById(R.id.cat_name);
            color= itemView.findViewById(R.id.cat_color);
            eyeColor = itemView.findViewById(R.id.cat_eye_color);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (int) imageView.getTag();
                    listener.onClick(id);

                }
            });

        }
    }

}
