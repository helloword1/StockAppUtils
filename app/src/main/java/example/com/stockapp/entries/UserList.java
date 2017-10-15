package example.com.stockapp.entries;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/15.
 */

public class UserList implements Serializable{


    /**
     * UserID : 1
     * AccsetID : 1
     * UserName : 管理员
     * UserCode : admin
     * Password :
     * Mobile : 13599999999
     * Email :
     * QQ : null
     * State : 1
     * IsSys : 1
     * RevMsgType : null
     * LastLoginTime : null
     * LastLoginIP : null
     * Remark : null
     * CreateTime : null
     * CreateUserID : 0
     * IsDel : 0
     */

    private int UserID;
    private int AccsetID;
    private String UserName;
    private String UserCode;
    private String Password;
    private String Mobile;
    private String Email;
    private Object QQ;
    private int State;
    private int IsSys;
    private Object RevMsgType;
    private Object LastLoginTime;
    private Object LastLoginIP;
    private Object Remark;
    private Object CreateTime;
    private int CreateUserID;
    private int IsDel;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public int getAccsetID() {
        return AccsetID;
    }

    public void setAccsetID(int AccsetID) {
        this.AccsetID = AccsetID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public Object getQQ() {
        return QQ;
    }

    public void setQQ(Object QQ) {
        this.QQ = QQ;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public int getIsSys() {
        return IsSys;
    }

    public void setIsSys(int IsSys) {
        this.IsSys = IsSys;
    }

    public Object getRevMsgType() {
        return RevMsgType;
    }

    public void setRevMsgType(Object RevMsgType) {
        this.RevMsgType = RevMsgType;
    }

    public Object getLastLoginTime() {
        return LastLoginTime;
    }

    public void setLastLoginTime(Object LastLoginTime) {
        this.LastLoginTime = LastLoginTime;
    }

    public Object getLastLoginIP() {
        return LastLoginIP;
    }

    public void setLastLoginIP(Object LastLoginIP) {
        this.LastLoginIP = LastLoginIP;
    }

    public Object getRemark() {
        return Remark;
    }

    public void setRemark(Object Remark) {
        this.Remark = Remark;
    }

    public Object getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Object CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getCreateUserID() {
        return CreateUserID;
    }

    public void setCreateUserID(int CreateUserID) {
        this.CreateUserID = CreateUserID;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int IsDel) {
        this.IsDel = IsDel;
    }
}
