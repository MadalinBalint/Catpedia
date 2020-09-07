#include <jni.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_mendelin_catpedia_server_ServerConfig_getBaseUrl(JNIEnv *env, jobject obj) {
    return (*env).NewStringUTF("https://api.thecatapi.com/");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_mendelin_catpedia_server_ServerConfig_getApiHeader(JNIEnv *env, jobject obj) {
    return (*env).NewStringUTF("x-api-key");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_mendelin_catpedia_server_ServerConfig_getApiKey(JNIEnv *env, jobject obj) {
    return (*env).NewStringUTF("af33a72b-4420-4190-a803-cab3b273c3db");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_mendelin_catpedia_server_ServerConfig_getMockedUserName(JNIEnv *env, jobject obj) {
    return (*env).NewStringUTF("Pumpkin");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_mendelin_catpedia_server_ServerConfig_getMockedUserPassword(JNIEnv *env, jobject obj) {
    return (*env).NewStringUTF("catnip");
}