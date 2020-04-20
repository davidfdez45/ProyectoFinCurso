package com.example.proyectofincurso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddMascota extends AppCompatActivity {
    EditText addTitulo;
    EditText addImagen;
    Spinner addTipo;
    EditText addRaza;
    Spinner addSexo;
    EditText addDescripcion;
    Spinner addCiudad;
    Button Publicar;
    RadioGroup radioGroup;
    RadioButton AnimalPerdido;
    RadioButton AnimalEncontradoo;
    RadioButton AnimalAdopcion;
    String id_rolMascota = "";
    int id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mascota);

        //recogemos el valor del intent (el id del usuario)
        id_usuario = getIntent().getIntExtra("id_usuario",0);

        //les asignamos su vista xml
        addTitulo = findViewById(R.id.addTitulo);
        addImagen = findViewById(R.id.addImagen);
        addTipo = findViewById(R.id.addTipo);
        addRaza = findViewById(R.id.addRaza);
        addSexo = findViewById(R.id.addSexo);
        addDescripcion = findViewById(R.id.addDescripcion);
        addCiudad = findViewById(R.id.addCiudad);
        Publicar = findViewById(R.id.publicar);
        radioGroup = findViewById(R.id.addRol);
        AnimalPerdido = findViewById(R.id.idAnimalPerdido);
        AnimalEncontradoo = findViewById(R.id.idAnimalEncontrado);
        AnimalAdopcion = findViewById(R.id.idAnimalEnAdopcion);

        //RadioGroup del Rol de la mascota
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idSeleccionado = radioGroup.getCheckedRadioButtonId();
                if(idSeleccionado == AnimalPerdido.getId()){
                    id_rolMascota = "1";
                }else if(idSeleccionado == AnimalEncontradoo.getId()){
                    id_rolMascota = "2";
                }else if(idSeleccionado == AnimalAdopcion.getId()){
                    id_rolMascota = "3";
                }
            }
        });


        Publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addTitulo.getText().toString().isEmpty() || addDescripcion.getText().toString().isEmpty()){
                    //Vacio
                    Toast.makeText(AddMascota.this, "Campos Obligatorios Vacios", Toast.LENGTH_SHORT).show();
                }else{
                    //lleno
                    if(addImagen.getText().toString().isEmpty() ){
                        addImagen.setText(" ");
                    }if(addRaza.getText().toString().isEmpty()){
                        addRaza.setText("0");
                    }
                    addMascota("http://192.168.56.1/PhpProject2/insertarMascotaProyecto.php");
                }
            }
        });

    }
    public void addMascota(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.trim().equals("1")){
                    Toast.makeText(AddMascota.this, "insertado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }else if(response.trim().equals("2")){
                    Toast.makeText(AddMascota.this, "error en la insercion", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo",addTitulo.getText().toString());
                parametros.put("imagen",addImagen.getText().toString());
                parametros.put("tipo",addTipo.getSelectedItem().toString());
                parametros.put("raza",addRaza.getText().toString());
                parametros.put("sexo",addSexo.getSelectedItem().toString());
                parametros.put("descripcion",addDescripcion.getText().toString());
                parametros.put("ciudad",addCiudad.getSelectedItem().toString());
                parametros.put("id_usuario",id_usuario+"");
                parametros.put("id_rolMascota", id_rolMascota);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}


