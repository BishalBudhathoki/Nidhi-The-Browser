package com.erbisdev.mbrow;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ER.Bishal on 8/13/2017.
 */

public class Activity2 extends Activity {
    TextView tv;
    String content="<p><h2><u><b>About</b></u></h2></p>" +
            "<h3><u>Nidhi</u> </h3>" +
            "     <p>This is a basic browser application with only basic features required. Some other basic features will be added soon.  </p>\n" +
            "     <h2><b><u>Thanks To: </u></b></h2>" +
            "     <ul>\n" +
            "     <li>1.<b> Jerry Lin</b></li>\n<BR>" +
            "     <li>2.<b> Archana Narayan</b></li>\n<BR>" +
            "     <li>3.<b> Biplove Shakya</b></li>\n" +
            "     </ul>\n" +
            "     <h3><u>Contact Us:</u> </h3>\n" +
            "     <p><u>deverbishal331@gmail.com</u></p>"
            ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);
        ImageView back =(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"Back Pressed",Toast.LENGTH_SHORT).show();
            }
        });
      //  tv = (TextView) findViewById(R.id.textView5);
 //       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
   //         tv.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
   //    } else
    //        tv.setText(Html.fromHtml(content));

    }
}