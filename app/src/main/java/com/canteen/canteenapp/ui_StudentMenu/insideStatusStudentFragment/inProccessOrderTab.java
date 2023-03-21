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

public class inProccessOrderTab extends Fragment {

    RecyclerView orderProccess;
    TextView noorder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_status_student_ordernproccess_tab, container, false);
        orderProccess = root.findViewById(R.id.orderProcessStudent);
        noorder = root.findViewById(R.id.noOrders);

        orderProccess.setHasFixedSize(true);
        orderProccess.setLayoutManager(new LinearLayoutManager(getActivity()));

        StudentPrespectiveController cont = new StudentPrespectiveController(getActivity());
        cont.fillUpRecycleOrderProccess(orderProccess , noorder );





        return root;
    }
}
