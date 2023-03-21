package com.canteen.canteenapp.ui_StudentMenu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.canteen.canteenapp.controller.StudentPrespectiveController;
import com.canteen.canteenapp.Adapters.FoodListStudentHomeAdapter;

import com.canteen.canteenapp.R;
import com.canteen.canteenapp.models.FoodDto;
import com.canteen.canteenapp.ui_StudentMenu.insideHomeStudentFragment.Drinks;
import com.canteen.canteenapp.ui_StudentMenu.insideHomeStudentFragment.Roti;
import com.canteen.canteenapp.ui_StudentMenu.insideHomeStudentFragment.Home;
import com.canteen.canteenapp.ui_StudentMenu.insideHomeStudentFragment.RiceSets;
import com.canteen.canteenapp.ui_StudentMenu.insideHomeStudentFragment.Special;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class HomeStudentFragment extends Fragment {


//    FoodListStudentHomeAdapter adapter;
    private List<FoodDto> itemList;
    private RecyclerView view;
    private StudentPrespectiveController order;
    private FoodListStudentHomeAdapter adapter;
    private FirebaseRecyclerOptions<FoodDto> options;
    private TextView txt;
    private SearchView search;
    private Switch spc;
//    private SharedPreferences sharedPreferences;

    private TabLayout tabLayout;
    private ViewPager viewPager;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_student, container, false);


        viewPager = root.findViewById(R.id.ViewPagerForHomeSudent);
        tabLayout = root.findViewById(R.id.tabLayoutFoeHomeStudent);
        TabAdapterForHomeFragment adapter = new TabAdapterForHomeFragment(getActivity().getSupportFragmentManager());
        adapter.addFragment(new Home(), "All");
        adapter.addFragment(new Special(), "Special");
        adapter.addFragment(new Roti(), "Roti Sets");
        adapter.addFragment(new RiceSets(), "Rice Sets");
        adapter.addFragment(new Drinks(), "Drinks");



        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);






//        view = root.findViewById(R.id.foodList);
//        txt = root.findViewById(R.id.textView3);
//        search = root.findViewById(R.id.searchView);
//        spc = root.findViewById(R.id.switchSpecial);
//
//        StudentPrespectiveController order = new StudentPrespectiveController(getContext());
//        view.setHasFixedSize(true);
//        view.setLayoutManager(new LinearLayoutManager(getContext()));
//        spc.setVisibility(View.GONE);
//        order.retriveFoodFromSeller(view , getActivity() , txt , search , spc);


        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
//        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        super.onAttach(context);
    }
}