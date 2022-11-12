package com.example.maplication.ui.notifications;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.maplication.AppliedSettiings;
import com.example.maplication.R;
import com.example.maplication.databinding.FragmentNotificationsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotificationsFragment extends Fragment{

    private FragmentNotificationsBinding binding;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    String userID;
    String distanceScale;
    String landmarksType;
    String darkMode;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        userID = FirebaseAuth.getInstance().getUid();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("Users").child(userID);


        ArrayAdapter<CharSequence> distanceAdapter = ArrayAdapter.createFromResource(getContext(), R.array.distances, android.R.layout.simple_spinner_item);
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.distanceSpinner.setAdapter(distanceAdapter);
        binding.distanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                distanceScale = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> landmarkAdapter = ArrayAdapter.createFromResource(getContext(), R.array.landmarks, android.R.layout.simple_spinner_item);
        landmarkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.landmarksSpinner.setAdapter(landmarkAdapter);
        binding.landmarksSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                landmarksType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.saveSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.darkModeSwitch.isChecked())
                {
                    darkMode = "dmon";
                }
                else
                {
                    darkMode = "dmoff";
                }
                AppliedSettiings appSet = new AppliedSettiings(darkMode, distanceScale, landmarksType);
                myRef.child("Settings").setValue(appSet).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Settings Have Been Saved!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "An error has occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}