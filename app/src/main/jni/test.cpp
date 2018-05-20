#include <jni.h>
#include <stdio.h>


void callJavaMethod(JNIEnv *env, jobject thiz){
    jclass clazz = env->FindClass("com/example/countbunny/launchmodetest1/jnindk/NDKTestActivity");
    if (clazz == NULL) {
        printf("find class NDKTestActivity error!");
        return;
    }
    jmethodID id = env->GetStaticMethodID(clazz, "methodCalledByJni", "(Ljava/lang/String;)V");
    if (id == NULL) {
        printf("find method methodCalledByJni error!");
    }
    jstring msg = env->NewStringUTF("msg send by callJavaMethod");
    env->CallStaticVoidMethod(clazz, id, msg);
}

#ifdef __cplusplus
extern "C" {
#endif

jstring Java_com_example_countbunny_launchmodetest1_jnindk_NDKTestActivity_get
  (JNIEnv *env, jobject thiz){
  	printf("invoke get in c++\n");
    callJavaMethod(env, thiz);
  	return env->NewStringUTF("Hello from JNI in libjni-test.so!");
  }

void Java_com_example_countbunny_launchmodetest1_jnindk_NDKTestActivity_set(JNIEnv *env,
    jobject thiz, jstring string){
        printf("invoke set from C++\n");
        char* str = (char*)env->GetStringUTFChars(string,NULL);
        printf("%s\n", str);
        env->ReleaseStringUTFChars(string, str);
    }

#ifdef __cplusplus
}
#endif