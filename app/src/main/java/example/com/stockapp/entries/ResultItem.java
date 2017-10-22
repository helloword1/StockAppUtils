package example.com.stockapp.entries;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/22.
 */

public class ResultItem implements Serializable {
    private String CategoryName;
    private String PrincipalName;
    private String PrincipalTel;
    private int ItemID;
    private int AccsetID;
    private int ItemType;
    private int CategoryID;
    private String ItemName;
    private String ItemCode;
    private String Barcode;
    private String Spec;
    private Object SKU;
    private String Pic1;
    private String Pic2;
    private Object URL;
    private int Status;
    private int KeepTime;
    private int PrincipalID;
    private Object TraderName;
    private int Stock;
    private String Remark;
    private String batchNo;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getPrincipalName() {
        return PrincipalName;
    }

    public void setPrincipalName(String principalName) {
        PrincipalName = principalName;
    }

    public String getPrincipalTel() {
        return PrincipalTel;
    }

    public void setPrincipalTel(String principalTel) {
        PrincipalTel = principalTel;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getAccsetID() {
        return AccsetID;
    }

    public void setAccsetID(int accsetID) {
        AccsetID = accsetID;
    }

    public int getItemType() {
        return ItemType;
    }

    public void setItemType(int itemType) {
        ItemType = itemType;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getSpec() {
        return Spec;
    }

    public void setSpec(String spec) {
        Spec = spec;
    }

    public Object getSKU() {
        return SKU;
    }

    public void setSKU(Object SKU) {
        this.SKU = SKU;
    }

    public String getPic1() {
        return Pic1;
    }

    public void setPic1(String pic1) {
        Pic1 = pic1;
    }

    public String getPic2() {
        return Pic2;
    }

    public void setPic2(String pic2) {
        Pic2 = pic2;
    }

    public Object getURL() {
        return URL;
    }

    public void setURL(Object URL) {
        this.URL = URL;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getKeepTime() {
        return KeepTime;
    }

    public void setKeepTime(int keepTime) {
        KeepTime = keepTime;
    }

    public int getPrincipalID() {
        return PrincipalID;
    }

    public void setPrincipalID(int principalID) {
        PrincipalID = principalID;
    }

    public Object getTraderName() {
        return TraderName;
    }

    public void setTraderName(Object traderName) {
        TraderName = traderName;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
}
