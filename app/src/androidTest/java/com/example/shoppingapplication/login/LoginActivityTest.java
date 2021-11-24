package com.example.shoppingapplication.login;

import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.shoppingapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasTextColor;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4ClassRunner.class)
public class LoginActivityTest {



    @Before
    public void setUp(){
        FirebaseAuth.getInstance().signOut();
        ActivityScenario.launch(LoginActivity.class);
    }

    @Test
    public void test1ActivityInView(){
        onView(withId(R.id.loginActivity)).check(matches(isDisplayed()));
    }

    @Test
    public void test2RegistrationInView(){
        onView(withId(R.id.constraintLayout2)).check(matches(isDisplayed()));
        onView(withId(R.id.emailEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.registracijaText)).check(matches(isDisplayed()));
        onView(withId(R.id.prijavaText)).check(matches(isDisplayed()));
        onView(withId(R.id.slashText)).check(matches(isDisplayed()));
    }

    @Test
    public void test3UpperPartInView(){
        onView(withId(R.id.skipButton)).check(matches(isDisplayed()));
        onView(withId(R.id.textView6)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView3)).check(matches(isDisplayed()));
    }


    @Test
    public void test4SkipButton(){
        onView(withId(R.id.skipButton)).perform(click());
        onView(withId(R.id.mainActivity)).check(matches(isDisplayed()));
    }

    @Test
    public void test5ChangeColors(){
        onView(withId(R.id.registracijaText)).perform(click());

        onView(withId(R.id.registracijaText)).check(matches(hasTextColor(R.color.teal_200)));
        onView(withId(R.id.prijavaText)).check(matches(hasTextColor(R.color.black)));

        onView(withId(R.id.prijavaText)).perform(click());
        onView(withId(R.id.prijavaText)).check(matches(hasTextColor(R.color.teal_200)));
        onView(withId(R.id.registracijaText)).check(matches(hasTextColor(R.color.black)));

    }


    @Test
    public void test6FailedLogin() {
        onView(withId(R.id.emailEditText)).perform(typeText("test"),pressImeActionButton());
        onView(withId(R.id.passwordEditText)).perform(typeText("test"),pressImeActionButton());
        //onView(withText("Error occurred.")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
        //checkToastMessage
    }

    @Test
    public void test7SuccessLogin() throws InterruptedException {
        onView(withId(R.id.emailEditText)).perform(typeText("haris@gmail.com"),pressImeActionButton());
        onView(withId(R.id.passwordEditText)).perform(typeText("haris123"),pressImeActionButton());
        Thread.sleep(2000);
        onView(withId(R.id.mainActivity)).check(matches(isDisplayed()));
    }


    @Test
    public void test7SuccessRegistration() throws InterruptedException {
        String email = UUID.randomUUID().toString()+"@gmail.com";
        String password = "password";

        onView(withId(R.id.registracijaText)).perform(click());
        onView(withId(R.id.emailEditText)).perform(typeText(email),pressImeActionButton());
        onView(withId(R.id.passwordEditText)).perform(typeText(password),pressImeActionButton());
        Thread.sleep(2000);
        onView(withId(R.id.mainActivity)).check(matches(isDisplayed()));
    }





}