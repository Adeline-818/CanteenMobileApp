package com.canteen.canteenapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.canteen.canteenapp.R;
import com.canteen.canteenapp.controller.SellerPrespectiveController;
import com.canteen.canteenapp.models.OrderDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodListSellerOrderAdapter extends RecyclerView.Adapter<FoodListSellerOrderAdapter.Holder> {

    Context context;
    List<OrderDto> list;

    public FoodListSellerOrderAdapter(List<OrderDto> list  , Context context) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public FoodListSellerOrderAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_order, parent, false);

        return new FoodListSellerOrderAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodListSellerOrderAdapter.Holder holder, final int position) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String millisInString  = dateFormat.format(new Date(list.get(position).getBasket().getTime()));

        holder.name.setText(list.get(position).getFood().getName());
        holder.student.setText(list.get(position).getStudentModel().getfName() + " " +list.get(position).getStudentModel().getfName());
        holder.matric.setText(list.get(position).getStudentModel().getMatric());
        holder.comment.setText(list.get(position).getBasket().getComment());
        holder.time.setText(millisInString);
        holder.numberOrder.setText("x"+list.get(position).getBasket().getNoOrder());
        holder.key.setText(list.get(position).getBasket().getOrderKey());

        List<Boolean> add = list.get(position).getBasket().getAddition();
        String addds ="";

        for (int c = 0 ; c<add.size() ; c++){
            switch (c) {
                case 1:
                    if(add.get(1)) addds += "Fish Curry , ";
                    break;
                case 2:
                    if(add.get(2)) addds += "Chicken Sambal , ";
                    break;
                case 3:
                    if(add.get(3)) addds += "Fried Chicken ,";
                    break;
                case 4:
                    if(add.get(5)) addds +=" Fried Fish ,";
                    break;
                case 5:
                    if(add.get(5)) addds +=  "Cabbage stir-fry ,";
                    break;
                case 6:
                    if(add.get(6)) addds += "Brinjal stir-fry  ,";
                    break;
            }
        }

        holder.addition.setText(addds);




        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SellerPrespectiveController(context).doneCreateMeal(list.get(position));
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        TextView name  , student , matric , comment , time  , numberOrder , key , addition;
        Button done;

        public Holder(@NonNull View itemView) {
            super(itemView);

            matric = itemView.findViewById(R.id.matricCust);
            name = itemView.findViewById(R.id.foodNameOrder);
            student = itemView.findViewById(R.id.custID);
            done = itemView.findViewById(R.id.doneOrderButton);
            comment = itemView.findViewById(R.id.commentOrderSeller);
            time = itemView.findViewById(R.id.orderTimeForSeller);
            numberOrder  = itemView.findViewById(R.id.NumberOrderSeller);
            key = itemView.findViewById(R.id.orderKey);
            addition = itemView.findViewById(R.id.additionFoodOrder);
        }
    }
}


