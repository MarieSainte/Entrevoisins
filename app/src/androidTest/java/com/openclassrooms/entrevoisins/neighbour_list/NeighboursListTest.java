
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.design.widget.TabItem;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.MyNeighbourRecyclerViewAdapter;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.ui.neighbour_list.DetailsActivity;
import com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.List;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    private List<Neighbour> Neighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;


    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));

    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {

       onView(allOf(withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(ITEMS_COUNT));

       onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
               .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * When we click on a cell, DetailsActivity starts
     */
    @Test
    public void myNeighboursList_clickCell_shouldStartDetailsActivity() {

        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(allOf(withId(R.id.mainName), isDisplayed()));
    }

    @Test
    public void myNeighboursList_favorite_shouldDisplayFavoriteUsers(){

        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).perform(swipeLeft());

        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(0));

        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .perform(swipeRight())
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.view_favorite)).perform(click());
        onView(withId(R.id.backArrow)).perform(click());

        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).perform(swipeLeft());
        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(1));

    }
}
