package com.dariuszterefenko.reportgenerator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ConfirmAdapter extends RecyclerView.Adapter<ConfirmAdapter.ViewHolder>{

    private final List<Container> containers;

    public ConfirmAdapter(List<Container> containers){
        this.containers = containers;
    }

    /**
     * creates view for single element of the list
     * @param parent
     *        root
     * @param viewType
     *        not used
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_confirm, parent, false);
        return new ViewHolder(view);
    }

    /**
     * bind the view element from the list with specific element from the list and gets the values
     * @param holder
     *        stores the view for the list element
     * @param position
     *        element from the list
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.container = containers.get(position);
        holder.barcode.setText(holder.container.getBarcode());
        holder.items.setText(holder.container.getItemsStr());

    }

    /**
     * @return returns the size for the list to be displayed
     */
    @Override
    public int getItemCount() {
        return containers.size();
    }

    /**
     * binds the view with list element
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * textview which displays the client
         */
        public final TextView barcode;
        public final TextView items;
        /**
        * displayed element
        */
        public Container container;

        /**
         * constructor for
         * @param view
         *        main view
         */
        public ViewHolder(View view) {
            super(view);
            barcode = (TextView) view.findViewById(R.id.barcode);
            items = (TextView) view.findViewById(R.id.items);
        }

    }
}