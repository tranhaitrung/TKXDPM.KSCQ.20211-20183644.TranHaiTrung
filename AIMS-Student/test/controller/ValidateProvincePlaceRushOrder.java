package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ValidateProvincePlaceRushOrder {

	private PlaceRushOrderController placeRushOrderController;
	
	@BeforeEach
	void setUp() throws Exception {
		placeRushOrderController = new PlaceRushOrderController();
	}
	
	@ParameterizedTest
	@CsvSource({
		"Hà Nội,true",
		"Bắc Giang,false",
		"Hà Nam,false",
		"Bắc Ninh,false",
		"Huế,false"
	})
	void test(String province, boolean expected) {
		boolean isValided = placeRushOrderController.checkProvinceRushShipping(province);
		assertEquals(isValided, expected);
	}
}
