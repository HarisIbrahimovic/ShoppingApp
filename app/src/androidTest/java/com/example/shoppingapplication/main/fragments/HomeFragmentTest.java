package com.example.shoppingapplication.main.fragments;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.shoppingapplication.R;
import com.example.shoppingapplication.main.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4ClassRunner.class)
public class HomeFragmentTest {

    @Before
    public void setUp(){
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void test1FragmentInView(){
        onView(withId(R.id.fragmentHome)).check(matches(isDisplayed()));
    }

    @Test
    public void test2RecyclerViewsVisible(){
        onView(withId(R.id.recyclerViewOne)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerViewCategories)).check(matches(isDisplayed()));
        onView(withId(R.id.newRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void test3UpperPartVisible(){
        onView(withId(R.id.constraintLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.changeLocationButton)).check(matches(isDisplayed()));
        onView(withId(R.id.locationTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        onView(withId(R.id.bagButton)).check(matches(isDisplayed()));
    }

    @Test
    public void test4LowerPartVisible(){
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        onView(withId(R.id.textView2)).check(matches(isDisplayed()));
        onView(withId(R.id.textView3)).check(matches(isDisplayed()));
        onView(withId(R.id.textView4)).check(matches(isDisplayed()));
        onView(withId(R.id.textView5)).check(matches(isDisplayed()));
        onView(withId(R.id.textView7)).check(matches(isDisplayed()));
        onView(withId(R.id.novoUTuzli)).check(matches(isDisplayed()));
    }


    @Test
    public void test5ChangeLocationButton(){
        onView(withId(R.id.changeLocationButton)).perform(click());
        onView(withId(R.id.locationView)).check(matches(isDisplayed()));
        onView(withId(R.id.locationEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.addLocationButton)).check(matches(isDisplayed()));
    }


    @Test
    public void test5ChangeLocationSuccess(){
        String location = "Tuzla";
        onView(withId(R.id.changeLocationButton)).perform(click());
        onView(withId(R.id.locationEditText)).perform(typeText(location));
        onView(withId(R.id.addLocationButton)).perform(click());
        onView(withId(R.id.locationTextView)).check(matches(withText(location)));
    }

    @Test
    public void test6BagButtonCheck(){
        onView(withId(R.id.bagButton)).perform(click());
        onView(withId(R.id.fragmentBag)).check(matches(isDisplayed()));
    }

    @Test
    public void test7RecyclerViewOneClicks() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.recyclerViewOne)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.menuActivity)).check(matches(isDisplayed()));
    }

    @Test
    public void test8RecyclerViewTwoClicks() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.newRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.menuActivity)).check(matches(isDisplayed()));
    }


    @Test
    public void test9RecyclerViewCatClicks() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.recyclerViewCategories)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.categoryActivity)).check(matches(isDisplayed()));
    }


}