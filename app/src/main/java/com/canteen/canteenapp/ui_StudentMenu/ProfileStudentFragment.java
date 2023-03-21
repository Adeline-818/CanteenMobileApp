package com.canteen.canteenapp.ui_StudentMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.canteen.canteenapp.LoginActivity;
import com.canteen.canteenapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileStudentFragment extends Fragment {

    EditText fname , lname , matric ;
    TextView UID , token , collegeName;
    Button saveChanges , logOut;
    DatabaseReference db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile_student, container, false);

        FirebaseAuth test = FirebaseAuth.getInstance();

        fname = root.findViewById(R.id.fNameStudent);
        lname = root.findViewById(R.id.lNameStudent);
        matric = root.findViewById(R.id.studentMatricr);
        UID = root.findViewById(R.id.studentUID);
        token =  root.findViewById(R.id.studentToken);
        saveChanges = root.findViewById(R.id.saveChangesStudent);
        collegeName = root.findViewById(R.id.collegeName);

        String guest = LoginActivity.getDefaults("guest" , getContext());

        if(guest.equals("guest")){
            fname.setEnabled(false);
            lname.setEnabled(false);
            matric.setEnabled(false);
            saveChanges.setEnabled(false);
        }

        db = FirebaseDatabase.getInstance().getReference("Student/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fname.setText(snapshot.child("fName").getValue(String.class));
                lname.setText(snapshot.child("lName").getValue(String.class));
                matric.setText(snapshot.child("matric").getValue(String.class));
                collegeName.setText(snapshot.child("college").getValue(String.class));
                token.setText(snapshot.child("token").getValue(String.class));
                UID.setText(snapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseDatabase.getInstance().getReference("Student/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
                db.child("fName").setValue(fname.getText().toString());
                db.child("lName").setValue(lname.getText().toString());
                db.child("matric").setValue(matric.getText().toString());

            }
        });





        return root;
    }
}
