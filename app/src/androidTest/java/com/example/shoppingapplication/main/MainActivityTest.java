package com.example.shoppingapplication.main;

import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.shoppingapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Before
    public void setUp(){
        ActivityScenario.launch(MainActivity.class);
    }


    @Test
    public void test1ActivityInView(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword("haris@gmail.com","haris123").addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                onView(withId(R.id.mainActivity)).check(matches(isDisplayed()));
                onView(withId(R.id.mainFrame)).check(matches(isDisplayed()));
                onView(withId(R.id.mainMenu)).check(matches(isDisplayed()));
            }
        });
    }


    @Test
    public void test2NavigationValidation(){

        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.fragmentMaps)).check(matches(isDisplayed()));

        onView(withId(R.id.pocetna)).perform(click());
        onView(withId(R.id.fragmentHome)).check(matches(isDisplayed()));

        onView(withId(R.id.korpa)).perform(click());
        onView(withId(R.id.fragmentBag)).check(matches(isDisplayed()));

        onView(withId(R.id.user)).perform(click());
        onView(withId(R.id.fragmentProfile)).check(matches(isDisplayed()));

    }


    @Test
    public void test3LoginInView(){
        FirebaseAuth.getInstance().signOut();
        onView(withId(R.id.loginActivity)).check(matches(isDisplayed()));
    }

}