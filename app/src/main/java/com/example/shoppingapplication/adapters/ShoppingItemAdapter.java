package com.example.shoppingapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapplication.R;
import com.example.shoppingapplication.model.MenuItem;

import java.util.List;

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemAdapter.ViewHolder> {

    private List<MenuItem> listOfMenuItems;
    private TouchListener touchListener;
    private final Context context;

    public void setListOfMenuItems(List<MenuItem> listOfMenuItems) {
        this.listOfMenuItems = listOfMenuItems;
        notifyDataSetChanged();
    }

    public ShoppingItemAdapter(Context context, TouchListener touchListener) {
        this.touchListener = touchListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item,parent,false);
        return new ViewHolder(view,touchListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MenuItem item = listOfMenuItems.get(position);
        holder.itemDesc.setText(item.getDesc());
        holder.itemName.setText(item.getName());
        String itemPriceS = item.getPrice()+" KM";
        holder.itemPrice.setText(itemPriceS);
        Glide.with(context).load(item.getImageUrl()).into(holder.itemImage);


    }

    @Override
    public int getItemCount() {
        if(listOfMenuItems==null)return 0;
        return listOfMenuItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView itemPrice = itemView.findViewById(R.id.menuItemPrice);
        private final TextView itemName = itemView.findViewById(R.id.menuItemName);
        private final TextView itemDesc = itemView.findViewById(R.id.menuItemDesc);
        private final ImageView itemImage = itemView.findViewById(R.id.menuItemImage);


        public ViewHolder(@NonNull View itemView,TouchListener listener) {
            super(itemView);
            touchListener=listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            touchListener.onClickListener(listOfMenuItems.get(getAdapterPosition()));
            if(touchListener.checkUser()){
                listOfMenuItems.get(getAdapterPosition()).setImageUrl("https://cdn-icons-png.flaticon.com/512/443/443138.png");
                notifyDataSetChanged();
            }
        }
    }

    public interface TouchListener{
        void onClickListener(MenuItem item);
        boolean checkUser();
    }

}
