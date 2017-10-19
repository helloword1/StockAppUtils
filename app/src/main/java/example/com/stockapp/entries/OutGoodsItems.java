package example.com.stockapp.entries;

/**
 * Created by Administrator on 2017/10/17.
 */

public class OutGoodsItems {

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

    public double getStockQty() {
        return StockQty;
    }

    public void setStockQty(double stockQty) {
        StockQty = stockQty;
    }

    private double StockQty;

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
