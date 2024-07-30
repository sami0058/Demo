package com.example.notepad;

import static com.example.notepad.R.drawable.baseline_arrow_back_24;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.window.BackEvent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class UpdateNotes extends AppCompatActivity {
    MaterialToolbar addNoteAppBar;
    AppCompatImageButton btnReturn;
    EditText etTitle,etContent;
    SQLiteDatabase sqLiteDatabase;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);
        addNoteAppBar = findViewById(R.id.topAppBarNote);
        btnReturn = findViewById(R.id.btnReturn);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);

        DB db = DB.getInstance(this);
        List<Notes> notesList = db.fetchNotes();

        Intent updateIntent = getIntent();
        int position = updateIntent.getIntExtra("position",-1);
        updateIntent.getStringExtra("title");
        updateIntent.getStringExtra("content");

        etTitle.setText(updateIntent.getStringExtra("title"));
        etContent.setText(updateIntent.getStringExtra("content"));
//        Notes notes = new Notes();
//        notes.setNoteId(Integer.parseInt(updateIntent.getStringExtra("noteId")));


        addNoteAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.btnUpdate){
                    try {
                        int id = notesList.get(position).getNoteId();
                        String title = etTitle.getText().toString();
                        String content = etContent.getText().toString();

                        Notes note2 = new Notes(id,title,content);
//                        note1.setNoteId(notes.getNoteId());
//                        db.updateNote(note1);

                        if (db.updateNote(note2)){
                            Toast.makeText(UpdateNotes.this, "Note Update", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(UpdateNotes.this, "Note Not Updated", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception ex){
                        ex.getMessage();
                    }
                }
                if (item.getItemId() == R.id.btnDelete){
                    if (db.deleteNote(notesList.get(position))){
                        Toast.makeText(UpdateNotes.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    return true;
                }

                if (item.getItemId() == R.id.btnNoteInfo){
                    Toast.makeText(UpdateNotes.this, "Note Info", Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                }
                if (item.getItemId() == R.id.btnShare){
                    Toast.makeText(UpdateNotes.this, " Note Shared", Toast.LENGTH_SHORT).show();
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, etTitle.getText().toString()+"\n\n"+etContent.getText().toString());
                    shareIntent.setType("text/plain");
                    shareIntent = Intent.createChooser(shareIntent,"Share via : ");
                    startActivity(shareIntent);

                    return true;
                }
                return true;
            }

        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}








