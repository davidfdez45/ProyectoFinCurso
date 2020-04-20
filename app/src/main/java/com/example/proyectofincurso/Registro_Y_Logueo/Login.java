package com.example.proyectofincurso.Registro_Y_Logueo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofincurso.MiMenu;
import com.example.proyectofincurso.MiPerfil;
import com.example.proyectofincurso.DetalleMascota;
import com.example.proyectofincurso.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;



public class Login extends AppCompatActivity {

    EditText usuario, contraseña;
    Button btnLogin;
    TextView registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        usuario=findViewById(R.id.usuario);
        contraseña=findViewById(R.id.contraseña);
        btnLogin= findViewById(R.id.btnLogin);
        registrarse = findViewById(R.id.tvRegistrar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarUsuario("http://192.168.56.1//PhpProject2/loginProyecto.php");

            }
        });
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });


    }
    private void validarUsuario(String URL){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.trim().equals("-1")){
                    Toast.makeText(getApplicationContext(),"Usuario incorrecto",Toast.LENGTH_LONG).show();

                }else if(response.trim().equals("0")){
                    Toast.makeText(getApplicationContext(),"Contraseña incorrecta",Toast.LENGTH_LONG).show();

                }else{

                    try {

                        JSONArray jsonArray = new JSONArray(response);
                        //obtenemos los datos que devuelve el servidor
                        int id_usuario = jsonArray.getJSONObject(0).getInt("id");
                        String usuario = jsonArray.getJSONObject(0).getString("user");
                        String contraseña = jsonArray.getJSONObject(0).getString("password");
                        int rol = jsonArray.getJSONObject(0).getInt("id_rol");
                        String nombre = jsonArray.getJSONObject(0).getString("name");
                        String apellido = jsonArray.getJSONObject(0).getString("lastname");
                        String contacto = jsonArray.getJSONObject(0).getString("contact");

                        //Guardamos los datos del usuario en preferencias para acceder
                        //a ellos cuando los necesitemos
                        SharedPreferences prefs = getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("id_usuario", id_usuario);
                        editor.putString("usuario",usuario);
                        editor.putString("contraseña",contraseña);
                        editor.putInt("rol",rol);
                        editor.putString("nombre",nombre);
                        editor.putString("apellido",apellido);
                        editor.putString("contacto",contacto);
                        editor.commit();


                        if(rol ==0){
                            //Administrador
                            Intent intent = new Intent(getApplicationContext(), DetalleMascota.class);
                            startActivity(intent);
                        }else if(rol==1){
                            //Usuario Normal
                            Intent intent = new Intent(getApplicationContext(), MiMenu.class);
                            intent.putExtra("id_usuario",id_usuario);
                            intent.putExtra("usuario",usuario);
                            intent.putExtra("contraseña",contraseña);
                            intent.putExtra("rol",rol);
                            intent.putExtra("nombre",nombre);
                            intent.putExtra("apellido",apellido);
                            intent.putExtra("contacto",contacto);
                            startActivity(intent);

                        }else if(rol==2){
                            //Usuario Premium
                            Intent intent = new Intent(getApplicationContext(), MiPerfil.class);
                            startActivity(intent);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("user",usuario.getText().toString());
                parametros.put("password",contraseña.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}