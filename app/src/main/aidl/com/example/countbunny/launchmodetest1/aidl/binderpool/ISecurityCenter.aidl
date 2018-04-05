// ISecurityCenter.aidl
package com.example.countbunny.launchmodetest1.aidl.binderpool;

// Declare any non-default types here with import statements

interface ISecurityCenter {

    String encrypt(String content);

    String decrypt(String content);
}
