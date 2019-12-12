package com.example.fakeyolo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView image;

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

        public class RequestHttpURLConnection {

            public String request(String _url, ContentValues _params) {

                HttpURLConnection urlConn = null;

                // URL 뒤에 붙여서 보낼 파라미터.
                StringBuffer sbParams = new StringBuffer();

                /**
                 * 1. StringBuffer에 파라미터 연결
                 * */
                // 보낼 데이터가 없으면 파라미터를 비운다.
                if (_params == null)
                    sbParams.append("");
                    // 보낼 데이터가 있으면 파라미터를 채운다.
                else {
                    // 파라미터가 2개 이상이면 파라미터 연결에 &가 필요하므로 스위칭할 변수 생성.
                    boolean isAnd = false;
                    // 파라미터 키와 값.
                    String key;
                    String value;

                    for (Map.Entry<String, Object> parameter : _params.valueSet()) {
                        key = parameter.getKey();
                        value = parameter.getValue().toString();

                        // 파라미터가 두개 이상일때, 파라미터 사이에 &를 붙인다.
                        if (isAnd)
                            sbParams.append("&");

                        sbParams.append(key).append("=").append(value);

                        // 파라미터가 2개 이상이면 isAnd를 true로 바꾸고 다음 루프부터 &를 붙인다.
                        if (!isAnd)
                            if (_params.size() >= 2)
                                isAnd = true;
                    }
                }

                /**
                 * 2. HttpURLConnection을 통해 web의 데이터를 가져온다.
                 * */
                try {
                    URL url = new URL(_url);
                    urlConn = (HttpURLConnection) url.openConnection();

                    // [2-1]. urlConn 설정.
                    urlConn.setReadTimeout(10000);
                    urlConn.setConnectTimeout(15000);
                    urlConn.setRequestMethod("POST"); // URL 요청에 대한 메소드 설정 : GET/POST.
                    urlConn.setDoOutput(true);
                    urlConn.setDoInput(true);
                    urlConn.setRequestProperty("Accept-Charset", "utf-8"); // Accept-Charset 설정.
                    urlConn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded");

                    // [2-2]. parameter 전달 및 데이터 읽어오기.
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(urlConn.getOutputStream()));
                    pw.write(sbParams.toString());
                    pw.flush(); // 출력 스트림을 flush. 버퍼링 된 모든 출력 바이트를 강제 실행.
                    pw.close(); // 출력 스트림을 닫고 모든 시스템 자원을 해제.

                    // [2-3]. 연결 요청 확인.
                    // 실패 시 null을 리턴하고 메서드를 종료.
                    if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
                        return null;

                    // [2-4]. 읽어온 결과물 리턴.
                    // 요청한 URL의 출력물을 BufferedReader로 받는다.
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

                    // 출력물의 라인과 그 합에 대한 변수.
                    String line;
                    String page = "";

                    // 라인을 받아와 합친다.
                    while ((line = reader.readLine()) != null) {
                        page += line;
                    }
                    return page;

                } catch (MalformedURLException e) { // for URL.
                    e.printStackTrace();
                } catch (IOException e) { // for openConnection().
                    e.printStackTrace();
                } finally {
                    if (urlConn != null)
                        urlConn.disconnect();
                }
                return null;
            }
        }

        public class NetworkTask extends AsyncTask<Void, Void, String> {

            String url;
            ContentValues values;

            NetworkTask(String url, ContentValues values){
                this.url = url;
                this.values = values;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progress bar를 보여주는 등등의 행위
            }

            @Override
            protected String doInBackground(Void... params) {
                String result;
                RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
                result = requestHttpURLConnection.request(url, values);
                return result; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
            }

            @Override
            protected void onPostExecute(String result) {
                // 통신이 완료되면 호출됩니다.
                // 결과에 따른 UI 수정 등은 여기서 합니다.
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        }



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
