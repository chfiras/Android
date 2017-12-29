package com.example.firas.bettertunisia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
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

public class MainActivity extends Activity implements View.OnClickListener {


    private RecyclerView bloglist;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = (Button)findViewById(R.id.button2);
        login.setOnClickListener(this);
        bloglist=(RecyclerView)findViewById(R.id.blog_list2);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        bloglist.setHasFixedSize(true);
        bloglist.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<Blog,Main2Activity.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, Main2Activity.BlogViewHolder>(
                Blog.class,
                R.layout.blog_row,
                Main2Activity.BlogViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(Main2Activity.BlogViewHolder viewHolder, Blog model, int position) {
                viewHolder.setLocalisation(model.getLocalisation());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setCategorie(model.getCatégorie());
                viewHolder.setIdentifiant(model.getIdentifiant());
                viewHolder.setImage(getApplicationContext(), model.getImage());
            }
        };
        bloglist.setAdapter(firebaseRecyclerAdapter);


    }
    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setLocalisation(String Localisation) {
            TextView post_localisation = (TextView) mView.findViewById(R.id.post_localisation);
            post_localisation.setText(Localisation);
        }

        public void setDescription(String Description) {
            TextView post_description = (TextView) mView.findViewById(R.id.post_description);
            post_description.setText(Description);
        }

        public void setImage(Context ctx, String image) {
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);

        }

        public void setIdentifiant(String identifiant) {
            TextView post_identifiant = (TextView) mView.findViewById(R.id.post_identifiant);
            post_identifiant.setText(identifiant);
        }

        public void setCategorie(String Catégorie) {
            TextView post_categorie = (TextView) mView.findViewById(R.id.post_categorie);
            post_categorie.setText(Catégorie);
        }
    }

        public void onClick (View v)
    {
        switch (v.getId())
        {
            case R.id.button2 :
                startActivity(new Intent(MainActivity.this,Login.class));
                break;
        }
    }
}
