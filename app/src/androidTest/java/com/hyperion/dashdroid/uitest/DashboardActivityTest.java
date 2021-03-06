package com.hyperion.dashdroid.uitest;

import android.test.ActivityInstrumentationTestCase2;

import com.hyperion.dashdroid.DashboardActivity;
import com.hyperion.dashdroid.books.BooksModuleActivity;
import com.hyperion.dashdroid.news.NewsModuleActivity;
import com.hyperion.dashdroid.radio.RadioModuleActivity;
import com.hyperion.dashdroid.settings.SettingsActivity;
import com.robotium.solo.Solo;

/**
 * Created by infinity on 27-Apr-16.
 */
public class DashboardActivityTest extends ActivityInstrumentationTestCase2<DashboardActivity> {

	private Solo solo;

	public DashboardActivityTest() {
		super(DashboardActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAllViews() {

		boolean viewsCreated = false;

		while(!viewsCreated) {

			if(solo.getCurrentViews().size() != 0) {
				viewsCreated = true;
			}

		}

		if(viewsCreated) {

			solo.clickOnText("News");
			solo.waitForActivity(NewsModuleActivity.class, 200);
			solo.assertCurrentActivity("Expected activity is:", NewsModuleActivity.class);
			solo.goBack();

			solo.clickOnText("Radio");
			solo.waitForActivity(RadioModuleActivity.class, 200);
			solo.assertCurrentActivity("Expected activity is:", RadioModuleActivity.class);
			solo.goBack();

			solo.clickOnText("Books");
			solo.waitForActivity(BooksModuleActivity.class, 200);
			solo.assertCurrentActivity("Expected activity is:", BooksModuleActivity.class);
			solo.goBack();

			solo.clickOnView(getActivity().findViewById(getActivity().getResources().getIdentifier("" + getActivity().getMENU_SETTINGS_BUTTON_ID(), "id", getActivity().getPackageName())));
			solo.waitForActivity(SettingsActivity.class, 200);
			solo.assertCurrentActivity("Expected activity is:", SettingsActivity.class);
			solo.goBack();

		}

	}
}