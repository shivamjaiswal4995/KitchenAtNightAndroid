package kitchenatnight.admin;

public class orderItem {
    private String itemList;
    private String contactNo;
    private String userName;
    private String totalAmount;
    private String tax;
    private String discount;
    private String offerApplied;
    private String paybleAmount;

    public orderItem(String itemList, String contactNo, String userName, String totalAmount, String tax, String discount, String offerApplied, String paybleAmount) {
        this.itemList = itemList;
        this.contactNo = contactNo;
        this.userName = userName;
        this.totalAmount = totalAmount;
        this.tax = tax;
        this.discount = discount;
        this.offerApplied = offerApplied;
        this.paybleAmount = paybleAmount;
    }

    public String getItemList() {
        return itemList;
    }

    public void setItemList(String itemList) {
        this.itemList = itemList;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getOfferApplied() {
        return offerApplied;
    }

    public void setOfferApplied(String offerApplied) {
        this.offerApplied = offerApplied;
    }

    public String getPaybleAmount() {
        return paybleAmount;
    }

    public void setPaybleAmount(String paybleAmount) {
        this.paybleAmount = paybleAmount;
    }
}
