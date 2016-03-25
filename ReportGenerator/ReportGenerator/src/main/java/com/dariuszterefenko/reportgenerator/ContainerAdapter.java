package com.dariuszterefenko.reportgenerator;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//this class binds the containers with list element
public class ContainerAdapter extends CursorRecyclerViewAdapter<ContainerAdapter.ViewHolder>{

    public ContainerAdapter(Context context, Cursor cursor){
        super(context,cursor);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView barcode;
        public TextView location;
        public View root;
        public ViewHolder(View view) {
            super(view);
            root = view;
            barcode = (TextView) view.findViewById(R.id.barcode);
            location = (TextView) view.findViewById(R.id.location);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_container, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        final Container container = new Container(cursor);
        viewHolder.barcode.setText(container.getBarcode());
        viewHolder.location.setText(container.getLocation1());
        viewHolder.root.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ContainerViewActivity.class);
                i.putExtra(ContainerViewActivity.CONTAINER_ID_STR, container.getId());
                view.getContext().startActivity(i);
            }
        });
    }
}