package bookmall.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class BookVo {
    private Long no;

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    private String title;
    private Integer price;

    private Long categoryNo;

    public BookVo(String title, Integer price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCategoryNo() {
        return title;
    }

    public void setCategoryNo(Long categoryNo) {
        this.categoryNo = categoryNo;
    }
}
