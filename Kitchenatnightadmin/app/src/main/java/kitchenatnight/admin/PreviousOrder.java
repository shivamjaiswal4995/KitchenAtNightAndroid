package kitchenatnight.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreviousOrder extends AppCompatActivity {

    Button btOrders,btItems,btOffers,btMyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_order);


        btOrders = (Button)findViewById(R.id.bt_orders);
        btItems = (Button)findViewById(R.id.bt_items);
        btOffers = (Button)findViewById(R.id.bt_offers);
        btMyAccount = (Button)findViewById(R.id.bt_my_account);

        btOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviousOrder.this,Orders.class);
                startActivity(intent);
            }
        });
        btItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviousOrder.this,Items.class);
                startActivity(intent);
            }
        });







    }
}
