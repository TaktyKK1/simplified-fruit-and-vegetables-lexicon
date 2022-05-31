package com.example.proj10_jurek_lukasz_leksykon_owocow_i_warzyw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WpisDefinicjaActivity extends AppCompatActivity {

    private TextView txtTitle, txtDesc, txtType;
    private Roslina wybranaRoslina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wpis_definicja);

        initWidgets();
        Intent previousIntent = getIntent();

        int passedRoslinaID = previousIntent.getIntExtra(Roslina.ROSLINA_EDIT_EXTRA, -1);
        wybranaRoslina = Roslina.getRoslinaForID(passedRoslinaID);

        if (wybranaRoslina != null) {
            txtTitle.setText(wybranaRoslina.getNazwa());
            txtDesc.setText(wybranaRoslina.getOpis());
            txtType.setText(wybranaRoslina.getTyp());
        }
    }

    private void initWidgets() {

        txtTitle = findViewById(R.id.txtTitle);
        txtDesc = findViewById(R.id.editTextDescription);
        txtType = findViewById(R.id.editTextType);
    }

    public void Edytuj(View view)
    {
        Intent editRoslinaIntent = new Intent(getApplicationContext(), WpisAddEditActivity.class);
        editRoslinaIntent.putExtra(Roslina.ROSLINA_EDIT_EXTRA, wybranaRoslina.getId());
        startActivity(editRoslinaIntent);
        finish();
    }
}
