package com.example.proj10_jurek_lukasz_leksykon_owocow_i_warzyw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WpisAddEditActivity extends AppCompatActivity {

    private EditText titleEditText, descEditText;
    private Button btn_delete, btn_Zapisz;
    private Roslina wybranaRoslina;
    private RadioGroup radioGroup;
    DatabaseManager databaseManager = DatabaseManager.instanceOfDatabase(this);
    private RadioButton radioButton, radioButtonWarzywo, radioButtonOwoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wpis_addedit);
        initWidgets();
        radioButtonWarzywo.toggle();
        radioButton = radioButtonWarzywo;
        checkForEdit();
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> radioButton = (RadioButton) findViewById(checkedId));
    }
    private void initWidgets() {
        titleEditText = findViewById(R.id.txtTitle);
        descEditText = findViewById(R.id.editTextDescription);
        btn_delete = findViewById(R.id.btn_delete);
        radioGroup = findViewById(R.id.radioGroupTyp);
        radioButtonWarzywo = findViewById(R.id.radioButtonWarzywo);
        radioButtonOwoc = findViewById(R.id.radioButtonOwoc);
        btn_Zapisz = findViewById(R.id.btnZapisz);
    }

    private void checkForEdit() {
        Intent previousIntent = getIntent();
        int passedRoslinaID = previousIntent.getIntExtra(Roslina.ROSLINA_EDIT_EXTRA, -1);
        wybranaRoslina = Roslina.getRoslinaForID(passedRoslinaID);

        if (wybranaRoslina != null)
        {
            titleEditText.setText(wybranaRoslina.getNazwa());
            descEditText.setText(wybranaRoslina.getOpis());
            if(wybranaRoslina.getTyp().equalsIgnoreCase("warzywo")){
                radioButtonWarzywo.toggle();
                radioButton = radioButtonWarzywo;
            }
            else
            {
                radioButtonOwoc.toggle();
                radioButton = radioButtonOwoc;
            }
            btn_Zapisz.setText("Edytuj");

        }
        else
        {
            btn_Zapisz.setText("Zapisz");
            btn_delete.setVisibility(View.INVISIBLE);
        }
    }



    public void saveWpis(View view) {

    String title = String.valueOf(titleEditText.getText()).toLowerCase();
    String desc = String.valueOf(descEditText.getText());
    String type = String.valueOf(radioButton.getText());
        boolean flaga = false;
        for(Roslina roslina : Roslina.roslinyArrayList)
        {
            if(roslina.getNazwa().toLowerCase().equals(title)){
            flaga = true;
            break;
        }
        }
        if(wybranaRoslina!=null && wybranaRoslina.getNazwa().equalsIgnoreCase(title) && btn_Zapisz.getText().toString().equals("Edytuj"))
        {
            flaga = false;
        }
        if(flaga)
        {
            Toast.makeText(getApplicationContext(), "Taki rekord jest juz w bazie danych!", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if(title.isEmpty() || desc.isEmpty() || type.isEmpty()){
                Toast.makeText(getApplicationContext(), "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(wybranaRoslina == null)
            {
            Roslina nowaRoslina = new Roslina(title, desc, type);
            Roslina.roslinyArrayList.add(nowaRoslina);
            databaseManager.dodajRosline(nowaRoslina);
        }
        else
        {
            wybranaRoslina.setNazwa(title);
            wybranaRoslina.setOpis(desc);
            wybranaRoslina.setTyp(type);
            databaseManager.updateRoslinaInDB(wybranaRoslina);
            Intent editRoslinaIntent = new Intent(getApplicationContext(), WpisDefinicjaActivity.class);
            editRoslinaIntent.putExtra(Roslina.ROSLINA_EDIT_EXTRA, wybranaRoslina.getId());
            finish();
            startActivity(editRoslinaIntent);
        }}
        finish();
    }


    public void deleteWpis(View view) {
        databaseManager.deleteRoslinaInDB(wybranaRoslina.getId());
        finish();
    }
}