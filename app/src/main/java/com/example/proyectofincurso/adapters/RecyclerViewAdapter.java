package com.example.proyectofincurso.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyectofincurso.R;
import com.example.proyectofincurso.model.Mascotas;

import java.util.List;

public class RecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
        implements View.OnClickListener
        {

    private List<Mascotas> listaMascotas;
    Context context;
    RequestOptions option;
    private View.OnClickListener listener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_descripcion;
        TextView rol;
        ImageView img_thumbnail;


        public MyViewHolder(View view) {
            super(view);

            tv_descripcion = view.findViewById(R.id.descripcion);
            img_thumbnail = view.findViewById(R.id.thumbnail);
            rol = view.findViewById(R.id.rol);
        }
    }

    //Constructor
    public RecyclerViewAdapter(Context context, List<Mascotas> listaMascotas) {
        this.context = context;
        this.listaMascotas = listaMascotas;

        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_descripcion.setText(listaMascotas.get(position).getTitulo());

        if(listaMascotas.get(position).getId_rolMascota() ==1){
            holder.rol.setText("PERDIDO");
        }else if(listaMascotas.get(position).getId_rolMascota() ==2){
            holder.rol.setText("ENCONTRADO");
        }else if(listaMascotas.get(position).getId_rolMascota() ==3){
            holder.rol.setText("EN ADOPCION");
        }


        Glide.with(context)
                .load(listaMascotas.get(position).getImagen())
                .apply(option)
                .into(holder.img_thumbnail);
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.mascotas_row_item, parent, false);

        view.setOnClickListener(this);

        return new MyViewHolder(view);

    }


    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }




}
