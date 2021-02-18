package kg.geektech.android3.lesson_7;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Prefs {
    private static SharedPreferences preferences;
    private String name;
    public Prefs(Context context){
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void putCoords(String KEY, List<LatLng> lngList){
        Gson gson = new Gson();
        String json = gson.toJson(lngList);
        preferences.edit().putString(KEY,json).apply();
    }
    public void putPolyLines(String KEY, List<PolylineOptions> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        preferences.edit().putString(KEY,json).apply();
    }
    public void putPolygons(String KEY, List<PolygonOptions> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        preferences.edit().putString(KEY,json).apply();
    }
    public List<LatLng> getCoords(String KEY){
        List<LatLng> coords = new ArrayList<>();
        String converted = preferences.getString(KEY, null);
        if (converted != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<LatLng>>(){}.getType();
            coords = gson.fromJson(converted, type);
        }
        return coords;
    }
    public List<PolylineOptions> getPolyLines(String KEY){
        List<PolylineOptions> polyLines = new ArrayList<>();
        String converted = preferences.getString(KEY, null);
        if (converted != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<PolylineOptions>>(){}.getType();
            polyLines = gson.fromJson(converted, type);
        }
        return polyLines;
    }
    public List<PolygonOptions> getPolygons(String KEY){
        List<PolygonOptions> polygons = new ArrayList<>();
        String converted = preferences.getString(KEY, null);
        if (converted != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<PolygonOptions>>(){}.getType();
            polygons = gson.fromJson(converted, type);
        }
        return polygons;
    }
    public void clearPrefs(){
        preferences.edit().clear().apply();
    }
}
