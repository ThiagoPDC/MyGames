package com.example.mygames;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroUserActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnEditarUser;
    SQLiteDatabase bancoDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_user);

        txtUsername = (EditText) findViewById(R.id.txtUsernameUser);
        txtPassword = (EditText) findViewById(R.id.txtPasswordUser);
        btnEditarUser = (Button) findViewById(R.id.btnEditarUser);

        criarTabela();


        btnEditarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarUser();
            }
        });


    }

    public void cadastrarUser(){
        if(!TextUtils.isEmpty(txtUsername.getText().toString())){
            try{
                bancoDados = openOrCreateDatabase("mygames", MODE_PRIVATE, null);
                String sql = "INSERT INTO user (username,password) VALUES (?,?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1,txtUsername.getText().toString());
                stmt.bindString(2,txtPassword.getText().toString());
                stmt.executeInsert();
                bancoDados.close();
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void criarTabela() {
        try {
            bancoDados = openOrCreateDatabase("mygames", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                    "   username PRIMARY KEY" +
                    " , password VARCHAR)");
            //bancoDados.execSQL("DELETE FROM animal");
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}