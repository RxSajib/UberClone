package com.example.uberclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class UserRegisterActivity extends AppCompatActivity {

    private EditText email, password, rpassword;
    private RelativeLayout registerbutton;
    private MaterialTextView haveaccount;
    private FirebaseAuth Mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        Mauth = FirebaseAuth.getInstance();
        haveaccount = findViewById(R.id.UserRegisterHaveAccountID);
        haveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                CustomIntent.customType(UserRegisterActivity.this, "left-to-right");
            }
        });

        email = findViewById(R.id.RUserEmailAddressID);
        password  =findViewById(R.id.RUserLoginPasswordID);
        rpassword = findViewById(R.id.RUserRetypePasswordID);

        registerbutton = findViewById(R.id.UserRegisterButtoNID);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtext = email.getText().toString();
                String passwordtext = password.getText().toString();
                String rpasswordtext =rpassword.getText().toString();

                if(emailtext.isEmpty()){
                    email.setError("Email require");
                }
                else if(passwordtext.isEmpty()){
                    password.setError("Password require");
                }
                else if(rpasswordtext.isEmpty()){
                    rpassword.setError("Password require");
                }
                else if(!passwordtext.equals(rpasswordtext)){
                    Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_LONG).show();
                }
                else if(rpasswordtext.length() >= 8){
                    Toast.makeText(getApplicationContext(), "Password need 8 char or more", Toast.LENGTH_LONG).show();
                }
                else {

                    MaterialAlertDialogBuilder Mdiolog = new MaterialAlertDialogBuilder(UserRegisterActivity.this);
                    View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.registerprogress_dioloag, null, false);
                    Mdiolog.setView(view);

                    final AlertDialog alertDialog = Mdiolog.create();
                    alertDialog.show();

                    Mauth.createUserWithEmailAndPassword(emailtext, passwordtext)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        alertDialog.dismiss();
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
    }

    @Override
    public void finish() {

        CustomIntent.customType(UserRegisterActivity.this, "rotateout-to-rotatein");

        super.finish();
    }
}