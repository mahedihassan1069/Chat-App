package com.id.socketio;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddUserActivity extends AppCompatActivity {

    private Button setNickName;
    private EditText userNickName;

    private FirebaseUser currentUser;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        userNickName = findViewById(R.id.userNickName);
        setNickName = findViewById(R.id.setNickName);


        userNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    setNickName.setEnabled(true);
                    Log.i(MainActivity.TAG, "onTextChanged: ABLED");
                } else {
                    Log.i(MainActivity.TAG, "onTextChanged: DISABLED");
                    setNickName.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        setNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUserActivity.this, MainActivity.class);
                intent.putExtra("username", userNickName.getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(currentUser==null){
            sendUserToLoginActivity();
        }

    }

    private void sendUserToLoginActivity()
    {
        Intent loginIntent=new Intent(AddUserActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        if (item.getItemId()==R.id.main_logout_option)
        {
            mAuth.signOut();
            sendUserToLoginActivity();
        }
        if (item.getItemId()==R.id.main_sattings_option)
        {

        }
        if (item.getItemId()==R.id.main_find_friends_option)
        {

        }
        return true;
    }


}
