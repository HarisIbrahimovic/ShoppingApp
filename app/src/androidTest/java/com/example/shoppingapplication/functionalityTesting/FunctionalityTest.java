package com.example.shoppingapplication.functionalityTesting;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.shoppingapplication.R;
import com.example.shoppingapplication.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4ClassRunner.class)
public class FunctionalityTest {

    @Before
    public void setUp(){
        FirebaseAuth.getInstance().signOut();
        ActivityScenario.launch(MainActivity.class);
        FirebaseAuth.getInstance().signInWithEmailAndPassword("haris@gmail.com","haris123");
    }

    @Test
    public void mainAppTest() throws InterruptedException {
        String location = "Tuzla";
        //tryLogin
        onView(withId(R.id.loginActivity)).check(matches(isDisplayed()));
        onView(withId(R.id.emailEditText)).perform(typeText("haris@gmail.com"),pressImeActionButton());
        onView(withId(R.id.passwordEditText)).perform(typeText("haris123"),pressImeActionButton());
        Thread.sleep(1000);
        onView(withId(R.id.mainActivity)).check(matches(isDisplayed()));
        onView(withId(R.id.fragmentHome)).check(matches(isDisplayed()));

        //changeLocation
        onView(withId(R.id.changeLocationButton)).perform(click());
        onView(withId(R.id.locationEditText)).perform(typeText(location));
        onView(withId(R.id.addLocationButton)).perform(click());
        onView(withId(R.id.locationTextView)).check(matches(withText(location)));

        //openMenu
        onView(withId(R.id.newRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.newRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.menuActivity)).check(matches(isDisplayed()));

        //addItemToBag
        Thread.sleep(1000);
        onView(withId(R.id.menuItemsRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.menuItemsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.pressBack();

        //CheckBag
        onView(withId(R.id.korpa)).perform(click());
        onView(withId(R.id.fragmentBag)).check(matches(isDisplayed()));
        onView(withId(R.id.bagLinearLayout)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recyclerViewBag)).check(matches(isDisplayed()));

        //EmptyBag
        onView(withId(R.id.deleteBagButton)).perform(click());
        onView(withId(R.id.bagLinearLayout)).check(matches(isDisplayed()));

        //OpenMapsAndProfile
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.fragmentMaps)).check(matches(isDisplayed()));
        onView(withId(R.id.user)).perform(click());
        onView(withId(R.id.fragmentProfile)).check(matches(isDisplayed()));

        //openFavorites
        onView(withId(R.id.favorites)).perform(click());
        onView(withId(R.id.favoritesActivity)).check(matches(isDisplayed()));
        Espresso.pressBack();

        //LogOut
        onView(withId(R.id.signOut)).perform(click());
    }

}
