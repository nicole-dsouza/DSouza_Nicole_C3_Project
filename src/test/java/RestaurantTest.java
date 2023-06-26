import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void setup() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime currentTime = LocalTime.now();
        LocalTime openingTime = currentTime.minusHours(1);
        LocalTime closingTime = currentTime.plusHours(1);
        restaurant.openingTime = openingTime;
        restaurant.closingTime = closingTime;

        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime currentTime = LocalTime.now();
        LocalTime closingTime = currentTime.minusHours(1);
        LocalTime openingTime = currentTime.plusHours(1);
        restaurant.openingTime = openingTime;
        restaurant.closingTime = closingTime;

        assertFalse(restaurant.isRestaurantOpen());
    }

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
    public void getMenu_should_return_list_of_items() {
        List<Item> menu = restaurant.getMenu();

        assertNotNull(menu);
        assertEquals(2, menu.size());
        assertEquals("Sweet corn soup", menu.get(0).getName());
        assertEquals(119, menu.get(0).getPrice());
        assertEquals("Vegetable lasagne", menu.get(1).getName());
        assertEquals(269, menu.get(1).getPrice());
    }

    @Test
    public void calculate_order_value_should_return_total_price_of_selected_items() throws itemNotFoundException {
        restaurant.addToMenu("Butter Chicken", 100);
        restaurant.addToMenu("Paneer Tikka Masala", 200);
        restaurant.addToMenu("Butter Chicken Kulcha", 300);

        List<String> itemNames = new ArrayList<>();
        itemNames.add("Butter Chicken");
        itemNames.add("Butter Chicken Kulcha");

        int orderValue = restaurant.calculateOrderValue(itemNames);
        assertEquals(400, orderValue);
    }
}
