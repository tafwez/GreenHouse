package com.hassantafwez.greenhouse.AllActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hassantafwez.greenhouse.Apis.Urls;
import com.hassantafwez.greenhouse.R;
import com.hassantafwez.greenhouse.Usersession.UserSession;
import com.hassantafwez.greenhouse.VollySingletonClasses.VolleySingleton;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText UserEmail,UserPassword;
    String check,email,password;
    UserSession session;
    AppCompatButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserEmail=findViewById(R.id.emailbox);
        UserPassword=findViewById(R.id.passwordbox);
        button=findViewById(R.id.login);
        session=new UserSession(getApplicationContext());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(validateEmail() && validatePass()){

                    email=UserEmail.getText().toString();
                    password=UserPassword.getText().toString();

                    Log.e("tag",email+" "+password);
                    LoginUser(email,password);

                }else{

                    Toast.makeText(getApplicationContext(),"Fill your email and password both",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




   /* public void sendToCreateAccount(View view) {

        finish();
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }*/










    private void LoginUser(String email1, String password1) {

        Log.e("LoginUser: ", email1+"  "+password1);
        final KProgressHUD progressDialog=  KProgressHUD.create(LoginActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.e("tag",response);
                        progressDialog.dismiss();
                       session.createLoginSession("Username",email,"","");
                       finish();
                       startActivity(new Intent(getApplicationContext(),MainActivity.class));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Something went Wrong"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email1);
                params.put("password", password1);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);



    }

    private boolean validatePass() {


        check = UserPassword.getText().toString();

        if (check.length() < 4 || check.length() > 20) {
            return false;
        } else if (!check.matches("^[A-za-z0-9@]+")) {
            return false;
        }
        return true;
    }

    private boolean validateEmail() {

        check = UserEmail.getText().toString();

        if (check.length() < 4 || check.length() > 40) {
            return false;
        } else if (!check.matches("^[A-za-z0-9.@]+")) {
            return false;
        } else if (!check.contains("@") || !check.contains(".")) {
            return false;
        }

        return true;
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}