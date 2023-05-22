package com.maou.bleapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.maou.bleapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


/*
*
*           This section that I comment is used for scanning beacon using Bluetooth Adapter
*           If you want to try it, just uncomment this section
*
*
 */

//    private val bluetoothAdapter: BluetoothAdapter by lazy {
//        (getSystemService(BLUETOOTH_SERVICE) as BluetoothManager).adapter
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (bluetoothAdapter.isEnabled) {
//            // start scanning
//            startScanning()
//        } else {
//            Log.v("MyTag", "BT is disable")
//            val btIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.BLUETOOTH_CONNECT
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                startActivityForResult(btIntent, 1)
//            }
//        }
//    }
//
//    private fun startScanning() {
//        Log.v("MyTag", "Start Scanning")
//        val scanFilter = ScanFilter.Builder().build()
//
//        val scanFilters: MutableList<ScanFilter> = mutableListOf()
//        scanFilters.add(scanFilter)
//
//        val scanSettings =
//            ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build()
//
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.BLUETOOTH_SCAN
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            bluetoothAdapter.bluetoothLeScanner.startScan(
//                scanFilters,
//                scanSettings,
//                bleScanCallback
//            )
//        }
//
//    }
//
//    private val bleScanCallback: ScanCallback by lazy {
//        object : ScanCallback() {
//            override fun onScanResult(callbackType: Int, result: ScanResult?) {
//                //super.onScanResult(callbackType, result)
//                val bluetoothDevice = result?.device
//                if (bluetoothDevice != null) {
//                    if (ActivityCompat.checkSelfPermission(
//                            this@MainActivity,
//                            Manifest.permission.BLUETOOTH_CONNECT
//                        ) != PackageManager.PERMISSION_GRANTED
//                    ) {
//                        Log.v(
//                            "MyTag",
//                            "Device name:${bluetoothDevice.name}, Device UUID: ${bluetoothDevice.uuids}"
//                        )
//
//                    }
//
//                }
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Comment this section (line 111 until 116) when use Bluetooth Adapter for scanning
        //

        checkPermissions()
        binding.apply {
            button.setOnClickListener {
                startActivity(Intent(this@MainActivity, ProximityActivity::class.java))
            }
        }
    }


    private fun checkPermissions() {
        if (!allPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (REQUEST_CODE_PERMISSIONS == requestCode) {
                Toast.makeText(this, "Permissions granted!", Toast.LENGTH_SHORT).show()
            }
        } else {
            disableButtons()
            Toast.makeText(
                this,
                "Location permissions are mandatory to use BLE features on Android 6.0 or higher",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun disableButtons() {
        binding.button.isEnabled = false
    }


    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_CODE_PERMISSIONS = 100
        private val REQUIRED_PERMISSIONS =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                arrayOf(
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } else {
                arrayOf(Manifest.permission.BLUETOOTH)
            }
    }
}