package kitchenatnight.admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Items extends AppCompatActivity {

    Button btAddNewItem;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<category> categoryList;
    HashMap<category, List<childitems>> listDataChield;
    // json array response url
    private String urlJsonArry = "https://protected-reef-56374.herokuapp.com/api/v1/items/byCategory";

    private static String TAG = Items.class.getSimpleName();

    // Progress dialog
    private ProgressDialog pDialog;

    // temporary string to show the parsed response
    private String jsonResponse;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        //itemCategoryList = (ListView)findViewById(R.id.id_item_cateory);

        //get the list view
        expListView = (ExpandableListView) findViewById(R.id.id_lis_item_cateory);
        btAddNewItem = (Button) findViewById(R.id.bt_add_new_item);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        //    pDialog.setCancelable(false);

        queue = Volley.newRequestQueue(this);


        //showpDialog();
        pDialog.show();

        getItemList(new VolleyCallBack() {
            @Override
            public void onSuccess() {

                Toast.makeText(Items.this, "success ho gya" + "", Toast.LENGTH_LONG).show();
                listAdapter = new kitchenatnight.admin.ExpandableListAdapter(Items.this, categoryList, listDataChield);
                expListView.setAdapter(listAdapter);
                pDialog.dismiss();

            }
        });


//        itemCategoryList.setAdapter(adapter);
        btAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View viewdialog = LayoutInflater.from(Items.this).inflate(R.layout.item_edit, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Items.this);
                builder.setView(viewdialog);

                final EditText etItemName,etItemPrice,etCategory,etSequenceNo;
                final TextView tvItemId,tvItemId1,tvCategoryName;
                final Spinner spItemType;

                etItemName = (EditText)viewdialog.findViewById(R.id.et_item_name);
                etItemPrice = (EditText)viewdialog.findViewById(R.id.et_item_price);
                etCategory = (EditText)viewdialog.findViewById(R.id.et_item_category);
                etSequenceNo = (EditText)viewdialog.findViewById(R.id.et_item_seq_no);

                tvItemId = (TextView)viewdialog.findViewById(R.id.tv_item_id);
                tvItemId1 = (TextView)viewdialog.findViewById(R.id.tv_item_id_1);
                tvCategoryName = (TextView)viewdialog.findViewById(R.id.tv_item_category);
                spItemType = (Spinner)viewdialog.findViewById(R.id.sp_type_name);

                tvItemId1.setVisibility(View.GONE);
                tvItemId.setVisibility(View.GONE);

                List<String> list = new ArrayList<String>();
                    list.add("veg");
                    list.add("Non veg");

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Items.this,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spItemType.setAdapter(dataAdapter);

                builder.setCancelable(false);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String itemName, itemId, category, categorySequenceNo, price, type;

                        itemName = etItemName.getText().toString();
                        itemId = tvItemId.getText().toString();
                        category = etCategory.getText().toString();
                        categorySequenceNo = etSequenceNo.getText().toString();
                        price = etItemPrice.getText().toString();
                        type = spItemType.getSelectedItem().toString();



                        RequestQueue requestQueue = Volley.newRequestQueue(Items.this);
                        String URL = "https://protected-reef-56374.herokuapp.com/api/v1/items/addItem";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                if (response.contains("itemName")) {
                                    Toast.makeText(Items.this, "Successfully added !", Toast.LENGTH_SHORT).show();
                                    Log.i("VOLLEY", response);
                                } else {
                                    Toast.makeText(Items.this, response, Toast.LENGTH_SHORT).show();
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

                                params.put("itemName", itemName);
                                params.put("category", category);
                                params.put("categorySequenceNo",categorySequenceNo);
                                params.put("type", type);
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

                    }
                });
                builder.show();


            }
        });


    }
/*



    /**
     * Method to make json array request where response starts with [
     */
    private void getItemList(final VolleyCallBack callBack) {

        categoryList = new ArrayList<category>();
        listDataChield = new HashMap<category, List<childitems>>();

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        //   Toast.makeText(MainActivity.this, response.length() + "", Toast.LENGTH_LONG).show();

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                List<childitems> childitems = null;

                                JSONObject jsonObject1 = (JSONObject) response.get(i);
                                JSONObject jsonObject2 = jsonObject1.getJSONObject("_id");
                                String category = jsonObject2.getString("category");
                                String categorySequenceNo = jsonObject2.getString("categorySequenceNo");
                                categoryList.add(new category(R.drawable.image1, category,categorySequenceNo));
                                JSONArray jsonArray1 = jsonObject1.getJSONArray("items");
                                childitems = new ArrayList<childitems>();
                                int type1;
                                for (int j = 0; j < jsonArray1.length(); j++) {
                                    JSONObject jsonObject3 = (JSONObject) jsonArray1.get(j);
                                    String itemId = jsonObject3.getString("itemId");
                                    String category1 = jsonObject3.getString("category");
                                    String categorySequenceNo1 = jsonObject3.getString("categorySequenceNo");
                                    String itemName = jsonObject3.getString("itemName");
                                    String itemPrice = jsonObject3.getString("price");
                                    String type = jsonObject3.getString("type");

                                    childitems.add(new childitems(itemId, type, itemName, itemPrice, category1, categorySequenceNo1));
                                }
                                listDataChield.put(categoryList.get(i), childitems);
                                callBack.onSuccess();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error1: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            pDialog.dismiss();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error2: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });

        // Adding request to request queue
        queue.add(req);
    }

    public interface VolleyCallBack {
        void onSuccess();
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
