package com.nd.cbs.astestproj;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LogHistoryAndroidUnitTest {

    public static final String TEST_STRING = "This is a string";
//    public static final long TEST_LONG = 12345678L;
//    private LogHistory mLogHistory;

    private ParcelDemo mParcel;

    @Before
    public void createParcel() {
        mParcel = new ParcelDemo();
    }

    @Test
    public void testParcelData() {
        mParcel.setContent(TEST_STRING);

        Parcel parcel = Parcel.obtain();
        mParcel.writeToParcel(parcel,mParcel.describeContents());

        parcel.setDataPosition(0);

        ParcelDemo parcelDemo = ParcelDemo.CREATOR.createFromParcel(parcel);

        assertThat(parcelDemo.getContent(),is(TEST_STRING));
    }

}