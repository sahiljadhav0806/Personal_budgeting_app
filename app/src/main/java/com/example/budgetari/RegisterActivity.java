package com.example.budgetari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText textInputEditText2,textInputEditText3,textInputEditText4,textInputEditText5;
    Button button,button1,button2;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
        



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    // upload data to database//
                    String user_Email = textInputEditText3.getText().toString().trim();
                    String user_password = textInputEditText5.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_Email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                sendEmailVerification();


                                Toast.makeText(RegisterActivity.this, "Registration Succesful", Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

    }
    private void setupUIViews() {
        button = (Button)findViewById(R.id.rb);
        button1 = (Button)findViewById(R.id.as);
        button2 = (Button)findViewById(R.id.ev);
        textInputEditText2=(TextInputEditText)findViewById(R.id.fn2);
        textInputEditText3 = (TextInputEditText)findViewById(R.id.em2);
        textInputEditText4 = (TextInputEditText)findViewById(R.id.mn);
        textInputEditText5 = (TextInputEditText)findViewById(R.id.pss);


    }
    private Boolean validate() {
        Boolean result = false;

        String name = textInputEditText2.getText().toString();
        String password = textInputEditText5.getText().toString();
        String Email = textInputEditText3.getText().toString();

        if (name.isEmpty() || password.isEmpty() || Email.isEmpty()) {
            Toast.makeText(this, "Please enter all thr details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;

    }
    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, " Verification Email sent Successfully!!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();



                    } else {
                        Toast.makeText(RegisterActivity.this, "Verification Email hasn't sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void process(View view  ) {



        String name = textInputEditText2.getText().toString().trim();
        String user_Email = textInputEditText3.getText().toString().trim();
        String user_phone = textInputEditText4.getText().toString().trim();
        com.example.budgetari.datasaver obj=new com.example.budgetari.datasaver(user_phone,user_Email);

        FirebaseDatabase db =FirebaseDatabase.getInstance();
        DatabaseReference node = db.getReference("Users");


        node.child(name).setValue(obj);

        textInputEditText2.setText("");
        textInputEditText4.setText("");
        textInputEditText3.setText("");



        Toast.makeText(getApplicationContext(),"Going to phone authentification ",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this, PhoneVerification.class));
        finish();





    }






}
