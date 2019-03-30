package  com.example.jsrgjhl.hlapp.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.jsrgjhl.hlapp.Activity.MainActivity;

public class NotificationClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //todo 跳转之前要处理的逻辑
        Log.i("TAG", "userClick:我被点击啦！！！ ");
        Bundle x=intent.getExtras();
        Intent newIntent = new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("devicenum",x.getString("devicenum"));
        context.startActivity(newIntent);
    }
}