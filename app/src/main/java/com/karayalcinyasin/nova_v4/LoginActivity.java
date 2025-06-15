package com.karayalcinyasin.nova_v4;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;
public class LoginActivity extends AppCompatActivity {
    EditText kulad, sifre;
    TextView girisbilgi;
    //LoginButton loginButton;
    ImageView girisyap;

    // Button loginfacebook;

       // CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //facebook sdk başlatmak için,
        // FacebookSdk.sdkInitialize(login.this);
        //kimlik doğrulama
        mAuth = FirebaseAuth.getInstance();
        /* Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
       loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });*/

        //Tanımlama
        kulad = findViewById(R.id.giriskulad);
        sifre = findViewById(R.id.girissifre);
        girisyap=findViewById(R.id.girisyap);
        girisbilgi = findViewById(R.id.girisbilgi);
        //girisyap butonuna basıldığında
        girisyap.setOnClickListener(view -> {
            if (kuladidogrulama() || sifredogrulama()) {
                checkUser();
            }

        });

        //kayıtol sayfasına gönderme
        girisbilgi.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();


        });

    }

    /*private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        if(user != null){
            Intent intent = new Intent(login.this,mainpage.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Lütfen Giriş Yapın!", Toast.LENGTH_SHORT).show();
        }
    }*/

    public Boolean kuladidogrulama() {
        String val = kulad.getText().toString();
        if (val.isEmpty()) {
            kulad.setError("Kullanıcı adı boş olamaz!");
            return false;
        } else {
            kulad.setError(null);
            return true;
        }
    }

    public Boolean sifredogrulama() {

        String val = sifre.getText().toString();
        if (val.isEmpty()) {
            sifre.setError("Parola boş olamaz!");
            return false;
        } else {
            sifre.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userUsername = kulad.getText().toString().trim();
        String userPassword = sifre.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Kullanici_bilgi");
        Query checkUserDatabase = reference.orderByChild("kullaniciadi").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    kulad.setError(null);
                    String sifreDB = snapshot.child(userUsername).child("sifre").getValue(String.class);

                    if (!Objects.equals(sifreDB,userPassword) ) {
                        kulad.setError(null);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        sifre.setError("Geçersiz kimlik bilgileri");
                        sifre.requestFocus();
                    }
                } else {
                    kulad.setError("Böyle bir kullanıcı yok");
                    kulad.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}
