package com.example.fakeyolo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
