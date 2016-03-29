package org.codebehind.mrslmaintenance.Services;

import android.os.AsyncTask;

import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by root on 22/03/16.
 */
public class JsonService extends AsyncTask<String, Void, String> {
    private SiteDbModel _model;
    public JsonService(SiteDbModel model){
        _model=model;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            List<Site> list = _model.getList();
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for (Site s : list){
                sb.append("[");
                sb.append("uuid:"+s.getUUID());
                sb.append(",");
                sb.append("name:"+s.getName());
                sb.append("]");
                sb.append(",");
            }
            sb.replace(sb.length()-1,sb.length()-1,"}");


            URL url = new URL("http://192.168.1.108/siteTest.php?mydataj="+ URLEncoder.encode(sb.toString()));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");

            //String text="mydataj=helpmeJ";
            ///byte[] bytes = text.getBytes("UTF-8");
            //OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            //out.write(bytes);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            byte[] bs=new byte[1000];
            int i = in.read(bs);

            String str = new String(bs, "UTF-8"); // for UTF-8 encoding

            urlConnection.disconnect();
            //readStream(in);
        } catch(Exception e) {
            String s=e.getMessage();
        }
        return null;
    }
}
