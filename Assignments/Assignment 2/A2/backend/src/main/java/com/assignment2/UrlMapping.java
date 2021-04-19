package com.assignment2;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String BOOK_STORE = API_PATH + "/bookstore";
    public static final String EXPORT_REPORT = "/export/{type}";

    public static final String SELL_BOOK = "/sell/{amount}";
    public static final String DELETE_ENTITY = "/{id}";

    // authentication
    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/user";

}
