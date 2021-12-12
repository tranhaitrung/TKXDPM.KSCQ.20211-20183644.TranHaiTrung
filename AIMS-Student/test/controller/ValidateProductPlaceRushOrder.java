package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import entity.media.Book;
import entity.media.CD;
import entity.media.DVD;
import entity.media.Media;

public class ValidateProductPlaceRushOrder {

	private PlaceRushOrderController placeRushOrderController;
	
	private List listMediaList;
	
	@BeforeEach
	void setUp() throws Exception {
		placeRushOrderController = new PlaceRushOrderController();
	}
	
	@Test
	void test() throws SQLException {
		
		Book book = new Book();
		
		CD cd = new CD();
		
		DVD dvd = new DVD();
		
		boolean isValided1 = placeRushOrderController.checkProductPlaceRushOrder(book);
		assertEquals(isValided1, true);
		
		boolean isValided2 = placeRushOrderController.checkProductPlaceRushOrder(cd);
		assertEquals(isValided2, true);
		
		boolean isValided3 = placeRushOrderController.checkProductPlaceRushOrder(dvd);
		assertEquals(isValided3, true);
	}
}
