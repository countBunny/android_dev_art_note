package com.example.countbunny.launchmodetest1.aidl.manimpl;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.example.countbunny.launchmodetest1.aidl.Book;

import java.util.List;

/**
 * Created by countBunny on 2018/2/25.
 */

public interface IBookManager extends IInterface {

    String DESCRIPTOR = "com.example.countbunny.launchmodetest1.aidl.manimpl.IBookManager";

    int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;

    int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;

    List<Book> getBookList() throws RemoteException;

    void addBook(Book book) throws RemoteException;
}
