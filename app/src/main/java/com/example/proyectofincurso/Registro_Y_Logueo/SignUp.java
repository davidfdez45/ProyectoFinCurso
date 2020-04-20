package com.example.proyectofincurso.Registro_Y_Logueo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofincurso.R;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText nombre;
    EditText apellidos;
    EditText usuario;
    EditText contraseña;
    EditText contraseña2;
    EditText contacto;
    Button loguearse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nombre = findViewById(R.id.nombre);
        apellidos = findViewById(R.id.apellidos);
        usuario = findViewById(R.id.usuario);
        contraseña = findViewById(R.id.contraseña);
        contraseña2 = findViewById(R.id.contraseña2);
        contacto = findViewById(R.id.contacto);
        loguearse = findViewById(R.id.btnLogin);

        loguearse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().toString().isEmpty() || usuario.getText().toString().isEmpty() || contraseña.getText().toString().isEmpty() || contraseña2.getText().toString().isEmpty() || contacto.getText().toString().isEmpty()) {
                    Toast.makeText(SignUp.this, "Campos Obligatorios Vacios", Toast.LENGTH_SHORT).show();
                } else {
                    if (apellidos.getText().toString().isEmpty()) {
                        apellidos.setText("0");
                    }
                    if(contraseña.getText().toString().equals(contraseña2.getText().toString())){
                        addUsuario("http://192.168.56.1/PhpProject2/insertarUsuario.php");
                    }else{
                        Toast.makeText(SignUp.this, "La Contraseña no coincide", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }

    public void addUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.trim().equals("0")) {
                    Toast.makeText(SignUp.this, "Este usuario ya existe", Toast.LENGTH_SHORT).show();
                } else if (response.trim().equals("1")) {
                    Toast.makeText(SignUp.this, "Insertado Correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (response.trim().equals("2")) {
                    Toast.makeText(SignUp.this, "Error en la insercion", Toast.LENGTH_SHORT).show();
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

                parametros.put("user", usuario.getText().toString());
                parametros.put("password", contraseña.getText().toString());
                parametros.put("name", nombre.getText().toString());
                parametros.put("lastname", apellidos.getText().toString());
                parametros.put("contact", contacto.getText().toString());

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
