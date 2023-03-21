package com.canteen.canteenapp.ui_StudentMenu.insideHomeStudentFragment;

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

public class Drinks extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_student_drinks_tab, container, false);
        TextView noSpecialMenu = root.findViewById(R.id.NoDrinks);
        RecyclerView recycle = root.findViewById(R.id.recycleDrinksSet);

        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));

        StudentPrespectiveController po = new StudentPrespectiveController(getActivity());
        po.retriveFoodFromSellerDrink(recycle , getActivity() , noSpecialMenu);

        return root;
    }
}
