package bookmall.vo;

public class CartVo {

    private Long no;

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    private Long userNo;
    private Long bookNo;
    private Integer quantity;
    private String bookTitle;

    public CartVo(Long no, Long userNo, Long bookNo, Integer quantity, String bookTitle) {
        this.no = no;
        this.userNo = userNo;
        this.bookNo = bookNo;
        this.quantity = quantity;
        this.bookTitle = bookTitle;
    }

    public CartVo(){

    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
/////////////////////////////////////

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer qty) {
        this.quantity = qty;
    }

    public Long getBookNo() {
        return bookNo;
    }

    public void setBookNo(Long bookNo) {
        this.bookNo = bookNo;
    }
}
