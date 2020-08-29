package com.test.appgate.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.test.appgate.R;
import com.test.appgate.data.dto.Response;

import java.util.List;

public class ValidationListAdapter extends RecyclerView.Adapter<ValidationListAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView text_result,text_time;

        private WordViewHolder(View itemView) {
            super(itemView);
            text_result = itemView.findViewById(R.id.text_result);
            text_time = itemView.findViewById(R.id.text_time);

        }
    }

    private final LayoutInflater mInflater;
    private List<Response> mValidation; // Cached copy of words

    ValidationListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mValidation != null) {
            Response current = mValidation.get(position);
            holder.text_time.setText(current.getTime());
            holder.text_result.setText(current.getResult() ? R.string.exitoso:R.string.denegado);
        } else {
            holder.text_result.setText(R.string.no_register);
        }
    }

    void setWords(List<Response> words) {
        mValidation = words;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mValidation != null)
            return mValidation.size();
        else return 0;
    }
}


