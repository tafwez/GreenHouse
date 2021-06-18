package com.hassantafwez.greenhouse.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hassantafwez.greenhouse.Adapters.PhotoAdapter;
import com.hassantafwez.greenhouse.AllActivity.LoginActivity;
import com.hassantafwez.greenhouse.Models.PhotosModel;
import com.hassantafwez.greenhouse.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView textView;
    ArrayList<PhotosModel> arrayList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PhotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotoFragment newInstance(String param1, String param2) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_photo, container, false);
        recyclerView=view.findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(container.getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();
        Getdata();


        return view;
    }

    private void Getdata() {


        final KProgressHUD progressDialog=  KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        String url="https://jsonplaceholder.typicode.com/photos";
        // creating a new variable for our request queue
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        // parseData(response);
                        Log.e("onResponse: ",response.toString() );
                        for (int i=0;i<response.length();i++)
                        {
                            try {
                                //  Log.e( "onResponse123: ", response.getString(0));
                                JSONObject jsonObject=response.getJSONObject(i);
                                PhotosModel photosModel=new PhotosModel();
                                photosModel.setAlbumid(jsonObject.getString("albumId"));
                                photosModel.setId(jsonObject.getString("id"));
                                photosModel.setTitle(jsonObject.getString("title"));
                                photosModel.setUrl(jsonObject.getString("url"));
                                photosModel.setThumbnailUrl(jsonObject.getString("thumbnailUrl"));
                                progressDialog.dismiss();
                                arrayList.add(photosModel);
                                PhotoAdapter photoAdapter=new PhotoAdapter(arrayList,getActivity().getApplicationContext());
                                recyclerView.setAdapter(photoAdapter);

                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        //If an error occurs that means end of the list has reached
                        Toast.makeText(getActivity().getApplicationContext(), "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(jsonArrayRequest);
    }
}