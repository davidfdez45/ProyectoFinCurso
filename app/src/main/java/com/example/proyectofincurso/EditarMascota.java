package com.example.proyectofincurso;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditarMascota extends AppCompatActivity {

    EditText editarTitulo;
    EditText editarImagen;
    Spinner editarTipo;
    EditText editarRaza;
    Spinner editarSexo;
    EditText editarDescripcion;
    Spinner editarCiudad;
    Button editar;
    RadioGroup radioGroup;
    RadioButton AnimalPerdido;
    RadioButton AnimalEncontradoo;
    RadioButton AnimalAdopcion;
    //Variables donde guardaremos los datos del intent
    int id_mascota;
    String imagen1;
    String titulo1;
    String sexo1;
    String raza1;
    String tipo1;
    String descripcion1;
    String ciudad1;
    int id_Usuario;
    int rol_mascota;
    int id_usuario_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mascota);

        //recogemos el id del usuario que esta usando la app
        SharedPreferences prefs = getSharedPreferences("DatosUsuario", 0);
        id_usuario_preferences = prefs.getInt("id_usuario", 0);

        //les asignamos su vista xml
        editarTitulo = findViewById(R.id.editarTitulo);
        editarImagen = findViewById(R.id.editarImagen);
        editarTipo = findViewById(R.id.editarTipo);
        editarRaza = findViewById(R.id.editarRaza);
        editarSexo = findViewById(R.id.editarSexo);
        editarDescripcion = findViewById(R.id.editarDescripcion);
        editarCiudad = findViewById(R.id.editarCiudad);
        editar = findViewById(R.id.editar);
        radioGroup = findViewById(R.id.editarRol);
        AnimalPerdido = findViewById(R.id.idAnimalPerdido);
        AnimalEncontradoo = findViewById(R.id.idAnimalEncontrado);
        AnimalAdopcion = findViewById(R.id.idAnimalEnAdopcion);

        //Recogemos los valores del intent
        Intent intent= getIntent();
        id_mascota = intent.getIntExtra("id",0);
        titulo1 = intent.getStringExtra("titulo");
        imagen1 = intent.getStringExtra("imagen");
        sexo1 = intent.getStringExtra("sexo");
        raza1 = intent.getStringExtra("raza");
        tipo1 = intent.getStringExtra("tipo");
        descripcion1 = intent.getStringExtra("descripcion");
        ciudad1 = intent.getStringExtra("ciudad");
        rol_mascota = intent.getIntExtra("id_rolMascota",0);
        id_Usuario = intent.getIntExtra("id_usuario",0);

        //Ponemos los campos de los intent en los Texview/Imageview/Editext....ect
        editarTitulo.setText(titulo1);
        editarImagen.setText(imagen1);
        editarRaza.setText(raza1);
        editarDescripcion.setText(descripcion1);

        //comparamos los valores del spinner con el String que contiene el sexo,tipo y ciudad de la mascota selecionada
        editarSexo.setSelection(((ArrayAdapter<String>)editarSexo.getAdapter()).getPosition(sexo1));
        editarTipo.setSelection(((ArrayAdapter<String>)editarTipo.getAdapter()).getPosition(tipo1));
        editarCiudad.setSelection(((ArrayAdapter<String>)editarCiudad.getAdapter()).getPosition(ciudad1));


        //Segun el id del rol que tengamos marcamos un radiobutton u otro
        if(rol_mascota == 1){
            AnimalPerdido.setChecked(true);
        }else if(rol_mascota == 2){
            AnimalEncontradoo.setChecked(true);
        }else if(rol_mascota ==3){
            AnimalAdopcion.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idSeleccionado = radioGroup.getCheckedRadioButtonId();
                if(idSeleccionado == AnimalPerdido.getId()){
                    rol_mascota = 1;
                }else if(idSeleccionado == AnimalEncontradoo.getId()){
                    rol_mascota = 2;
                }else if(idSeleccionado == AnimalAdopcion.getId()){
                    rol_mascota = 3;
                }
            }
        });




        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarMascota("http://192.168.56.1/PhpProject2/editarMascota.php");
            }
        });


    }

    private void editarMascota(String URL) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.trim().equals("1")){
                    Toast.makeText(getApplicationContext(), "Editado", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.putExtra("id_mascota",id_mascota);
                    intent.putExtra("titulo",editarTitulo.getText().toString());
                    intent.putExtra("imagen",editarImagen.getText().toString());
                    intent.putExtra("tipo",editarTipo.getSelectedItem().toString());
                    intent.putExtra("raza",editarRaza.getText().toString());
                    intent.putExtra("sexo",editarSexo.getSelectedItem().toString());
                    intent.putExtra("descripcion",editarDescripcion.getText().toString());
                    intent.putExtra("id_rolMascota", rol_mascota);
                    intent.putExtra("ciudad",editarCiudad.getSelectedItem().toString());
                    setResult(RESULT_OK,intent);
                    finish();


                }else if(response.trim().equals("2")){
                    Toast.makeText(getApplicationContext(), "Error en el borrado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
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

                parametros.put("id",id_mascota+"");
                parametros.put("titulo",editarTitulo.getText().toString());
                parametros.put("imagen",editarImagen.getText().toString());
                parametros.put("tipo",editarTipo.getSelectedItem().toString());
                parametros.put("raza",editarRaza.getText().toString());
                parametros.put("sexo",editarSexo.getSelectedItem().toString());
                parametros.put("descripcion",editarDescripcion.getText().toString());
                parametros.put("ciudad",editarCiudad.getSelectedItem().toString());
                parametros.put("id_usuario",id_usuario_preferences+"");
                parametros.put("id_rolMascota", rol_mascota+"");
                return parametros;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}
