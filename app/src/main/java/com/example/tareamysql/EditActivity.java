package com.example.tareamysql;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

public class EditActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.nameEditText)
    EditText mNameEditText;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ageEditText)
    EditText  mAgeEditText;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.clienteButton)
    Button  mClienteButton;

    private final String url_listById = "http://10.0.0.23/androiddb/buscarClienteId.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ButterKnife.bind(this);

        int id= Objects.requireNonNull(getIntent().getExtras()).getInt("id");
        MysqlDatabase mDatabase = new MysqlDatabase(this);
        getClient(id);

        mClienteButton.setOnClickListener(v -> {
            String name = mNameEditText.getText().toString();
            String age = mAgeEditText.getText().toString();

            if(name.length() == 0 || age.length() == 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Advertencia")
                        .setMessage("Debe completar ambos campos")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                builder.setIcon(R.drawable.baseline_warning_24);
                builder.create();
                builder.show();
            }else{
                Cliente mNewClient = new Cliente(name, Integer.parseInt(age));
                mDatabase.updateClient(mNewClient,id);
                Intent intent= new Intent(this, MainActivity.class);
                startActivity(intent);
            }


        });

    }

    private void getClient(int id){
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.url_listById, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Cliente cliente = new Cliente();
                    try {
                        JSONArray array = new JSONArray(response);
                        JSONObject row = array.getJSONObject(0);
                        cliente.setId(row.getInt("id"));
                        cliente.setName(row.getString("name"));
                        cliente.setAge(row.getInt("age"));

                        mNameEditText.setText(cliente.getName());
                        mAgeEditText.setText(String.valueOf(cliente.getAge()));
                    } catch (JSONException exception) {
                        Toast.makeText(EditActivity.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        },
                error -> Toast.makeText(EditActivity.this, "Error de conexión a la base de datos", Toast.LENGTH_SHORT).show()){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", String.valueOf(id));

                return parametros;
            }
        };

        queue.add(stringRequest);
    }

}
