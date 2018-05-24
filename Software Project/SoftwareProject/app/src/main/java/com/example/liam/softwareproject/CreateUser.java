package com.example.liam.softwareproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateUser extends AppCompatActivity implements View.OnClickListener {

    private Button create;
    private Button login;

    private EditText user;
    private EditText password;
    TextView loginUsr;

    private ProgressDialog progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), data.class));
        }

        login = (Button) findViewById(R.id.login);
        create = findViewById(R.id.create);

        loginUsr = (TextView) findViewById(R.id.loginUsr);

        user = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);

        create.setOnClickListener(this);
        loginUsr.setOnClickListener(this);

        progressBar = new ProgressDialog(this);


    }

    private void registerUser(){
        String email = user.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty

            Toast.makeText(this,"Please enter username", Toast.LENGTH_LONG).show();

            return;
        }
        else if(TextUtils.isEmpty(pass)){
            //password is empty

            Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
            return;

        }

        progressBar.setMessage("Registering User...");
        progressBar.show();

        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), data.class));
                        }
                        else{
                            Toast.makeText(CreateUser.this, "Could not register, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    @Override
    public void onClick(View v) {
        if(v == create){
            registerUser();
        }

        if(v == loginUsr){
            finish();
            startActivity(new Intent(this, Login.class));
        }
    }


}
