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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpTabFragment extends Fragment {

    EditText rEmail, rPassword, rReEnterPassword, rFullName;
    private FirebaseAuth mAuth;
    Button rButton;
    String email, password, rePassword, fullName;
    ProgressBar rProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_sign_up_tab_fragment, container, false);
        rFullName = root.findViewById(R.id.registerFName);
        rEmail = root.findViewById(R.id.registerEmail);
        rPassword = root.findViewById(R.id.registerPassword);
        rReEnterPassword = root.findViewById(R.id.registerReEnterPassword);
        rButton = root.findViewById(R.id.registerBtn);
        rProgressBar = root.findViewById(R.id.rProgressBar);

        mAuth = FirebaseAuth.getInstance();

        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullName = rFullName.getText().toString().trim();
                email = rEmail.getText().toString().trim();
                password = rPassword.getText().toString().trim();
                rePassword = rReEnterPassword.getText().toString().trim();

                if (fullName.isEmpty()){
                    rFullName.setError("Full Name is required!");
                    rFullName.requestFocus();
                    return;
                }

                if (email.isEmpty()){
                    rEmail.setError("Email is required!");
                    rEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    rEmail.setError("Please provide a valid email!");
                    rEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()){
                    rPassword.setError("Password is required!");
                    rPassword.requestFocus();
                    return;
                }

                if (rePassword.isEmpty()){
                    rReEnterPassword.setError("Please Re-Enter your password!");
                    rReEnterPassword.requestFocus();
                    return;
                }

                if (password == rePassword) {
                    rReEnterPassword.setError("Passwords do not match!");
                    rReEnterPassword.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            User user = new User(fullName, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getContext(), "Registration completed successfully!", Toast.LENGTH_SHORT).show();
                                                rProgressBar.setVisibility(root.VISIBLE);
                                                Intent intent = new Intent(getContext(), MainActivity.class);
                                                startActivity(intent);
                                            }
                                            else{
                                                Toast.makeText(getContext(), "Registration Failed!", Toast.LENGTH_SHORT).show();
                                                rProgressBar.setVisibility(root.GONE);
                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(getContext(), "Registration Failed, Please check your internet connection!", Toast.LENGTH_SHORT).show();
                            rProgressBar.setVisibility(root.GONE);
                        }
                    }
                });
            }
        });
        return root;
    }
}