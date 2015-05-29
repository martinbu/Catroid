/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2015 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package org.catrobat.catroid.espresso;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.test.ActivityInstrumentationTestCase2;

import org.catrobat.catroid.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class EspressoTest extends ActivityInstrumentationTestCase2<MainMenuActivity> {

	public EspressoTest() {
		super(MainMenuActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		getActivity();
	}

	public void testListGoesOverTheFold() throws InterruptedException {
		onView(withId(R.id.main_menu_button_continue)).check(matches(isDisplayed()));
		onView(withId(R.id.main_menu_button_continue)).perform(click());
		openActionBarOverflowOrOptionsMenu(this.getInstrumentation().getTargetContext());
		onView(withText(R.string.copy));
//
		pressBack();


//		onView(withText("t")).perform(click());

		onView(withText(R.string.background)).perform(click());
		onView(withText(R.string.scripts)).perform(click());
//		onView(withText("t")).perform(scrollTo());
		//onView(allOf(withId(android.R.id.list), withText("t"))).perform(click());

		//onData(allOf(is(instanceOf(ArrayList.class)), hasItem(withText("")))).perform(click());

		//onData(allOf(is(instanceOf(Sprite.class)), withSpriteName(R.string.mol))).perform(click());
		//onView(withText("t")).perform(click());

//		onData(anything()).atPosition(0).perform(click());

		pressBack();
		pressBack();
		pressBack();
		onView(withId(R.id.main_menu_button_continue)).check(matches(isDisplayed()));
	}

	public static Matcher<Object> withSpriteName(final String spriteName) {
		return new BoundedMatcher<Object, Sprite>(Sprite.class) {
			@Override
			protected boolean matchesSafely(Sprite sprite) {
				return spriteName.equals(sprite.getName());
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("with id: " + spriteName);
			}
		};
	}

}
