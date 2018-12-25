package kitchenatnight.admin;

import android.widget.Button;

public class childitems {
    private String itemId;
    private String itemName;
    private String itemPrice;
    private String vegOrNonveg;
    private String categoryName;
    private String categorySequenceNo;
    private int noOfItem;






    public childitems(String itemId,String vegOrNonveg, String itemName, String itemPrice, String categoryName, String categorySequenceNo) {
        this.vegOrNonveg = vegOrNonveg;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.noOfItem = noOfItem;
        this.itemId = itemId;
        this.categoryName = categoryName;
        this.categorySequenceNo = categorySequenceNo;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategorySequenceNo() {
        return categorySequenceNo;
    }

    public void setCategorySequenceNo(String categorySequenceNo) {
        this.categorySequenceNo = categorySequenceNo;
    }

    public String getVegOrNonveg() {
        return vegOrNonveg;
    }

    public void setVegOrNonveg(String vegOrNonveg) {
        this.vegOrNonveg = vegOrNonveg;
    }

    public int getNoOfItem() {
        return noOfItem;
    }

    public void setNoOfItem(int noOfItem) {
        this.noOfItem = noOfItem;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }


}
