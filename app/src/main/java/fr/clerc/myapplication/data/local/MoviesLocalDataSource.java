package fr.clerc.myapplication.data.local;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import fr.clerc.myapplication.MyApplication;


public class MoviesLocalDataSource {

    private static final String FILENAME = "favorites_movies.txt";

    private static volatile MoviesLocalDataSource sInstance;


    private MoviesLocalDataSource() {}

    public static MoviesLocalDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new MoviesLocalDataSource();
        }
        return sInstance;
    }


    public ArrayList<Integer> getAllFavoriteMoviesIds() {
        ArrayList<String> listFavorites = readFromFile(MyApplication.getAppContext());
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < listFavorites.size(); i++) {
            list.add(Integer.parseInt(listFavorites.get(i)));
        }
        return list;
    }

    public boolean isFavorite(int movieId) {
        ArrayList<String> listFavorites = readFromFile(MyApplication.getAppContext());
        return listFavorites.contains(Integer.toString(movieId));
    }

    public void favoriteMovie(int movieId) {
        ArrayList<String> listFavorites = readFromFile(MyApplication.getAppContext());
        if (!listFavorites.contains(Integer.toString(movieId))) {
            appendToFile(Integer.toString(movieId), MyApplication.getAppContext());
        }
    }

    public void unfavoriteMovie(int movieId) {
        ArrayList<String> listFavorites = readFromFile(MyApplication.getAppContext());
        int index = listFavorites.indexOf(Integer.toString(movieId));
        writeToFile("", MyApplication.getAppContext());
        if (index!=-1) {
            listFavorites.remove(index);
            for (String s : listFavorites) {
                appendToFile(s, MyApplication.getAppContext());
            }
        }
    }


    private void appendToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILENAME, Context.MODE_APPEND));
            outputStreamWriter.append(data+"\n");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write("");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private ArrayList<String> readFromFile(Context context) {
        ArrayList<String> favoris = new ArrayList<String>();
/*        String ret = "";*/

        try {
            InputStream inputStream = context.openFileInput(FILENAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    favoris.add(receiveString.trim());
                    //stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                //ret = stringBuilder.toString();

            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return favoris;
    }
}
