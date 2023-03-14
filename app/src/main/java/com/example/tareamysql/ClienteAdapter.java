package com.example.tareamysql;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tareamysql.database.MysqlDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ClienteAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final List<Cliente> mClientList;

    public ClienteAdapter(List<Cliente> clientList) {
        this.mClientList = clientList;
    }

    @NonNull
    @Override
    public com.example.tareamysql.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.tareamysql.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (mClientList != null & mClientList.size() > 0) {
            return mClientList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<Cliente> clientList) {
        clientList.addAll(clientList);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        if (mClientList != null & mClientList.size() > 0) {
            mClientList.remove(position);
        }
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends com.example.tareamysql.ViewHolder{
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.nameTextView)
        TextView mNameTextView;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ageTextView)
        TextView mAgeTextView;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.dateTextView)
        TextView mDateTextView;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.deleteImageView)
        ImageView mDeleteImageVIew;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.editImageView)
        ImageView mEditImageView;

        private final String url_delete= "http://localhost/androiddb/eliminarCliente.php";

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            mNameTextView.setText("");
            mAgeTextView.setText("");
            mDateTextView.setText("");
        }

        public void onBind(int position){
            super.onBind(position);

            Cliente cliente = mClientList.get(position);

            mNameTextView.setText(cliente.getName());
            mAgeTextView.setText(String.valueOf(cliente.getAge()));
            mDateTextView.setText(cliente.getDate().toString());

            mEditImageView.setOnClickListener(v -> {
                Intent intent=new Intent(itemView.getContext(), EditActivity.class);
                intent.putExtra("id",  cliente.getId());
                itemView.getContext().startActivity(intent);
            });

            mDeleteImageVIew.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                builder.setTitle("Confirmación")
                        .setMessage("¿Está seguro de eliminar al cliente " + cliente.getName() + "?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MysqlDatabase dataBase = new MysqlDatabase(itemView.getContext());
                                dataBase.deleteClient(cliente.getId());
                                deleteItem(position);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                builder.setIcon(R.drawable.baseline_warning_24);
                builder.create();
                builder.show();
            });
        }
    }
}