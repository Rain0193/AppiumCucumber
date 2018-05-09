Feature: App Configuration

  @ignore
  Scenario: Install and Configure App in Device

    Given  Install APP on Device
    When   Open patient link on Device
    And    Enter patient ID while setting up the app
    And    Login to patient portal in APP
    Then   Set PIN in Oncomate APP
    Then   Confirm PIN in Oncomate APP
    Then   Verify conformation message of setting PIN in APP


  @OM-100
  Scenario: Perform swipe operation on Welcome screens

    And    WelcomeScreen Screen should display the label Inbox for inboxLabelText
    And    Swipe from right to left in Inbox welcome screen
    And    WelcomeScreen Screen should display the label New Tasks for newTasksLabelText
    And    Swipe from right to left in New Tasks welcome screen
    And    WelcomeScreen Screen should display the label Calendar for calendarLabelText
    And    Swipe from right to left in Calendar welcome screen
    Then   Click on Skip button
