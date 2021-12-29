package views.screen.shipping;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.popup.PopupScreen;

public class ShippingScreenHandler extends BaseScreenHandler implements Initializable {

	@FXML
	private Label screenTitle;

	@FXML
	private TextField name;

	@FXML
	private TextField phone;

	@FXML
	private TextField address;

	@FXML
	private TextField instructions;

	@FXML
	private ComboBox<String> province;
	
	@FXML
	private DatePicker dateShipping;
	
	@FXML
	private TextField scheduledTime;
	
	@FXML
	private CheckBox rushOrder;

	private Order order;

	public ShippingScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
		super(stage, screenPath);
		this.order = order;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
		name.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                content.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
		this.province.getItems().addAll(Configs.PROVINCES);
	}

	@FXML
	void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {

		// add info to messages
		HashMap messages = new HashMap<>();
		messages.put("name", name.getText());
		messages.put("phone", phone.getText());
		messages.put("address", address.getText());
		messages.put("instructions", instructions.getText());
		messages.put("province", province.getValue());
		messages.put("dateShipping", dateShipping.getValue());
		System.out.println(dateShipping.getValue());
		messages.put("scheduledTime", scheduledTime.getText());
		messages.put("rushOrder", rushOrder.isSelected());
		
		try {
			if (province.getValue() == null) {
				PopupScreen.error("Please select province!");
				return;
			}
			// process and validate delivery info
			getBController().processDeliveryInfo(messages);
			
		} catch (InvalidDeliveryInfoException e) {
			throw new InvalidDeliveryInfoException(e.getMessage());
		}
		
		if (rushOrder.isSelected()) {
			System.out.println("Giao hàng nhanh");
			PlaceRushOrderController placeRushOrderController = new PlaceRushOrderController();
			if (!placeRushOrderController.checkProvinceRushShipping(messages.get("province").toString())) {
				PopupScreen.error("Province is not supported");
				return;
			}
			if (!placeRushOrderController.validateProductSupport(order)) {
				PopupScreen.error("No product support place rush order!");
				return;
			}
			
			int shippingFees = getBController().calculateShippingFee(order.getAmount());
			order.setShippingFees(shippingFees);
			order.setDeliveryInfo(messages);
			
			// create invoice screen
			Invoice invoice = getBController().createInvoice(order);
			BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
			InvoiceScreenHandler.setPreviousScreen(this);
			InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
			InvoiceScreenHandler.setScreenTitle("Invoice Screen");
			InvoiceScreenHandler.setBController(getBController());
			InvoiceScreenHandler.show();
			
			
		} else {
			
			System.out.println("Giao hàng thường");
			
			// calculate shipping fees
			int shippingFees = getBController().calculateShippingFee(order.getAmount());
			order.setShippingFees(shippingFees);
			order.setDeliveryInfo(messages);
			
			// create invoice screen
			Invoice invoice = getBController().createInvoice(order);
			BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
			InvoiceScreenHandler.setPreviousScreen(this);
			InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
			InvoiceScreenHandler.setScreenTitle("Invoice Screen");
			InvoiceScreenHandler.setBController(getBController());
			InvoiceScreenHandler.show();
		}
		
		
	}

	public PlaceOrderController getBController(){
		return (PlaceOrderController) super.getBController();
	}
	
	public PlaceRushOrderController getRController() {
		return (PlaceRushOrderController) super.getBController();
	}

	public void notifyError(){
		// TODO: implement later on if we need
	}

}
