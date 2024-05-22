package com.example.foodlarecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodlarecipe.FetchFood;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private TextView headline;
    private EditText editText;
    private Button buttonCari;

    private RecyclerView recyclerView;
    private ArrayList<ItemData> values;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headline = findViewById(R.id.headline);
        updateGreetingText();
        editText = findViewById(R.id.editquery);
        buttonCari = findViewById(R.id.buttonCari);
        recyclerView = findViewById(R.id.recyclerview);
        //recylerview bro
        values = new ArrayList<>();
        itemAdapter = new ItemAdapter(this, values);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
        buttonCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cariFood();}
        });

    }
    private void cariFood(){
        String query = editText.getText().toString().trim();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            if (!query.isEmpty()){
                //memanggil AscynTaask Food untuk mengambil data dari API
                FetchFood foodTask =new FetchFood(values, itemAdapter, recyclerView, MainActivity.this);
                foodTask.execute(query);
            }else{
                //menampilkan pesan toast jika input kosong
                Toast.makeText(MainActivity.this,"Masukkan nama menu terlebih dahulu", Toast.LENGTH_SHORT).show();
            }
        }else {
            //menampilkan pesan toast jika tidak terhubung ke internet
            Toast.makeText(MainActivity.this,"Tidak terhubung ke internet", Toast.LENGTH_SHORT).show();
        }
    }
    private void updateGreetingText(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // Sesuaikan teks selamat berdasarkan waktu
        if (hour >= 0 && hour < 12) {
            headline.setText("Selamat Pagi");
        } else if (hour >= 12 && hour < 18) {
            headline.setText("Selamat Siang");
        } else {
            headline.setText("Selamat Malam");
        }
    }
}