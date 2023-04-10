package com.example.mygames;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class LoginActivity extends AppCompatActivity {

    TextView txtTelacadastro;
    EditText txtUsername, txtPassword;
    Button btnLogar;
    SQLiteDatabase bancoDados;

    SharedPreferences sPreferences = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sPreferences = getSharedPreferences("firstRun", MODE_PRIVATE);
        inserirDadosUser();

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogar = (Button) findViewById(R.id.btnLogar);
        txtTelacadastro = (TextView) findViewById(R.id.txtTelacadastro);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logar();
            }



        });


        txtTelacadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastroUser();
            }
        });

        criarBancoDados();
        //inserirDadosTemp();
        //inserirDadosUser();
        criarTabela();


    }


    @Override
    public void onResume() {
        super.onResume();

        if (sPreferences.getBoolean("firstRun", true))
            sPreferences.edit().putBoolean("firstRun", false).apply();

    }







    public void inserirDadosTemp(){
        try{
            bancoDados = openOrCreateDatabase("mygames", MODE_PRIVATE, null);
            String sql = "INSERT INTO game (title,studio) VALUES (?,?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            stmt.bindString(1,"Battlefield 1");
            stmt.bindString(2,"Frostbite");
            stmt.executeInsert();

            stmt.bindString(1,"The Last of Us");
            stmt.bindString(2,"Naughtydog");
            stmt.executeInsert();

            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void inserirDadosUser(){
        try{
            bancoDados = openOrCreateDatabase("mygames", MODE_PRIVATE, null);
            String sql = "INSERT INTO user (username,password) VALUES (?,?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            stmt.bindString(1,"user");
            stmt.bindString(2,"1423");
            stmt.executeInsert();

            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
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



    public void logar() {
        try {

            bancoDados = openOrCreateDatabase("mygames", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT * FROM user WHERE username = " +
                    "'"+txtUsername.getText().toString()+"' " +
                    "and password = '"+txtPassword.getText().toString()+"'", null);
            meuCursor.moveToFirst();
            if(meuCursor.getCount()>0){
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);


            }else {
                erroMsg();
            };
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void criarBancoDados(){
        try {
            bancoDados = openOrCreateDatabase("mygames", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    " , username VARCHAR" +
                    " , password VARCHAR)");
            //bancoDados.execSQL("DELETE FROM animal");
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void erroMsg(){
        AlertDialog.Builder msgBox = new AlertDialog.Builder(LoginActivity.this);
        msgBox.setTitle("Erro!!");
        msgBox.setIcon(android.R.drawable.ic_dialog_alert);
        msgBox.setMessage("login ou senha inválidos ou inexistententes");
        msgBox.setNeutralButton("ok", new DialogInterface.OnClickListener(){
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {
         }
     });
     msgBox.show();





    }

    public void abrirTelaCadastroUser(){
        Intent intent = new Intent(this, CadastroUserActivity.class);
        startActivity(intent);

    }


}





