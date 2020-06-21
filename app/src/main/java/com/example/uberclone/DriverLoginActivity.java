package com.example.uberclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import maes.tech.intentanim.CustomIntent;

public class DriverLoginActivity extends AppCompatActivity {

    private MaterialTextView registerbutton;

    private EditText email, password;
    private RelativeLayout loginbuttonID;
    private FirebaseAuth Mauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        Mauth  = FirebaseAuth.getInstance();
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        registerbutton = findViewById(R.id.DriverNewAccountButtonID);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DriverRegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                CustomIntent.customType(DriverLoginActivity.this, "left-to-right");

            }
        });

        loginbuttonID = findViewById(R.id.DriverSiginButtonID);
        email = findViewById(R.id.DriverEmailAddressID);
        password = findViewById(R.id.DriverLoginPasswordID);


        loginbuttonID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtext = email.getText().toString();
                String passwordtext = password.getText().toString();

                if(emailtext.isEmpty()){
                    email.setError("Email require");
                }
                else if(passwordtext.isEmpty()){
                    password.setError("Password require");
                }
                else {

                    final MaterialAlertDialogBuilder Mbuilder  = new MaterialAlertDialogBuilder(DriverLoginActivity.this);
                    final View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.costom_loadingbar, null, false);
                    Mbuilder.setView(view);
                    Mbuilder.setCancelable(false);

                    final AlertDialog alertDialog = Mbuilder.create();
                    alertDialog.show();

                    Mauth.signInWithEmailAndPassword(emailtext, passwordtext)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        alertDialog.dismiss();

                                        Intent intent = new Intent(getApplicationContext(), DrivermapsActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        CustomIntent.customType(DriverLoginActivity.this, "left-to-right");
                                    }
                                    else {
                                        alertDialog.dismiss();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    alertDialog.dismiss();
                                }
                            });
                }
            }
        });
    }

    @Override
    public void finish() {
        CustomIntent.customType(DriverLoginActivity.this, "fadein-to-fadeout");
        super.finish();
    }


    @Override
    protected void onStart() {

        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null){
            Intent intent = new Intent(getApplicationContext(), DrivermapsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            CustomIntent.customType(DriverLoginActivity.this, "left-to-right");
        }

        super.onStart();
    }
}