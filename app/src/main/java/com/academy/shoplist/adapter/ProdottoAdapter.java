package com.academy.shoplist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.academy.shoplist.R;
import com.academy.shoplist.bean.Prodotto;
import com.academy.shoplist.interfaccia.GestioneClick;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProdottoAdapter extends RecyclerView.Adapter<ProdottoAdapter.ProdottoViewHolder> {
    private ArrayList<Prodotto> prodottiList = new ArrayList<>();
    private GestioneClick interfaccia;

    public void setOnItemClickListener(GestioneClick interfaccia){
        this.interfaccia=interfaccia;
    }
    public static class ProdottoViewHolder extends RecyclerView.ViewHolder {
        public ImageView immagine;
        public TextView nomeProdotto;
        public TextView descrizione;
        public ImageView elimina;
        public ImageView modifica;

        public ProdottoViewHolder(@NonNull View itemView, final GestioneClick interfaccia) {
            super(itemView);
            immagine =(ImageView) itemView.findViewById(R.id.immagine_prodotto);
            nomeProdotto = itemView.findViewById(R.id.nome_prodotto);
            descrizione = itemView.findViewById(R.id.descrizione_prodotto);
            elimina =(ImageView) itemView.findViewById(R.id.img_elimina);
            modifica =(ImageView) itemView.findViewById(R.id.img_modifica);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //interfaccia.onItemClick(getAdapterPosition());
                    if(interfaccia!=null){
                        int position=getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            interfaccia.onItemClick(position);
                        }
                    }
                }
            });
            elimina.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    interfaccia.onItemElimina(getAdapterPosition());
                }
            });
            modifica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaccia.onItemModifica(getAdapterPosition());
                }
            });
        }
    }


    public ProdottoAdapter(ArrayList<Prodotto> prodottiList) {
        this.prodottiList = prodottiList;
    }

    @NonNull
    @Override
    public ProdottoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prodotto_item, parent, false);
        ProdottoViewHolder pvh = new ProdottoViewHolder(v,interfaccia);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdottoViewHolder holder, int position) {
        Prodotto currentProdotto = prodottiList.get(position);

        holder.immagine.setImageResource(currentProdotto.getImmagine());
        holder.nomeProdotto.setText(currentProdotto.getNome());
        holder.descrizione.setText(currentProdotto.getDescrizione());
    }

    @Override
    public int getItemCount() {
        return prodottiList.size();
    }


}
