import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

class RestaurantTest {
    Restaurant restaurant;
    Restaurant mockedRestaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @BeforeEach
    public void doSetup()
    {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        mockedRestaurant = Mockito.spy(restaurant);

        //Mocking getCurrentTime() of restaurant class to return LocalTime exactly at opening time
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("10:30:00"));
        assertTrue(mockedRestaurant.isRestaurantOpen());

        //Mocking getCurrentTime() of restaurant class to return LocalTime after opening and before closing time
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("20:00:00"));
        assertTrue(mockedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        mockedRestaurant = Mockito.spy(restaurant);

        //Mocking getCurrentTime() of restaurant class to return LocalTime before opening time
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("10:29:59"));
        assertFalse(mockedRestaurant.isRestaurantOpen());

        //Mocking getCurrentTime() of restaurant class to return LocalTime at closing time
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("22:00:00"));
        assertFalse(mockedRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    @Test
    public void when_calculating_price_of_menu_items_with_valid_item_list_should_return_correct_amount() throws itemNotFoundException {
        List chosenItems = getMockChosenItems();
        assertEquals(388,restaurant.calculatePriceTotal( chosenItems));
    }

    @Test
    public void when_calculating_price_of_menu_items_with_empty_item_list_should_return_zero() throws itemNotFoundException {
        List chosenItems = new ArrayList<String>();
        assertEquals(0,restaurant.calculatePriceTotal( chosenItems));
    }

    @Test
    public void when_calculating_price_of_menu_items_with_non_existent_item_in_list_should_throw_ItemNotFoundException() {

        List chosenItems = getMockChosenItems();
        chosenItems.add("French fries");

        assertThrows(itemNotFoundException.class,
                ()->restaurant.calculatePriceTotal( chosenItems));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public List<String> getMockChosenItems(){

        List chosenItems = new ArrayList<String>();
        chosenItems.add("Sweet corn soup");
        chosenItems.add("Vegetable lasagne");

        return  chosenItems;
    }

}