package com.data.trucksharingapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.data.trucksharingapp.model.OrderModel;
import com.data.trucksharingapp.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    Cursor cursor;

    public DBHelper(@Nullable Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DRUG_TABLE = "CREATE TABLE " +
                Constant.TABLE_TRUCK + "(" +
                Constant.COLUMN_ID + " INTEGER PRIMARY KEY," +
                Constant.COLUMN_RECEIVER_NAME + " TEXT," +
                Constant.COLUMN_PICK_UP_TIME + " TEXT," +
                Constant.COLUMN_PICK_UP_DATE + " TEXT," +
                Constant.COLUMN_PICK_UP_ADDRESS + " TEXT," +
                Constant.COLUMN_PICK_LAT + " TEXT," +
                Constant.COLUMN_PICK_LONG + " TEXT," +
                Constant.COLUMN_DROP_OFF_ADDRESS + " TEXT," +
                Constant.COLUMN_DROP_LAT + " TEXT," +
                Constant.COLUMN_DROP_LONG + " TEXT," +
                Constant.COLUMN_GOODS_TYPE + " TEXT," +
                Constant.COLUMN_WEIGHT + " TEXT," +
                Constant.COLUMN_LEGTH + " TEXT," +
                Constant.COLUMN_WIDTH + " TEXT," +
                Constant.COLUMN_HEIGHT + " TEXT," +
                Constant.COLUMN_QUANTITY + " TEXT," +
                Constant.COLUMN_VEHICLE_TYPE + " TEXT," +
                Constant.COLUMN_TIME_MILLIS + " INTEGER" + ")";
        db.execSQL(CREATE_DRUG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_TRUCK);
        onCreate(db);
    }

    public void addOrder(OrderModel orderModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.COLUMN_RECEIVER_NAME, orderModel.getReceiver_name());
        contentValues.put(Constant.COLUMN_PICK_UP_TIME, orderModel.pickup_time);
        contentValues.put(Constant.COLUMN_PICK_UP_DATE, orderModel.pickup_date);
        contentValues.put(Constant.COLUMN_PICK_UP_ADDRESS, orderModel.pickup_address);
        contentValues.put(Constant.COLUMN_PICK_LAT, orderModel.pick_lat);
        contentValues.put(Constant.COLUMN_PICK_LONG, orderModel.pick_long);
        contentValues.put(Constant.COLUMN_DROP_OFF_ADDRESS, orderModel.dropoff_address);
        contentValues.put(Constant.COLUMN_DROP_LAT, orderModel.drop_lat);
        contentValues.put(Constant.COLUMN_DROP_LONG, orderModel.drop_long);
        contentValues.put(Constant.COLUMN_GOODS_TYPE, orderModel.goods_type);
        contentValues.put(Constant.COLUMN_WEIGHT, orderModel.weight);
        contentValues.put(Constant.COLUMN_LEGTH, orderModel.length);
        contentValues.put(Constant.COLUMN_WIDTH, orderModel.width);
        contentValues.put(Constant.COLUMN_HEIGHT, orderModel.height);
        contentValues.put(Constant.COLUMN_QUANTITY, orderModel.quantity);
        contentValues.put(Constant.COLUMN_VEHICLE_TYPE, orderModel.vehicle_type);
        contentValues.put(Constant.COLUMN_TIME_MILLIS, orderModel.time_millis);
        db.insert(Constant.TABLE_TRUCK, null, contentValues);
        db.close();
    }

    public List<OrderModel> getAllOrders() {
        List<OrderModel> orderModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + Constant.TABLE_TRUCK, null);
        if (cursor.moveToFirst()) {
            do {
                OrderModel orderModel = new OrderModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11),cursor.getString(12), cursor.getString(13),cursor.getString(14), cursor.getString(15),cursor.getString(16),cursor.getLong(17));
                orderModelList.add(orderModel);
            } while (cursor.moveToNext());
        }
        return orderModelList;
    }

    public OrderModel getOrder(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + Constant.TABLE_TRUCK + " WHERE " + Constant.COLUMN_ID + " = " + id, null);
        if (cursor != null)
            cursor.moveToFirst();
        assert cursor != null;
        return new OrderModel(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11),cursor.getString(12),cursor.getString(13),cursor.getString(14), cursor.getString(15),cursor.getString(16), cursor.getLong(17));
    }
}
