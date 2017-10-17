package example.com.stockapp.entries;

import java.util.List;

/**
 * Created by Administrator on 2017/10/14.
 */

public class InventoryEntity {
    private int RecordCount;
    private int PageSize;

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }

    public List<DataSetBean> getDataSet() {
        return DataSet;
    }

    public void setDataSet(List<DataSetBean> dataSet) {
        DataSet = dataSet;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    private int PageCount;
    private List<DataSetBean> DataSet;
    private int PageIndex;


    public class DataSetBean {
        private String Barcode;
        private String ItemCode;
        private String ItemName;
        private String ItemSpec;
        private String StoreCode;
        private String StoreName;
        private int ItemType;
        private String CategoryName;
        private String ProductDate;
        private String KeepTime;
        private String Indate;
        private String PrincipalName;

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

        public String Pic1;

        public String Pic2;

        public String getBarcode() {
            return Barcode;
        }

        public void setBarcode(String barcode) {
            Barcode = barcode;
        }

        public String getItemCode() {
            return ItemCode;
        }

        public void setItemCode(String itemCode) {
            ItemCode = itemCode;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String itemName) {
            ItemName = itemName;
        }

        public String getItemSpec() {
            return ItemSpec;
        }

        public void setItemSpec(String itemSpec) {
            ItemSpec = itemSpec;
        }

        public String getStoreCode() {
            return StoreCode;
        }

        public void setStoreCode(String storeCode) {
            StoreCode = storeCode;
        }

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String storeName) {
            StoreName = storeName;
        }

        public int getItemType() {
            return ItemType;
        }

        public void setItemType(int itemType) {
            ItemType = itemType;
        }

        public String getCategoryName() {
            return CategoryName;
        }

        public void setCategoryName(String categoryName) {
            CategoryName = categoryName;
        }

        public String getProductDate() {
            return ProductDate;
        }

        public void setProductDate(String productDate) {
            ProductDate = productDate;
        }

        public String getKeepTime() {
            return KeepTime;
        }

        public void setKeepTime(String keepTime) {
            KeepTime = keepTime;
        }

        public String getIndate() {
            return Indate;
        }

        public void setIndate(String indate) {
            Indate = indate;
        }

        public String getPrincipalName() {
            return PrincipalName;
        }

        public void setPrincipalName(String principalName) {
            PrincipalName = principalName;
        }

        public String getPrincipalID() {
            return PrincipalID;
        }

        public void setPrincipalID(String principalID) {
            PrincipalID = principalID;
        }

        public String getPrincipalTel() {
            return PrincipalTel;
        }

        public void setPrincipalTel(String principalTel) {
            PrincipalTel = principalTel;
        }

        public String getStockID() {
            return StockID;
        }

        public void setStockID(String stockID) {
            StockID = stockID;
        }

        public int getAccsetID() {
            return AccsetID;
        }

        public void setAccsetID(int accsetID) {
            AccsetID = accsetID;
        }

        public int getStoreID() {
            return StoreID;
        }

        public void setStoreID(int storeID) {
            StoreID = storeID;
        }

        public String getBatchNo() {
            return BatchNo;
        }

        public void setBatchNo(String batchNo) {
            BatchNo = batchNo;
        }

        public String getItemID() {
            return ItemID;
        }

        public void setItemID(String itemID) {
            ItemID = itemID;
        }

        public String getQty() {
            return Qty;
        }

        public void setQty(String qty) {
            Qty = qty;
        }

        public String getOccupiedOutQty() {
            return OccupiedOutQty;
        }

        public void setOccupiedOutQty(String occupiedOutQty) {
            OccupiedOutQty = occupiedOutQty;
        }

        public String getOccupiedInQty() {
            return OccupiedInQty;
        }

        public void setOccupiedInQty(String occupiedInQty) {
            OccupiedInQty = occupiedInQty;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String updateTime) {
            UpdateTime = updateTime;
        }

        public String getIsDel() {
            return IsDel;
        }

        public void setIsDel(String isDel) {
            IsDel = isDel;
        }

        public String getVersion() {
            return Version;
        }

        public void setVersion(String version) {
            Version = version;
        }

        private String PrincipalID;
        private String PrincipalTel;
        private String StockID;
        private int AccsetID;
        private int StoreID;
        private String BatchNo;
        private String ItemID;
        private String Qty;
        private String OccupiedOutQty;
        private String OccupiedInQty;
        private String Remark;
        private String CreateTime;
        private String UpdateTime;
        private String IsDel;
        private String Version;
    }
}
