package com.example.jroslindo.gerenciador_de_memoria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner spinner_tamanho_memoria = (Spinner) findViewById(R.id.spinner_tamanho_memoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tamanho_da_memoria, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tamanho_memoria.setAdapter(adapter);

        Spinner spinner_tamanho_pagina = (Spinner) findViewById(R.id.spinner_tamanho_pagina);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.tamanho_da_pagina, android.R.layout.simple_spinner_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tamanho_pagina.setAdapter(adapter2);
    }


    public void funcao_button_sair (View quemclicou){
        finishAffinity();
    }
}
