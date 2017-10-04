package example.com.stockapp.entries;

/**
 * Created by Administrator on 2017/10/3.
 */

public class EnterGoodsBean {
    private String title;
    private String content;
    private boolean isHaveRight;
    private boolean isInput;

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

    public EnterGoodsBean(String title, String content, boolean isHaveRight, boolean isInput) {
        this.title = title;
        this.content = content;
        this.isHaveRight = isHaveRight;
        this.isInput = isInput;
    }
}
