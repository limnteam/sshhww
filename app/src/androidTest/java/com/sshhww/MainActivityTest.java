package com.sshhww;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sshhww.utils.FileUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * author： xiongdejin
 * date: 2018/4/19
 * describe:
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testFileUtils() {
        List<String> stringList = new ArrayList<>();
        stringList.add("sshhwwstart.sh");
        stringList.add("sshhwwstrap.jar");
//        FileUtil.checkFilesIsExist("/data/local/tmp",stringList);
        FileUtil.checkFilesIsInTmp(stringList);
    }

    @Test
    public void testGetInsidePath() {
//        FileUtil.getInsideFile(activityTestRule.getActivity());
        FileUtil.listFile("/");
    }
}

