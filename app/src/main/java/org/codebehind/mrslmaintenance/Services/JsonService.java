package org.codebehind.mrslmaintenance.Services;

import android.os.AsyncTask;

import org.codebehind.mrslmaintenance.Entities.LastUpdate;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.LastUpdateModel;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by root on 22/03/16.
 */
public class JsonService extends AsyncTask<String, Void, String> {
    private SiteDbModel _sitemodel;
    private LastUpdateModel _lastUpdateModel;
    public JsonService(SiteDbModel sitemodel, LastUpdateModel lastUpdateModel){
        _sitemodel=sitemodel;
        _lastUpdateModel=lastUpdateModel;
    }

    @Override
    protected String doInBackground(String... params) {
        List<Site> sites = _sitemodel.getList();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        StringBuilder sb = new StringBuilder();
        LastUpdate lastUpdate=_lastUpdateModel.getLastUpdate();
        String dateAsString=dateFormat.format(lastUpdate.getTimestamp()), str;
        URL url;
        HttpURLConnection urlConnection;
        InputStream in;
        byte[] bs;
        int i=0;
        JSONObject jObject;
        JSONArray jSites;

        try {
            sb.append("{");
            sb.append("\"timestamp\":\"" + dateAsString + "\",");
            sb.append("\"sites\":[");

            for (Site s : sites) {
                if (s.getTimestamp().after(lastUpdate.getTimestamp())) {
                    sb.append("{");
                    sb.append("\"uuid\":\"" + s.getUUID()+"\"");
                    sb.append(",");
                    sb.append("\"name\":\"" + s.getName()+"\"");
                    sb.append(",");
                    sb.append("\"address\":\"" + s.getAddress()+"\"");
                    sb.append(",");
                    sb.append("\"timestamp\":\"" + dateFormat.format(s.getTimestamp())+"\"");
                    sb.append(",");
                    sb.append("\"deleted\":\"" + s.getDeleted()+"\"");
                    sb.append("}");
                    if (i<sites.size()-1) sb.append(",");
                }
                i++;
            }
            sb.append("]");
            sb.append("}");

            url = new URL("http://192.168.1.108/siteTest.php?mydataj="+ URLEncoder.encode(sb.toString()));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");

            //String text="mydataj=helpmeJ";
            ///byte[] bytes = text.getBytes("UTF-8");
            //OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            //out.write(bytes);

            in = new BufferedInputStream(urlConnection.getInputStream());

            bs=new byte[1000];
            in.read(bs);
            str = new String(bs, "UTF-8").trim(); // for UTF-8 encoding

            jObject=new JSONObject(str);
            jSites=jObject.getJSONArray("sites");

            // this is allowed here because this needs to be generalised
            JSONObject jo;
            UUID siteUuid;
            Site site;

            if(jSites.length()-1>0) { // there's a blank at the end of JSON
                for (i = 0; i < jSites.length() - 1; i++) {
                    jo = (JSONObject) jSites.get(i);

                    // check the uuid is not already in the database table
                    if (jo.getString("uuid") == null) { // fore-go the check as the UUID is null and hasn't been sent for some reason
                        siteUuid = UUID.fromString(jo.getString("uuid"));
                        if (!_sitemodel.checkUuidInList(siteUuid)) {
                            site = new Site(jo.getString("name"), jo.getString("address"));
                            site.setUUID(UUID.fromString(jo.getString("uuid")));
                            _sitemodel.add(site);
                        }
                    }
                }
            }
            _lastUpdateModel.setUpdated();
            urlConnection.disconnect();
            //readStream(in);
        } catch(Exception e) {
            str=e.getMessage();
        }
        return null;
    }
}
