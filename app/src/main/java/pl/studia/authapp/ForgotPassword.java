package pl.studia.authapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText resetPasswprdET;
    private Button resetPasswordBTN;
    private ProgressBar progressBar;
    private ImageButton backBTN;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        resetPasswprdET = findViewById(R.id.resertPasswordET);
        resetPasswordBTN = findViewById(R.id.resetPasswordBTN);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        resetPasswordBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        backBTN = findViewById(R.id.backBTN);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this, MainActivity.class));
            }
        });
    }

    private void resetPassword() {
        String email = resetPasswprdET.getText().toString().trim();

        if(email.isEmpty()){
            resetPasswprdET.setError("email is required...");
            resetPasswprdET.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            resetPasswprdET.setError("Invalid email pattern...");
            resetPasswprdET.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check yout email!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ForgotPassword.this, "Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}