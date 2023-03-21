package com.canteen.canteenapp.Adapters;

import android.content.Context;
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
import com.canteen.canteenapp.R;
import com.canteen.canteenapp.models.FoodDto;
import com.canteen.canteenapp.models.checkOutBasket;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class FoodListStudentOrderReadyNProcessAdapter extends RecyclerView.Adapter<FoodListStudentOrderReadyNProcessAdapter.Holder>{


    Context context;
    List<checkOutBasket> list;

    public FoodListStudentOrderReadyNProcessAdapter(Context context , List<checkOutBasket> list) {
        System.out.println(list.size());
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_for_student_status, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        holder.quantity.setText(list.get(position).getBasketKeyDif().substring(0,8));
        holder.noOrder.setText(" X"+list.get(position).getNoorder());

        System.out.println(list.get(position).getFood().getName());
        holder.name.setText(list.get(position).getFood().getName());
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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        TextView name , noOrder , quantity;
        ImageView image;
        ProgressBar progress;


        public Holder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageViewStudentStatus);
            name = itemView.findViewById(R.id.nameOfFoodForStatusStudent);
            progress = itemView.findViewById(R.id.progressBarStatusStudent);
            noOrder = itemView.findViewById(R.id.noOrderStatus);
            quantity = itemView.findViewById(R.id.foodQuantityStatus);


        }
    }
}
