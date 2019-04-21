package com.example.dell.mobiletest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailScreen extends AppCompatActivity {
    private RequestQueue requestQueue;
    List<Followers> mDataset;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        requestQueue = Volley.newRequestQueue(DetailScreen.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mDataset = new ArrayList<Followers>();

        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyFollowersAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        if(getSupportActionBar()!=null)
        { getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // MyFollowersAdapter
        loadimage();
        loadNameandEmail();
getFollowers();


    }

    public void loadNameandEmail()
    {
        TextView email= (TextView)findViewById(R.id.Email);
        email.setText(""+getIntent().getExtras().getString("email"));
        TextView name =(TextView)findViewById(R.id.Name);
        name.setText(""+getIntent().getExtras().getString("name"));
    }
    public void loadimage()
    {
        final ImageView mImageView;
        String url = getIntent().getExtras().getString("avatar");
        mImageView = (ImageView) findViewById(R.id.imageView);


        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        //    mImageView.setImageResource(R.drawable.image_load_error);
                    }
                });

        requestQueue.add(request);
    }
    void getFollowers ()
    {
        String name = getIntent().getExtras().getString("name");
        String url = "https://api.github.com/users/"+name+"/followers";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Followers followers = new Followers();
                                followers.setName(""+obj.getString("login"));
                                followers.setUrl(""+obj.getString("avatar_url"));
mDataset.add(followers);
mAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(jsonObjectRequest);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home)
        {
          super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
