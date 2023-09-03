package com.vascodes.spaced.Common;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.vascodes.spaced.Model.Deck;

import java.util.ArrayList;

public class Utils {
    public static boolean isStringEmpty(String str){
        str = str.trim();
        return str.isEmpty() || str.equals("");
    }

    public static <T> Spinner fillSpinner(Context context, Spinner spinner, ArrayList<T> dataList){
        ArrayAdapter<T> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, dataList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        return spinner;
    }
}
