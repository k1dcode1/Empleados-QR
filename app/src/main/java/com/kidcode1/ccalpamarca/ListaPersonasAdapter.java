package com.kidcode1.ccalpamarca;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListaPersonasAdapter extends RecyclerView.Adapter<ListaPersonasAdapter.PersonasViewHolder> {
    ArrayList<Usuario> listaUsuario;
    public ListaPersonasAdapter(ArrayList<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    @Override
    public PersonasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item_personas,null,false);
        return new PersonasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonasViewHolder holder, int position) {
        holder.documento.setText(listaUsuario.get(position).getId().toString());
        holder.nombre.setText(listaUsuario.get(position).getName());
        holder.telefono.setText(listaUsuario.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return listaUsuario.size();
    }

    public class PersonasViewHolder extends RecyclerView.ViewHolder {

        TextView documento,nombre,telefono;

        public PersonasViewHolder(View itemView) {
            super(itemView);
            documento = (TextView) itemView.findViewById(R.id.textDocumento);
            nombre = (TextView) itemView.findViewById(R.id.textNombre);
            telefono = (TextView) itemView.findViewById(R.id.textCargo);
        }
    }


}
