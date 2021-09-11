package com.example.noteapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteId;
    ImageView tick;
    TextView toolbarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText noteEditor = (EditText) findViewById(R.id.noteEditor);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("NoteID",-1);

        if (noteId != -1){
            noteEditor.setText(MainActivity.notes.get(noteId));
        } else {
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() -1;
        }

            noteEditor.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    MainActivity.notes.set(noteId, String.valueOf(charSequence));
                    MainActivity.adapter.notifyDataSetChanged();

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.noteapp", Context.MODE_PRIVATE);
                    HashSet<String> set = new HashSet<>(MainActivity.notes);
                    sharedPreferences.edit().putStringSet("Notes", set).apply();

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
    }
}