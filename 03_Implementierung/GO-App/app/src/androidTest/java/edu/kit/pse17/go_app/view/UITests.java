package edu.kit.pse17.go_app.view;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vovas on 05.09.2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITests {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("edu.kit.pse17.go_app", appContext.getPackageName());
    }
}
