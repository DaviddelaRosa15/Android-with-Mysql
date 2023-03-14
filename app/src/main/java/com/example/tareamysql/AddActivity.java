package com.example.tareamysql;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tareamysql.database.MysqlDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity {

    @BindView(R.id.nameEditText)
    EditText mNameEditText;

    @BindView(R.id.ageEditText)
    EditText  mAgeEditText;

    @BindView(R.id.clienteButton)
    Button  mClienteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ButterKnife.bind(this);

        MysqlDatabase dataBase = new MysqlDatabase(this);

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
                Cliente cliente = new Cliente(name, Integer.parseInt(age));
                dataBase.newClient(cliente);
                Intent intent= new Intent(this, MainActivity.class);
                startActivity(intent);
            }


        });

    }
}
