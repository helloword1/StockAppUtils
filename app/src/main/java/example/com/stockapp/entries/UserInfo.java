package example.com.stockapp.entries;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class UserInfo extends BaseEntity {
    private int UserID;
    private String UserName;
    private String UserCode;
    private String Mobile;
    private String Email;
    private String Ver;

    private List<StoresAuthorized> StoresAuthorized;

    public List<UserInfo.StoresAuthorized> getStoresAuthorized() {
        return StoresAuthorized;
    }

    public void setStoresAuthorized(List<UserInfo.StoresAuthorized> storesAuthorized) {
        StoresAuthorized = storesAuthorized;
    }

    @Override
    public String getVer() {
        return Ver;
    }

    @Override
    public void setVer(String ver) {
        Ver = ver;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
    public class StoresAuthorized implements Serializable{
        private int UserId;
        private int StoreId;
        private int OprtType;
        private String UserName;

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int userId) {
            UserId = userId;
        }

        public int getStoreId() {
            return StoreId;
        }

        public void setStoreId(int storeId) {
            StoreId = storeId;
        }

        public int getOprtType() {
            return OprtType;
        }

        public void setOprtType(int oprtType) {
            OprtType = oprtType;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String storeName) {
            StoreName = storeName;
        }

        private String StoreName;

    }
}
