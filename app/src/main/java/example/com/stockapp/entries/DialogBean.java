package example.com.stockapp.entries;

/**
 * Created by Administrator on 2017/10/14.
 */

public class DialogBean {
    private String TypeName;
    private boolean isSelct;
    private String id;

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public boolean isSelct() {
        return isSelct;
    }

    public void setSelct(boolean selct) {
        isSelct = selct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DialogBean(String typeName, String id) {
        TypeName = typeName;
        this.id = id;
    }
}
