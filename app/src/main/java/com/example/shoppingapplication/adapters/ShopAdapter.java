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
import com.example.shoppingapplication.model.ShopItem;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<ShopItem> shopItems;
    private Context context;
    private ShopTouchListener shopTouchListener;

    public ShopAdapter(Context context, ShopTouchListener shopTouchListener) {
        this.context = context;
        this.shopTouchListener = shopTouchListener;
    }

    public void setShopItems(List<ShopItem> shopItems) {
        this.shopItems = shopItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_item,parent,false);
        return new ViewHolder(view, shopTouchListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopItem shopItem = shopItems.get(position);
        holder.itemName.setText(shopItem.getName());
        holder.itemDesc.setText(shopItem.getDesc());
        holder.itemRating.setText(String.valueOf(shopItem.getRatingScore()));
        holder.itemDeliveryTime.setText(shopItem.getDeliveryTime());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms( new CenterCrop(), new RoundedCorners(5));
        Glide.with(context).load(shopItem.getImageUrl()).apply(requestOptions).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(shopItems==null)return 0;
        return shopItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView imageView = itemView.findViewById(R.id.shopItemImage);
        private final TextView itemName = itemView.findViewById(R.id.shopItemName);
        private final TextView itemDesc = itemView.findViewById(R.id.shopItemDescription);
        private final TextView itemRating = itemView.findViewById(R.id.ratingShopItem);
        private final TextView itemDeliveryTime = itemView.findViewById(R.id.shopItemDeliveryTime);

        public ViewHolder(@NonNull View itemView, ShopTouchListener touchListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            shopTouchListener = touchListener;
        }

        @Override
        public void onClick(View v) {
            shopTouchListener.shopClicked(shopItems.get(getAdapterPosition()));
        }
    }

    public interface ShopTouchListener{
        void shopClicked(ShopItem shopItem);
    }
}
