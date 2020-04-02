package com.academy.shoplist.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProdottoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        ShoplistDatabaseManager.getInstance(MainActivity.this).getProdotti();
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
                //Toast.makeText(MainActivity.this,"hai cliccato l'elemento "+Singleton.getIstance().prodotti.get(position).getNome(),Toast.LENGTH_LONG).show();
                Cursor cursore=ShoplistDatabaseManager.getInstance(MainActivity.this).getProdotti();
                ArrayList<Prodotto> listaProdotti= ShoplistDatabaseManager.getInstance(MainActivity.this).getProdottiByCursor(cursore);
                Prodotto prodotto=listaProdotti.get(position);
                Toast.makeText(MainActivity.this,"hai cliccato l'elemento " + adapter.prodottiList.get(position).getNome(),Toast.LENGTH_LONG).show();
                refresh();
            }

            @Override
            public void onItemElimina(final int position) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Elimina Prodotto");
                builder.setMessage("Sei sicuro di voler cancellare il prodotto selezionato?");
                builder.setCancelable(true);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            Cursor cursore=ShoplistDatabaseManager.getInstance(MainActivity.this).getProdotti();
                          ArrayList<Prodotto> listaProdotti= ShoplistDatabaseManager.getInstance(MainActivity.this).getProdottiByCursor(cursore);
                            Prodotto prodotto=listaProdotti.get(position);
                            ShoplistDatabaseManager.getInstance(MainActivity.this).deleteProdotto(prodotto.getNome());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        refresh();
                    }
                });
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.show();
            }

        });

    }
}

//hello home
