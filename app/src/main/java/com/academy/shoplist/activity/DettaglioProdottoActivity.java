package com.academy.shoplist.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.academy.shoplist.R;
import com.academy.shoplist.adapter.ProdottoAdapter;
import com.academy.shoplist.bean.Prodotto;
import com.academy.shoplist.singleton.ShoplistDatabaseManager;


import java.util.ArrayList;

public class DettaglioProdottoActivity extends AppCompatActivity {

    TextView nome;
    TextView descrizione;
    Button button;
    private ProdottoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dettaglio_prodotto);
        nome = (TextView) findViewById(R.id.textView1);
        descrizione = (TextView) findViewById(R.id.textView2);

        button = (Button) findViewById(R.id.backTo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
