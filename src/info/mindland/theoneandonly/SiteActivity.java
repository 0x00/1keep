package info.mindland.theoneandonly;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SiteActivity extends Activity {

	static List<String> old = new LinkedList<String>();
	static Map<String, String> prefs = new HashMap<String, String>();

	static void loadHistory(Context context) {
		old.clear();
		prefs.clear();

		try {
			SharedPreferences pm = PreferenceManager
					.getDefaultSharedPreferences(context);
			String sites = pm.getString("sites", "");
			if (sites.length() > 0) {
				for (String line : sites.split("####")) {
					String site = line.split("###")[0];
					old.add(site);
					prefs.put(site, line.split("###")[1]);
				}
			}
		} catch (Exception e) {
			old.clear();
			prefs.clear();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadHistory(this);
	}

	static void storeHistory(Context context) {

		try {
			SharedPreferences pm = PreferenceManager
					.getDefaultSharedPreferences(context);
			Editor ed = pm.edit();

			StringBuilder sb = new StringBuilder();
			for (String site : old) {
				sb.append(site + "###" + prefs.get(site) + "####");
			}

			ed.putString("sites", sb.toString());
			ed.commit();
		} catch (Exception e) {

		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_site);
		loadHistory(this);

		final String master = getIntent().getExtras().getString("master");

		final EditText site = (EditText) findViewById(R.id.websitename);
		final EditText num = (EditText) findViewById(R.id.pwNumber);
		final EditText pw = (EditText) findViewById(R.id.generatedPw);

		String prefill = getIntent().getExtras().getString("site");
		if (prefill != null) {
			site.setText(prefill);
			num.setText(prefs.get(prefill));
			String pass = Password.pw(master, prefill, num(num));
			pw.setText(pass);
		}

		TextWatcher watcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				String name = site.getText().toString();
				int n = num(num);
				Log.d("site", name + " " + n);

				String pass = Password.pw(master, name, n);
				pw.setText(pass);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		};
		site.addTextChangedListener(watcher);
		num.addTextChangedListener(watcher);

		Button store = (Button) findViewById(R.id.store);
		store.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = site.getText().toString();
				if (name.length() == 0)
					return;
				int n = num(num);

				old.remove(name);
				old.add(name);
				prefs.put(name, n + "");

				storeHistory(getApplicationContext());

			}

		});

		final Button show = (Button) findViewById(R.id.showPw);
		OnClickListener onShow = new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean shouldShow = show.getText().equals(
						getString(R.string.showPw));

				if (shouldShow) {
					show.setText(R.string.hidePw);
					pw.setTransformationMethod(null);

				}
				if (!shouldShow) {
					show.setText(R.string.showPw);
					pw.setTransformationMethod(PasswordTransformationMethod
							.getInstance());
				}

			}
		};
		show.setOnClickListener(onShow);

		Button clip = (Button) findViewById(R.id.copy);
		clip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				copyClip(pw.getText().toString());
			}
		});
	}

	private int num(EditText num) {
		int n = 1;
		try {
			n = Integer.valueOf(num.getText().toString());
		} catch (NumberFormatException e) {
			n = 1;
		}
		return n;
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	void copyClip(String text) {

		int currentApiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentApiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			ClipData clip = ClipData.newPlainText("text", text);
			clipboard.setPrimaryClip(clip);
		} else {
			android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			clipboard.setText(text);
		}

	}

}
