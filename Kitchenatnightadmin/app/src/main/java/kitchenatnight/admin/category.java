package kitchenatnight.admin;

import android.widget.Button;

public class category {
    private Integer catImage;
    private String catName;
    private Button catbutton;
    private String sequenceNo;

    public category(Integer catImage, String catName,String sequenceNo) {
        this.catImage = catImage;
        this.catName = catName;
        this.sequenceNo = sequenceNo;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Integer getCatImage() {
        return catImage;
    }

    public void setCatImage(Integer catImage) {
        this.catImage = catImage;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Button getCatbutton() {
        return catbutton;
    }

    public void setCatbutton(Button catbutton) {
        this.catbutton = catbutton;
    }
}
