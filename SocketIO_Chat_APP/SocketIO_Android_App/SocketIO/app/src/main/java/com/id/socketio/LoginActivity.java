package com.id.socketio;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseUser currentUser;
    private Button LoginButton,PhoneLoginButton;
    private EditText userEmail,userPassword;
    private TextView NeedNewAccountLink,ForgetPasswordLink;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        InitializeFields();

        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sendUserToRegistarActivity();
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AllowUserLogin();
            }
        });
    }

    private void AllowUserLogin()
    {
        String email=userEmail.getText().toString();
        String password=userPassword.getText().toString();
        if  (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please Enter Your Email!!", Toast.LENGTH_SHORT).show();
        }

        if  (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter Your Password!!", Toast.LENGTH_SHORT).show();
        }

        else
        {

            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please Wait.....");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                sendUserToAddUserActivity();
                                Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                            else
                            {

                                String message=task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();


                            }

                        }
                    });
        }

    }

    private void InitializeFields()
    {
        LoginButton=(Button) findViewById(R.id.login_button);
        userEmail=(EditText) findViewById(R.id.login_email);
        userPassword=(EditText) findViewById(R.id.login_password);
        NeedNewAccountLink=(TextView) findViewById(R.id.need_new_account_link);
        ForgetPasswordLink=(TextView) findViewById(R.id.forget_password_link);
        loadingBar=new ProgressDialog(this);


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(currentUser!=null){
            sendUserToAddUserActivity();
        }

    }

    private void sendUserToAddUserActivity()
    {

        Intent loginIntent=new Intent(LoginActivity.this,AddUserActivity.class);
        startActivity(loginIntent);
    }

    private void sendUserToRegistarActivity()
    {

        Intent registarIntent=new Intent(LoginActivity.this,RegistarActivity.class);
        startActivity(registarIntent);
    }
}
