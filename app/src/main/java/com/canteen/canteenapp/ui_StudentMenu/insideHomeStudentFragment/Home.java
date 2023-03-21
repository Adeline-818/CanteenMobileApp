package com.canteen.canteenapp.ui_StudentMenu.insideHomeStudentFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import com.canteen.canteenapp.Adapters.FoodListStudentHomeAdapter;
import com.canteen.canteenapp.R;
import com.canteen.canteenapp.controller.StudentPrespectiveController;
import com.canteen.canteenapp.models.FoodDto;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Home extends Fragment {

    private List<FoodDto> itemList;
    private RecyclerView view;
    private StudentPrespectiveController order;
    private FoodListStudentHomeAdapter adapter;
    private FirebaseRecyclerOptions<FoodDto> options;
    private TextView txt;
    private SearchView search;
    private Switch spc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_student_primary, container, false);
        view = root.findViewById(R.id.foodList);
        txt = root.findViewById(R.id.textView3);
        search = root.findViewById(R.id.searchView);
        spc = root.findViewById(R.id.switchSpecial);

        StudentPrespectiveController order = new StudentPrespectiveController(getContext());
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(getContext()));
        spc.setVisibility(View.GONE);
        order.retriveFoodFromSeller(view , getActivity() , txt , search , spc);



        return root;
    }
}
