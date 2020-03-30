package com.academy.shoplist.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.academy.shoplist.R;
import com.academy.shoplist.constants.DbConstants;
import com.academy.shoplist.singleton.Singleton;
import com.academy.shoplist.adapter.ProdottoAdapter;
import com.academy.shoplist.bean.Prodotto;
import com.academy.shoplist.interfaccia.GestioneClick;
import com.academy.shoplist.singleton.ShoplistDatabaseManager;
import com.academy.shoplist.utility.Utilitis;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProdottoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        ShoplistDatabaseManager.getInstance(MainActivity.this).addProdotto(new Prodotto(R.drawable.washing_machine,"nome di test","desc di test"));
      setUp();

        Button aggiungi=(Button)findViewById(R.id.btn_aggiungi);

        aggiungi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent2ndActivity = new Intent(MainActivity.this, MainActivity2.class);
                startActivityForResult(intent2ndActivity,100);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            refresh();
        }
    }
    private void setUp(){
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        adapter=new ProdottoAdapter(ShoplistDatabaseManager.getInstance(MainActivity.this).getProdottiByCursor(ShoplistDatabaseManager.getInstance(MainActivity.this).getProdotti()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void refresh(){
        setUp();
        adapter.setOnItemClickListener(new GestioneClick() {
            @Override
            public void onItemClick(int position) {
                //TODO fare un'altra activity che riporta la schermata con il prodotto che hai selezionato
                Toast.makeText(MainActivity.this,"hai cliccato l'elemento "+Singleton.getIstance().prodotti.get(position).getNome(),Toast.LENGTH_LONG).show();
                refresh();
            }

            @Override
            public void onItemElimina(int position) {
//Singleton.getIstance().prodotti.remove(position)
                ShoplistDatabaseManager.getInstance(MainActivity.this).deleteProdottoByIdD(position);
                refresh();
            }

            @Override
            //TODO qui va il fragment
            public void onItemModifica(int position) {

            }
        });

    }
}
