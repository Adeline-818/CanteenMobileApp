package com.canteen.canteenapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.canteen.canteenapp.EditFoodDetailsActivity;
import com.canteen.canteenapp.R;
import com.canteen.canteenapp.models.FoodDto;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class FoodListFromSellerAdapter extends RecyclerView.Adapter<FoodListFromSellerAdapter.ViewHolder> {

    List<FoodDto> list ;
    Context context;

    public FoodListFromSellerAdapter(Context context , List<FoodDto> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_item_for_seller_management, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.foodNam.setText(list.get(position).getName());
        holder.foodPric.setText("RM "+list.get(position).getPrice());
        holder.foodDet.setText(list.get(position).getDetails());
        holder.pr.setVisibility(View.VISIBLE);
        if(list.get(position).getSpecial()){
            holder.special.setVisibility(View.VISIBLE);
        }else{
            holder.special.setVisibility(View.GONE);
        }

        Glide.with(context).load(list.get(position).getURLPic())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.pr.setVisibility(View.GONE);

                        return false;
                    }

        }).into(holder.allMenuImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, EditFoodDetailsActivity.class);
                i.putExtra("name", list.get(position).getName());
                i.putExtra("price", list.get(position).getPrice());
                i.putExtra("key", list.get(position).getKey());
                i.putExtra("details", list.get(position).getDetails());
                i.putExtra("urlPic", list.get(position).getURLPic());
                i.putExtra("special" , list.get(position).getSpecial());
                i.putExtra("category" , list.get(position).getCategory());



                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView foodNam, foodPric, foodDet, price , special;
        ImageView allMenuImage;
        ProgressBar pr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pr = itemView.findViewById(R.id.ProgressBarImageFoodSeller);
            foodNam = itemView.findViewById(R.id.foodName);
            foodPric = itemView.findViewById(R.id.foodPrice);
            foodDet = itemView.findViewById(R.id.foodDetails);
            allMenuImage = itemView.findViewById(R.id.all_menu_image);
            special = itemView.findViewById(R.id.specialText);
        }
    }


}


