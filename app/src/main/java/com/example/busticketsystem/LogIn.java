package com.example.busticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.Authenticator;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;


public class LogIn extends AppCompatActivity {

    EditText Email, Password;
    String email, password;
    Button button;

    FirebaseDatabase database;
    DatabaseReference reference;
    boolean authenticationStatus = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Email = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);
        button = findViewById(R.id.LogInbutton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=Email.getText().toString();
                String pass=Password.getText().toString();

                authenticateLogin(email,pass);
            }
        });
    }

    private void authenticateLogin(String email,String password) {
        HashMap<String,String>userCredentials= new HashMap<>();
        userCredentials.put("Email",email);
        userCredentials.put("Password",password);

        HashMap<String,String>lol=new HashMap<>();
        lol.put("lol","lol");

        AuthenticatorAsync authenticatorAsync=new AuthenticatorAsync();
        try {
            boolean loll=authenticatorAsync.execute(lol).get();
            if (loll){
                Log.d("TAG","opop");
            }



        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        if (credible("Admin",user)){
//            Log.d("TAG","Admin Equals");
//        } else if (credible("Users",user)) {
//            Log.d("TAG","User Equals");
//        }else{
//            Toast.makeText(this,"Invalid Password Email",Toast.LENGTH_SHORT).show();
//            Log.d("TAG","None Equals");
//        }

    }

    public boolean credible(String Path, HashMap<String, String> user) {

        database=FirebaseDatabase.getInstance("https://bus-pass-management-c51ef-default-rtdb.firebaseio.com/");
        reference=database.getReference(Path);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue()==null)
                {
                    Log.d("TAG","No Data Retrieved from Firebase");
                }
                else
                {
                    for(DataSnapshot ss:snapshot.getChildren()){
                        HashMap<String,String>temp= (HashMap<String, String>) ss.getValue();

                        if (Path=="Users"){
                            HashMap<String,String>tempU=new HashMap<>();
                            tempU.put("Email",temp.get("email"));
                            tempU.put("Password",temp.get("password"));
                            temp=tempU;
                        }

                        Log.d("TAG", String.valueOf(temp));

                        if(temp.equals(user)){
                            authenticationStatus =true;
                            Log.d("TAG","lol");
                            break;
                        }else{
                            authenticationStatus=false;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return authenticationStatus;
    }


    public class AuthenticatorAsync extends AsyncTask<HashMap,Void,Boolean>{

        @Override
        protected Boolean doInBackground(HashMap... hashMaps) {
            Log.d("TAG", String.valueOf(hashMaps[0]));
            return null;
        }
    }

    private void navigateToSplashScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void NavigateToSplashscreen(View view) {
        navigateToSplashScreen();
    }

}