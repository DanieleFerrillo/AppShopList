package com.academy.shoplist.singleton;

import com.academy.shoplist.bean.Prodotto;

import java.util.ArrayList;

public class Singleton {

    public static Singleton istanza=new Singleton();
    public static ArrayList<Prodotto> prodotti=new ArrayList<>();

    public Singleton(){

    }
public static Singleton getIstance(){
        //controlla che l'istanza non sia vuota
        if(istanza==null){
            istanza=new Singleton();
        }
        return istanza;
}

public void addProdotToList(Prodotto p){
        prodotti.add(p);
}


}
