package com.kajoba.app.Utilities;

public class Utilities {

    //Constants fields table clients
    public static final String TABLE_CLIENT = "CLIENT";
    public static final String FIELD_CLIENT_ID = "ID";
    public static final String FIELD_CLIENT_NAME = "NAME";
    public static final String FIELD_CLIENT_IDENTIFICATION = "IDENTIFICATION";
    public static final String FIELD_CLIENT_PHONE = "PHONE";
    public static final String FIELD_CLIENT_EMAIL = "EMAIL";
    public static final String FIELD_CLIENT_LOCATION = "LOCATION";
    public static final String FIELD_CLIENT_BIRTHDATE = "BIRTHDATE";

    public static final String CREATE_TABLE_CLIENT = "CREATE TABLE " +TABLE_CLIENT +" ("
            + FIELD_CLIENT_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FIELD_CLIENT_NAME +" TEXT, "
            + FIELD_CLIENT_IDENTIFICATION +" TEXT, "
            + FIELD_CLIENT_PHONE +" TEXT, "
            + FIELD_CLIENT_EMAIL + " TEXT, "
            + FIELD_CLIENT_LOCATION +" TEXT, "
            + FIELD_CLIENT_BIRTHDATE + " TEXT)";

    //Constants fields table businessman
    public static final String TABLE_BUSINESSMAN = "BUSINESSMAN";
    public static final String FIELD_BUSINESSMAN_ID = "ID";
    public static final String FIELD_BUSINESSMAN_NAME = "NAME";
    public static final String FIELD_BUSINESSMAN_IDENTIFICATION = "IDENTIFICATION";
    public static final String FIELD_BUSINESSMAN_CODBUSINESS= "COD_BUSINESS";
    public static final String FIELD_BUSINESSMAN_PASSBUSINESS = "PASS_BUSINESS";
    public static final String FIELD_BUSINESSMAN_PHONE = "PHONE";
    public static final String FIELD_BUSINESSMAN_EMAIL = "EMAIL";
    public static final String FIELD_BUSINESSMAN_PASSEMAIL = "PASS_EMAIL";
    public static final String FIELD_BUSINESSMAN_LOCATION = "LOCATION";
    public static final String FIELD_BUSINESSMAN_BIRTHDATE = "BIRTHDATE";

    public static final String CREATE_TABLE_BUSINESSMAN= "CREATE TABLE " +TABLE_BUSINESSMAN +" ("
            + FIELD_BUSINESSMAN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FIELD_BUSINESSMAN_NAME +" TEXT, "
            + FIELD_BUSINESSMAN_IDENTIFICATION +" TEXT, "
            + FIELD_BUSINESSMAN_CODBUSINESS +" TEXT, "
            + FIELD_BUSINESSMAN_PASSBUSINESS +" TEXT, "
            + FIELD_BUSINESSMAN_PHONE +" TEXT, "
            + FIELD_BUSINESSMAN_EMAIL + " TEXT, "
            + FIELD_BUSINESSMAN_PASSEMAIL +" TEXT, "
            + FIELD_BUSINESSMAN_LOCATION +" TEXT, "
            + FIELD_BUSINESSMAN_BIRTHDATE + " TEXT)";

    //Constants fields table partners
    public static final String TABLE_PARTNER = "PARTNER";
    public static final String FIELD_PARTNER_ID = "ID";
    public static final String FIELD_PARTNER_NAME = "NAME";
    public static final String FIELD_PARTNER_IDENTIFICATION = "IDENTIFICATION";
    public static final String FIELD_PARTNER_PHONE = "PHONE";
    public static final String FIELD_PARTNER_EMAIL = "EMAIL";
    public static final String FIELD_PARTNER_LOCATION = "LOCATION";
    public static final String FIELD_PARTNER_BIRTHDATE = "BIRTHDATE";

    public static final String CREATE_TABLE_PARTNER= "CREATE TABLE " +TABLE_PARTNER +" ("
            + FIELD_PARTNER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FIELD_PARTNER_NAME +" TEXT, "
            + FIELD_PARTNER_IDENTIFICATION +" TEXT, "
            + FIELD_PARTNER_PHONE +" TEXT, " + FIELD_PARTNER_EMAIL + " TEXT, "
            + FIELD_PARTNER_LOCATION +" TEXT, "
            + FIELD_PARTNER_BIRTHDATE + " TEXT)";

    //Constants fields table products
    public static final String TABLE_PRODUCT = "PRODUCT";
    public static final String FIELD_PRODUCT_ID = "ID_SKU";
    public static final String FIELD_PRODUCT_NAME = "NAME";
    public static final String FIELD_PRODUCT_CATEGORY = "CATEGORY";
    public static final String FIELD_PRODUCT_DESCRIPTION = "DESCRIPTION";
    public static final String FIELD_PRODUCT_QUANTITY = "QUANTITY";
    public static final String FIELD_PRODUCT_COST = "COST";
    public static final String FIELD_PRODUCT_PRICE = "PRICE";

    public static final String CREATE_TABLE_PRODUCT= "CREATE TABLE " +TABLE_PRODUCT +" ("
            + FIELD_PRODUCT_ID +" TEXT PRIMARY KEY NOT NULL, "
            + FIELD_PRODUCT_NAME +" TEXT, "
            + FIELD_PRODUCT_CATEGORY +" TEXT, "
            + FIELD_PRODUCT_DESCRIPTION +" TEXT, "
            + FIELD_PRODUCT_QUANTITY + " INTEGER, "
            + FIELD_PRODUCT_COST +" INTEGER, "
            + FIELD_PRODUCT_PRICE + " INTEGER)";

}
