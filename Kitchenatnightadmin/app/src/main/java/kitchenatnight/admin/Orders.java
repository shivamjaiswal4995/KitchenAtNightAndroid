package kitchenatnight.admin;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Orders extends AppCompatActivity {

    List<orderItem> orderItems;
    ListView lvOrderList;

    // json array response url
    private String urlJsonArry = "https://protected-reef-56374.herokuapp.com/api/v1/orders/inTheKitchenOrders";

    private static String TAG = Orders.class.getSimpleName();

    // Progress dialog
    private ProgressDialog pDialog;

    // temporary string to show the parsed response
    private String jsonResponse;

    RequestQueue queue;
    orderListAdapter orderListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        lvOrderList = (ListView)findViewById(R.id.lv_orders_list);

        orderItems = new ArrayList<orderItem>();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        //    pDialog.setCancelable(false);

        queue = Volley.newRequestQueue(this);


        //showpDialog();
        pDialog.show();

        getItemList(new VolleyCallBack() {
            @Override
            public void onSuccess() {

                Toast.makeText(Orders.this, "success ho gya" + "", Toast.LENGTH_LONG).show();
                orderListAdapter = new orderListAdapter(Orders.this,R.layout.orders_item,orderItems);
                lvOrderList.setAdapter(orderListAdapter);
                pDialog.dismiss();

            }
        });

    }
    private void getItemList(final VolleyCallBack callBack) {


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
                                JSONObject jsonObject = (JSONObject) response.get(i);

                                String contactNo = jsonObject.getString("contactNo");
                                String userName = jsonObject.getString("userName");
                                String totalCost = jsonObject.getString("totalCost");
                                String tax = jsonObject.getString("tax");
                                String discount = jsonObject.getString("discount");
                                String offerApplied = jsonObject.getString("offerApplied");
                                String payable_amount = jsonObject.getString("payabale_Amount");
                                StringBuffer stringBuffer = new StringBuffer();
                                JSONArray items = jsonObject.getJSONArray("items");
                                for(int j=0;j<items.length();j++){
                                    JSONObject item = (JSONObject)items.get(j);
                                    String itemName = item.getString("itemName");
                                    String noOfItem = item.getString("noOfItem");
                                    String oneItem = itemName + " : " +noOfItem+"\n";
                                    stringBuffer.append(oneItem);
                                }
                                String fitems = stringBuffer.toString();
                                orderItems.add(new orderItem(fitems,contactNo,userName,totalCost,tax,discount,offerApplied,payable_amount));
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
}
