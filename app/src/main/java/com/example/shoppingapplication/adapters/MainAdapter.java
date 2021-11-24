package com.example.shoppingapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoppingapplication.R;
import com.example.shoppingapplication.model.BasicItem;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<BasicItem> basicItemList;
    private Context context;
    private OnItemClick onItemClick;

    public MainAdapter( Context context, OnItemClick onItemClick) {
        this.context = context;
        this.onItemClick = onItemClick;
    }

    public void setBasicItemList(List<BasicItem> basicItemList) {
        this.basicItemList = basicItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.big_item_mainrec,parent,false);
        return new ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BasicItem item = basicItemList.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms( new CenterCrop(), new RoundedCorners(25));
        Glide.with(context).load(item.getImageUrl()).apply(requestOptions).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(basicItemList==null)return 0;
        return basicItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView imageView = itemView.findViewById(R.id.big_item_image);

        public ViewHolder(@NonNull View itemView,OnItemClick touchListener) {
            super(itemView);
            onItemClick = touchListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClick.itemClicked(basicItemList.get(getAdapterPosition()).getId());
        }
    }

    public interface OnItemClick{
        void itemClicked(int id);
    }

}
