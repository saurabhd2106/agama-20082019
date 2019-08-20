package in.amazon.pages;

import org.openqa.selenium.WebDriver;

import commonLibs.implementations.AlertControl;
import commonLibs.implementations.DropdownControl;
import commonLibs.implementations.ElementControl;

public class BasePage {

	DropdownControl dropdownControl;

	ElementControl elementControl;
	
	AlertControl alertControl;

	public BasePage(WebDriver driver) {
		dropdownControl = new DropdownControl();

		elementControl = new ElementControl();
		
		alertControl = new AlertControl(driver);
	}

}
