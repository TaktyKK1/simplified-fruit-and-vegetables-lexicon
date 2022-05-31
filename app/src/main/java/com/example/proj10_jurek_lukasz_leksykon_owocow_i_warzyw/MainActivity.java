package com.example.proj10_jurek_lukasz_leksykon_owocow_i_warzyw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ListView roslinaListView;
    DatabaseManager databaseManager = DatabaseManager.instanceOfDatabase(this);
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        setWpisAdapter();
        setOnClickListener();
        loadFromDBToMemory();
        if(Roslina.roslinyArrayList.isEmpty()){
            Roslina roslina = new Roslina("ziemniak",
                    "Nazwa „ziemniak” odnosi się tak do całej rośliny, jak i do jej jadalnych, bogatych w skrobię bulw pędowych.",
                    "Warzywo");
            databaseManager.dodajRosline(roslina);
            roslina = new Roslina("truskawka",
                    "Roślina mieszaniec dwóch gatunków poziomki z rodziny różowatych",
                    "Owoc");
            databaseManager.dodajRosline(roslina);
            roslina = new Roslina("banan",
                    "jadalny owoc tropikalny, z botanicznego punktu widzenia – jagoda, wytwarzany przez kilka gatunków roślin zielnych z rodzaju banan",
                    "Owoc");
            databaseManager.dodajRosline(roslina);
            roslina = new Roslina("pietruszka", "gatunek rośliny dwuletniej z rodziny selerowatych", "Warzywo");
            databaseManager.dodajRosline(roslina);
            roslina = new Roslina("marchew zwyczajna", "gatunek rośliny z rodziny selerowatych.", "Warzywo");
            databaseManager.dodajRosline(roslina);
            roslina = new Roslina("jagoda",
                    "owoc o mięsistej, niepękającej owocni, zbudowanej z zewnętrznego egzokarpu oraz zmięśniałego mezokarpu, wypełniającego całe wnętrze owocu.",
                    "Owoc");
            databaseManager.dodajRosline(roslina);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        ArrayList<Roslina> filteredRosliny = new ArrayList<>();

        for(Roslina roslina : Roslina.roslinyArrayList)
        {
            if(roslina.getNazwa().toLowerCase().contains(s.toLowerCase()) || roslina.getTyp().toLowerCase().contains(s.toLowerCase()))
            {
                filteredRosliny.add(roslina);
            }
        }
        WpisAdapter wpisAdapter = new WpisAdapter(getApplicationContext(), filteredRosliny);
        roslinaListView.setAdapter(wpisAdapter);

        return false;
    }
});
    }



    private void initWidgets() {

        roslinaListView = findViewById(R.id.roslinaListView);
        searchView = findViewById(R.id.searchView);
    }

    private void loadFromDBToMemory() {
        {
                DatabaseManager databaseManager = DatabaseManager.instanceOfDatabase(this);
                databaseManager.populateRoslinyListArray();
                databaseManager.close();
        }
    }
    private void setWpisAdapter() {

        WpisAdapter wpisAdapter = new WpisAdapter(getApplicationContext(), Roslina.roslinyArrayList);
        roslinaListView.setAdapter(wpisAdapter);
    }

    private void setOnClickListener(){
        roslinaListView.setOnItemClickListener((adapterView, view, position, l) -> {
            Roslina wybranaRoslina = (Roslina) roslinaListView.getItemAtPosition(position);
            Intent editRoslinaIntent = new Intent(getApplicationContext(), WpisDefinicjaActivity.class);
            editRoslinaIntent.putExtra(Roslina.ROSLINA_EDIT_EXTRA, wybranaRoslina.getId());
            startActivity(editRoslinaIntent);
        });
    }
    public void nowyWpis(View view) {
        Intent nowyWpisIntent = new Intent(this, WpisAddEditActivity.class);
        startActivity(nowyWpisIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Roslina.roslinyArrayList.clear();
        loadFromDBToMemory();
        setWpisAdapter();
    }

    public void nameAZ(View view){
        Collections.sort(Roslina.roslinyArrayList, Roslina.nazwaAZ);
        setWpisAdapter();
    }


    public void nameZA(View view) {
        Collections.sort(Roslina.roslinyArrayList, Roslina.nazwaAZ);
        Collections.reverse(Roslina.roslinyArrayList);
        setWpisAdapter();
    }

}