package com.example.deepakrattan.contentproviderwithsqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Deepak Rattan on 05-Nov-17.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    public static final String TAG = WordListAdapter.class.getSimpleName();
    private Context context;
    private LayoutInflater inflater;
    private WordListOpenHelper db;


    public WordListAdapter(Context context, WordListOpenHelper db) {
        this.context = context;
        this.db = db;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.word_list_item, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        WordItem current = db.query(position);
        holder.txtWord.setText(current.getWord());
        final int id = current.getId();
        final WordViewHolder h = holder;

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int deleted = db.delete(id);
                if (deleted >= 0) {
                    notifyItemRemoved(h.getAdapterPosition());
                }

            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return (int) db.count();
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private TextView txtWord;
        private Button btnDelete, btnEdit;

        public WordViewHolder(View itemView) {
            super(itemView);
            txtWord = (TextView) itemView.findViewById(R.id.txtWord);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
        }
    }


}
