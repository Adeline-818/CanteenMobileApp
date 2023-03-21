package com.canteen.canteenapp.ui_SellerMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canteen.canteenapp.R;
import com.canteen.canteenapp.controller.SellerPrespectiveController;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderManagmentFragment extends Fragment {


    RecyclerView recyclerView;
    TextView noOrd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_order_seller, container, false);

        recyclerView = root.findViewById(R.id.orderForSeller);
        SellerPrespectiveController food = new SellerPrespectiveController(getActivity());
        noOrd = root.findViewById(R.id.noOrder);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        food.fillupRecycleOrder(recyclerView , getActivity(),noOrd);




        return root;
    }




}
