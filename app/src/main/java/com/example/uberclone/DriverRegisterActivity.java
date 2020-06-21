package com.example.uberclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.View;
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

import maes.tech.intentanim.CustomIntent;

public class DriverRegisterActivity extends AppCompatActivity {

    private MaterialTextView haceaccount;

    private EditText email, password, retypepassword;
    private RelativeLayout RegisterButton;
    private FirebaseAuth Mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);

        Mauth = FirebaseAuth.getInstance();
        email = findViewById(R.id.RDriverEmailAddressID);
        password = findViewById(R.id.RDriverLoginPasswordID);
        retypepassword = findViewById(R.id.RDriverRetypePasswordID);
        RegisterButton = findViewById(R.id.DriverRegisterButtoNID);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtext = email.getText().toString();
                String passwordtext = password.getText().toString();
                String repasswordtext = retypepassword.getText().toString();

                if(emailtext.isEmpty()){
                    email.setError("Email require");
                }
                else if(passwordtext.isEmpty()){
                    password.setError("Password require");
                }
                else if(repasswordtext.isEmpty()){
                    retypepassword.setError("Password require");
                }
                else if(!passwordtext.equals(repasswordtext)){
                    Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_LONG).show();
                }
                else if(passwordtext.length() >= 8){
                    Toast.makeText(getApplicationContext(), "Password need 8 char", Toast.LENGTH_LONG).show();
                }
                else {

                    MaterialAlertDialogBuilder Mbuilder = new MaterialAlertDialogBuilder(DriverRegisterActivity.this);
                    View Mview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.registerprogress_dioloag, null, false);
                    Mbuilder.setView(Mview);

                    final AlertDialog alertDialog = Mbuilder.create();
                    alertDialog.show();

                    Mauth.createUserWithEmailAndPassword(emailtext, passwordtext)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        alertDialog.dismiss();
                                    /*    Intent intent = new Intent(getApplicationContext(), DriversMapsActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        CustomIntent.customType(DriverRegisterActivity.this, "left-to-right");*/
                                    }
                                    else {
                                        alertDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    alertDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });

        haceaccount = findViewById(R.id.DriverRegisterHaveAccountID);
        haceaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DriverLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                CustomIntent.customType(DriverRegisterActivity.this, "left-to-right");
            }
        });
    }

    @Override
    public void finish() {
        CustomIntent.customType(DriverRegisterActivity.this, "fadein-to-fadeout");
        super.finish();
    }
}