package info.mindland.theoneandonly;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MasterActivity extends ActionBarActivity {

	@Override
	protected void onResume() {
		super.onResume();
		SiteActivity.loadHistory(this);
		adapter.notifyDataSetChanged();
	}

	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_master);

		final TextView hint = (TextView) findViewById(R.id.hint);

		final EditText pwbox = (EditText) findViewById(R.id.websitename);
		pwbox.requestFocus();

		ListView history = (ListView) findViewById(R.id.historyList);

		SiteActivity.loadHistory(this);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1,
				SiteActivity.old);

		history.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				if (pwbox.getText().length() < 10)
					return;

				Intent in = new Intent(getApplicationContext(),
						SiteActivity.class);
				
				hint.setText("");
				in.putExtra("master", pwbox.getText().toString());
				in.putExtra("site", SiteActivity.old.get(pos));
				
				startActivity(in);

				
			}
		});

		history.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				SiteActivity.old.remove(arg2);
				SiteActivity.storeHistory(getApplicationContext());
				adapter.notifyDataSetChanged();
				return false;
			}
		});

		history.setAdapter(adapter);

		Button ok = (Button) findViewById(R.id.showPw);
		OnClickListener okListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (pwbox.getText().length() > 9) {

					Intent in = new Intent(getApplicationContext(),
							SiteActivity.class);

					hint.setText("");
					in.putExtra("master", pwbox.getText().toString());

					startActivity(in);

				} else {
					hint.setTextColor(Color.RED);
					hint.setText(getString(R.string.tooshort));
				}
			}
		};
		ok.setOnClickListener(okListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
