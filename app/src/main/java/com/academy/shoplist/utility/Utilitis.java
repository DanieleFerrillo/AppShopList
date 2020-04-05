package com.academy.shoplist.utility;

import android.text.TextUtils;
import android.widget.EditText;

import java.util.HashMap;

public class Utilitis {
    public static HashMap<Boolean, String> validaInput(EditText editX, EditText editY) {
        HashMap<Boolean, String> mapReturn = new HashMap<>();

        if (TextUtils.isEmpty(editX.getText().toString())) {
            mapReturn.put(Boolean.FALSE, "Non hai inserito il nome, per favore controlla.");
        }

        if (TextUtils.isEmpty(editY.getText().toString())) {
            if (mapReturn.containsKey(Boolean.FALSE)) {
                String stringErrore = mapReturn.get(Boolean.FALSE);
                stringErrore += "\n" + " Non hai inserito la descrizione, per favore controlla.";
                mapReturn.put(Boolean.FALSE, stringErrore);
            } else {
                mapReturn.put(Boolean.FALSE, " Non hai inserito la descrizione, per favore controlla.");
            }
        }
        return mapReturn;
    }
}
