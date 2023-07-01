package com.example.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void Login (View view){
        EditText Corre =(EditText) findViewById(R.id.Txtemail);
        EditText Pass =(EditText) findViewById(R.id.Txtpass);
        String Email= Corre.getText().toString();
        String Contra= Pass.getText().toString();
        Intent intent= new Intent(this, MainActivity2.class);
        Bundle GlobalInfo = new Bundle();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Response is: "+ response, Toast.LENGTH_SHORT).show();
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            GlobalInfo.putString("token", json.getString("access_token"));
                            intent.putExtras(GlobalInfo);
                            startActivity(intent);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "That didn't work!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("correo",Email);
                params.put("clave", Contra);
                return params;
            }

        };
        queue.add(stringRequest);

    }
}