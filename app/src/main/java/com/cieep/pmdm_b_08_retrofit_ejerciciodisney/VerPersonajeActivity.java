package com.cieep.pmdm_b_08_retrofit_ejerciciodisney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cieep.pmdm_b_08_retrofit_ejerciciodisney.conexiones.ApiConexiones;
import com.cieep.pmdm_b_08_retrofit_ejerciciodisney.conexiones.RetrofitObject;
import com.cieep.pmdm_b_08_retrofit_ejerciciodisney.modelos.Personaje;
import com.squareup.picasso.Picasso;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerPersonajeActivity extends AppCompatActivity {

    private ImageView imgPersonaje;
    private TextView lblNombre;
    private TextView lblFilms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_personaje);

        imgPersonaje = findViewById(R.id.imgPersonajeVer);
        lblNombre = findViewById(R.id.lblNombrePersonajeVer);
        lblFilms = findViewById(R.id.lblFilmsPersonajeVer);

        if (getIntent().getExtras() != null && getIntent().getExtras().getString("ID") != null) {
            ApiConexiones api = RetrofitObject.getConexion().create(ApiConexiones.class);
            Call<Personaje> doGetPersonaje = api.getPersonaje(getIntent().getExtras().getString("ID"));

            doGetPersonaje.enqueue(new Callback<Personaje>() {
                @Override
                public void onResponse(Call<Personaje> call, Response<Personaje> response) {
                    if (response.code() == HttpsURLConnection.HTTP_OK) {
                        lblNombre.setText(response.body().getName());
                        lblFilms.setText("");
                        for (String film: response.body().getFilms()) {
                            lblFilms.setText(lblFilms.getText()+"\n"+film);
                        }
                        Picasso.get()
                                .load(response.body().getImageUrl())
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.ic_launcher_background)
                                .into(imgPersonaje);
                    }
                }

                @Override
                public void onFailure(Call<Personaje> call, Throwable t) {

                }
            });
        }
    }
}