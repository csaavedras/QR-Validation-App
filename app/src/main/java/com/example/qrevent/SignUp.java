package com.example.qrevent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private EditText userName;
    private EditText userEmail;
    private EditText userPassword;
    private EditText userConfirmPassword;
    private Button signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName = findViewById(R.id.input_userName);
        userEmail = findViewById(R.id.input_userEmail);
        userPassword = findViewById(R.id.input_userPassword);
        userConfirmPassword = findViewById(R.id.input_userConfirmPassword);

        signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CharSequence text = "Validación correcta";
                int duration = Toast.LENGTH_SHORT;

                if(dataValidation()) {
                    String name = userName.getText().toString();
                    String email = userEmail.getText().toString();
                    String password = userPassword.getText().toString();

                    saveData(name, email, password);

                    showTotalUsersData();
                    CharSequence successMessage = "Usuario registrado con éxito";
                    Toast.makeText(getApplicationContext(), successMessage, duration).show();

                    // Reset Form
                    userName.setText("");
                    userEmail.setText("");
                    userPassword.setText("");
                    userConfirmPassword.setText("");

                }

            }

        });

    }

    private boolean dataValidation() {
        String name = userName.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        String confirmPassword = userConfirmPassword.getText().toString();

        CharSequence requiredNotificationMessage = "Todos los campos son obligatorios!";
        CharSequence validEmailNotificationMessage = "El correo electrónico no es válido!";
        CharSequence checkPasswordsNotificationMessage = "Las contraseñas no coinciden!";
        CharSequence validNameNotificationMessage = "El nombre solo debe contener letras";



        int duration = Toast.LENGTH_SHORT;

        // Validation Setup

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, requiredNotificationMessage, duration).show();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, validEmailNotificationMessage, duration).show();
            return false;
        }

        if (!name.matches("[a-zA-Z]+")){
            Toast.makeText(this, validNameNotificationMessage, duration).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, checkPasswordsNotificationMessage, duration).show();
            return false;
        }

        return true;
    }


    private void saveData(String name, String email, String password) {

        CharSequence successNotificationMessage = "Usuario registrado con éxito";
        int duration = Toast.LENGTH_SHORT;

        SharedPreferences sharedPreferences = getSharedPreferences("usersData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int totalRegisteredUsers = sharedPreferences.getInt("totalUsers", 0);

        totalRegisteredUsers++;

        editor.putString("name" + totalRegisteredUsers, name);
        editor.putString("email" + totalRegisteredUsers, email);
        editor.putString("password" + totalRegisteredUsers, password);

        editor.putInt("totalUsers", totalRegisteredUsers);

        editor.apply();

        Toast.makeText(this, successNotificationMessage, duration);
    }

    // MARK: FOR DEVELOPMENT AND DEBUG PURPOSES ONLY
    private void showTotalUsersData() {
        SharedPreferences sharedPreferences = getSharedPreferences("usersData", MODE_PRIVATE);
        int totalUsersData = sharedPreferences.getInt("totalUsersData", 0);

        String totalUsersMessage = "Total de usuarios: " + totalUsersData;

        Toast.makeText(this, totalUsersMessage, Toast.LENGTH_SHORT).show();
    }
}