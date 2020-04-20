package com.example.proyectofincurso;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofincurso.model.Mascotas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrincipalUsuario extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private List<Mascotas> lstMascotas;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principalusuario);

        recyclerView=findViewById(R.id.recyclerviewid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        listView = findViewById(R.id.listview);
        lstMascotas = new ArrayList<>();
        jsonrequest("http://192.168.56.1/PhpProject2/listaMascotasProyecto.php");
        Log.i("mascotas 2.0 :", lstMascotas.size() + "");




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
                        mascota1.setId_rolMascota(jsonObject.getInt("id_rolMascota"));
                        mascota1.setCiudad(jsonObject.getString("ciudad"));
                        mascota1.setId_usuario(jsonObject.getInt("id_usuario"));
                        lstMascotas.add(mascota1);


                        //Mascotas Mascota = new Mascotas(jsonObject.getInt("id"), jsonObject.getString("titulo"), jsonObject.getString("imagen"),
                        //      jsonObject.getString("tipo"), jsonObject.getString("raza"), jsonObject.getString("sexo"),
                        //    jsonObject.getInt("id_rolMascota"), jsonObject.getString("ciudad"), jsonObject.getInt("idUsuario"));

                    }
                    Log.i("MASCOTAS:", lstMascotas.size() + "");
                    CargarListView((ArrayList<Mascotas>) lstMascotas);
                    // adapter = new RecyclerViewAdapter(lstMascotas);
                    //recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("city", "Leon");
                return parametros;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    public void CargarListView(ArrayList<Mascotas> mascotas){
        ArrayList<String> titulos= new ArrayList<>();
        for (int i = 0; i < mascotas.size(); i++) {
            titulos.add(mascotas.get(i).getTitulo());
        }
        ArrayAdapter<String> nuevoadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titulos);
        listView.setAdapter(nuevoadapter);
    }


}

