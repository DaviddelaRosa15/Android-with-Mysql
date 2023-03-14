package com.example.tareamysql;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.item_list)
    RecyclerView mRecyclerView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnCount)
    Button btnCount;
    ClienteAdapter mClienteAdapter;
    GridLayoutManager mLayoutManager;
    private final String url_list = "http://10.0.0.23/androiddb/listarClientes.php";
    private final String url_countMen = "http://10.0.0.23/androiddb/clientesMenores.php";
    private final String url_countMay = "http://10.0.0.23/androiddb/clientesMayores.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Recycler();

        mFab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        btnCount.setOnClickListener(view -> {
            Intent count = new Intent(MainActivity.this, CountActivity.class);
            startActivity(count);
        });

    }

    public void Recycler() {

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayoutManager = new GridLayoutManager(this, 2);
        } else {
            mLayoutManager = new GridLayoutManager(this, 1);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mClienteAdapter = new ClienteAdapter(new ArrayList<>());

        listClients();
    }

    private void Content(List<Cliente> clientes) {
        if (clientes != null && clientes.size() > 0) {
            mClienteAdapter = new ClienteAdapter(clientes);
        } else {
            ArrayList<Cliente> animeEmpty = new ArrayList<>();
            mClienteAdapter.addItems(animeEmpty);
        }

        mRecyclerView.setAdapter(mClienteAdapter);

    }

    private void listClients(){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.url_list, response -> {
            if (!response.isEmpty()) {
                try {
                    List<Cliente> listado = new ArrayList<>();
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);

                        Cliente cliente = new Cliente(row.getInt("id"), row.getString("name"), row.getInt("age"), Date.valueOf(row.getString("registerdate")));
                        listado.add(cliente);
                    }
                    Content(listado);
                } catch (JSONException exception) {
                    Toast.makeText(this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                }
            }
        },
                error -> Toast.makeText(MainActivity.this, "Error de conexión a la base de datos", Toast.LENGTH_SHORT).show());

        queue.add(stringRequest);

    }
}