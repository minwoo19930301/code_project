package com.example.fakeyolo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.touch);
        Button share =  (Button) findViewById(R.id.share);
        Button random = (Button) findViewById(R.id.button);
        Button color = (Button) findViewById(R.id.button2);
        Button text = (Button) findViewById(R.id.button3);
        Button zep = (Button) findViewById(R.id.button4);




        random.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textView.setText(new Content().getRandom());
            }
        });

        color.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int[] androidColors = getResources().getIntArray(R.array.androidcolors);
                int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
                textView.setBackgroundColor(randomAndroidColor);
            }
        });

        /*
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getPackageList(공유할 패키지 앱)){
                    Intent intent = getPackageManager().getLaunchIntentForPackage(TaxiPackage);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    String url = "market://details?id=" + 공유할 패키지 앱;
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                }
            }
        });

        public boolean getPackageList(String AppPackage) {
            boolean isExist = false;

            PackageManager pkgMgr = getPackageManager();
            List<ResolveInfo> mApps;
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            mApps = pkgMgr.queryIntentActivities(mainIntent, 0);

            try {
                for (int i = 0; i < mApps.size(); i++) {
                    if(mApps.get(i).activityInfo.packageName.startsWith(AppPackage)){
                        isExist = true;
                        break;
                    }
                }
            }
            catch (Exception e) {
                isExist = false;
            }
            return isExist;
        }//end of getPackageList
        */




    }
}
