package com.canteen.canteenapp.ui_StudentMenu.insideStatusStudentFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canteen.canteenapp.R;
import com.canteen.canteenapp.controller.StudentPrespectiveController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class completedOrderTabs extends Fragment {


    RecyclerView orderReady;
    TextView  nocomplete;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_status_order_ready_tab, container, false);
        orderReady = root.findViewById(R.id.orderReadyStudent);
        nocomplete = root.findViewById(R.id.noComplete);

        orderReady.setHasFixedSize(true);
        orderReady.setLayoutManager(new LinearLayoutManager(getActivity()));

        StudentPrespectiveController cont = new StudentPrespectiveController(getActivity());
        cont.fillUpRecycleCompleteProccess(orderReady , nocomplete );





        return root;
    }
}
