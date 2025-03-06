package com.example.ass5a3;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etEmp_name, etEmp_address, etEmp_phone, etEmp_salary, etDep_id, etDep_name, etDep_loc;
    private Button btnInsert, btnShow, btnShowDep, btnInsertDep;
    private myDatabase dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etEmp_name = findViewById(R.id.name);
        etEmp_address = findViewById(R.id.address);
        etEmp_phone = findViewById(R.id.phoneno);
        btnInsert = findViewById(R.id.insertButton);
        btnShow = findViewById(R.id.showButton);
        etEmp_salary = findViewById(R.id.salary);
        etDep_id = findViewById(R.id.dep_id);
        etDep_name = findViewById(R.id.dep_name);
        etDep_loc = findViewById(R.id.location);
        btnShowDep = findViewById(R.id.btnShowDep);
        btnInsertDep = findViewById(R.id.insertDep);

        // Initialize Database Helper
        dbHelper = new myDatabase(this);

        // Insert employee button click event
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputs
                String name = etEmp_name.getText().toString();
                String address = etEmp_address.getText().toString();
                String phone = etEmp_phone.getText().toString();
                String salary = etEmp_salary.getText().toString();
                String dep_id = etDep_id.getText().toString();

                int dep_id_int = Integer.parseInt(dep_id);
                // Insert employee data into the database
                long result = dbHelper.insertDataEmp(name, address, phone, salary, dep_id_int);

                Log.d("MainActivity", "result"+result);

                Log.d("MainActivity", "name"+name);
                Log.d("MainActivity", "address"+address);
                Log.d("MainActivity", "phone"+phone);
                Log.d("MainActivity", "salary"+salary);
                Log.d("MainActivity", "dep_id"+dep_id_int);

                // Clear input fields after insertion
                etEmp_name.setText("");
                etEmp_address.setText("");
                etEmp_phone.setText("");
                etEmp_salary.setText("");
                etDep_id.setText("");
            }
        });

        // Insert department button click event
        btnInsertDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dep_id = etDep_id.getText().toString();
                int dep_id_int = Integer.parseInt(dep_id);
                String dep_name = etDep_name.getText().toString();
                String dep_loc = etDep_loc.getText().toString();

                // Insert department data into the database
                dbHelper.insertDataDep(dep_id_int, dep_name, dep_loc);

                // Clear input fields after insertion
                etDep_id.setText("");
                etDep_name.setText("");
                etDep_loc.setText("");
            }
        });

        // Show employee data button click event
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.showDataEmp();
                if (cursor.getCount() == 0) {
                    showMessage("Error", "No Data Found");
                } else {
                    StringBuffer buffer = new StringBuffer();
                    cursor.moveToFirst();
                    do {
                        String id = cursor.getString(0); // Get the ID column
                        String name = cursor.getString(1); // Get the NAME column
                        String address = cursor.getString(2); // Get the ADDRESS column
                        String phone = cursor.getString(3); // Get the PHONE column
                        String salary = cursor.getString(4); // Get the SALARY column
                        String dep_id = cursor.getString(5); // Get the DEPARTMENT column

                        buffer.append("Emp ID = " + id);
                        buffer.append("\nEmp Name = " + name);
                        buffer.append("\nEmp Address = " + address);
                        buffer.append("\nEmp Phone = " + phone);
                        buffer.append("\nEmp Salary = " + salary);
                        buffer.append("\nEmp Department = " + dep_id);
                        buffer.append("\n\n");
                    } while (cursor.moveToNext());

                    showMessage("Data", buffer.toString());
                }
            }
        });

        // Show department data button click event
        btnShowDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.showDataDep();
                if (cursor.getCount() == 0) {
                    showMessage("Error", "No Data Found");
                } else {
                    StringBuffer buffer = new StringBuffer();
                    cursor.moveToFirst();
                    do {
                        String id = cursor.getString(0); // Get the ID column
                        String name = cursor.getString(1); // Get the NAME column
                        String location = cursor.getString(2); // Get the LOCATION column

                        buffer.append("Dep ID = " + id);
                        buffer.append("\nDep Name = " + name);
                        buffer.append("\nDep Location = " + location);
                        buffer.append("\n\n");
                    } while (cursor.moveToNext());

                    showMessage("Data", buffer.toString());
                }
            }
        });
    }

    // Method to show a message in a dialog
    private void showMessage(String title, String info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(info);
        builder.show();
    }
}
