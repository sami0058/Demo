package com.example.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    AppCompatButton btnSaveNote;
MaterialToolbar topAppBar;
FloatingActionButton floating_action_button;
RecyclerView recyclerView;
NotepadAdaptor notepadAdaptor;
List<Notepad> notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSaveNote = findViewById(R.id.btnSaveNote);
        topAppBar =findViewById(R.id.topAppBar);
        recyclerView = findViewById(R.id.recyclerView);
        notepadAdaptor = new NotepadAdaptor(generateData());
        recyclerView.setAdapter(notepadAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        floating_action_button = findViewById(R.id.floating_action_button);
        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        floating_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent noteintent = new Intent(MainActivity.this, addNoteActivity.class);
                startActivity(noteintent);
            }
        });

        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.search){
                    SearchView searchView = (SearchView) item.getActionView();
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            return false;
                        }
                        @Override
                        public boolean onQueryTextChange(String s) {
                            return false;
                        }
                    });
                }
                return false;
            }
        });
    }

    public List<Notepad> generateData(){
        List<Notepad> notes = new ArrayList<>();
    notes.add(new Notepad("Title 1","Content 1"));
    notes.add(new Notepad("Title 2","Content 2"));
    notes.add(new Notepad("Title 3","Content 3"));
    return notes;
    }
}
