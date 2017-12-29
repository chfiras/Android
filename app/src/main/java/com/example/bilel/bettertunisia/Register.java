package com.example.firas.bettertunisia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity  {


    public EditText identifiant;
    EditText email;
    EditText password;
    Button register;
    private FirebaseAuth mauth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mauth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        identifiant = (EditText)findViewById(R.id.textView4);
        email = (EditText)findViewById(R.id.textView5);
        password = (EditText) findViewById(R.id.textView6);
        register = (Button)findViewById(R.id.button5);
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startRegister();
            }
        });
    }



    private void startRegister()
    {

        final String sidentifiant = identifiant.getText().toString().trim();
        String semail = email.getText().toString().trim();
        String spassword = password.getText().toString().trim();
        if(!TextUtils.isEmpty(sidentifiant)&&(!TextUtils.isEmpty(semail))&&(!TextUtils.isEmpty(spassword)))
        {
            mProgress.setMessage("Signing up ...");
            mProgress.show();
            mauth.createUserWithEmailAndPassword(semail,spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        String user_id = mauth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = mDatabase.child(user_id);
                        current_user_db.child("identifiant").setValue(sidentifiant);
                        mProgress.dismiss();
                    }
                }
            });
        }
    }

    public void onClick (View v)
    {
        startActivity(new Intent(this , Login.class));
    }


}

