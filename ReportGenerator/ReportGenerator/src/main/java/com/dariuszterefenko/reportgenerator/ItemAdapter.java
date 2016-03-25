package com.dariuszterefenko.reportgenerator;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//this class binds the item with displayed list element
public class ItemAdapter extends CursorRecyclerViewAdapter<ItemAdapter.ViewHolder>{

    public ItemAdapter(Context context, Cursor cursor){
        super(context,cursor);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView barcode;

        public ViewHolder(View view) {
            super(view);
            barcode = (TextView) view.findViewById(R.id.barcode);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        Item item = new Item(cursor);
        viewHolder.barcode.setText(""+item.getBarcode());

    }
}