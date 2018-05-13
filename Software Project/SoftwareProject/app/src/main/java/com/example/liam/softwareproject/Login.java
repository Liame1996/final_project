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


/**
 * Created by Liam on 03/12/2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener{


    private FirebaseAuth mAuth;

    EditText usernameET;
    EditText passwordET;
    TextView createUsr;

    Button login;
    Button create;

    private ProgressDialog progressBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        createUsr = findViewById(R.id.createUsr);
        login = findViewById(R.id.login);
        create = findViewById(R.id.create);

        usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        createUsr = (TextView) findViewById(R.id.createUsr);

        login.setOnClickListener(this);
        createUsr.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), data.class));
        }

        progressBar = new ProgressDialog(this);


    }

    public void loginUser(){
        String email = usernameET.getText().toString().trim();
        String pass = passwordET.getText().toString().trim();

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

        progressBar.setMessage("Logging in. Please wait....");
        progressBar.show();

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), data.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == login){
            loginUser();
        }

        if(v == createUsr){
            finish();
            startActivity(new Intent(this, CreateUser.class));
        }
    }
}
