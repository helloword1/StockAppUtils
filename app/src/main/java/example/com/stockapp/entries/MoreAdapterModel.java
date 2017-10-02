package example.com.stockapp.entries;

/**
 * Created by CMQ on 2017/6/22.
 */

public class MoreAdapterModel {

    public String title;

    public String des;

    public boolean isGo;

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
