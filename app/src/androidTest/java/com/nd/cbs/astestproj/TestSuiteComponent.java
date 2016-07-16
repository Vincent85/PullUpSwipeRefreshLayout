package com.nd.cbs.astestproj;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by cbs on 2016/5/20 0020.
 * Espresso 简单demo
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestSuiteComponent {

//    @Test
//    public void testHello() {
//        String test = "Hello world";
//
//        assertEquals(test,"Hello world");
//    }

    private String mStringToBeTyped;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initValidString() {
        mStringToBeTyped = "Espresso";
    }

    @Test
    public void changeText() {
        onView(withId(R.id.editText)).perform(typeText(mStringToBeTyped),closeSoftKeyboard());

        onView(withId(R.id.editText)).check(matches(withText(mStringToBeTyped)));
    }
}
