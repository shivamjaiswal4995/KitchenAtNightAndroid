package kitchenatnight.admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class orderListAdapter extends ArrayAdapter<orderItem> {

    List<orderItem> orderItemList;

    Context context;
    int resource;

    public orderListAdapter(@NonNull Context context, int resource, @NonNull List<orderItem> orderItemList) {
        super(context, resource, orderItemList);
        this.context = context;
        this.orderItemList = orderItemList;
        this.resource = resource;
    }

    private class Holder {
        TextView itemList, contactNo, userName, totalAmount, tax, discount, offerApplied, payableAmount, delivered, dispatched, cancelled;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Holder holder;

        if (convertView == null) {
            LayoutInflater layoutinflater = LayoutInflater.from(context);
            convertView = layoutinflater.inflate(R.layout.orders_item, null, false);
            holder = new Holder();

            holder.itemList = (TextView) convertView.findViewById(R.id.tv_item_list);
            holder.contactNo = (TextView) convertView.findViewById(R.id.tv_contact_no);
            holder.userName = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.totalAmount = (TextView) convertView.findViewById(R.id.tv_total_amount);
            holder.tax = (TextView) convertView.findViewById(R.id.tv_tax);
            holder.discount = (TextView) convertView.findViewById(R.id.tv_discount);
            holder.offerApplied = (TextView) convertView.findViewById(R.id.tv_offer_applied);
            holder.payableAmount = (TextView) convertView.findViewById(R.id.tv_payable_amount);
            holder.delivered = (TextView) convertView.findViewById(R.id.tv_delivered);
            holder.dispatched = (TextView) convertView.findViewById(R.id.tv_dispatched);
            holder.cancelled = (TextView) convertView.findViewById(R.id.tv_cancelled);

            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }
        orderItem orderItem = orderItemList.get(position);
        holder.itemList.setText(orderItem.getItemList());
        holder.contactNo.setText(orderItem.getContactNo());
        holder.userName.setText(orderItem.getUserName());
        holder.totalAmount.setText(orderItem.getTotalAmount());
        holder.tax.setText(orderItem.getTax());
        holder.discount.setText(orderItem.getDiscount());
        holder.offerApplied.setText(orderItem.getOfferApplied());
        holder.payableAmount.setText(orderItem.getPaybleAmount());

        return convertView;


    }
}
