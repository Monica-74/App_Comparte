//package com.example.comparte.activities;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.comparte.R;
//import com.example.comparte.fragments.ChatFragment;
//
//public class InquilinoActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_inquilino);
//
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
//        View btnChatear = findViewById(R.id.btnChatear);
//
//        btnChatear.setOnClickListener(view -> {
//            findViewById(R.id.fragment_chat).setVisibility(View.VISIBLE);
//
//            //cargo el fragment del chat
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_chat, new ChatFragment()) // Reemplaza el contenedor con el fragment
//                    .addToBackStack(null) // Permite retroceder
//                    .commit(); // Confirma la transacción
//
//        });
//    }
//}
//
//
//
//
//
//
//
//
