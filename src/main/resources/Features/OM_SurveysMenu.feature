Feature: Messages Functionalities

#  Background:
#    Given  Launch the Oncomate App
#    When   Provide Security Pin as 0000

  @OM-370
  Scenario: Create Task from Landing screen
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    click on Surveys option from the Home screen
    Then   verify Burger menu icon should be present in Surveys screen
    Then   verify Home icon should be present in Surveys screen
    And    Click on Home icon from Header

  @OM-14
  Scenario: Verify Surveys menu is displaying on Landing Page
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    HomePage Screen should display the label Surveys for surveysMenu

