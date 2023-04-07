package com.example.mygames;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class UserScreenActivity extends AppCompatActivity {

    public SQLiteDatabase bancoDados;

    ListView lvUser;

    public ArrayList<String> arrayUser;

    String usernameSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);

        lvUser = (ListView) findViewById(R.id.lvUser);


        listarUser();


        lvUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                usernameSelected = arrayUser.get(i);
                confirmaExcluirUser();
                return true;
            }
        });

    }





    public void listarUser(){
        try{

            bancoDados = openOrCreateDatabase("mygames", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT username, password FROM user", null);
            ArrayList<String> linhas = new ArrayList<String>();
            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            lvUser.setAdapter(meuAdapter);
            arrayUser = new ArrayList<>();
            meuCursor.moveToFirst();
            do{
                linhas.add(meuCursor.getString(0) + " - " + meuCursor.getString(1));
                arrayUser.add(String.valueOf(meuCursor.moveToNext()));
            }while(meuCursor.moveToNext());



        } catch (Exception e) {
            e.printStackTrace();


        }



    }
    public void confirmaExcluirUser() {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(UserScreenActivity.this);
        msgBox.setTitle("Excluir");
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setMessage("Você realmente deseja excluir esse usuário?");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletar();
                listarUser();
            }
        });
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        msgBox.show();
    }

    public void deletar(){
        try{
            bancoDados = openOrCreateDatabase("mygames", MODE_PRIVATE, null);
            String sql = "DROP TABLE user";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.executeUpdateDelete();
            bancoDados.close();
            abrirTelalogin();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void abrirTelalogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
