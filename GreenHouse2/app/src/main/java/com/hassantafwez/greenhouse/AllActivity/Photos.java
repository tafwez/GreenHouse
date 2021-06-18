package com.hassantafwez.greenhouse.AllActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hassantafwez.greenhouse.Adapters.PhotoAdapter;
import com.hassantafwez.greenhouse.Models.PhotosModel;
import com.hassantafwez.greenhouse.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Photos extends AppCompatActivity {

    TextView textView;
    ArrayList<PhotosModel>arrayList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager=new GridLayoutManager(this,3,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();
        Getdata();
    }

    private void Getdata() {
        String url="https://jsonplaceholder.typicode.com/photos";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e("onResponse: ",response.toString() );
                        for (int i=0;i<response.length();i++)
                        {
                            try {

                                JSONObject jsonObject=response.getJSONObject(i);
                                PhotosModel photosModel=new PhotosModel();
                                photosModel.setAlbumid(jsonObject.getString("albumId"));
                                photosModel.setId(jsonObject.getString("id"));
                                photosModel.setTitle(jsonObject.getString("title"));
                                photosModel.setUrl(jsonObject.getString("url"));
                                photosModel.setThumbnailUrl(jsonObject.getString("thumbnailUrl"));
                                arrayList.add(photosModel);
                                PhotoAdapter photoAdapter=new PhotoAdapter(arrayList,getApplicationContext());
                                recyclerView.setAdapter(photoAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(getApplicationContext(), "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(getApplicationContext()).add(jsonArrayRequest);
    }
}