package com.blackhistory;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.blackhistory.db.DbOpenHelper;
import com.blackhistory.ui.AdMobActivity;
import com.blackhistory.ui.ResetCountActivity;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BlockOutgoing extends BroadcastReceiver {
    String number;
    SessionManager session;

    @Override
    public void onReceive(Context context, Intent intent) {
        session = new SessionManager(context);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        String getTime = sdf.format(date);
        long time = Long.parseLong(getTime);
//22시부터 06시
      //  Calendar cal = Calendar.getInstance();
       // int week = cal.get(Calendar.DAY_OF_WEEK);











        NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();

        if(StringUtils.isNotEmpty(getTime)) {
            number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.d("~!", number);
            DbOpenHelper mDbOpenHelper = new DbOpenHelper(context);
            mDbOpenHelper.open();
            mDbOpenHelper.create();

            Cursor iCursor = mDbOpenHelper.selectColumns();
            while (iCursor.moveToNext()) {
                long tempId = iCursor.getLong(iCursor.getColumnIndex("_id"));
                String tempName = iCursor.getString(iCursor.getColumnIndex("name"));
                String tempMemo = iCursor.getString(iCursor.getColumnIndex("memo"));
                String tempNumber = iCursor.getString(iCursor.getColumnIndex("number"));
                String tempCreatedAt = iCursor.getString(iCursor.getColumnIndex("createdat"));
                //String tempCount =iCursor.getString(iCursor.getColumnIndex("count"));
                Log.d("~!", "DB" + tempNumber);
                if (number.equals(tempNumber)) {

                    //mDbOpenHelper.increaseCount(tempId, String.valueOf(Long.parseLong(tempCount) + 1));
                    int count = session.getCount() + 1;
                    session.setCount(count);

                    //Toast.makeText(context, "당신의 흑역사를 방지 합니다!! " + String.valueOf(session.getCount()) + "회 시도!!!", Toast.LENGTH_LONG).show();
                    setResultData(null);
                    Intent i = new Intent(context, AdMobActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.putExtra("type", "outgoing");
                    i.putExtra("id", String.valueOf(tempId));
                    i.putExtra("name", tempName);
                 //   i.putExtra("memo", tempMemo);
                    i.putExtra("number", tempNumber);
                 //   i.putExtra("createdat", tempCreatedAt);
                  //  i.putExtra("count", tempCount);
                    PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_ONE_SHOT);
                    try {

                        pi.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }



                    String channelId = "channel";
                    String channelName = "통화 시도 횟수 알림";


                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                        int importance = NotificationManager.IMPORTANCE_HIGH;

                        NotificationChannel mChannel = new NotificationChannel(
                                channelId, channelName, importance);

                        notifManager.createNotificationChannel(mChannel);

                    }

                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(context, channelId);

                    Intent notificationIntent = new Intent(context

                            , ResetCountActivity.class);
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    notificationIntent.putExtra("type", "outgoing");
                    notificationIntent.putExtra("id", String.valueOf(tempId));
                    notificationIntent.putExtra("name", tempName);
                    //   i.putExtra("memo", tempMemo);
                    notificationIntent.putExtra("number", tempNumber);
                    //   i.putExtra("createdat", tempCreatedAt);
                    //  i.putExtra("count", tempCount);

                    int requestID = (int) System.currentTimeMillis();



                    PendingIntent pendingIntent
                            = PendingIntent.getActivity(context
                            , requestID
                            , notificationIntent
                            , PendingIntent.FLAG_UPDATE_CURRENT);

                    builder.setContentTitle(session.getCount() + " 번의 통화 시도 !! (터치 시 횟수 초기화)") // required
                            .setContentText("흑역사에 등록된 전화번호로 발신을 할 수 없습니다.")  // required
                            .setDefaults(Notification.DEFAULT_ALL) // 알림, 사운드 진동 설정
                            .setAutoCancel(true)
                            .setStyle(bigTextStyle.bigText("흑역사에 등록된 전화번호로 발신을 할 수 없습니다."))
                            .setSound(RingtoneManager

                                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

                            .setSmallIcon(R.mipmap.ic_launcher)


                            .setContentIntent(pendingIntent);

                    notifManager.notify(0, builder.build());




                }

            }


        } else {
            notifManager.cancel(0);
        }





    }
}