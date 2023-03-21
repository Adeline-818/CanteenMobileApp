package com.canteen.canteenapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.canteen.canteenapp.EditFoodInBasketActivity;
import com.canteen.canteenapp.OpenFoodDetailsActivity;
import com.canteen.canteenapp.R;
import com.canteen.canteenapp.controller.StudentPrespectiveController;
import com.canteen.canteenapp.models.FoodDto;
import com.canteen.canteenapp.models.checkOutBasket;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class FoodListStudentCheckOutAdapter extends RecyclerView.Adapter<FoodListStudentCheckOutAdapter.Holder> {

    Context context;
    List<checkOutBasket> list;

    public FoodListStudentCheckOutAdapter(Context context , List<checkOutBasket> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FoodListStudentCheckOutAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_checkout, parent, false);

        return new FoodListStudentCheckOutAdapter.Holder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        holder.name.setText(list.get(position).getFood().getName());

        DecimalFormat precision = new DecimalFormat("0.00");

        holder.price.setText("RM "+precision.format(list.get(position).getFood().getPrice())+" x"+list.get(position).getUnpaidBasketDto().getNoorder());

        Glide.with(holder.image.getContext()).load(list.get(position).getFood().getURLPic())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progress.setVisibility(View.GONE);

                        return false;
                    }

                }).into(holder.image);


//        holder.deleteOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new StudentPrespectiveController(view.getContext()).deleteItemInBasket(list.get(position).getBasketKey());
//                Toast.makeText(context, list.get(position).getFood().getName()+" has been deleted from your basket", Toast.LENGTH_LONG).show();
//            }
//        });

        holder.comment.setText(list.get(position).getUnpaidBasketDto().getComment());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.itemView.getContext() , EditFoodInBasketActivity.class);
                intent.putExtra("details", list.get(position));
                holder.itemView.getContext().startActivity(intent);

            }
        });

    }





    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        TextView name  , price , comment;
        ImageView image;
        Button deleteOrder;
        ProgressBar progress;

        public Holder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.checkOutImage);
            name = itemView.findViewById(R.id.foodNameCheckOut);
            price = itemView.findViewById(R.id.priceFoodInBasket);
//            deleteOrder = itemView.findViewById(R.id.deleteFoodinBasket);
            progress = itemView.findViewById(R.id.progressBarFoodCheckOut);
            comment = itemView.findViewById(R.id.CommentBasket);

        }
    }

}

