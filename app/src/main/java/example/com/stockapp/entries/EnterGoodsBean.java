package example.com.stockapp.entries;

/**
 * Created by Administrator on 2017/10/3.
 */

public class EnterGoodsBean {
    private String title;
    private String content;
    private boolean isHaveRight;
    private boolean isInput;

    public String getPic2Url() {
        return pic2Url;
    }

    public void setPic2Url(String pic2Url) {
        this.pic2Url = pic2Url;
    }

    private String pic2Url;

    public String getPic1Url() {
        return pic1Url;
    }

    public void setPic1Url(String pic1Url) {
        this.pic1Url = pic1Url;
    }

    private String pic1Url;

    public String getHint() {
        return Hint;
    }

    public void setHint(String hint) {
        Hint = hint;
    }

    private String Hint;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHaveRight() {
        return isHaveRight;
    }

    public void setHaveRight(boolean haveRight) {
        isHaveRight = haveRight;
    }

    public boolean isInput() {
        return isInput;
    }

    public void setInput(boolean input) {
        isInput = input;
    }

    public EnterGoodsBean(String title, String hint, boolean isHaveRight, boolean isInput) {
        this.title = title;
        this.Hint = hint;
        this.isHaveRight = isHaveRight;
        this.isInput = isInput;
    }
}
