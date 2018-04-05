// IBookManager.aidl
package com.example.countbunny.launchmodetest1.aidl;

// Declare any non-default types here with import statements

import com.example.countbunny.launchmodetest1.aidl.Book;
import com.example.countbunny.launchmodetest1.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();

    void addBook(in Book book);

    void registerListener(IOnNewBookArrivedListener listener);

    void unregisterListener(IOnNewBookArrivedListener listener);
}
