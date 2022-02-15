package com.example.budgetari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextInputEditText loginEmail,loginpass;
    Button Reg01,Login,Forgetpass;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        loginEmail = findViewById(R.id.RegEmail);
        loginpass = findViewById(R.id.RegPass);
        Forgetpass = findViewById(R.id.forgetPassword);
        Reg01 = findViewById(R.id.NewUser);
        Login = findViewById(R.id.Login);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        if (user !=null){

            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            finish();
        }


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        Reg01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        Forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgetPasswordActivity.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(loginEmail.getText().toString(), loginpass.getText().toString());



            }
        });



    }

    private String email,password;

    private void validate(String userName2, String userPassword){

        progressDialog.setMessage("logging In!!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName2,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    // Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                    Login.setEnabled(true);

                }
                else{
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void checkEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if (emailflag) {
            finish();
            startActivity(new Intent(MainActivity.this, HomeActivity.class));


        } else {
            Toast.makeText(this, "Verify your Email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();


        }
    }


}