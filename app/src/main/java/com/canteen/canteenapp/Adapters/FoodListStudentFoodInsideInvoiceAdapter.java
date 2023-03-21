package com.canteen.canteenapp.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.canteen.canteenapp.models.FoodWithNumberOrderInvoice;
import com.canteen.canteenapp.models.Invoice;
import com.canteen.canteenapp.models.checkOutBasket;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class FoodListStudentFoodInsideInvoiceAdapter extends RecyclerView.Adapter<FoodListStudentFoodInsideInvoiceAdapter.Holder> {

    Context context;
    List<FoodWithNumberOrderInvoice> list;

    public FoodListStudentFoodInsideInvoiceAdapter(Context context , List<FoodWithNumberOrderInvoice>list){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_item_food_ordered, parent, false);

        return new FoodListStudentFoodInsideInvoiceAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {

        holder.name.setText(list.get(position).getFood().getName() + "x"+list.get(position).getNumeber());

        Glide.with(holder.img.getContext()).load(list.get(position).getFood().getURLPic())
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

                }).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        TextView numeber  , name ;
        ImageView img;
        ProgressBar pr;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageOffFoodInvoice);
            name = itemView.findViewById(R.id.nameOfFoodinvoice);
//            numeber = itemView.findViewById(R.id.numberOfItemFoodInvoice);
            pr = itemView.findViewById(R.id.progressBarLoadImage);


        }
    }
}
