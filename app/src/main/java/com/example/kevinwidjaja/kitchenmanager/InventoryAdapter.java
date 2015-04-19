package com.example.kevinwidjaja.kitchenmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kevinwidjaja on 4/13/15.
 */
public class InventoryAdapter extends ArrayAdapter<Inventory> {

    private final int inventoryEntryLayoutResource;

    public InventoryAdapter(final Context context, final int inventoryEntryLayoutResource) {
        super(context, 0);
        this.inventoryEntryLayoutResource = inventoryEntryLayoutResource;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        // We need to get the best view (re-used if possible) and then
        // retrieve its corresponding ViewHolder, which optimizes lookup efficiency
        final View view = getWorkingView(convertView);
        final ViewHolder viewHolder = getViewHolder(view);
        final Inventory entry = getItem(position);

        // Setting the title view is straightforward
        viewHolder.titleView.setText(entry.getName());

        // Setting the subTitle view
        //viewHolder.subTitleView.setText(new Integer(entry.getQuantity()).toString());
        viewHolder.subTitleView.setText(entry.toString());

        // Setting image view is also simple
        viewHolder.imageView.setImageResource(R.drawable.bag);

        return view;
    }

    private View getWorkingView(final View convertView) {
        // The workingView is basically just the convertView re-used if possible
        // or inflated new if not possible
        View workingView = null;

        if(null == convertView) {
            final Context context = getContext();
            final LayoutInflater inflater = (LayoutInflater)context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);

            workingView = inflater.inflate(inventoryEntryLayoutResource, null);
        } else {
            workingView = convertView;
        }

        return workingView;
    }

    private ViewHolder getViewHolder(final View workingView) {
        // The viewHolder allows us to avoid re-looking up view references
        // Since views are recycled, these references will never change
        final Object tag = workingView.getTag();
        ViewHolder viewHolder = null;


        if(null == tag || !(tag instanceof ViewHolder)) {
            viewHolder = new ViewHolder();

            viewHolder.titleView = (TextView) workingView.findViewById(R.id.entryname);
            viewHolder.subTitleView = (TextView) workingView.findViewById(R.id.entrydetails);
            viewHolder.imageView = (ImageView) workingView.findViewById(R.id.entryicon);

            workingView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) tag;
        }

        return viewHolder;
    }

    /**
     * ViewHolder allows us to avoid re-looking up view references
     * Since views are recycled, these references will never change
     */
    private static class ViewHolder {
        public TextView titleView;
        public TextView subTitleView;
        public ImageView imageView;
    }

}
