package com.example.dell.mobiletest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
private RequestQueue requestQueue;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // Toast.makeText(MainActivity.this,"start",Toast.LENGTH_SHORT).show();

        progressBar = findViewById(R.id.progress);

        requestQueue = Volley.newRequestQueue(MainActivity.this);



    }
    public void Search(View view)
    {
        final View snackbarview=view;
        EditText SearchEditText = (EditText) findViewById(R.id.SearchEditText);
        String SearchGetTextVariable = SearchEditText.getText().toString().trim();
        if(SearchGetTextVariable.isEmpty())
        {
           Toast.makeText(MainActivity.this,"Please Enter ID/Email",Toast.LENGTH_SHORT).show();
        }
        else {
showLoading();

            String url = "https://api.github.com/users/" + SearchGetTextVariable;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            hideLoading();

                            try {
                                Intent intent = new Intent(MainActivity.this,DetailScreen.class);
                                intent.putExtra("name",response.getString("login"));
                                intent.putExtra("avatar",response.getString("avatar_url"));
                                intent.putExtra("email",response.getString("email"));
                                startActivity(intent);
                              //  Toast.makeText(MainActivity.this,""+response.getString("avatar_url"),Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideLoading();
                            Snackbar.make(snackbarview, "This User does not exists", Snackbar.LENGTH_SHORT)
                                    .show();

                        }
                    });

            requestQueue.add(jsonObjectRequest);
        }

    }
    void showLoading()
    {

        progressBar.setVisibility(View.VISIBLE);

    }
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
