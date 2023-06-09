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
import com.google.firebase.storage.internal.Sleeper;

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

    String Path;
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
                String email = Email.getText().toString();
                String pass = Password.getText().toString();

                authenticateLogin(email,pass);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        authenticationStatus=false;
    }

    private void authenticateLogin(String email, String password) {
        HashMap<String,String>userCredentials= new HashMap<>();
        userCredentials.put("Email",email);
        userCredentials.put("Password",password);

//        Manually intent ko change kro yaha
//        TO go to Admin##############################
//        Intent intent=new Intent(this,AdminDashboard.class);
//        startActivity(intent);

//        TO go to User##############################
//        Intent intent1=new Intent(this,UserHomeScreen.class);
//        startActivity(intent1);




//        HashMap<String,String>lol=new HashMap<>();
//        lol.put("lol","lol");

//        AuthenticatorAsync authenticatorAsync=new AuthenticatorAsync();
//        try {
//            Path="Admin";
//            if (authenticatorAsync.execute(userCredentials).get()){
//                Intent intent;
//                intent=new Intent(this,AdminDashboard.class);
//                startActivity(intent);
//            }
//            Path ="Users";
//            if (authenticatorAsync.execute(userCredentials).get()){
//                Intent intent;
//                intent=new Intent(this,UserHomeScreen.class);
//                startActivity(intent);
//
//            }else{
//                Toast.makeText(this,"Invalid Password Email",Toast.LENGTH_SHORT).show();
//            }
//
//
//
//
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        Log.d("TAG","Cred -- "+userCredentials.toString());


//        For now manually acitvity run krlo
//        Intent intent;
//        intent=new Intent(this,UserHomeScreen.class);
//        startActivity(intent);


//        if (credible("Admin",userCredentials)){
//            Log.d("TAG","Admin Equals");
//            intent=new Intent(this,AdminDashboard.class);
//            startActivity(intent);
//        } else if (credible("Users",userCredentials)) {
//            intent=new Intent(this,UserHomeScreen.class);
//            startActivity(intent);
//            Log.d("TAG","User Equals");
//        }else{
//            Toast.makeText(this,"Invalid Password Email",Toast.LENGTH_SHORT).show();
//            Log.d("TAG","None Equals");
//        }
        verify(userCredentials);

    }

    private void verify(HashMap<String, String> userCredentials) {
        Log.d("TAG",userCredentials.toString()+"--Cred");
        String[] ad={"admin1@gmai.com","admin2@gmail.com"};
        String[] adPas={"Admin@1","Admin@2"};
        String[] us={"ammaraali@gmail.com","mashoodali@hotmail.com","kiwikent21@gmail.com","ali111zahid@gmail.com","amna@gmail.com","sam7676@gmail.com","zammadd@yahoo.com","ahmed312@hotmail.com","mahnoortariq@yahoo.com","zaino67@gmail.com"};
        String[] usPas={"Dream@big1","M@shood1","Kiwi543@","Ali987$","Amna3456@","Sam343@","holykingW34$","Blackman45@","Thebeatles2@","Z1no123*"};
        String[] roll={"19F-0987","19F-2314","20F-0322","20F-0567","20F-0987","20F-8967","21F-0322","22F-4566","22F-5674"};
        String temp=userCredentials.get("Email").toString();
        String temp1=userCredentials.get("Password").toString();
        boolean check=false;
        Log.d("TAG", String.valueOf(ad.length)+" - "+us.length);


        for (int i=0;i<ad.length;i++){
//            Log.d("TAG","lol");
            Log.d("TAG",ad[i]+" () "+adPas[i]);


            Log.d("TAG",temp+" = "+temp1);
            if (ad[i].equals(temp) && adPas[i].equals(temp1)){
                Log.d("TAG","Admin PAss");
                check=true;
                Intent intent=new Intent(LogIn.this,AdminDashboard.class);
                intent.putExtra("Email",temp);
                intent.putExtra("Password",temp1);
                startActivity(intent);
                break;
            }
        }

        for (int i=0;i<us.length;i++){
            if (us[i].equals(temp) && usPas[i].equals(temp1)){
                Log.d("TAG","User PAss");
                check=true;
                Intent intent=new Intent(LogIn.this,UserHomeScreen.class);
                intent.putExtra("Email",temp);
                intent.putExtra("Password",temp1);
                intent.putExtra("Rollno",roll[i]);
                startActivity(intent);
                break;
            }
        }

        if (check==false){
                Toast.makeText(this,"Invalid Password Email",Toast.LENGTH_SHORT).show();
        }

    }

    public boolean credible(String Path, HashMap<String, String> user) {
//        authenticationStatus=false;
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
                            Log.d("TAG","User Equality Performed");
                        }

//                        Log.d("TAG", String.valueOf(temp));

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
            HashMap<String,String>user=hashMaps[0];
            database=FirebaseDatabase.getInstance("https://bus-pass-management-c51ef-default-rtdb.firebaseio.com/");
            reference=database.getReference(Path);
            Log.d("TAG",Path+"---------");
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
                                Log.d("TAG","User Equality Performed");
                            }

//                        Log.d("TAG", String.valueOf(temp));

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