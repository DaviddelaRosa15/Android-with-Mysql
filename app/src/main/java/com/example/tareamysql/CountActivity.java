package com.example.tareamysql;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tareamysql.database.MysqlDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.mayoresTextView)
    TextView mayores;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.menoresTextView)
    TextView menores;

    private final String url_countMen = "http://10.0.0.23/androiddb/clientesMenores.php";
    private final String url_countMay = "http://10.0.0.23/androiddb/clientesMayores.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        ButterKnife.bind(this);

        countMenores();
        countMayores();
    }

    private void countMenores(){
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.url_countMen,
                response -> {
                    if (!response.isEmpty()) {
                        Cliente cliente = new Cliente();
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject row = array.getJSONObject(0);
                            int cantidad = row.getInt("menores");

                            menores.setText("Total de menores de edad: " + cantidad);
                        } catch (JSONException exception) {
                            Toast.makeText(CountActivity.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> Toast.makeText(CountActivity.this, "Error de conexión a la base de datos", Toast.LENGTH_SHORT).show());

        queue.add(stringRequest);
    }

    private void countMayores(){
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.url_countMay,
                response -> {
            if (!response.isEmpty()) {
                Cliente cliente = new Cliente();
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject row = array.getJSONObject(0);
                    int cantidad = row.getInt("mayores");

                    mayores.setText("Total de mayores de edad: " + cantidad);
                } catch (JSONException exception) {
                    Toast.makeText(CountActivity.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                }
            }else {
                mayores.setText("No hay clientes");
            }
        },
                error -> Toast.makeText(CountActivity.this, "Error de conexíon a la base de datos", Toast.LENGTH_SHORT).show());

        queue.add(stringRequest);
    }

}
