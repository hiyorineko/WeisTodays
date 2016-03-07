package com.example.hiyoriaya.weistodays;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AsyncHttpRequest extends AsyncTask<Uri.Builder, Void, String> {

    private Activity mainActivity;
    private Document document;
    private List<String> urls;

    public AsyncHttpRequest(Activity activity) {
        // 呼び出し元のアクティビティ
        this.mainActivity = activity;
    }

    // このメソッドは必ずオーバーライドする必要があるよ
    // ここが非同期で処理される部分みたいたぶん。
    @Override
    protected String doInBackground(Uri.Builder... builder) {
        try {
            document = Jsoup.connect("http://ws-tcg.com/ws_today").get();
            return "成功";
        }catch(IOException e){
            return "失敗";
        }
    }

    // このメソッドは非同期処理の終わった後に呼び出されます
    @Override
    protected void onPostExecute(String result) {
        // 取得した結果をテキストビューに入れちゃったり
        Elements img = document.select("div.center");
        urls = new ArrayList<String>();
        for(int i = 0;i < img.size();i++){
            Element e = img.get(i);
            String parse = e.toString().replaceAll("<div.+?>", "");
            parse = parse.replaceAll("</div>", "");
            parse = parse.replaceAll("<img src=\"", "");
            parse = parse.replaceAll("\" alt=\"\">", "");
            parse = parse.replaceAll(" ", "");
            parse = parse.replaceAll("\n", "");
            urls.add(parse);
       }
        ViewFlipper flipper = (ViewFlipper) mainActivity.findViewById(R.id.flipper);

        //URL数だけImageViewを作成してflipperに追加
        for(int i=0;i<urls.size();i++) {
            ImageView iv = new ImageView(mainActivity);
            Picasso.with(mainActivity).load(urls.get(i)).into(iv);
            flipper.addView(iv);
        }
    }

}