package com.example.shoppingapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapplication.R;
import com.example.shoppingapplication.model.MenuItem;

import java.util.List;

public class BagAdapter extends RecyclerView.Adapter<BagAdapter.ViewHolder> {

    private List<MenuItem> bagList;
    private Context context;

    public BagAdapter( Context context) {
        this.context = context;
    }

    public void setBagList(List<MenuItem> bagList) {
        this.bagList = bagList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bag_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem menuItem = bagList.get(position);
        holder.itemName.setText("1 x "+menuItem.getName());
    }

    @Override
    public int getItemCount() {
        if(bagList!=null)return bagList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName = itemView.findViewById(R.id.bagItemName);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
