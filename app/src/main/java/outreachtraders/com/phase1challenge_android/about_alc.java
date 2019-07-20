package outreachtraders.com.phase1challenge_android;

import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class about_alc extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about_alc);
        //setting title on a page
        setTitle(R.string.about_alc);

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(final WebView view, final SslErrorHandler handler, SslError error) {
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        handler.proceed();
                        break;
                }
            }
        });
        webView.loadUrl("https://andela.com/alc/");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
}
