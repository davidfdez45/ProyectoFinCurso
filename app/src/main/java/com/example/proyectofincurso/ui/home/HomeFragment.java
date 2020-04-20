package com.example.proyectofincurso.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofincurso.DetalleMascota;
import com.example.proyectofincurso.R;
import com.example.proyectofincurso.adapters.RecyclerViewAdapter;
import com.example.proyectofincurso.model.Mascotas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private List<Mascotas> lstMascotas;
    int rol_buscar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setHasOptionsMenu(true);



        recyclerView = view.findViewById(R.id.recyclerviewid);
        recyclerView.setHasFixedSize(true);

        lstMascotas = new ArrayList<>();

        jsonrequest("http://192.168.56.1/PhpProject2/listaMascotasProyecto.php");


        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.activity_publicaciones, menu);
        super.onCreateOptionsMenu(menu,inflater);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                //al pulsar el boton buscar
                mostrarDialogoBuscar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void mostrarDialogoBuscar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialogo_buscar,null);

        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        Button buttonPerdidos = view.findViewById(R.id.perdido);
        buttonPerdidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rol_buscar = 1;
                lstMascotas.clear();
                jsonBuscarPorRol("http://192.168.56.1/PhpProject2/buscarPorRol.php");
                //adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        Button buttonEncontrados = view.findViewById(R.id.encontrado);
        buttonEncontrados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rol_buscar = 2;
                lstMascotas.clear();
                Log.i("00000000000", lstMascotas.size()+"");
                jsonBuscarPorRol("http://192.168.56.1/PhpProject2/buscarPorRol.php");
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        Button buttonAdopcion = view.findViewById(R.id.adopcion);
        buttonAdopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rol_buscar = 3;
                lstMascotas.clear();
                jsonBuscarPorRol("http://192.168.56.1/PhpProject2/buscarPorRol.php");
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        Button buttonTodos = view.findViewById(R.id.todos);
        buttonTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstMascotas.clear();
                jsonrequest("http://192.168.56.1/PhpProject2/listaMascotasProyecto.php");
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

    }


    private void jsonrequest(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);


                    //obtenemos los datos que devuelve el servidor
                    for (int i = 0; i < jsonArray.length(); i++) {


                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Mascotas mascota1 = new Mascotas();
                        mascota1.setId(jsonObject.getInt("id"));
                        mascota1.setTitulo(jsonObject.getString("titulo"));
                        mascota1.setImagen(jsonObject.getString("imagen"));
                        mascota1.setTipo(jsonObject.getString("tipo"));
                        mascota1.setRaza(jsonObject.getString("raza"));
                        mascota1.setSexo(jsonObject.getString("sexo"));
                        mascota1.setDescripcion(jsonObject.getString("descripcion"));
                        mascota1.setId_rolMascota(jsonObject.getInt("id_rolMascota"));
                        mascota1.setCiudad(jsonObject.getString("ciudad"));
                        mascota1.setId_usuario(jsonObject.getInt("id_usuario"));
                        lstMascotas.add(mascota1);

                    }
                    CargarRecicleView((ArrayList<Mascotas>) lstMascotas);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("city", "Leon");
                return parametros;
            }
        };

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void jsonBuscarPorRol(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);


                    //obtenemos los datos que devuelve el servidor
                    for (int i = 0; i < jsonArray.length(); i++) {


                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Mascotas mascota1 = new Mascotas();
                        mascota1.setId(jsonObject.getInt("id"));
                        mascota1.setTitulo(jsonObject.getString("titulo"));
                        mascota1.setImagen(jsonObject.getString("imagen"));
                        mascota1.setTipo(jsonObject.getString("tipo"));
                        mascota1.setRaza(jsonObject.getString("raza"));
                        mascota1.setSexo(jsonObject.getString("sexo"));
                        mascota1.setDescripcion(jsonObject.getString("descripcion"));
                        mascota1.setId_rolMascota(jsonObject.getInt("id_rolMascota"));
                        mascota1.setCiudad(jsonObject.getString("ciudad"));
                        mascota1.setId_usuario(jsonObject.getInt("id_usuario"));
                        lstMascotas.add(mascota1);

                    }
                    CargarRecicleView((ArrayList<Mascotas>) lstMascotas);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("city", "Leon");
                parametros.put("rol", rol_buscar+"");
                return parametros;
            }
        };

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }


    public void CargarRecicleView(ArrayList<Mascotas> mascotas) {
        //Creamos el adapter con la clase que hemos creado previamente
        //pasandole los parametros


        adapter = new RecyclerViewAdapter(getContext(),mascotas);

        ((RecyclerViewAdapter) adapter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), DetalleMascota.class);

                intent.putExtra("id",lstMascotas.get(recyclerView.getChildAdapterPosition(view)).getId());
                intent.putExtra("titulo",lstMascotas.get(recyclerView.getChildAdapterPosition(view)).getTitulo());
                intent.putExtra("imagen",lstMascotas.get(recyclerView.getChildAdapterPosition(view)).getImagen());
                intent.putExtra("descripcion",lstMascotas.get(recyclerView.getChildAdapterPosition(view)).getDescripcion());
                intent.putExtra("tipo",lstMascotas.get(recyclerView.getChildAdapterPosition(view)).getTipo());
                intent.putExtra("raza",lstMascotas.get(recyclerView.getChildAdapterPosition(view)).getRaza());
                intent.putExtra("sexo",lstMascotas.get(recyclerView.getChildAdapterPosition(view)).getSexo());
                intent.putExtra("id_rolMascota",lstMascotas.get(recyclerView.getChildAdapterPosition(view)).getId_rolMascota());
                intent.putExtra("ciudad",lstMascotas.get(recyclerView.getChildAdapterPosition(view)).getCiudad());
                intent.putExtra("id_usuario",lstMascotas.get(recyclerView.getChildAdapterPosition(view)).getId_usuario());

                startActivityForResult(intent,1);
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //requestCode == 1 --> DestalleMascota
        if (requestCode == 1) {
            //ResultCode:
            // 1--> Si vienes de borrar el registro
            // 2--> Si vienes de editar el registro
            //
            if (resultCode == 1) {
                for (int i = 0; i < lstMascotas.size(); i++) {
                    if(lstMascotas.get(i).getId() == data.getIntExtra("id_mascota",0)) {
                        lstMascotas.remove(i);
                        break;
                    }
                }

            }else if(resultCode == 2){
                for (int i = 0; i < lstMascotas.size(); i++) {
                    if(lstMascotas.get(i).getId() == data.getIntExtra("id_mascota",0)) {
                        lstMascotas.get(i).setTitulo(data.getStringExtra("titulo"));
                        lstMascotas.get(i).setImagen(data.getStringExtra("imagen"));
                        lstMascotas.get(i).setDescripcion(data.getStringExtra("descripcion"));
                        lstMascotas.get(i).setSexo(data.getStringExtra("sexo"));
                        lstMascotas.get(i).setTipo(data.getStringExtra("tipo"));
                        lstMascotas.get(i).setRaza(data.getStringExtra("raza"));
                        lstMascotas.get(i).setId_rolMascota(data.getIntExtra("id_rolMascota",0));
                        lstMascotas.get(i).setCiudad(data.getStringExtra("ciudad"));
                        Log.i("ooooooooooooo", "entra");
                        break;
                    }
                }
            }
            //actualizas el adapter
            adapter.notifyDataSetChanged();
        }

    }
}