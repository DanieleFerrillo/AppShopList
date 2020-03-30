package com.academy.shoplist.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.academy.shoplist.R;
import com.academy.shoplist.singleton.ShoplistDatabaseManager;
import com.academy.shoplist.singleton.Singleton;
import com.academy.shoplist.bean.Prodotto;
import com.academy.shoplist.utility.Utilitis;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    EditText nome;
    EditText descrizione;
    Button bottone;
    Button bottone1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.second_activity);
        nome = (EditText) findViewById(R.id.testo1);
        descrizione = (EditText) findViewById(R.id.testo2);
        bottone=(Button)findViewById(R.id.aggiungi);
        bottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utilitis.validaInput(nome, descrizione).containsKey(false)) {
                    String testoErrore=Utilitis.validaInput(nome,descrizione).get(false);
                    Toast.makeText(MainActivity2.this, testoErrore,Toast.LENGTH_LONG).show();

                }else{
                    Prodotto aggiuntivo = new Prodotto(R.drawable.coffee, nome.getText().toString(), descrizione.getText().toString());
                    ShoplistDatabaseManager.getInstance(MainActivity2.this).addProdotto(aggiuntivo);
                   // Singleton.getIstance().addProdotToList(aggiuntivo);
                    finish();
                }


            }
        });
        bottone1=(Button)findViewById(R.id.uccidi_activity);
        bottone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
