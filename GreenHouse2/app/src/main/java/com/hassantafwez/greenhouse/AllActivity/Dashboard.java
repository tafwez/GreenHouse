package com.hassantafwez.greenhouse.AllActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hassantafwez.greenhouse.Adapters.ArticleAdapter;
import com.hassantafwez.greenhouse.Adapters.VideoAdapter;
import com.hassantafwez.greenhouse.Models.MyListData;
import com.hassantafwez.greenhouse.R;
import com.hassantafwez.greenhouse.Usersession.UserSession;

public class Dashboard extends AppCompatActivity {

    UserSession session;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        session=new UserSession(getApplicationContext());


        session.checkLogin();
        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ShowFirstLIst();
        ShowSecondList();




    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_gifts:
                    Toast.makeText(getApplicationContext(),"News",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_cart:
                    Toast.makeText(getApplicationContext(),"Video",Toast.LENGTH_SHORT).show();
                    return true;

            }
            return false;
        }
    };


    public void ShowFirstLIst(){


        MyListData[] myListData = new MyListData[] {
                new MyListData("Email", R.drawable.pic12),
                new MyListData("Info", R.drawable.pic13),
                new MyListData("Delete", R.drawable.pic14),
                new MyListData("Dialer", R.drawable.pic15),
                new MyListData("Alert", R.drawable.pic1),
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.firstview);
        ArticleAdapter adapter = new ArticleAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);

    }


    public void ShowSecondList(){

        MyListData[] myListData = new MyListData[] {
                new MyListData("Email", R.drawable.pic5),
                new MyListData("Info", R.drawable.pic6),
                new MyListData("Delete", R.drawable.pic7),
                new MyListData("Dialer", R.drawable.pic8),
                new MyListData("Alert", R.drawable.pic9),
                new MyListData("Alert", R.drawable.pic10),

        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.seconview);
        VideoAdapter adapter = new VideoAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);

    }
}