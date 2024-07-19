package com.example.medtrx;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorSignUp extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectmedtrx-52121-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_sign_up);

        final EditText firstName = findViewById(R.id.FirstName);
        final EditText lastName = findViewById(R.id.LastName);
        final EditText SAddress = findViewById(R.id.AddresStudent);
        final EditText EAddress = findViewById(R.id.AddressEmail);
        final EditText IdNumber = findViewById(R.id.EmailAddress);
        final EditText Phone = findViewById(R.id.PhoneNumber);
        final EditText Password = findViewById(R.id.PassWord);
        final EditText CPassword = findViewById(R.id.ConfirmPassword);
        final Button signUp= findViewById(R.id.Register);
        final TextView login=findViewById(R.id.Btn2);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String firstNameTxt = firstName.getText().toString();
                final String lastNameTxt = lastName.getText().toString();
                final String StudentTxt = SAddress.getText().toString();
                final String AddressTxt = EAddress.getText().toString();
                final String IdNumberTxt = IdNumber.getText().toString();
                final String PhoneTxt = Phone.getText().toString();
                final String PasswordTxt = Password.getText().toString();
                final String CPasswordTxt = CPassword.getText().toString();

                if(firstNameTxt.isEmpty() || lastNameTxt.isEmpty() || IdNumberTxt.isEmpty() || StudentTxt.isEmpty() || AddressTxt.isEmpty() ||
                        PhoneTxt.isEmpty()) {
                    Toast.makeText(DoctorSignUp.this,"This field is required", Toast.LENGTH_SHORT).show();

                }
                else if(!PasswordTxt.equals(CPasswordTxt)){
                    Toast.makeText(DoctorSignUp.this,"Passwords do not match!", Toast.LENGTH_SHORT).show();
                }

                else{

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(IdNumberTxt)){
                                Toast.makeText(DoctorSignUp.this, "ID number is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("users").child(IdNumberTxt).child("firstName").setValue(firstNameTxt);
                                databaseReference.child("users").child(IdNumberTxt).child("lastName").setValue(lastNameTxt);
                                databaseReference.child("users").child(IdNumberTxt).child("Address").setValue(StudentTxt);
                                databaseReference.child("users").child(IdNumberTxt).child("Email Address").setValue(AddressTxt);
                                databaseReference.child("users").child(IdNumberTxt).child("Phone").setValue(PhoneTxt);
                                databaseReference.child("users").child(IdNumberTxt).child("Password").setValue(PasswordTxt);

                                Toast.makeText(DoctorSignUp.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}


