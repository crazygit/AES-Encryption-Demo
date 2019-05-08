//
// Created by Crazygit on 2019/5/8.
//


#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_anroid_testaescipher_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string key = "this is my key";
    return env->NewStringUTF(key.c_str());
}