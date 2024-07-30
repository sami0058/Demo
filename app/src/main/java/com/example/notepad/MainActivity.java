package com.example.notepad;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MaterialToolbar topAppBar, addNoteAppBar;
    FloatingActionButton floating_action_button;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    MaterialToolbar btnDrawToggle;
    NavigationView navigationView;
    SearchView searchView;
    NotepadAdaptor notepadAdaptor;
    List<Notes> notes;
    List<Notes> notesList;
    DB db = DB.getInstance(this);


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNoteAppBar = findViewById(R.id.topAppBarNote);
        topAppBar = findViewById(R.id.topAppBar);
        recyclerView = findViewById(R.id.recyclerView);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        searchView = findViewById(R.id.searchView);
        floating_action_button = findViewById(R.id.floating_action_button);

        notes = db.fetchNotes();
        notesList = db.fetchNotes();



        refreshRecyclerView();

//-------------------------------- DrawerLayout ------------------------------------------------------------


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return true;
            }
        });

        topAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.navAllNotes) {
                    Toast.makeText(MainActivity.this, "All Notes", Toast.LENGTH_SHORT).show();
                    refreshRecyclerView();
                }
                if (itemId == R.id.navFeedback) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("FeedBack")
                            .setMessage("Do you like this App")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainActivity.this, "Thank You for your FeedBack. ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainActivity.this, "Not satisfied", Toast.LENGTH_SHORT).show();
                                }
                            }).create().show();
                }
                if (itemId == R.id.navShare) {
                    Toast.makeText(MainActivity.this, "Share Us", Toast.LENGTH_SHORT).show();
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Here is My Notes App! Please try it! \nhttps://www.google.com/profile.php?id=pk.sami.notepad");
                    shareIntent.setType("text/plain");
                    shareIntent = Intent.createChooser(shareIntent, "Share via : ");
                    startActivity(shareIntent);

                }
                if (itemId == R.id.navExit) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Exit")
                            .setMessage("Do you want to exit this App?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainActivity.this, "Exit App", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainActivity.this, "Cancel Exit", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create().show();
                }
                drawerLayout.close();
                return false;
            }
        });


        //---------------------------------------------------

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

                if (item.getItemId() == R.id.help) {
                    Toast.makeText(MainActivity.this, "Help", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("HELP")
                            .setMessage("Create quick and simple notes. Tap to write, tap to edit. Long press to delete. Organize notes with folders. Use widgets for easy access. Search your notes. Share notes via email, message, or other apps. Supports text formatting like bold, italic, and underline. Choose from various font styles and sizes. Customize background colors. No " +
                                    "internet required. Back up notes to cloud storage (optional). Enjoy the simplicity of quick note-taking.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create().show();
                }
                if (item.getItemId() == R.id.btnNoteShare) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    Toast.makeText(MainActivity.this, "Share App", Toast.LENGTH_SHORT).show();
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Here is My Notes App! Please try it! \nhttps://www.google.com/profile.php?id=pk.sami.notepad");
                    shareIntent.setType("text/plain");
                    shareIntent = Intent.createChooser(shareIntent, "Share via : ");
                    startActivity(shareIntent);
                }
                return true;
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        refreshRecyclerView();
    }

    public void refreshRecyclerView() {
        DB db = DB.getInstance(this);
        notes = db.fetchNotes();
        notepadAdaptor = new NotepadAdaptor(notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(notepadAdaptor);
        recyclerView.setHasFixedSize(true);

        notepadAdaptor.setOnItemClickListener(new NotepadAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(NotepadAdaptor.NotesViewHolder holder, int position) {
                List<Notes> notes = db.fetchNotes();
                Intent updateIntent = new Intent(MainActivity.this, UpdateNotes.class);

                updateIntent.putExtra("position", position);
                updateIntent.putExtra("title", notes.get(position).getTitle());
                updateIntent.putExtra("content", notes.get(position).getContent());


                startActivity(updateIntent);


            }
        });

        notepadAdaptor.setOnItemLongClickListener(new NotepadAdaptor.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
//                Toast.makeText(MainActivity.this, "This", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Note")
                        .setMessage("Do you want to delete this note?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.deleteNote(notes.get(position));
                                notes.remove(position);
                                notepadAdaptor.notifyItemRemoved(position);
                                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                Toast.makeText(MainActivity.this, "Note Not Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();
            }
        });
    }

    private void filter(String text) {
        List<Notes> filteredList = new ArrayList<>();

        try {
            for (Notes note : notesList) {
                if (note.getTitle().toLowerCase().contains(text.toLowerCase()) || note.getContent().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(note);
                }
            }
        }catch (Exception ex){
            Toast.makeText(this, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        notepadAdaptor.filterList(filteredList);
        Log.d("PLAY", "filter: Done");
    }
}

