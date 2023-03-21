package com.canteen.canteenapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canteen.canteenapp.R;
import com.canteen.canteenapp.models.FoodDto;
import com.canteen.canteenapp.models.Invoice;
import com.canteen.canteenapp.models.InvoiceWithListOfFoodDto;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FoodListStudentInvoiceAdapter extends RecyclerView.Adapter<FoodListStudentInvoiceAdapter.Holder> {


    Context context;
    List<InvoiceWithListOfFoodDto> list;

    public FoodListStudentInvoiceAdapter(Context context , List<InvoiceWithListOfFoodDto>list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_item_for_student_invoice, parent, false);
        return new FoodListStudentInvoiceAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DecimalFormat precision = new DecimalFormat("0.00");


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String millisInString  = dateFormat.format(new Date(list.get(position).getInvoice().getTime()));

        holder.time.setText("Date/Time : "+ millisInString);
        holder.price.setText("Total Price : "+ "RM "+precision.format(list.get(position).getInvoice().getTotalPrice()));

        if(list.get(position).getInvoice().getPaymentMethod() == 1){
            holder.method.setText("Payment : "+ "Cash");
        }else if(list.get(position).getInvoice().getPaymentMethod() == 2){
            holder.method.setText("Payment : "+ "Online Banking");

        }

        holder.orderKey.setText("Order No : "+ list.get(position).getInvoice().getOrderKey().substring(0,8));

        String foods = "";


//        for(FoodDto foodssd :  list.get(position).getFoods()){
//            foods += "\n "+foodssd.getName();
//        }

//        holder.food.setVisibility(View.GONE);

//        holder.food.setText(foods);

        holder.recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));
        holder.recycle.setHasFixedSize(true);
        holder.recycle.setAdapter(new FoodListStudentFoodInsideInvoiceAdapter(context , list.get(position).getFoods()));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        TextView time  , price , method , orderKey;
        RecyclerView recycle;

        public Holder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.timePaymentInvoice);
            price = itemView.findViewById(R.id.pricePaymentInvoice);
            method = itemView.findViewById(R.id.paymentMethod);
            orderKey = itemView.findViewById(R.id.orderKeyInvoice);
            recycle = itemView.findViewById(R.id.listOfFOOD);

        }
    }
}
