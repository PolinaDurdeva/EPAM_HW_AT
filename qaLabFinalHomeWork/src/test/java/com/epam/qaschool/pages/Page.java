package com.epam.qaschool.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.LoggerFactory;

/**
 * Abstract class representation of a Page in the UI. Page object pattern
 */
public abstract class Page {

  protected WebDriver driver;
  private final static org.slf4j.Logger log = LoggerFactory.getLogger(Page.class);


  public Page(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public String getTitle() {
    return driver.getTitle();
  }

}
