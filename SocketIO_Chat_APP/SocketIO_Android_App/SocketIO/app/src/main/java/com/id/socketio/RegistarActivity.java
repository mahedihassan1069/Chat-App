package com.id.socketio;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistarActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText userEmail,userPassword;
    private TextView AlredyHaveAccount;
    private FirebaseAuth mAuth;

    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        mAuth=FirebaseAuth.getInstance();

        InitializeFields();

        AlredyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sendUserToLoginActivity();
            }
        });

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CreateNewAccount();

            }
        });
    }

    private void CreateNewAccount()
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
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please Wait.....");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                sendUserToLoginActivity();
                                Toast.makeText(RegistarActivity.this, "Account Created Successfully!!!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                            else
                            {
                                String message=task.getException().toString();
                                Toast.makeText(RegistarActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                    });
        }
    }

    private void InitializeFields() {
        CreateAccountButton=(Button) findViewById(R.id.registar_button);

        userEmail=(EditText) findViewById(R.id.registar_email);
        userPassword=(EditText) findViewById(R.id.registar_password);
        AlredyHaveAccount=(TextView) findViewById(R.id.alredy_have_account_link);
        loadingBar=new ProgressDialog(this);

    }
    private void sendUserToLoginActivity()
    {

        Intent loginIntent=new Intent(RegistarActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }
}
