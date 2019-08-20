package designPattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commonLibs.implementations.DropdownControl;
import commonLibs.implementations.ElementControl;

public class AmazonHomePageStyle1 {

	private WebElement searchBox;

	private WebElement selectCategory;

	private WebElement searchButton;
	
	private WebElement resultText;

	private DropdownControl dropdownControl;

	private ElementControl elementControl;

	public AmazonHomePageStyle1(WebDriver driver) {

		searchBox = driver.findElement(By.id("twotabsearchtextbox"));

		selectCategory = driver.findElement(By.id("searchDropdownBox"));

		searchButton = driver.findElement(By.xpath("//input[@value='Go']"));

		resultText = driver.findElement(By.xpath("//span[@data-component-type='s-result-info-bar']"));
		
		
		dropdownControl = new DropdownControl();

		elementControl = new ElementControl();
	}

	public void searchProduct(String product, String category) throws Exception {

		elementControl.setText(searchBox, product);

		dropdownControl.selectViaVisibleText(selectCategory, category);

		elementControl.clickElement(searchButton);
		
		String result = elementControl.getText(resultText);
		
		System.out.println(result);

	}

}
