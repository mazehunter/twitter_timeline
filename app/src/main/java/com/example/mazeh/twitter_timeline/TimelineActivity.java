package com.example.mazeh.twitter_timeline;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterListener;
import twitter4j.TwitterMethod;

public class TimelineActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //Buscamos a ver si hay algún fragmento en el contenedor
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);

        // En caso de que no haya ningún fragmento, obtenemos los Tweets del Timeline de la API de twitter y manejamos el Callback
        if (fragment == null) {
            TweetRepository.getInstance().getTimelineAsync(timelineListener); // => timelineListener
        } else {
            View progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
        }
    }

    // Esta clase interna es la encargada de manejar el callback,  tiene dos métodos para manejar la posibilidad de éxito y de error.
    TwitterListener timelineListener = new TwitterAdapter() {

        @Override
        public void gotHomeTimeline(ResponseList<Status> statuses) {
            showTimeline(statuses);
        }

        @Override
        public void onException(TwitterException te, TwitterMethod method) {

        }
    };

    private void showTimeline(ResponseList<Status> statuses) {
        // Creamos un array de Strings con el texto de los Status( Tweets )
        String[] tweets = new String[statuses.size()];
        int counter = 0;
        for (Status status : statuses) {
            tweets[counter] = status.getText();
            counter++;
        }

        // Lo guardamos en un bundle, el cual le pasaremos al Fragment
        final Bundle bundle = new Bundle();
        bundle.putStringArray("tweets", tweets);

        // Debido a que el callback se está ejecutando en otro Thread distinto al Thread de UI, necesitamos mandar un mensaje
        // Al Thread de UI para poder actualizar la vista, para ello usamos el método runOnUiThread de la clase Activity
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // Ocultamos la barra de progreso
                View progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.GONE);

                // Insertamos el TimelineFragment
                Fragment fragment = new TimelineFragment();
                //Añadimos el bundle con los tweets que hemos creado anteriormente
                fragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, fragment)
                        .commit();
            }
        });
    }
}


