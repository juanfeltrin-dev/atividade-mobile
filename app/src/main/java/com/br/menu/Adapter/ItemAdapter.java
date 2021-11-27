package com.br.menu.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.br.menu.Model.ItemModel;
import com.br.menu.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<ItemModel> items;
    private boolean isConnected;

    public ItemAdapter(ArrayList<ItemModel> data, @Nullable boolean isConnected) {
        this.items = data;
        this.isConnected = isConnected;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView photo;
        private final TextView title;
        private final TextView description;
        private final TextView gluten;
        private final TextView calorie;
        private final TextView price;

        public ViewHolder(View v) {
            super(v);

            this.photo = (ImageView) v.findViewById(R.id.item_photo);
            this.title = (TextView) v.findViewById(R.id.item_title);
            this.description = (TextView) v.findViewById(R.id.item_description);
            this.gluten = (TextView) v.findViewById(R.id.item_gluten);
            this.calorie = (TextView) v.findViewById(R.id.item_calorie);
            this.price = (TextView) v.findViewById(R.id.item_price);
        }

        public ImageView getPhoto() { return photo; }
        public TextView getTitle() { return title; }
        public TextView getDescription() { return description; }
        public TextView getGluten() { return gluten; }
        public TextView getCalorie() { return calorie; }
        public TextView getPrice() { return price; }
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.getPhoto().setImageBitmap(items.get(position).getPhotoBitmap());
        viewHolder.getTitle().setText(items.get(position).getTitle());
        viewHolder.getDescription().setText(String.valueOf(items.get(position).getDescription()));
        viewHolder.getGluten().setText(
                String.valueOf(items.get(position).getGluten() ? "Contém glúten" : "Sem glutén")
        );
        viewHolder.getCalorie().setText(String.valueOf(items.get(position).getCalorie() + "  kcal"));
        viewHolder.getPrice().setText(
            this.isConnected ?
                String.valueOf(items.get(position).getPrice()) :
                    "a consultar"
        );
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
