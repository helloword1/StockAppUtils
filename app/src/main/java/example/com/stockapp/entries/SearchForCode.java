package example.com.stockapp.entries;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class SearchForCode implements Serializable {

    /**
     * Item : {"CategoryName":"西药","PrincipalName":"管理员","PrincipalTel":"13599999999","ItemID":6,"AccsetID":1,"ItemType":1,"CategoryID":2,"ItemName":"葡萄糖酸钙锌口服溶液","ItemCode":"葡萄糖酸钙锌口服溶液","Barcode":"6931037800803","Spec":"10毫升X24支","SKU":null,"Pic1":"/upload/img/0997dbbfe4d74ce88bba0fdd6ba4999c.jpg","Pic2":"/upload/img/thumb_0997dbbfe4d74ce88bba0fdd6ba4999c.jpg","URL":null,"Status":1,"KeepTime":0,"PrincipalID":1,"TraderName":null,"Stock":40,"Remark":null}
     * BatchNos : [{"StockQty":0,"StoreID":1,"StoreName":"总队药品仓","ItemName":"葡萄糖酸钙锌口服溶液","ItemCode":"葡萄糖酸钙锌口服溶液","ItemSpec":"10毫升X24支","Barcode":"6931037800803","BatchID":12,"AccsetID":1,"ItemID":6,"BatchNo":"2020-10-30","ProductDate":null,"KeepTime":0,"Indate":"2020-10-30","Remark":null}]
     */

    private ItemBean Item;
    private List<BatchNosBean> BatchNos;

    public ItemBean getItem() {
        return Item;
    }

    public void setItem(ItemBean Item) {
        this.Item = Item;
    }

    public List<BatchNosBean> getBatchNos() {
        return BatchNos;
    }

    public void setBatchNos(List<BatchNosBean> BatchNos) {
        this.BatchNos = BatchNos;
    }

    public static class ItemBean {
        /**
         * CategoryName : 西药
         * PrincipalName : 管理员
         * PrincipalTel : 13599999999
         * ItemID : 6
         * AccsetID : 1
         * ItemType : 1
         * CategoryID : 2
         * ItemName : 葡萄糖酸钙锌口服溶液
         * ItemCode : 葡萄糖酸钙锌口服溶液
         * Barcode : 6931037800803
         * Spec : 10毫升X24支
         * SKU : null
         * Pic1 : /upload/img/0997dbbfe4d74ce88bba0fdd6ba4999c.jpg
         * Pic2 : /upload/img/thumb_0997dbbfe4d74ce88bba0fdd6ba4999c.jpg
         * URL : null
         * Status : 1
         * KeepTime : 0
         * PrincipalID : 1
         * TraderName : null
         * Stock : 40
         * Remark : null
         */

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

        public String getCategoryName() {
            return CategoryName;
        }

        public void setCategoryName(String CategoryName) {
            this.CategoryName = CategoryName;
        }

        public String getPrincipalName() {
            return PrincipalName;
        }

        public void setPrincipalName(String PrincipalName) {
            this.PrincipalName = PrincipalName;
        }

        public String getPrincipalTel() {
            return PrincipalTel;
        }

        public void setPrincipalTel(String PrincipalTel) {
            this.PrincipalTel = PrincipalTel;
        }

        public int getItemID() {
            return ItemID;
        }

        public void setItemID(int ItemID) {
            this.ItemID = ItemID;
        }

        public int getAccsetID() {
            return AccsetID;
        }

        public void setAccsetID(int AccsetID) {
            this.AccsetID = AccsetID;
        }

        public int getItemType() {
            return ItemType;
        }

        public void setItemType(int ItemType) {
            this.ItemType = ItemType;
        }

        public int getCategoryID() {
            return CategoryID;
        }

        public void setCategoryID(int CategoryID) {
            this.CategoryID = CategoryID;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public String getItemCode() {
            return ItemCode;
        }

        public void setItemCode(String ItemCode) {
            this.ItemCode = ItemCode;
        }

        public String getBarcode() {
            return Barcode;
        }

        public void setBarcode(String Barcode) {
            this.Barcode = Barcode;
        }

        public String getSpec() {
            return Spec;
        }

        public void setSpec(String Spec) {
            this.Spec = Spec;
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

        public void setPic1(String Pic1) {
            this.Pic1 = Pic1;
        }

        public String getPic2() {
            return Pic2;
        }

        public void setPic2(String Pic2) {
            this.Pic2 = Pic2;
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

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public int getKeepTime() {
            return KeepTime;
        }

        public void setKeepTime(int KeepTime) {
            this.KeepTime = KeepTime;
        }

        public int getPrincipalID() {
            return PrincipalID;
        }

        public void setPrincipalID(int PrincipalID) {
            this.PrincipalID = PrincipalID;
        }

        public Object getTraderName() {
            return TraderName;
        }

        public void setTraderName(Object TraderName) {
            this.TraderName = TraderName;
        }

        public int getStock() {
            return Stock;
        }

        public void setStock(int Stock) {
            this.Stock = Stock;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }
    }

    public static class BatchNosBean {
        /**
         * StockQty : 0
         * StoreID : 1
         * StoreName : 总队药品仓
         * ItemName : 葡萄糖酸钙锌口服溶液
         * ItemCode : 葡萄糖酸钙锌口服溶液
         * ItemSpec : 10毫升X24支
         * Barcode : 6931037800803
         * BatchID : 12
         * AccsetID : 1
         * ItemID : 6
         * BatchNo : 2020-10-30
         * ProductDate : null
         * KeepTime : 0
         * Indate : 2020-10-30
         * Remark : null
         */

        private int StockQty;
        private int StoreID;
        private String StoreName;
        private String ItemName;
        private String ItemCode;
        private String ItemSpec;
        private String Barcode;
        private int BatchID;
        private int AccsetID;
        private int ItemID;
        private String BatchNo;
        private String ProductDate;
        private int KeepTime;
        private String Indate;
        private String Remark;

        public int getStockQty() {
            return StockQty;
        }

        public void setStockQty(int StockQty) {
            this.StockQty = StockQty;
        }

        public int getStoreID() {
            return StoreID;
        }

        public void setStoreID(int StoreID) {
            this.StoreID = StoreID;
        }

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String StoreName) {
            this.StoreName = StoreName;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public String getItemCode() {
            return ItemCode;
        }

        public void setItemCode(String ItemCode) {
            this.ItemCode = ItemCode;
        }

        public String getItemSpec() {
            return ItemSpec;
        }

        public void setItemSpec(String ItemSpec) {
            this.ItemSpec = ItemSpec;
        }

        public String getBarcode() {
            return Barcode;
        }

        public void setBarcode(String Barcode) {
            this.Barcode = Barcode;
        }

        public int getBatchID() {
            return BatchID;
        }

        public void setBatchID(int BatchID) {
            this.BatchID = BatchID;
        }

        public int getAccsetID() {
            return AccsetID;
        }

        public void setAccsetID(int AccsetID) {
            this.AccsetID = AccsetID;
        }

        public int getItemID() {
            return ItemID;
        }

        public void setItemID(int ItemID) {
            this.ItemID = ItemID;
        }

        public String getBatchNo() {
            return BatchNo;
        }

        public void setBatchNo(String BatchNo) {
            this.BatchNo = BatchNo;
        }

        public String getProductDate() {
            return ProductDate;
        }

        public void setProductDate(String ProductDate) {
            this.ProductDate = ProductDate;
        }

        public int getKeepTime() {
            return KeepTime;
        }

        public void setKeepTime(int KeepTime) {
            this.KeepTime = KeepTime;
        }

        public String getIndate() {
            return Indate;
        }

        public void setIndate(String Indate) {
            this.Indate = Indate;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }
    }
}
