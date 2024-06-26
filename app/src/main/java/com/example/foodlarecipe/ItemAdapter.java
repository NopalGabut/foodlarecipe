package com.example.foodlarecipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ItemData> values;
    private LayoutInflater inflater;

    public ItemAdapter(Context context, ArrayList<ItemData> values) {
        this.context = context;
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemData data = values.get(position);
        holder.nameText.setText(data.itemName);
        holder.asalText.setText(data.itemAsl);
        Picasso.get().load(data.getItemThumbnail()).into(holder.bookCoverImage);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameText;
        TextView asalText;
        ImageView bookCoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.text1);
            asalText = itemView.findViewById(R.id.text2);
            bookCoverImage = itemView.findViewById(R.id.bookCoverImage);
        }
    }
}
