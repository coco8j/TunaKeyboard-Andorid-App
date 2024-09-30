package dev.patrickgold.florisboard

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.view.MotionEvent
import android.view.View
import androidx.core.app.NotificationCompat
//import dev.patrickgold.florisboard.app.tunaKeyboard.CoordinateHistoryManager
//
//class CollectTouchDataService : Service() {
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        // 포그라운드 서비스 시작
//        startForegroundService()
//        // 좌표 데이터 수집 로직
//        startCollectTouchData()
//        return START_STICKY
//    }
//// 포지 서비스 로직
//    private fun startForegroundService() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channelId = createNotificationChannel()
//            val notification = NotificationCompat.Builder(this, channelId)
//                .setContentTitle("Tuna Data Service")
//                .setContentText("Collecting touch coordinates")
//                .setSmallIcon(R.drawable.ic_app_icon_foreground)
//                .setPriority(NotificationCompat.PRIORITY_LOW)
//                .build()
//            startForeground(1, notification)
//        }
//    }
//// 알림 채널 로직
//    private fun createNotificationChannel(): String {
//        val channelId = "touch_service_channel"
//        val channelName = "Tuna Data Collection"
//        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
//        val manager = getSystemService(NotificationManager::class.java)
//        manager.createNotificationChannel(channel)
//        return channelId
//    }
//// 좌표 수집 로직
//    private fun startCollectTouchData() {
//        // 좌표 데이터 수집: 터치 이벤트 처리
//        val touchView = View(this)
//        touchView.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
//                    val x = event.x
//                    val y = event.y
//                    CoordinateHistoryManager.addCoordinate(x, y)
//                }
//            }
//            true
//        }
//    }
//
//    private fun saveTouchData(x: Float, y: Float) {
//        // TODO: 좌표 데이터를 저장하는 로직
//        val coordinate = Coordinate(x, y)
//        // 저장 로직을 구현 (예: DB 또는 파일)
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//}
