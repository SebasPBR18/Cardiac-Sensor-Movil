package com.example.cardiac_sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cardiac_sensor.client.reotrfit.Pulso;
import com.example.cardiac_sensor.client.reotrfit.PulsoApi;
import com.example.cardiac_sensor.client.reotrfit.RetrofitService;
import com.example.cardiac_sensor.client.reotrfit.UserApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerPulso extends AppCompatActivity {
    private EditText et_pulso;
    private EditText et_fecha;
    private EditText et_riesgoInfarto;
    Pulso result= new Pulso();
    List lista= new ArrayList<>();
    TextView tv_pulso;
    //Crear una instancia de Timer
    Timer timer = new Timer();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pulso);

        et_pulso = (EditText) findViewById(R.id.editText_pulso);
        et_fecha = (EditText) findViewById(R.id.editText_fecha);
        et_riesgoInfarto = (EditText) findViewById(R.id.editText_riesgo);


        tv_pulso = findViewById(R.id.textView_pulso);
        TextView tv_fecha = findViewById(R.id.textView_fecha);
        TextView tv_riesgoInfarto = findViewById(R.id.textView_riesgo);


        //Crear una instancia de TimeTask
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //Llamar al método cada segundo aquí
                try {
                    getDataMovil();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        //Ejecutar el método cada segundo
        timer.scheduleAtFixedRate(timerTask, 0, 1000);

       /* tv_pulso.setText(result.get(0).toString());
        tv_fecha.setText("FECHA");
        tv_riesgoInfarto.setText("RIESGO DE INFARTO");*/


    }




    private List getDataMovil() throws IOException {
        List lista= new ArrayList<>();
        RetrofitService retrofitService2 = new RetrofitService();
        PulsoApi plApi = retrofitService2.getRetrofit().create(PulsoApi.class);
        Call<Pulso> call = plApi.getDataMovil();
        call.enqueue(new Callback<Pulso>() {
            @Override
            public void onResponse(Call<Pulso> call, Response<Pulso> response) {
                if (response.isSuccessful()) {
                    // aquí obtienes la respuesta
                    result = response.body();


                  tv_pulso.setText(result.getCantpulsaciones().toString());
                } else {
                    // aquí obtienes un código de error
                    int errorCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<Pulso> call, Throwable t) {
                // aquí obtienes una excepción
                t.printStackTrace();
            }

        });

        return lista;
    }
}