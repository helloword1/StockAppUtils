package example.com.stockapp.entries;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/10.
 */

public class BaseEntity<T> implements Serializable {
    protected String ver;
    protected boolean result;
    protected T data;
    protected int errorcode;
    protected String errormsg;

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
}