package com.example.maplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {

    private FirebaseAuth mAuth;
    Button lButton;
    EditText lEmail, lPassword;
    String email, password;
    TextView forgotPassword;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_login_tab_fragment, container, false);

        lEmail = root.findViewById(R.id.loginEmail);
        lPassword = root.findViewById(R.id.loginPassword);
        lButton = root.findViewById(R.id.loginBtn);
        forgotPassword = root.findViewById(R.id.forgotPasswordTV);
        progressBar = root.findViewById(R.id.lProgessBar);

        mAuth = FirebaseAuth.getInstance();

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ForgotPassword.class);
                startActivity(intent);
            }
        });

        lButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = lEmail.getText().toString().trim();
                password = lPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    lEmail.setError("Please enter your email!");
                    lEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    lEmail.setError("Please enter a valid email address!");
                    lEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    lPassword.setError("Please enter your password!");
                    lPassword.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                            progressBar.setVisibility(root.VISIBLE);
                        }
                        else
                        {
                            progressBar.setVisibility(root.GONE);
                            Toast.makeText(getContext(), "Please check your credentials!" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return root;
    }
}
