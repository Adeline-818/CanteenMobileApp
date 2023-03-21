package com.canteen.canteenapp.ui_StudentMenu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.canteen.canteenapp.R;
import com.canteen.canteenapp.controller.StudentPrespectiveController;
import com.canteen.canteenapp.ui_StudentMenu.insideCheckOutStudentFragment.CheckOutTabs;
import com.canteen.canteenapp.ui_StudentMenu.insideCheckOutStudentFragment.InvoicesTabs;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.functions.FirebaseFunctions;

public class CheckoutStudentFragment extends Fragment {

    private TabAdapterForCheckOutFragment adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_checkout_student, container, false);

        viewPager = root.findViewById(R.id.ViewPagerForCheckOutSudent);
        tabLayout = root.findViewById(R.id.tabLayoutForCheckOutStudent);
        TabAdapterForCheckOutFragment adapter = new TabAdapterForCheckOutFragment(getActivity().getSupportFragmentManager());
        adapter.addFragment(new CheckOutTabs(), "Check Out");
        adapter.addFragment(new InvoicesTabs(), "Invoices");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);



        return root;
    }
}





