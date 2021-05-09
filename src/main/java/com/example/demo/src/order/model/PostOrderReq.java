package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostOrderReq {
    private int restaurantIdx;
    private int totalPrice;
    private String commentToOwner;
    private String commentToDeliveryer;
    private String paymentMethod;

    private MenuList[] menuList;

    public static class MenuList{

        private int menuIdx;
        private String menuOption;
        private int price;
        private int number;

        public int getmenuIdx(){return this.menuIdx;}
        public String getMenuOption(){return this.menuOption;}
        public int getPrice(){return this.price;}
        public int getNumber(){return this.number;}

    }

    public int getRestaurantIdx(){return this.restaurantIdx;}
    public int getTotalPrice(){return this.totalPrice;}
    public String getCommentToOwner(){return this.commentToOwner;}
    public String getCommentToDeliveryer(){return this.commentToDeliveryer;}
    public String getPaymentMethod(){return this.paymentMethod;}
    public MenuList[] getMenuList(){return this.menuList;}

}
