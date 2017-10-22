package example.com.stockapp.entries;

/**
 * Created by CMQ on 2017/6/22.
 */

public class MoreAdapterModel {

    public String title;

    public String des;

    public boolean isGo;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String Content;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int index;

    private String Pic1;

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    private String ItemName;

    public String getBatchNos() {
        return batchNos;
    }

    public void setBatchNos(String batchNos) {
        this.batchNos = batchNos;
    }

    private String batchNos;

    private int StockQty;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    private int sum;

    public int getStockQty() {
        return StockQty;
    }

    public void setStockQty(int stockQty) {
        StockQty = stockQty;
    }

    public String getPic1() {
        return Pic1;
    }

    public void setPic1(String pic1) {
        Pic1 = pic1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isGo() {
        return isGo;
    }

    public void setGo(boolean go) {
        isGo = go;
    }

    public MoreAdapterModel(String title, String des , boolean isGo)
    {
        this.title=title;

        this.des=des;

        this.isGo=isGo;

    }

}
