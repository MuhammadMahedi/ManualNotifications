# ManualNotifications

This repository demonstrates different types of Android notifications using a simple activity with 8 buttons. Clicking each button triggers a corresponding notification.

## Notification Types

1. Normal Notification

2. Notification with Heads-Up Display

3. Inbox Style Notification

4. Big Text Notification

5. Big Image Notification

6. Messaging Notification

7. Actionable Notification

8. Silent Notification



## Screenshots

| Notification Type          | Screenshot |
|---------------------------|------------|
| Normal Notification       | ![Normal Notification](screenshots/simple_notification.jpg) |
| Heads-Up Notification     | ![Heads-Up Notification](screenshots/heads_up_notification.jpg) |
| Inbox Style Notification (Summery)  | ![Inbox Style Notification](screenshots/inbox_style_summery.jpg) |
| Inbox Style Notification (Extended)  | ![Inbox Style Notification](screenshots/inbox_style_extended.jpg) |
| Big Text Notification (Summery)    | ![Big Text Notification](screenshots/big_text_summery.jpg) |
| Big Text Notification (Extended)    | ![Big Text Notification](screenshots/big_text_extended.jpg) |
|Image Notification (Summery)    | ![Image Notification](screenshots/image_summery.jpg) |
|Image Notification (Preview)    | ![Image Notification](screenshots/image_extended_preview.jpg) |
| Messaging Notification    | ![Messaging Notification](screenshots/messaging_notification_heads_up.jpg) |
| Messaging Notification (Replay)   | ![Messaging Notification](screenshots/replay_text_input.jpg) |
| Actionable Notification   | ![Actionable Notification](screenshots/actionable_notification.jpg) |
| Silent Notification       | ![Silent Notification](screenshots/silent_notification.jpg) |





## Features
```
- Uses Glide to display images in picture notifications.
- Implements BroadcastReceiver to handle notification actions, such as:
  - Replying to messages.
  - Marking notifications as read.
```

## How to Use
```
1. Clone the repository.
2. Open the project in Android Studio.
3. Run the app on an emulator or physical device.
4. Click on any button to trigger the corresponding notification.
```

## Dependencies
```gradle
dependencies {
    implementation 'com.github.bumptech.glide:glide:4.16.0'
}
```


