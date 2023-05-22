package com.maou.bleapplication

import android.app.Application
import com.kontakt.sdk.android.common.KontaktSDK


// ignore this file
class BleApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KontaktSDK.initialize(this)
    }
}