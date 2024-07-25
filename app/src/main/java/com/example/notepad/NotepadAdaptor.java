package com.example.notepad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotepadAdaptor extends RecyclerView.Adapter<NotepadAdaptor.NotepadViewHolder> {

     List<Notepad> notes;
public NotepadAdaptor(List<Notepad> notes){
    this.notes = notes;
}
    @NonNull
    @Override
    public NotepadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_row_view,null);
        return new NotepadViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotepadViewHolder holder, int position) {
    Notepad notepad =notes.get(position);
    holder.tvTitle.setText(notepad.getTitle());
    holder.tvContent.setText(notepad.getContent());
    }
    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class  NotepadViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvContent;
        public NotepadViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }


}