package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter<String> adapter;

    SharedPreferences sharedPreferences;


    public void AddNote(){

    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.addNote:
                Intent intent = new Intent(getApplicationContext(),NoteEditorActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "New Note", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);

        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.noteapp", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("Notes",null);

        if (set == null){
            notes.add("Add a Note");
        } else {
            notes = new ArrayList(set);
        }

        adapter = new ArrayAdapter<String>(this,R.layout.list_view,notes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),NoteEditorActivity.class);
                intent.putExtra("NoteID",i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                int item = i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Delete?")
                        .setMessage("It will delete the note permanently!!")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(item);
                                adapter.notifyDataSetChanged();

                                HashSet<String> set = new HashSet<>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("Notes", set).apply();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();

                return true;
            }
        });

    }
}