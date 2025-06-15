package com.karayalcinyasin.nova_v4;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karayalcinyasin.nova_v4.databinding.ActivityRegisterBinding;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser kulid;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    HashMap<String, Object> kullanicibilgi;
    public EditText kayitadi, kayitsoyadi, kayitkulad, kayitemail, kayitsifre, kayitdate, kayittel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        //Tanımlama
        kayitadi = findViewById(R.id.kayıtadi);
        kayitsoyadi = findViewById(R.id.kayitsoyadi);
        kayitkulad = findViewById(R.id.kayitkulad);
        kayitemail = findViewById(R.id.kayitemail);
        kayitsifre = findViewById(R.id.kayitsifre);
        kayittel = findViewById(R.id.kayittel);
        kayitdate = findViewById(R.id.kayitdate);
        //tanımlama
        TextView kayitolbilgi = findViewById(R.id.kayitolbilgi);
        ImageView creataccount = findViewById(R.id.girisyap);
        //kullanıcıOluşturup veritabanına ekleme
        creataccount.setOnClickListener(view1 -> {
            String email = binding.kayitemail.getText().toString();
            String sifre = binding.kayitsifre.getText().toString();
            String ad = kayitadi.getText().toString();
            String soyad = kayitsoyadi.getText().toString();
            String kulad = kayitkulad.getText().toString();
            String tel = kayittel.getText().toString();
            String dogumtarihi = kayitdate.getText().toString();

            if (email.equals("") || sifre.equals("") || ad.equals("") || soyad.equals("") || kulad.equals("")
                    || tel.equals("") || dogumtarihi.equals("")) {
                Toast.makeText(RegisterActivity.this, "E-mail veya şifre giriniz...", Toast.LENGTH_SHORT).show();
            } else {
                auth.createUserWithEmailAndPassword(email, sifre).addOnSuccessListener(authResult -> {
                    kulid = auth.getCurrentUser();
                    kullanicibilgi = new HashMap<>();
                    kullanicibilgi.put("id", kulid.getUid());
                    kullanicibilgi.put("adi", ad);
                    kullanicibilgi.put("soyadi::", soyad);
                    kullanicibilgi.put("kullaniciadi", kulad);
                    kullanicibilgi.put("email", email);
                    kullanicibilgi.put("sifre", sifre);
                    kullanicibilgi.put("dogumtarihi", dogumtarihi);
                    kullanicibilgi.put("telefon", tel);
                    kullanicibilgi.put("bio", "");
                    kullanicibilgi.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/nova-v4.appspot.com/o/Varl%C4%B1k%201hdpi.png?alt=media&token=5cdcf771-ec1c-4b77-a56b-60affce0abed");
                    reference.child("Kullanici_bilgi").child(kulid.getUid()).setValue(kullanicibilgi).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(RegisterActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, e.getLocalizedMessage() + "Kayıt Başarısız", Toast.LENGTH_SHORT).show();
                        }
                    });


                }).addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
            }

        });
        //tıklandığında kayıtol sayfasına gitme
        kayitolbilgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
