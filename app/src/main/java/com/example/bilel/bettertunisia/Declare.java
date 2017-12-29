package com.example.firas.bettertunisia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.example.firas.bettertunisia.R.id.spinner;

public class Declare extends AppCompatActivity implements View.OnClickListener {

    private TextView logout;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthListener;
    private ImageButton imageButton;
    private ImageButton selectimage;
    private static final int CAMERA_REQUEST=1;
    private Button declarer;
    private EditText loc;
    private EditText desc;
    private Spinner spinner;
    private Uri imageUri =null;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private int pos1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare);
        spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        logout=(TextView)findViewById(R.id.textView7);
        mStorage = FirebaseStorage.getInstance().getReference();
        logout.setOnClickListener(this);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        selectimage=(ImageButton)findViewById(R.id.imageButton4);
        selectimage.setOnClickListener(this);
        loc=(EditText)findViewById(R.id.editText3);
        desc=(EditText)findViewById(R.id.editText4);
        mauth=FirebaseAuth.getInstance();
        declarer=(Button)findViewById(R.id.button7);
        imageButton=(ImageButton)findViewById(R.id.imageButton2);
        imageButton.setOnClickListener(this);
        mProgress =new ProgressDialog(this);
        declarer.setOnClickListener(this);
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                adapterView.getItemAtPosition(pos);
                pos1=pos;

            };

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                pos1=0;
                adapterView.getItemAtPosition(pos1);
            }
        });
    }

    //String cat = spinner.getSelectedItem().toString();

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.textView7 :
            {
                startSignOut();
                startActivity(new Intent(Declare.this,MainActivity.class));
                break;
            }
            case R.id.imageButton2 :
                startActivity(new Intent(Declare.this,Main2Activity.class));
                break;
            case R.id.imageButton4 : {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,CAMERA_REQUEST);
                break;
            }
            case R.id.button7 :
            {
                startPosting();
                startActivity(new Intent(Declare.this,Main2Activity.class));
            }
         //   case R.id.spinner :


        }
    }

    private void startPosting() {
        mProgress.setMessage("Posting to server");
        mProgress.show();
        final String local=loc.getText().toString().trim();
        final String descrip=desc.getText().toString().trim();
        if(!TextUtils.isEmpty(local)&&!TextUtils.isEmpty(descrip))
        {
            StorageReference filepath=mStorage.child("Blog_Images").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    DatabaseReference newPost = mDatabase.push();
                    newPost.child("Localisation").setValue(local);
                    newPost.child("Description").setValue(descrip);
                    newPost.child("image").setValue(downloadUrl.toString());
                    newPost.child("identifiant").setValue(mauth.getCurrentUser().getEmail().toString());
                    switch (pos1) {
                        case 0: newPost.child("Catégorie").setValue("Faille");
                            break;
                        case 1 : newPost.child("C" +
                                "atégorie").setValue("Déchets");
                            break;
                        case 2 : newPost.child("Catégorie").setValue("Lampes");
                            break;

                    }
                    mProgress.dismiss();

                }
            });
        }
    }

    public void startSignOut()
    {
        mauth.signOut();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {
            imageUri = data.getData();
            selectimage.setImageURI(imageUri);
        }
    }

}


