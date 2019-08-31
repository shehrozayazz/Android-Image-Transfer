package com.example.mysqldatasending;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    Button button;
    EditText etName,etEmail,etPass;
    String server_url="http://192.168.1.3/android/info.php";
    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button)findViewById(R.id.btnSend);
        etName=(EditText)findViewById(R.id.etName);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etPass=(EditText)findViewById(R.id.etPass);
        builder=new AlertDialog.Builder(MainActivity.this);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name,email,pass;
                name=etName.getText().toString();
                email=etEmail.getText().toString();
                pass=etPass.getText().toString();
                StringRequest stringRequest=new StringRequest(Request.Method.GET, server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                builder.setTitle("Server Response");
                                builder.setMessage("Response : "+response);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                       etName.setText("");
                                        etEmail.setText("");
                                        etPass.setText("");

                                    }
                                });

                                AlertDialog alertDialog= builder.create();
                                alertDialog.show();


                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this,"Error... ",Toast.LENGTH_LONG).show();
                        error.printStackTrace();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> params=new HashMap<String, String>();
                        params.put("name",name);
                        params.put("email",email);
                        params.put("password",pass);

                        return params;
                    }
                };

                MySingleton.getInstance(MainActivity.this).addTorequest(stringRequest);

            }
        });
    }
}
