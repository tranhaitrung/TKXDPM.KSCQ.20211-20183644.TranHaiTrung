package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateAddressTest {

	private PlaceOrderController placeOrderController;

	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}

	@ParameterizedTest
	@CsvSource({
		",false",
		"Tran Nguyen Han,true",
		"abc123,true",
		"so 12 Tran Nguyen Han,true",
		"$# Nguyen Van Nam,false"
	})
	void test(String address, boolean expected) {
		boolean isValidated = placeOrderController.validateAddress(address);
		assertEquals(isValidated, expected);
	}

}
