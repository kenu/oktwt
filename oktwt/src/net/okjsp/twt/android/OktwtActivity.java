package net.okjsp.twt.android;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OktwtActivity extends Activity {
    Twitter twitter = new TwitterFactory().getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView listView = (ListView)findViewById(R.id.ListViewId);
        List<TwtRecord> list = getList();
		listView.setAdapter(new TwtItemAdapter(this, android.R.layout.simple_list_item_1, list));

    }

	private List<TwtRecord> getList() {
		List<TwtRecord> list = new ArrayList<TwtRecord>();
		try {
			List<Status> statuses = twitter.getPublicTimeline();
			for (Status status : statuses) {
				list.add(new TwtRecord("@" + status.getUser().getScreenName(), status.getText(), status.getUser().getProfileImageURL().getPath()) );
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			list.add(new TwtRecord("","Failed to get timeline: " + te.getMessage(),null));
		}
		return list;
	}
	
	public class TwtItemAdapter extends ArrayAdapter<TwtRecord> {
		

		private List<TwtRecord> twts;

		public TwtItemAdapter(Context context, int textViewResourceId,
				List<TwtRecord> twts) {
			super(context, textViewResourceId, twts);
			this.twts = twts;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.record, null);
			}
			
			TwtRecord twt = twts.get(position);
			if (twt != null) {
				TextView screenName = (TextView) v.findViewById(R.id.screenName);
				TextView text = (TextView) v.findViewById(R.id.text);

				if (screenName != null) {
					screenName.setText(twt.screenName);
				}

				if(text != null) {
					text.setText("text: " + twt.text );
				}
			}
			return v;
		}

		
	}
	
	public class TwtRecord {
		public String screenName;
		public String text;
		public String profileImage;
		
		public TwtRecord(String screenName, String text, String profileImage) {
			super();
			this.screenName = screenName;
			this.text = text;
			this.profileImage = profileImage;
		}
	}
}