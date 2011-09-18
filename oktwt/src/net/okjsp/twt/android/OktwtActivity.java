package net.okjsp.twt.android;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class OktwtActivity extends ListActivity {
    Twitter twitter = new TwitterFactory().getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        String[] list = getList();
		setListAdapter(new ArrayAdapter<String>(this, R.layout.record, list));

    }

	private String[] getList() {
		List<String> list = new ArrayList<String>();
		try {
			List<Status> statuses = twitter.getPublicTimeline();
			System.out.println("Showing public timeline.");
			for (Status status : statuses) {
				list.add("@" + status.getUser().getScreenName() + " - " + status.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			list.add("Failed to get timeline: " + te.getMessage());
		}
		return list.toArray(new String[0]);
	}
}