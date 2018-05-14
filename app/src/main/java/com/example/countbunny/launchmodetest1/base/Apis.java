package com.example.countbunny.launchmodetest1.base;

public interface Apis {

    String getBase();

    class ImageApis implements Apis {

        private static volatile ImageApis mApis;

        private ImageApis() {
        }

        private String mBase;

        public static ImageApis getApis() {
            if (null == mApis) {
                synchronized (ImageApis.class) {
                    if (null == mApis) {
                        mApis = new ImageApis();
                    }
                }
            }
            return mApis;
        }

        public String getWelfares(int page, int size) {
            StringBuilder sb = new StringBuilder(getBase());
            return sb.append("data/%E7%A6%8F%E5%88%A9/").append(size).append("/").append(page).toString();
        }

        @Override
        public String getBase() {
            return mBase == null ? "http://gank.io/api/" : mBase;
        }
    }
}
