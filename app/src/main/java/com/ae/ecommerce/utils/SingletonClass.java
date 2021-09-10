package com.ae.ecommerce.utils;

import com.ae.ecommerce.model.CheckoutInfo;
import com.ae.ecommerce.model.CartItemModel;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class SingletonClass extends Methods {
    private static SingletonClass sSoleInstance;

    private Gson gson;
    public CheckoutInfo checkoutInfo = new CheckoutInfo();
    public CheckoutInfo billingInfo = new CheckoutInfo();
    public List<CartItemModel> cartItemList;
    public boolean isLogin = false;

    public String TOKEN;


    private SingletonClass() {
        cartItemList = new ArrayList<>();
    }

    public static SingletonClass getInstance() {
        if (sSoleInstance == null) {
            sSoleInstance = new SingletonClass();
        }

        return sSoleInstance;
    }





    public Gson gson() {
        if (gson == null)
            gson = new Gson();
        return gson;
    }

}
