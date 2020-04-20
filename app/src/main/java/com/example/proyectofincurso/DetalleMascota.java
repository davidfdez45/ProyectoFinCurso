package com.example.proyectofincurso;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class DetalleMascota extends AppCompatActivity {

    ImageView imagen;
    TextView titulo;
    TextView sexo;
    TextView raza;
    TextView tipo;
    TextView descripcion;
    TextView ciudad;
    TextView datosUsuario;
    TextView formaContacto;

    //Variables donde guardaremos los datos del intent
    int id_mascota;
    String imagen1;
    String titulo1;
    String sexo1;
    String raza1;
    String tipo1;
    String descripcion1;
    String ciudad1;
    int rol_mascota;
    int id_Usuario;
    int id_usuario_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_mascota);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefs = getSharedPreferences("DatosUsuario", 0);
        id_usuario_preferences = prefs.getInt("id_usuario", 0);

        imagen = findViewById(R.id.imagen);
        titulo = findViewById(R.id.titulo);
        sexo = findViewById(R.id.sexo);
        raza = findViewById(R.id.raza);
        tipo = findViewById(R.id.tipo);
        descripcion = findViewById(R.id.descripcion);
        ciudad = findViewById(R.id.ciudad);
        datosUsuario = findViewById(R.id.datosUsuario);
        formaContacto = findViewById(R.id.contacto);


        Intent intent= getIntent();
        id_mascota = intent.getIntExtra("id",0);
        titulo1 = intent.getStringExtra("titulo");
        imagen1 = intent.getStringExtra("imagen");
        sexo1 = intent.getStringExtra("sexo");
        raza1 = intent.getStringExtra("raza");
        tipo1 = intent.getStringExtra("tipo");
        descripcion1 = intent.getStringExtra("descripcion");
        ciudad1 = intent.getStringExtra("ciudad");
        id_Usuario = intent.getIntExtra("id_usuario",0);
        rol_mascota = intent.getIntExtra("id_rolMascota",0);

        Picasso.with(getApplicationContext()).load(imagen1).into(imagen);
        titulo.setText(titulo1);
        sexo.setText(sexo1);
        raza.setText(raza1);
        tipo.setText(tipo1);
        descripcion.setText(descripcion1);
        ciudad.setText(ciudad1);

        jsonDetallesMascota("http://192.168.56.1/PhpProject2/detallesUsuario.php");

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        //Comprobamos que el usuario que esta usando la aplicacion es
        //el mismo que ha subido la publicacion
        //Y en caso de ser asi mostramos el boton de editar y el de borrar
        if(id_Usuario == id_usuario_preferences){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.botones_detalles_mascotas, menu);
            return super.onCreateOptionsMenu(menu);
        }else{
            return Boolean.parseBoolean(null);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_delete:
                //al pulsar el boton borrar
                mostrarDialogoBorrar();
                return true;
            case R.id.action_edit:
                //al pulsar el boton editar
                Intent intent = new Intent(getApplicationContext(),EditarMascota.class);
                intent.putExtra("id",id_mascota);
                intent.putExtra("titulo",titulo1);
                intent.putExtra("imagen",imagen1);
                intent.putExtra("descripcion",descripcion1);
                intent.putExtra("tipo",tipo1);
                intent.putExtra("raza",raza1);
                intent.putExtra("sexo",sexo1);
                intent.putExtra("id_rolMascota",rol_mascota);
                intent.putExtra("ciudad",ciudad1);
                intent.putExtra("id_usuario",id_Usuario);
                startActivityForResult(intent,3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void mostrarDialogoBorrar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.myDialog));
        builder.setTitle("Borrar Publicacion");
        builder.setMessage("¿Esta seguro de que desea borrar esta publicacion?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Si responde que si
                       jsonBorrarPublicacion("http://192.168.56.1/PhpProject2/borrarMascota.php");
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Si responde que no
                        Toast.makeText(DetalleMascota.this, "Cancelando", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).show();
    }


    private void jsonDetallesMascota(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    String nombre = jsonArray.getJSONObject(0).getString("name");
                    String apellido = jsonArray.getJSONObject(0).getString("lastname");
                    String contacto = jsonArray.getJSONObject(0).getString("contact");
                    CargarDatosUsuario(nombre,apellido,contacto);

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
                parametros.put("id_usuario", id_Usuario+"");
                return parametros;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    public  void CargarDatosUsuario(String nombre,String apellido,String contacto){
        formaContacto.setText(contacto);
        datosUsuario.setText(nombre+" "+apellido);
    }

    public void jsonBorrarPublicacion(String URL){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.trim().equals("1")){
                   Toast.makeText(getApplicationContext(), "Borrado con exito", Toast.LENGTH_SHORT).show();
                    //ResultCode:
                    // 1--> Si vienes de borrar el registro
                    // 2--> Si vienes de editar el registro
                    //
                    Intent intent=new Intent();
                    intent.putExtra("id_mascota",id_mascota);
                    setResult(1,intent);
                    finish();


               }else if(response.trim().equals("2")){
                   Toast.makeText(getApplicationContext(), "Error en el borrado", Toast.LENGTH_SHORT).show();
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
                parametros.put("id_mascota", id_mascota+"");
                parametros.put("id_usuario", id_usuario_preferences+"");
                return parametros;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                //ResultCode:
                // 1--> Si vienes de borrar el registro
                // 2--> Si vienes de editar el registro
                //
                Intent intent=new Intent();
                intent.putExtra("id_mascota",data.getIntExtra("id_mascota",0));
                intent.putExtra("titulo",data.getStringExtra("titulo"));
                intent.putExtra("imagen",data.getStringExtra("imagen"));
                intent.putExtra("tipo",data.getStringExtra("tipo"));
                intent.putExtra("raza",data.getStringExtra("raza"));
                intent.putExtra("sexo",data.getStringExtra("sexo"));
                intent.putExtra("descripcion",data.getStringExtra("descripcion"));
                intent.putExtra("id_rolMascota",data.getIntExtra("id_rolMascota",0));
                intent.putExtra("ciudad",data.getStringExtra("ciudad"));
                setResult(2,intent);
                finish();
            }
        }
    }
}
