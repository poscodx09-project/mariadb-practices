package bookmall.vo;


public class OrderBookVo {
    private Long orderNo;
    private Long bookNo;
    private Integer quantity;
    private Integer price;

    public Integer getPrice() {
        return price;
    }
    public OrderBookVo() {
    }

    public OrderBookVo(Long orderNo,  Integer quantity, Integer price, Long bookNo) {
        this.orderNo = orderNo;
        this.bookNo = bookNo;
        this.quantity = quantity;
        this.price = price;

    }

    public void setPrice(Integer price) {
        this.price = price;
    }
////////////////////////////////

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
/////////////////////////////////////


    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Long getBookNo() {
        return bookNo;
    }

    public void setBookNo(Long bookNo) {
        this.bookNo = bookNo;
    }
}
