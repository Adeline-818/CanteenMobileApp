package com.canteen.canteenapp.ui_StudentMenu.insideCheckOutStudentFragment;

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

public class InvoicesTabs extends Fragment {


    private RecyclerView invoice;
    private TextView invoiceText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_checkout_invoice_tab, container, false);
        invoice = root.findViewById(R.id.invoiceOrder);
        invoiceText = root.findViewById(R.id.noInvoiceText);

        invoice.setHasFixedSize(true);
        invoice.setLayoutManager(new LinearLayoutManager(getContext()));
        new StudentPrespectiveController(getActivity()).populateRecyleViewInvoice(invoice , invoiceText);




        return root;
    }
}
