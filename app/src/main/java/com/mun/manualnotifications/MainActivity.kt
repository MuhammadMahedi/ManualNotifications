package com.mun.manualnotifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mun.manualnotifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private lateinit var  binding:ActivityMainBinding
    private val CHANNEL_ID_ONE = "Mahedi"
    private val CHANNEL_ID_Two = "Munna"
    private val NOTIFICATION_ID_ONE = 1
    private val NOTIFICATION_ID_TWO = 2
    private val NOTIFICATION_ID_INBOX_STYLE = 3
    private val NOTIFICATION_ID_BIG_TEXT = 4
    private val NOTIFICATION_ID_PICTURE = 5
    private val NOTIFICATION_ID_MESSAGING = 6
    private val NOTIFICATION_ID_ACTIONABLE = 7
    private val KEY_TEXT_REPLY = "key_text_reply"

    companion object {
       lateinit var notificationManager: NotificationManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Set the default night mode globally
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        createNotificationChannel()
        
        binding.apply {
            btnNotification.setOnClickListener {
                if(isNotificationAllowed())
                makeNotification("Mahedi Said", "This is notification One",1)
                else{
                    showPermissionRequiredToast()
                }
            }
            btnSecondNotification.setOnClickListener {
                if(isNotificationAllowed())
                makeNotification("Munna Said", "Notification 2 Overrides the first one",2)
                else{
                    showPermissionRequiredToast()
                }
            }

            btnInboxStyleNotification.setOnClickListener {
                if(isNotificationAllowed())
                makeInboxStyleNotification()
                else{
                showPermissionRequiredToast()
                }
            }

            btnBigTextNotification.setOnClickListener {
                if(isNotificationAllowed())
                makeBigTextStyleNotification()
                else{
                showPermissionRequiredToast()
                }
            }

            btnImgNotification.setOnClickListener {
                if(AppUtil.isInternetAvailable(this@MainActivity)){
                    if(isNotificationAllowed())
                        makeBigPictureStyleNotification()
                    else{
                        showPermissionRequiredToast()
                    }
                }

            }

            btnMsgReplayNotification.setOnClickListener {
                if(isNotificationAllowed())
                     makeMessagingStyleNotification()
                else
                    showPermissionRequiredToast()

            }

            btnActionableNotification.setOnClickListener {
                if(isNotificationAllowed())
                    makeActionableNotification()
                else
                    showPermissionRequiredToast()

            }
        }


    }
    
    
    private fun makeNotification(title:String,text:String,notificationId:Int) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID_ONE)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Add an icon in res/drawable
            .setContentTitle(title)
            .setContentText(text)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)  only needed for android 7 or below
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificationId, builder.build())
        }
    }


    private fun makeInboxStyleNotification() {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        // Create InboxStyle notification with multiple lines
        val inboxStyle = NotificationCompat.InboxStyle()
            .setBigContentTitle("3 New Messages")
            .addLine("Alice: Hey, how are you?")
            .addLine("Bob: Meeting at 5 PM")
            .addLine("Charlie: Check out this new Android update!")
            .setSummaryText("+3 more")

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_ONE)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Ensure this icon exists
            .setContentTitle("New Messages")
            .setContentText("You have 3 new messages") // Shown when collapsed
            .setStyle(inboxStyle) // Apply InboxStyle
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Needed for Android 7.1 and below
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Show on lock screen
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager. notify(NOTIFICATION_ID_INBOX_STYLE, builder.build())

    }


    private fun makeBigTextStyleNotification() {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        // Create BigTextStyle notification
        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText("Breaking News: The latest Android update brings several performance improvements and new features that enhance user experience. Stay updated with the latest tech news.")
            .setBigContentTitle("Tech News Update")
            .setSummaryText("Android 15 Features")

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_ONE)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Ensure this icon exists
            .setContentTitle("Breaking News") // Shown when collapsed
            .setContentText("New Android 15 update is here!") // Short text
            .setStyle(bigTextStyle) // Apply BigTextStyle
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Needed for Android 7.1 and below
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Show on lock screen
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Dismiss when tapped

        notificationManager.notify(NOTIFICATION_ID_BIG_TEXT, builder.build())
    }

    private fun makeBigPictureStyleNotification() {
        val imageUrl = "https://www.psdstack.com/wp-content/uploads/2019/08/copyright-free-images-750x420.jpg" // Replace with your actual image URL

        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    showBigPictureNotification(resource) // Pass the downloaded image
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    private fun showBigPictureNotification(image: Bitmap) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(image) // Set the downloaded image
            .setBigContentTitle("New Photo Shared!")
            .setSummaryText("Check out this awesome picture!")

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_ONE)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Ensure this icon exists
            .setContentTitle("New Photo Alert") // Shown when collapsed
            .setContentText("Tap to view the full image.") // Short text
            .setStyle(bigPictureStyle) // Apply BigPictureStyle
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Needed for Android 7.1 and below
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Show on lock screen
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Dismiss when tapped

        notificationManager. notify(NOTIFICATION_ID_PICTURE, builder.build())
    }


    private fun makeMessagingStyleNotification() {
        val replyLabel = "Reply to message"
        val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
            .setLabel(replyLabel)
            .build()

        val replyIntent = Intent(this, ReplyReceiver::class.java)
        val replyPendingIntent = PendingIntent.getBroadcast(
            this, 0, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val replyAction = NotificationCompat.Action.Builder(
            R.drawable.ic_launcher_foreground, "Reply", replyPendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val messagingStyle = NotificationCompat.MessagingStyle(
            Person.Builder()
            .setName("You")
            .build()
        )
            .addMessage("Hey! How are you?", System.currentTimeMillis() - 60000, Person.Builder().setName("Alice").build())
            .addMessage("Let's catch up later.", System.currentTimeMillis() - 30000, Person.Builder().setName("Bob").build())
            .addMessage("Sure! Let me know the time.", System.currentTimeMillis(), Person.Builder().setName("You").build())

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_ONE)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setStyle(messagingStyle)
            .addAction(replyAction)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE) // Hide content on lock screen
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        notificationManager.notify(NOTIFICATION_ID_MESSAGING, builder.build())
    }


    private fun makeActionableNotification() {

        val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
            .setLabel("Type your reply...")
            .build()

        val replyIntent = Intent(this, ReplyReceiver::class.java)
        val replyPendingIntent = PendingIntent.getBroadcast(
            this, 0, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val replyAction = NotificationCompat.Action.Builder(
            R.drawable.ic_launcher_foreground, "Reply", replyPendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()

        // Mark as Read Action
        val markAsReadIntent = Intent(this, ReplyReceiver::class.java)
        val markAsReadPendingIntent = PendingIntent.getBroadcast(
            this, 1, markAsReadIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val markAsReadAction = NotificationCompat.Action.Builder(
            R.drawable.ic_launcher_foreground, "Mark as Read", markAsReadPendingIntent
        )
            .build()

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        // Build Notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID_ONE)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New Message")
            .setContentText("Alice: Hey, how are you?")
            .addAction(replyAction) // Add Reply Action
            .addAction(markAsReadAction) // Add Mark as Read Action
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID_ACTIONABLE, notification)
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyChannel"
            val descriptionText = "Channel for notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID_ONE, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }



    private fun isNotificationAllowed() :Boolean{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else {
            true // No need to ask for permission on Android 12 and below
        }    }

    private fun showPermissionRequiredToast(){
        Toast.makeText(this@MainActivity, "Notification Permission Required", Toast.LENGTH_SHORT).show()
    }

}