package com.canteen.canteenapp.ui_StudentMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canteen.canteenapp.R;
import com.canteen.canteenapp.controller.StudentPrespectiveController;
import com.canteen.canteenapp.ui_StudentMenu.insideCheckOutStudentFragment.CheckOutTabs;
import com.canteen.canteenapp.ui_StudentMenu.insideCheckOutStudentFragment.InvoicesTabs;
import com.canteen.canteenapp.ui_StudentMenu.insideStatusStudentFragment.completedOrderTabs;
import com.canteen.canteenapp.ui_StudentMenu.insideStatusStudentFragment.inProccessOrderTab;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class StatusStudentFragment extends Fragment {



    private TabAdapterForStatusFragment adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_status_student, container, false);

        viewPager = root.findViewById(R.id.ViewPagerForStatusSudent);
        tabLayout = root.findViewById(R.id.tabLayoutForStatusStudent);
        adapter = new TabAdapterForStatusFragment(getActivity().getSupportFragmentManager());
        adapter.addFragment(new completedOrderTabs(), "Ready");
        adapter.addFragment(new inProccessOrderTab(), "Process");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);







//        View root = inflater.inflate(R.layout.fragment_status_student, container, false);
//        orderProccess = root.findViewById(R.id.orderProcessStudent);
//        orderReady = root.findViewById(R.id.orderReadyStudent);
//        noorder = root.findViewById(R.id.noOrders);
//        nocomplete = root.findViewById(R.id.noComplete);
//
//        orderProccess.setHasFixedSize(true);
//        orderProccess.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        orderReady.setHasFixedSize(true);
//        orderReady.setLayoutManager(new LinearLayoutManager(getActivity()));

//        StudentPrespectiveController cont = new StudentPrespectiveController(getActivity());
//        cont.fillUpRecycleOrderProccess(orderProccess , noorder );
//        cont.fillUpRecycleCompleteProccess(orderReady , nocomplete );



        return root;
    }
}
