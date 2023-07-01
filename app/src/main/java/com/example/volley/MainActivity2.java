package com.example.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle GlobalInfo = this.getIntent().getExtras();
        TextView Mostrar = (TextView) findViewById(R.id.TxtMostrar);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.uealecpeterson.net/public/productos/search";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        TextView Mostrar = (TextView) findViewById(R.id.TxtMostrar);
                        String cliente = "";
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            JSONArray litar = json.getJSONArray("productos");
                            for (int i = 0; i < litar.length(); i++) {
                                JSONObject client = litar.getJSONObject(i);
                                cliente = cliente + "\n" +
                                        client.getString("id").toString() + ", " +
                                        client.getString("descripcion").toString() + ", " +
                                        client.getString("barcode").toString() + ", " +
                                        client.getString("costo").toString()+"\n";
                            }
                            Mostrar.setText("Respuesta: \n " + cliente);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity2.this, "That didn't work!", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fuente", "1");
                return params;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String,String> params = new HashMap<>();
            params.put("Authorization","Bearer "+GlobalInfo.getString("token"));
            return params;
            }
        };

        queue.add(stringRequest);
    }
}