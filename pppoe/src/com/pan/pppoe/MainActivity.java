package com.pan.pppoe;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	private RelativeLayout loginBtn;
	private ProgressBar loadProgressBar;
	private EditText userNameEditText;
	private EditText passWordEditText;
	private String newWorkTest;
	private int retryTimeNow = 0;
	private String aString;
	private boolean haveConnected = false;
	private String systemDataPath;
	private List<String> wlanName;
	private CheckBox checkBoxRememberPwd;
	private CheckBox checkBoxAutoLogin;
	private SharePreferenceHelper sharePreferenceHelper;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pppoe);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		sharePreferenceHelper = new SharePreferenceHelper();
		systemDataPath = this.getFilesDir().getPath();
		if (!(new File(systemDataPath)).exists()) {
			(new File(systemDataPath)).mkdirs();
		}
		webView = (WebView) findViewById(R.id.webView);
		checkBoxAutoLogin = (CheckBox) findViewById(R.id.autoLogin);
		checkBoxRememberPwd = (CheckBox) findViewById(R.id.rememberPwd);
		checkBoxAutoLogin.setChecked(sharePreferenceHelper
				.getIsPPPoeAutoLogin());
		checkBoxRememberPwd.setChecked((sharePreferenceHelper
				.getPPPoeLoginPassword()).equals("") ? false : true);
		loginBtn = (RelativeLayout) findViewById(R.id.loginButton);
		loadProgressBar = (ProgressBar) this.findViewById(R.id.loading);
		userNameEditText = (EditText) findViewById(R.id.username);
		passWordEditText = (EditText) findViewById(R.id.password);
		userNameEditText.setText(sharePreferenceHelper.getPPPoeLoginUsername());
		passWordEditText.setText(sharePreferenceHelper.getPPPoeLoginPassword());
		loadProgressBar.setVisibility(View.INVISIBLE);
		loginBtn.setOnClickListener(loginOnClickListener);
		moveFileToSystem();
		// DataOutputStream localoDataOutputStream=new
		// DataOutputStream(localProcess.getOutputStream());
	}

	public void moveFileToSystem() {
		try {

			Process suProcess = Runtime.getRuntime().exec("su");
			DataOutputStream suDataOutputStream = new DataOutputStream(
					suProcess.getOutputStream());
			suDataOutputStream.writeBytes("mount -o remount rw system\n");
			suDataOutputStream.flush();
			File filePppoe = new File(systemDataPath, "pppoe");
			if (!filePppoe.exists()) {
				InputStream inputStream = this.getResources().openRawResource(
						R.raw.pppoe);

				byte[] bytes = new byte[inputStream.available()];
				new DataInputStream(inputStream).readFully(bytes);
				FileOutputStream fileOutputStream = new FileOutputStream(
						systemDataPath + "/pppoe");
				fileOutputStream.write(bytes);
				fileOutputStream.close();

			}
			suDataOutputStream.writeBytes("chmod 755 " + systemDataPath
					+ "/pppoe\n");
			suDataOutputStream.flush();
			File fileWifiPppoe = new File(systemDataPath, "wifipppoe");
			if (!fileWifiPppoe.exists()) {
				InputStream inputStream = this.getResources().openRawResource(
						R.raw.wifipppoe);

				byte[] bytes = new byte[inputStream.available()];
				new DataInputStream(inputStream).readFully(bytes);
				FileOutputStream fileOutputStream = new FileOutputStream(
						systemDataPath + "/wifipppoe");
				fileOutputStream.write(bytes);
				fileOutputStream.close();

			}

			suDataOutputStream.writeBytes("chmod 755 " + systemDataPath
					+ "/wifipppoe\n");
			suDataOutputStream.flush();
			suDataOutputStream.writeBytes("dd if=" + systemDataPath
					+ "/pppoe of=/system/bin/pppoe\n");
			suDataOutputStream.flush();
			suDataOutputStream.writeBytes("chmod 755 /system/bin/pppoe\n");
			suDataOutputStream.flush();
			suDataOutputStream.writeBytes("dd if=" + systemDataPath
					+ "/wifipppoe of=/system/bin/wifipppoe\n");
			suDataOutputStream.flush();
			suDataOutputStream.writeBytes("chmod 755 /system/bin/wifipppoe\n");
			suDataOutputStream.flush();
			suDataOutputStream.writeBytes("exit\n");
			suDataOutputStream.flush();
			InputStream inputStream = suProcess.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String line = "";
			StringBuilder stringBuilder = new StringBuilder(line);
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append("\n");

			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("wrong", e.toString());
		}

	}

	OnClickListener loginOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			loadProgressBar.setVisibility(View.VISIBLE);
			wlanName = getWlanName();
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						for (int i = 0; i < wlanName.size(); i++) {

							for (retryTimeNow = 0; retryTimeNow < 3; retryTimeNow++) {

								String userName = userNameEditText.getText()
										.toString();
								String passWord = passWordEditText.getText()
										.toString();
								String strcommand = "/system/bin/wifipppoe "
										+ wlanName.get(i) + " " + userName
										+ " " + passWord + "\n";
								Process localpProcess = Runtime.getRuntime()
										.exec("su");
								DataOutputStream dataOutputStream = new DataOutputStream(
										localpProcess.getOutputStream());
								dataOutputStream.writeBytes(strcommand);
								dataOutputStream.flush();
								dataOutputStream.writeBytes("exit\n");
								dataOutputStream.flush();
								InputStream inputStream = localpProcess
										.getInputStream();
								InputStreamReader inputStreamReader = new InputStreamReader(
										inputStream);
								BufferedReader bufferedReader = new BufferedReader(
										inputStreamReader);
								String line = "";
								StringBuilder stringBuilder = new StringBuilder(
										line);
								while ((line = bufferedReader.readLine()) != null) {
									stringBuilder.append(line);
									stringBuilder.append("\n");

								}
								aString = stringBuilder.toString();
								// userNameEditText.setText(stringBuilder.toString());
							}
							handler.sendEmptyMessage(1);
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// TODO Auto-generated method stub
					if (!haveConnected) {
						handler.sendEmptyMessage(3);
					}

				}
			}).start();

		}
	};

	public List<String> getWlanName() {
		Process suProcess;
		List<String> wlanNameTemp = new ArrayList<String>();
		try {
			suProcess = Runtime.getRuntime().exec("su");
			DataOutputStream suDataOutputStream = new DataOutputStream(
					suProcess.getOutputStream());
			suDataOutputStream.writeBytes("netcfg\n");
			suDataOutputStream.flush();
			suDataOutputStream.writeBytes("exit\n");
			suDataOutputStream.flush();
			InputStream inputStream = suProcess.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String line = "";
			StringBuilder stringBuilder = new StringBuilder(line);
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(" ");

			}
			String[] possibleWlanName = stringBuilder.toString().split("\\s+");

			for (int i = 0; i < possibleWlanName.length / 5; i++) {
				if (possibleWlanName[5 * i + 1].equals("UP")
						|| possibleWlanName[5 * i + 1].equals("up")
						|| possibleWlanName[5 * i + 1].equals("Up")) {
					wlanNameTemp.add(possibleWlanName[5 * i]);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return wlanNameTemp;

	}

	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			webView.loadUrl("http://m.baidu.com/s?from1269&word=ip");
			webView.setWebViewClient(new WebViewClient() {
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return true;
				}
			});
			loadProgressBar.setVisibility(View.INVISIBLE);
			sharePreferenceHelper.setPPPoeLoginUsername(userNameEditText
					.getText().toString());
			if (checkBoxAutoLogin.isChecked()) {
				sharePreferenceHelper.setIsPPPoeAutoLogin(true);
				sharePreferenceHelper.setPPPoeLoginPassword(passWordEditText
						.getText().toString());
			}
			if (checkBoxRememberPwd.isChecked()) {
				sharePreferenceHelper.setPPPoeLoginPassword(passWordEditText
						.getText().toString());
			}
		}
	};

	public void dialer() {

	}

	public void changeLoginSetting(View view) {
		if (!checkBoxRememberPwd.isChecked()) {
			sharePreferenceHelper.setPPPoeLoginPassword("");
		}
		if (!checkBoxAutoLogin.isChecked()) {
			sharePreferenceHelper.setIsPPPoeAutoLogin(false);
		}
		if (checkBoxAutoLogin.isChecked()) {
			checkBoxRememberPwd.setChecked(true);
			sharePreferenceHelper.setIsPPPoeAutoLogin(false);
		}
	}

	public void finishActivity(View view) {
		finish();
	}

}
