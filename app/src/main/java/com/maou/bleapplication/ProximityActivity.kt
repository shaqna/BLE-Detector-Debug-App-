package com.maou.bleapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.kontakt.sdk.android.ble.manager.ProximityManager
import com.kontakt.sdk.android.ble.manager.ProximityManagerFactory
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener
import com.kontakt.sdk.android.common.KontaktSDK
import com.kontakt.sdk.android.common.profile.IBeaconDevice
import com.kontakt.sdk.android.common.profile.IBeaconRegion
import com.maou.bleapplication.databinding.ActivityProximityBinding

/*
      In this activity, using Kontakt Library for scanning beacon
 */

class ProximityActivity : AppCompatActivity() {

    private lateinit var proximityManager: ProximityManager

    private val binding: ActivityProximityBinding by lazy {
        ActivityProximityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // This is my API KEY. If does not work. Please try to put your API KEY
        KontaktSDK.initialize("mdjFAnjpGlLPeJGiCURgsWtxLBzqUxVi")

        setupProximityManager()


        binding.apply {
            buttonStart.setOnClickListener {
                startScanning()
            }
            buttonStop.setOnClickListener {
                stopScanning()
            }
        }



    }


    override fun onDestroy() {
        proximityManager.disconnect()
        super.onDestroy()
    }

    private fun setupProximityManager() {
        proximityManager = ProximityManagerFactory.create(this)
        //Log.v("Beacon", "here")

        proximityManager.setIBeaconListener(object : IBeaconListener {
            override fun onIBeaconDiscovered(iBeacon: IBeaconDevice?, region: IBeaconRegion?) {
                Log.v("Beacon", iBeacon.toString())

            }

            override fun onIBeaconsUpdated(
                iBeacons: MutableList<IBeaconDevice>?,
                region: IBeaconRegion?
            ) {
                Log.d("Beacon", iBeacons?.size.toString())

            }

            override fun onIBeaconLost(iBeacon: IBeaconDevice?, region: IBeaconRegion?) {
                Log.d("Beacon", iBeacon.toString())

            }

        })
    }

    private fun startScanning() {
        proximityManager.connect {
            if(proximityManager.isScanning) {
                Log.d("Proximity", "Already Scanning")
                return@connect
            }
            proximityManager.startScanning()
            binding.progressBar.visibility = View.VISIBLE
            Log.d("Proximity", "Scanning Started")

        }
    }

    private fun stopScanning() {
        if(proximityManager.isScanning) {
            proximityManager.stopScanning()
            binding.progressBar.visibility = View.GONE
            Log.d("Proximity", "Scanning Stopped")

        }
    }
}