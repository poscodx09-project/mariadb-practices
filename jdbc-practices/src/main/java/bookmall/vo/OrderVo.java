package bookmall.vo;


public class OrderVo {
    private Long no;
    private Long userNo;
    private String number;
    private String status;
    private Integer payment;
    private String shipping;
    ///////////////////////////////////////////////

    public OrderVo(){

    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPayment() {
        return payment;
    }

    public OrderVo(Long no, Long userNo, String number, String status, Integer payment, String shipping) {
        this.no = no;
        this.userNo = userNo;
        this.number = number;
        this.status = status;
        this.payment = payment;
        this.shipping = shipping;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }


    /////////////////////////////////////



}
