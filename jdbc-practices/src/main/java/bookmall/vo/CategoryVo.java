package bookmall.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class CategoryVo {

    private Long no;
    private String category;
    /////////////////////////////////////


    public CategoryVo(String category) {
        this.category = category;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
