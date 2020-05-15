package com.example.edd_2020_residential_water.models;

import android.util.Log;

import com.example.edd_2020_residential_water.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            NotificationHelper.displayNotification(getApplicationContext(), title, body);
        }

        /**
         * Called if InstanceID token is updated. This may occur if the security of
         * the previous token had been compromised. Note that this is called when the InstanceID token
         * is initially generated so this is where you would retrieve the token.
         */
//        public void onNewToken(String token) {
//            Log.d(TAG, "Refreshed token: " + token);
//
//            // If you want to send messages to this application instance or
//            // manage this apps subscriptions on the server side, send the
//            // Instance ID token to your app server.
//            sendRegistrationToServer(token);
//        }
    }
}
