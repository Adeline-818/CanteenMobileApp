package com.canteen.canteenapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;


import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.canteen.canteenapp.OpenFoodDetailsActivity;
import com.canteen.canteenapp.R;
import com.canteen.canteenapp.models.FoodDto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class FoodListStudentHomeAdapter extends RecyclerView.Adapter<FoodListStudentHomeAdapter.Holder> implements Filterable {

    Context context;
    List<FoodDto> list;
    List<FoodDto> Alllist;


    public FoodListStudentHomeAdapter(Context context , List<FoodDto> list) {
        this.context = context;
        this.list = list;
        this.Alllist =  list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_for_student_search, parent, false);

        return new Holder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        holder.name.setText(list.get(position).getName());
        DecimalFormat precision = new DecimalFormat("0.00");


        holder.price.setText("RM "+precision.format(list.get(position).getPrice()));
        holder.description.setText(list.get(position).getDetails());
        holder.progress.setVisibility(View.VISIBLE);

        if (list.get(position).getSpecial()){
            holder.special.setVisibility(View.VISIBLE);
        }else{
            holder.special.setVisibility(View.INVISIBLE);

        }



        Glide.with(holder.image.getContext()).load(list.get(position).getURLPic())
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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.itemView.getContext(), OpenFoodDetailsActivity.class);
                i.putExtra("name", list.get(position).getName());
                i.putExtra("price", list.get(position).getPrice());
                i.putExtra("key", list.get(position).getKey());
                i.putExtra("details", list.get(position).getDetails());
                i.putExtra("urlPic", list.get(position).getURLPic());
                i.putExtra("special" , list.get(position).getSpecial());
                i.putExtra("owner" , list.get(position).getOwner());
                i.putExtra("category" , list.get(position).getCategory());




                holder.itemView.getContext().startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<FoodDto> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(Alllist);
            }else{
                for(FoodDto dto : Alllist){
                    if(dto.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(dto);
                    }
                }
            }

            FilterResults res = new FilterResults();
            res.values = filteredList;


            return res;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((Collection<? extends FoodDto>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public void turnSpecialOn(){
        list.clear();
        List<FoodDto> filteredList = new ArrayList<>();

        for(FoodDto dt : Alllist){
            if(dt.getSpecial()){
                System.out.println("true");
                filteredList.add(dt);
            }
        }
        list.addAll(filteredList);
        notifyDataSetChanged();
    }

    public void turnSpecialOff(){
        list.clear();
        list.addAll(Alllist);
        notifyDataSetChanged();

    }

    public static class Holder extends RecyclerView.ViewHolder{

        TextView name, price, description , special;
        ImageView image;
        ProgressBar progress;

        public Holder(@NonNull View itemView) {
            super(itemView);

            special = itemView.findViewById(R.id.specialForToday);

            image = itemView.findViewById(R.id.imgFoodFromFoodList);
            name = itemView.findViewById(R.id.nameOfFood);
            price = itemView.findViewById(R.id.priceFood);
            description = itemView.findViewById(R.id.descriptionFood);
            progress = itemView.findViewById(R.id.progressBarFoodStudent);
        }
    }

}
