package com.example.darek.lekcja13;

import android.database.sqlite.SQLiteCursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Phone> listaTelefonow;
    private EditText editMarka;
    private EditText editNazwa;
    private EditText editOpis;

    private TextView textMarka;
    private TextView textNazwa;
    private TextView textOpis;

    private Button dodajTelefon;
    private Button usunTelefon;
    private Button nastepnyTelefon;
    private Button poprzedniTelefon;
    private Button wyswietlTelefony;

    DatabaseAdapter databaseAdapter;

    private int wartosc;
    private int wartoscMAX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        OnClickListeners();
    }

    protected void init() {
        editMarka = (EditText) findViewById(R.id.editMarka);
        editNazwa = (EditText) findViewById(R.id.editNazwa);
        editOpis = (EditText) findViewById(R.id.editOpis);

        textMarka = (TextView) findViewById(R.id.textMarka);
        textNazwa = (TextView) findViewById(R.id.textNazwa);
        textOpis = (TextView) findViewById(R.id.textOpis);

        dodajTelefon = (Button) findViewById(R.id.dodaj);
        usunTelefon = (Button) findViewById(R.id.usun);
        nastepnyTelefon = (Button) findViewById(R.id.nastepny);
        poprzedniTelefon = (Button) findViewById(R.id.poprzedni);
        wyswietlTelefony = (Button) findViewById(R.id.wyswietl);

        databaseAdapter = new DatabaseAdapter(this);

        listaTelefonow = new ArrayList<>();
    }

    protected void OnClickListeners() {
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.dodaj:
                        addPhoneToDatabase();
                        Toast.makeText(MainActivity.this, "Dodano telefon do bazy danych.", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.poprzedni:
                        wartosc--;
                        if (wartosc < wartoscMAX) wartosc = wartoscMAX - 1;
                        if (wartosc < 0) wartosc = wartoscMAX - 1;
                        if (wartoscMAX != 0)
                            showPhone(listaTelefonow.get(wartosc));
                        else
                            showPhone(null);
                        break;
                    case R.id.nastepny:
                        wartosc++;
                        if (wartosc >= wartoscMAX) wartosc = 0;
                        if (wartoscMAX != 0)
                            showPhone(listaTelefonow.get(wartosc));
                        else
                            showPhone(null);
                        break;
                    case R.id.usun:
                        if (wartoscMAX != 0) {
                            deletePhone(Long.toString(listaTelefonow.get(wartosc).getId()));
                            getPhonesFromDatabase();
                            Toast.makeText(MainActivity.this, "Usunięto dane telefonu z bazy danych.", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.wyswietl:
                        getPhonesFromDatabase();
                        Toast.makeText(MainActivity.this, "Pobrano dane o telefonach z bazy danych.", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        };
        dodajTelefon.setOnClickListener(listener);
        nastepnyTelefon.setOnClickListener(listener);
        poprzedniTelefon.setOnClickListener(listener);
        usunTelefon.setOnClickListener(listener);
        wyswietlTelefony.setOnClickListener(listener);
    }

    public void addPhoneToDatabase(){
        Phone phone = new Phone(0, editMarka.getText().toString(), editNazwa.getText().toString(), editOpis.getText().toString());
        databaseAdapter.insertPhone(phone);
        getPhonesFromDatabase();
    }

    public void getPhonesFromDatabase(){
        listaTelefonow.clear();
        SQLiteCursor cursor = databaseAdapter.getData();
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                listaTelefonow.add(new Phone(Long.parseLong(cursor.getString(0)),cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
        }
        wartoscMAX = listaTelefonow.size();
        wartosc = 0;
        if(wartoscMAX != 0)
            showPhone(listaTelefonow.get(0));
        else{
            showPhone(null);
        }
    }

    public void showPhone(Phone phone){
        if(phone != null) {
            textMarka.setText(phone.getMarka());
            textNazwa.setText(phone.getNazwa());
            textOpis.setText(phone.getOpis());
        }else{
            textMarka.setText("Brak danych w bazie");
            textNazwa.setText("Brak danych w bazie");
            textOpis.setText("Brak danych w bazie");
        }
    }

    public void deletePhone(String id){
        databaseAdapter.deleteData(id);
    }

}
