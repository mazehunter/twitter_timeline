package com.example.mazeh.twitter_timeline;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class TimelineFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Obtenemos los tweets
        String[] tweets = getArguments().getStringArray("tweets");

        // El ArrayAdapter creará la jerarquia de vistas a partir de un Array de Objetos
        // En este caso usaremos un layout que nos proporciona Android que simplemente mostrará
        // La lista de Strings, el último parámetro es el array con los objetos a mostrar
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                tweets
        );

        // Este método es parte de la clase ListFragment y nos permitirá indicarle cual es el adaptador de la vista.
        setListAdapter(arrayAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}