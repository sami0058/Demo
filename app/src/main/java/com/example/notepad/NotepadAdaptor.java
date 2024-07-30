package com.example.notepad;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotepadAdaptor extends
        RecyclerView.Adapter<NotepadAdaptor.NotesViewHolder>{


    private List<Notes> filteredNoteList;
     private OnItemClickListener onItemClickListener;
     public void setOnItemClickListener(OnItemClickListener onItemClickListener){
         this.onItemClickListener = onItemClickListener;
     }
     private OnItemLongClickListener onItemLongClickListener;
     public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
         Log.d("PLAY", "setOnItemLongClickListener: Assigned");

         this.onItemLongClickListener = onItemLongClickListener;
     }

    public NotepadAdaptor(List<Notes> notesList){

        this.filteredNoteList = notesList;    }
    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("PLAY", "onCreateViewHolder: Created");
        View rootView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_row_view,null);
        return new NotesViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
    Notes notes = this.filteredNoteList.get(position);
    holder.tvTitle.setText(notes.getTitle());
    holder.tvContent.setText(notes.getContent());


        DateFormat df = new SimpleDateFormat("HH:mm:ss a, dd/MM/yyyy", Locale.getDefault());
        String currentDateAndTime = df.format(new Date());
        holder.tvDate.setText(currentDateAndTime);
    }
    @Override
    public int getItemCount() {
        return filteredNoteList.size();
    }

//-------------------------------------------------------------------------------------------------------------


    public void filterList(List<Notes> filteredNoteList){
         this.filteredNoteList = filteredNoteList;
         notifyDataSetChanged();
    }
//
//
//    public Filter getFilter(){
//
//        return new MyFilter();
//    }
//
//    private class MyFilter extends Filter {
//
//
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//
//            String query = constraint.toString().toLowerCase();
//            List<Notes> filteredList =  new ArrayList<>();
//            for (Notes note : notesList){
//                if (note.getTitle().toLowerCase().contains(query)){
//                    filteredList.add(note);
//                }
//            }
//
//            FilterResults results = new FilterResults();
//            results.values= filteredList;
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            filteredNoteList = (List<Notes>) results.values;
//        }
//    }

    //---------------------------------------------------------------------------


    public class  NotesViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent,tvDate;
        CardView cardView;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvDate  = itemView.findViewById(R.id.tvDate);
            cardView = itemView.findViewById(R.id.cardView);
//            Log.d("PLAY", "NotesViewHolder: Done");
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null){
                        int position = getAdapterPosition();
                        onItemClickListener.onItemClick(NotesViewHolder.this,position);
                    }
                }
            });
            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
//                    if (onItemLongClickListener != null){
                        int position = getAdapterPosition();
                        onItemLongClickListener.onItemLongClick(position);
//                    }
                    return true;
                }
            });
        }
    }



    interface OnItemClickListener{
        void onItemClick(NotepadAdaptor.NotesViewHolder holder, int position);
    }
    interface OnItemLongClickListener{
        void onItemLongClick(int position);
    }


}


