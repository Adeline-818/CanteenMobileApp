package com.canteen.canteenapp.ui_StudentMenu.insideCheckOutStudentFragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.canteen.canteenapp.R;
import com.canteen.canteenapp.controller.StudentPrespectiveController;
import com.google.firebase.functions.FirebaseFunctions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CheckOutTabs extends Fragment {

    private FirebaseFunctions mFunctions;
    RecyclerView recyclerView;
    Button pay;
    TextView text , totalPrice;
    StudentPrespectiveController order;
    int chooseMethod;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mFunctions = FirebaseFunctions.getInstance();

        View root = inflater.inflate(R.layout.fragment_checkout_student_tab, container, false);
        recyclerView = root.findViewById(R.id.checkOutBasket);
        pay = root.findViewById(R.id.checkOutButton);
        text = root.findViewById(R.id.textView5);
        totalPrice = root.findViewById(R.id.TotlPrice);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        order = new StudentPrespectiveController(getActivity());
        order.fillUpBasketRecycle(recyclerView , text , totalPrice);

//        final double total = Double.parseDouble(totalPrice.getText().toString());


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAlertDialog();

            }
        });

        return root;
    }


    private void showAlertDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle("Choose payment method");
        String[] items = {"Cash","Online Banking"};
        int checkedItem = 1;
        chooseMethod = 2;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        chooseMethod = 1;
                        Toast.makeText(getActivity(), "Pay with cash , please prepare your cash", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        chooseMethod = 2;
                        Toast.makeText(getActivity(), "Pay with Onlnine Banking , Please prepare your card", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        alertDialog.setCancelable(true);


        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                order.pushNofiticationToSeller(getActivity() , chooseMethod );

            }
        });

        alertDialog.setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.setCancelable(true);
            }
        });

        final AlertDialog alert = alertDialog.create();
        alert.show();
    }
}
