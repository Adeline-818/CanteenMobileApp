package com.canteen.canteenapp.ui_SellerMenu;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.canteen.canteenapp.LoginActivity;
import com.canteen.canteenapp.R;
import com.canteen.canteenapp.controller.SellerPrespectiveController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ProfileManagmentFragment extends Fragment {


    EditText fname , lname , namePlace  ,  openTime , closeTime , oprOpenTime , oprCloseTime ;
    TextView UID , token , collegeName;
    Button saveChanges , logOut;
    DatabaseReference db;
    Date openingTime , closingTime , oprOperningTime , oprClosingTime;
    LinearLayout po;
    String college;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FirebaseAuth test = FirebaseAuth.getInstance();
        View root = inflater.inflate(R.layout.fragment_profile_seller, container, false);

        oprCloseTime = root.findViewById(R.id.operationaalclosingTimeSeller);
        oprOpenTime = root.findViewById(R.id.operationaaopeningTimeSeller);

        fname = root.findViewById(R.id.fNameSeller);
        lname = root.findViewById(R.id.lNameSeller);
        namePlace = root.findViewById(R.id.NameOfSeller);
        openTime = root.findViewById(R.id.openingTimeSeller);
        closeTime = root.findViewById(R.id.closingTimeSeller);


        collegeName = root.findViewById(R.id.collegeName);
        UID = root.findViewById(R.id.sellerUID);
        token =  root.findViewById(R.id.sellerToken);
        saveChanges = root.findViewById(R.id.saveChangesSeller);

        db = FirebaseDatabase.getInstance().getReference("Seller/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fname.setText(snapshot.child("fName").getValue(String.class));
                lname.setText(snapshot.child("lName").getValue(String.class));
                namePlace.setText(snapshot.child("nameStall").getValue(String.class));
                collegeName.setText(snapshot.child("college").getValue(String.class));

                openTime.setText( new SimpleDateFormat("hh:mm aa").format(new Time(snapshot.child("timeOpen").getValue(Long.class))));
                closeTime.setText( new SimpleDateFormat("hh:mm aa").format(new Time(snapshot.child("timeClose").getValue(Long.class))));
                openingTime = new Time(snapshot.child("timeOpen").getValue(Long.class));
                closingTime = new Time(snapshot.child("timeClose").getValue(Long.class));


                token.setText(snapshot.child("token").getValue(String.class));
                UID.setText(snapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        college = LoginActivity.getDefaults("college" , getContext());

        System.out.println(college);
        db = FirebaseDatabase.getInstance().getReference("Foods/"+college);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                oprCloseTime.setText( new SimpleDateFormat("hh:mm aa").format(new Time(snapshot.child("oprClosing").getValue(Long.class))));
                oprOpenTime.setText( new SimpleDateFormat("hh:mm aa").format(new Time(snapshot.child("oprOpening").getValue(Long.class))));
                oprOperningTime = new Time(snapshot.child("oprOpening").getValue(Long.class));
                oprClosingTime = new Time(snapshot.child("oprClosing").getValue(Long.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        openTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity() , new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Date time = new  Time(hourOfDay , minute , 00);
                        openingTime = time;
                        openTime.setText(new SimpleDateFormat("hh:mm aa").format(time));
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        closeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity() , new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Date time = new  Time(hourOfDay , minute , 00);
                        closingTime = time;
                        closeTime.setText( new SimpleDateFormat("hh:mm aa").format(time));
                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });

        oprOpenTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity() , new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Date time = new  Time(hourOfDay , minute , 00);
                        oprOperningTime = time;
                        oprOpenTime.setText( new SimpleDateFormat("hh:mm aa").format(time));
                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });

        oprCloseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity() , new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Date time = new  Time(hourOfDay , minute , 00);
                        oprClosingTime = time;
                        oprCloseTime.setText( new SimpleDateFormat("hh:mm aa").format(time));
                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });


        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = FirebaseDatabase.getInstance().getReference("Seller/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
                db.child("fName").setValue(fname.getText().toString());
                db.child("lName").setValue(lname.getText().toString());
                db.child("nameStall").setValue(namePlace.getText().toString());
                db.child("timeClose").setValue(closingTime.getTime());
                db.child("timeOpen").setValue(openingTime.getTime());

                db = FirebaseDatabase.getInstance().getReference("Foods/"+college);
                db.child("oprClosing").setValue(oprOperningTime.getTime());
                db.child("oprOpening").setValue(oprClosingTime.getTime());


            }
        });


        return root;
    }
}
