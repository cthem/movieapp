package com.gnt.moviesapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gnt.appobjects.User;
import com.gnt.utils.RetrofitCalls;
import com.gnt.utils.UserSessionManager;
import com.gnt.utils.Utils;

import java.time.LocalDate;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    boolean success;
    private Context c;

    private EditText firstname, lastname, age, email, username, password, confirmpassword;
    private TextView birthdate, loginlink;
    private ImageButton btnBirthDate;
    private Button bregister;
    private Calendar cal;
    private int mYear, mMonth, mDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initRegisterForm();
    }

    private void initRegisterForm(){
        c = this;
        /** Mandatory fields to be completed by user in order to register **/
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        birthdate = findViewById(R.id.tvBirthDate);
        btnBirthDate = findViewById(R.id.btnBirthDate);
        age = findViewById(R.id.age);
        email = findViewById(R.id.username);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        bregister = findViewById(R.id.register);
        loginlink = findViewById(R.id.loginlink);

        firstname.setSelected(false);
        lastname.setSelected(false);
        age.setSelected(false);
        email.setSelected(false);
        username.setSelected(false);
        password.setSelected(false);
        confirmpassword.setSelected(false);

        linkToLoginActivity();
        addListenerForCalendar();
        //when register button is clicked, data are sent to be stored to the database
        bregister.setOnClickListener((v) -> registerUser());
    }

    private void linkToLoginActivity(){
        //link the text view loginlink to the LoginActivity in case user has already an account
        loginlink.setOnClickListener((l) -> {
            Intent loginintent = new Intent(RegisterActivity.this, LoginActivity.class);
            RegisterActivity.this.startActivity(loginintent);
        });
    }

    private void addListenerForCalendar(){
        btnBirthDate.setOnClickListener((v) -> {
            if (v == btnBirthDate) {
                // Get Current Date
                cal = Calendar.getInstance();
                mYear = cal.get(Calendar.YEAR);
                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        (DatePicker view, int year, int monthOfYear, int dayOfMonth)->
                                birthdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    private void registerUser() {
        final String firstName = firstname.getText().toString();
        final String lastName = lastname.getText().toString();
        final String BirthDate = birthdate.getText().toString();
        final String ageUser = this.age.getText().toString();
        final String Email = email.getText().toString();
        final String Username = username.getText().toString();
        final String Password = password.getText().toString();
        final String ConfirmPassword = confirmpassword.getText().toString();


        //check if user has filled all fields of the registration form
        if (Username.length() == 0 || firstName.length() == 0 || lastName.length() == 0 || age.length() == 0 || Email.length() == 0 || Password.length() == 0
                || ConfirmPassword.length() == 0 || BirthDate.length() == 0) {
            //if something is not filled in, user must fill again the form
            Toast.makeText(c, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        //check if password and confirmation of password are equal
        if (!Password.equals(ConfirmPassword)) {
            //if there is a mismatch a message appears and user must fill in again the fields
            Toast.makeText(c, "Passwords do not match, please try again!", Toast.LENGTH_SHORT).show();
            return;
        }
        int age = Integer.parseInt(ageUser);
        LocalDate bdate = LocalDate.parse(BirthDate);
        PostResult(firstName, lastName, Username, Password, Email, age, bdate);
        /** Check if user exists already **/
        if(!success){
            Toast.makeText(c, "Registration failed, please try again!", Toast.LENGTH_SHORT).show();
        }
        else{
            /** Successful POST request, user is redirected to the home activity and session data are stored in order user to remain logged in **/
            Toast.makeText(c, "You have been successfully registered, please login", Toast.LENGTH_SHORT).show();
            RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        }
    }

    /**
     * Send user input for posting in the database and create a new user
     **/
    public void PostResult(String firstname, String lastname, String username, String password, String email, int age, LocalDate bdate) {
        User UserParameters = new User(age, email, firstname, lastname, password, username, bdate);
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        success = retrofitCalls.postUser(UserParameters);
    }

    @Override
    public void onBackPressed() {
        Intent greetingIntent = new Intent(this, GreetingActivity.class);
        startActivity(greetingIntent);
        super.onBackPressed();
    }
}
