package com.example.tareamysql.database;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tareamysql.Cliente;

import java.util.HashMap;
import java.util.Map;

public class MysqlDatabase {
    private final String url_delete= "http://10.0.0.23/androiddb/eliminarCliente.php";
    private final String url_update = "http://10.0.0.23/androiddb/editarCliente.php";
    private final String url_insert = "http://10.0.0.23/androiddb/insertarCliente.php";
    private Context context;

    public MysqlDatabase(Context context) {
        this.context = context;
    }

    public void newClient(Cliente cliente){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.url_insert,
                response -> Toast.makeText(context, "Se creó corectamente", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(context, "Error al crear cliente", Toast.LENGTH_SHORT).show()){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("name", cliente.getName());
                parametros.put("age", String.valueOf(cliente.getAge()));

                return parametros;
            }
        };

        queue.add(stringRequest);
    }

    public void updateClient(Cliente cliente, int id){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.url_update,
                response -> Toast.makeText(context, "Se actualizó correctamente", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(context, "Error al actualizar cliente", Toast.LENGTH_SHORT).show()){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", String.valueOf(id));
                parametros.put("name", cliente.getName());
                parametros.put("age", String.valueOf(cliente.getAge()));

                return parametros;
            }
        };

        queue.add(stringRequest);
    }

    public void deleteClient(int id){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.url_delete,
                response -> Toast.makeText(context, "Se eliminó correctamente", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(context, "Error al eliminar cliente", Toast.LENGTH_SHORT).show()){
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