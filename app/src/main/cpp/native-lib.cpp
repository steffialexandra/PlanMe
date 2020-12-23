//
// Created by Steffi Alexandra on 07/12/2020.
//

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring Java_id_ac_ui_cs_mobileprogramming_steffialexandra_PlanMe_TaskActivity_greetJNI(JNIEnv *env, jobject obj, jstring nama) {
    const char *name = env->GetStringUTFChars(nama, NULL);
    char msg[60] = "Welcome, ";
    jstring result;

    strcat(msg, name);
    env->ReleaseStringUTFChars(nama, name);
    puts(msg);
    result = env->NewStringUTF(msg);
    return result;
}