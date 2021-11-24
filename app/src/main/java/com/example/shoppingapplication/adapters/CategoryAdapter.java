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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoppingapplication.R;
import com.example.shoppingapplication.model.Item;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> categoryItems;
    private final Context context;
    private CategoryClicked categoryClicked;
    private int type;

    public CategoryAdapter(Context context, CategoryClicked categoryClicked,int type) {
        this.context = context;
        this.categoryClicked = categoryClicked;
        this.type = type;
    }

    public void setCategoryItems(List<Item> categoryItems) {
        this.categoryItems = categoryItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(type==1) {
            View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
            return new ViewHolder(view, categoryClicked);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.new_item_item, parent, false);
            return new SecondViewHolder(view,categoryClicked);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = categoryItems.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms( new CenterCrop(), new RoundedCorners(3));
        if(type==1) {
            ViewHolder viewHolder = (ViewHolder)holder;
            viewHolder.categoryName.setText(item.getName());
            Glide.with(context).load(item.getImageUrl()).apply(requestOptions).into(viewHolder.categoryImage);
        }else {

            SecondViewHolder viewHolder = (SecondViewHolder)holder;
            viewHolder.categoryName.setText(item.getName());
            Glide.with(context).load(item.getImageUrl()).apply(requestOptions).into(viewHolder.categoryImage);
        }

    }




    @Override
    public int getItemCount() {
        if(categoryItems==null)return 0;
        return categoryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView categoryImage = itemView.findViewById(R.id.categoryItemPhoto);
        private final TextView categoryName = itemView.findViewById(R.id.categoryItemName);

        public ViewHolder(@NonNull View itemView, CategoryClicked touchListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            categoryClicked = touchListener;
        }

        @Override
        public void onClick(View v) {
            categoryClicked.onCategoryClicked(categoryItems.get(getAdapterPosition()).getName());
        }
    }

    public class SecondViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView categoryImage = itemView.findViewById(R.id.newItemPhoto);
        private final TextView categoryName = itemView.findViewById(R.id.newItemName);

        public SecondViewHolder(@NonNull View itemView, CategoryClicked touchListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            categoryClicked = touchListener;
        }

        @Override
        public void onClick(View v) {
            categoryClicked.onNewClicked(categoryItems.get(getAdapterPosition()).getId());
        }
    }

    public interface CategoryClicked{
        void onCategoryClicked(String name);
        void onNewClicked(int position);
    }
}