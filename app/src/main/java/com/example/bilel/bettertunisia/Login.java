package com.example.firas.bettertunisia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity  {


    private Button login;
    private ImageButton imageButton;
    private EditText email;
    private EditText password;
    private Button inscrire;
    public FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.button3);
        email = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        inscrire = (Button)findViewById(R.id.button4);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
                    public void onClick(View view)
            {
                startSignIn();
            }
        });
        imageButton=(ImageButton)findViewById(R.id.imageButton);
        inscrire.setOnClickListener(new View.OnClickListener()
        {
            @Override
                    public void onClick(View view)
            {
                startActivity(new Intent(Login.this , Register.class));
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,MainActivity.class));
            }
        });

        mauth = FirebaseAuth.getInstance();
        mauthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(Login.this ,Main2Activity.class ));
                }

            }
        };

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mauth.addAuthStateListener(mauthListener);
    }


    private void startSignIn()
    {
        String semail = email.getText().toString();
        String spassword = password.getText().toString();
        if ((TextUtils.isEmpty(semail)) || (TextUtils.isEmpty(spassword))) {
            Toast.makeText(Login.this, "les champs sont vides", Toast.LENGTH_LONG).show();
        }else
        {

            mauth.signInWithEmailAndPassword(semail, spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()==false) {
                        Toast.makeText(Login.this, "Sign in problem", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }



    }
}
