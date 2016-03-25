package com.dariuszterefenko.reportgenerator;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//this adapter creates relation between the db cursor which contains the client and the viewholder
public class ClientAdapter extends CursorRecyclerViewAdapter<ClientAdapter.ViewHolder>{

    public ClientAdapter(Context context,Cursor cursor){
        super(context,cursor);
    }

    //holds view for the list
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView email;
        public TextView phone;
        public TextView data;
        public View root;
        public ViewHolder(View view) {
            super(view);
            root = view;
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
            phone = (TextView) view.findViewById(R.id.phone);
            data = (TextView) view.findViewById(R.id.data);
        }
    }

    //crates view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_client, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    //displays client in the list element
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        final Client client = new Client(cursor);
        viewHolder.name.setText(client.getFirstname()+" "+client.getSurname());
        viewHolder.phone.setText(client.getPhone());
        viewHolder.email.setText(client.getEmail());
        viewHolder.data.setText(client.getDate());
        viewHolder.root.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ClientViewActivity.class);
                i.putExtra(ClientViewActivity.CLIENT_ID_STR, client.getId());
                v.getContext().startActivity(i);

            }
        });
    }
}