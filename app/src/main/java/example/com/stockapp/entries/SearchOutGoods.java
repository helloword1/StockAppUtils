package example.com.stockapp.entries;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class SearchOutGoods implements Serializable {

    /**
     * Bill : {"OutstockType":100,"OutstockDate":"2017-10-140","PrincipalID":11,"StoreID":15,"Remark":"备注"}
     * Items : [{"BatchNo":"批次号","ItemID":10,"ItemName":"商品名称","ItemBarcode":"条码","ImgUrl":"商品图片","Qty":19,"Remark":"备注"}]
     */

    private BillBean Bill;
    private List<ItemsBean> Items;

    public BillBean getBill() {
        return Bill;
    }

    public void setBill(BillBean Bill) {
        this.Bill = Bill;
    }

    public List<ItemsBean> getItems() {
        return Items;
    }

    public void setItems(List<ItemsBean> Items) {
        this.Items = Items;
    }

    public static class BillBean {
        /**
         * OutstockType : 100
         * OutstockDate : 2017-10-140
         * PrincipalID : 11
         * StoreID : 15
         * Remark : 备注
         */

        private int OutstockType;
        private String OutstockDate;
        private int PrincipalID;
        private int StoreID;
        private String Remark;

        public int getOutstockType() {
            return OutstockType;
        }

        public void setOutstockType(int OutstockType) {
            this.OutstockType = OutstockType;
        }

        public String getOutstockDate() {
            return OutstockDate;
        }

        public void setOutstockDate(String OutstockDate) {
            this.OutstockDate = OutstockDate;
        }

        public int getPrincipalID() {
            return PrincipalID;
        }

        public void setPrincipalID(int PrincipalID) {
            this.PrincipalID = PrincipalID;
        }

        public int getStoreID() {
            return StoreID;
        }

        public void setStoreID(int StoreID) {
            this.StoreID = StoreID;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }
    }

    public static class ItemsBean {
        /**
         * BatchNo : 批次号
         * ItemID : 10
         * ItemName : 商品名称
         * ItemBarcode : 条码
         * ImgUrl : 商品图片
         * Qty : 19.0
         * Remark : 备注
         */

        private String BatchNo;
        private int ItemID;
        private String ItemName;
        private String ItemBarcode;
        private String ImgUrl;
        private double Qty;
        private String Remark;

        public String getBatchNo() {
            return BatchNo;
        }

        public void setBatchNo(String BatchNo) {
            this.BatchNo = BatchNo;
        }

        public int getItemID() {
            return ItemID;
        }

        public void setItemID(int ItemID) {
            this.ItemID = ItemID;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public String getItemBarcode() {
            return ItemBarcode;
        }

        public void setItemBarcode(String ItemBarcode) {
            this.ItemBarcode = ItemBarcode;
        }

        public String getImgUrl() {
            return ImgUrl;
        }

        public void setImgUrl(String ImgUrl) {
            this.ImgUrl = ImgUrl;
        }

        public double getQty() {
            return Qty;
        }

        public void setQty(double Qty) {
            this.Qty = Qty;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }
    }
}
