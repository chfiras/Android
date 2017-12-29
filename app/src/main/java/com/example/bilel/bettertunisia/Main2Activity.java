package com.example.firas.bettertunisia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button logout;
    private Button declarer;
    private RecyclerView bloglist;
    private FirebaseAuth mauth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        logout=(Button)findViewById(R.id.button9);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        declarer=(Button)findViewById(R.id.button10);
        bloglist=(RecyclerView)findViewById(R.id.blog_list);
        logout.setOnClickListener(this);
        declarer.setOnClickListener(this);
        mauth=FirebaseAuth.getInstance();
        bloglist.setHasFixedSize(true);
        bloglist.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<Blog,BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {
                viewHolder.setLocalisation(model.getLocalisation());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setCategorie(model.getCatégorie());
                viewHolder.setIdentifiant(model.getIdentifiant());
                viewHolder.setImage(getApplicationContext(), model.getImage());
            }
        };
        bloglist.setAdapter(firebaseRecyclerAdapter);


    }
    public static class BlogViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setLocalisation(String Localisation)
        {
            TextView post_localisation=(TextView)mView.findViewById(R.id.post_localisation);
            post_localisation.setText(Localisation);
        }
        public void setDescription(String Description)
        {
            TextView post_description=(TextView)mView.findViewById(R.id.post_description);
            post_description.setText(Description);
        }
        public void setImage(Context ctx, String image)
        {
            ImageView post_image = (ImageView)mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);

        }
        public void setIdentifiant(String identifiant)
        {
            TextView post_identifiant = (TextView)mView.findViewById(R.id.post_identifiant);
            post_identifiant.setText(identifiant);
        }
        public void setCategorie(String Catégorie)
        {
            TextView post_categorie=(TextView)mView.findViewById(R.id.post_categorie);
            post_categorie.setText(Catégorie);
        }

    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.button10 :
                startActivity(new Intent(Main2Activity.this,Declare.class));
                break;
            case R.id.button9 :
                startSignOut();
                startActivity(new Intent(Main2Activity.this,MainActivity.class));

        }

    }

    public void startSignOut()
    {
        mauth.signOut();
    }
}
