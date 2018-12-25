package kitchenatnight.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {



    public List<childitems> cartItem;
    private Context context;
    private List<category> categoryList;
    private HashMap<category, List<childitems>> listDataChild;

    public ExpandableListAdapter(Context context, List<category> categoryList, HashMap<category, List<childitems>> listDataChild) {
        this.context = context;
        this.categoryList = categoryList;
        this.listDataChild = listDataChild;
    }

    public ExpandableListAdapter() {

    }

    @Override
    public int getGroupCount() {
        return this.categoryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.categoryList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.categoryList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.categoryList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private class groupholder {
        ImageView hCatImage;
        TextView hCatName;
        ImageView hAdditem;
        LinearLayout hgLayout;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        category categoryitem = (category) getGroup(groupPosition);
        final groupholder gholder;
        if (convertView == null) {
            gholder = new groupholder();
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_item_category, null);

            gholder.hCatImage = (ImageView) convertView.findViewById(R.id.id_item_cateory_image);
            gholder.hCatName = (TextView) convertView.findViewById(R.id.id_item_cateory_name);
            gholder.hAdditem = (ImageView) convertView.findViewById(R.id.id_bt_drop_cat);
            convertView.setTag(gholder);
        } else {
            gholder = (groupholder) convertView.getTag();
        }
        gholder.hCatImage.setImageResource(categoryitem.getCatImage());
        gholder.hCatName.setText(categoryitem.getSequenceNo()+" : "+categoryitem.getCatName());
        if (isExpanded) {
            gholder.hAdditem.setImageResource(R.drawable.expand_less_icon);
        } else {
            gholder.hAdditem.setImageResource(R.drawable.expand_icon);
        }
        return convertView;
    }


    private class Holder {
        TextView hItemName, hItemPrice, hNoOfItem;
        Button hEditItem;
        ImageView hVegOrNonVeg;
        LinearLayout hRootLayout;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final childitems childitems = (childitems) getChild(groupPosition, childPosition);

        final Holder holder;
        if (convertView == null) {
            holder = new Holder();

            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);

            holder.hVegOrNonVeg = (ImageView) convertView.findViewById(R.id.id_sign_veg_or_nonveg);
            holder.hItemName = (TextView) convertView.findViewById(R.id.id_item_name);
            holder.hItemPrice = (TextView) convertView.findViewById(R.id.id_item_price);
            holder.hEditItem = (Button) convertView.findViewById(R.id.bt_edit_item);
            holder.hNoOfItem = (TextView) convertView.findViewById(R.id.tv_no_of_item);
            holder.hRootLayout = (LinearLayout) convertView.findViewById(R.id.lo_list_item);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }

        /*
        ImageView vegOrNonveg = (ImageView)convertView.findViewById(R.id.id_sign_veg_or_nonveg);
        TextView itemName = (TextView)convertView.findViewById(R.id.id_item_name);
        TextView itemPrice = (TextView)convertView.findViewById(R.id.id_item_price);
        final Button btAddItem = (Button)convertView.findViewById(R.id.bt_add_item);
        final Button inceaseButton = (Button)convertView.findViewById(R.id.bt_increase_item);
        final Button decreaseButton = (Button)convertView.findViewById(R.id.bt_decrease_item);
        final TextView noOfItem = (TextView)convertView.findViewById(R.id.tv_no_of_item);

*/
        cartItem = new ArrayList<childitems>();
        holder.hEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View viewdialog = LayoutInflater.from(context).inflate(R.layout.item_edit, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(viewdialog);

                final EditText etItemName,etItemPrice,etCategory,etSequenceNo;
                final TextView tvItemId;
                final Spinner spItemType;

                etItemName = (EditText)viewdialog.findViewById(R.id.et_item_name);
                etItemPrice = (EditText)viewdialog.findViewById(R.id.et_item_price);
                etCategory = (EditText)viewdialog.findViewById(R.id.et_item_category);
                etSequenceNo = (EditText)viewdialog.findViewById(R.id.et_item_seq_no);

                tvItemId = (TextView)viewdialog.findViewById(R.id.tv_item_id);
                spItemType = (Spinner)viewdialog.findViewById(R.id.sp_type_name);

                List<String> list = new ArrayList<String>();

                if(childitems.getVegOrNonveg().equals("veg")){

                    list.add("veg");
                    list.add("Non veg");

                }
                else {

                    list.add("Non veg");
                    list.add("veg");
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spItemType.setAdapter(dataAdapter);

                etItemName.setText(childitems.getItemName());
                etCategory.setText(childitems.getCategoryName());
                etItemPrice.setText(childitems.getItemPrice());
                etSequenceNo.setText(childitems.getCategorySequenceNo());
                tvItemId.setText(childitems.getItemId());
                final String[] type1 = new String[1];
                builder.setCancelable(false);
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String itemName, itemId, category, categorySequenceNo, price, type;

                        itemName = etItemName.getText().toString();
                        itemId = tvItemId.getText().toString();
                        category = etCategory.getText().toString();
                        categorySequenceNo = etSequenceNo.getText().toString();
                        price = etItemPrice.getText().toString();
                        type1[0] = spItemType.getSelectedItem().toString();



                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        String URL = "https://protected-reef-56374.herokuapp.com/api/v1/items/updateItem";
                        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                    if (response.contains("itemId")) {
                                        Toast.makeText(context, "Successfully updated !", Toast.LENGTH_SHORT).show();
                                        Log.i("VOLLEY", response);
                                    } else {
                                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                    }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("VOLLEY", error.toString());
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                params.put("itemId", itemId);
                                params.put("itemName", itemName);
                                params.put("category", category);
                                params.put("categorySequenceNo",categorySequenceNo);
                                params.put("type", type1[0]);
                                params.put("price", price);

                                return params;
                            }

                            @Override
                            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                String responseString = "";
                                if (response != null) {
                                    responseString = String.valueOf(response.statusCode);
                                    // can get more details such as response.headers
                                }
                                // return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                                return super.parseNetworkResponse(response);
                            }
                        };
                        requestQueue.add(stringRequest);





                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(context,type1[0],Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();




            }
        });


        if (childitems.getVegOrNonveg().equals("veg")) {
            holder.hVegOrNonVeg.setImageResource(R.drawable.veg);
        } else {
            holder.hVegOrNonVeg.setImageResource(R.drawable.nonveg);
        }
        holder.hItemName.setText(childitems.getItemName());
        holder.hItemPrice.setText(childitems.getItemPrice());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public int getChildTypeCount() {
        return 57;
    }

    @Override
    public int getGroupType(int groupPosition) {
        return super.getGroupType(groupPosition);
    }

    @Override
    public int getGroupTypeCount() {
        return 9;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }
}
