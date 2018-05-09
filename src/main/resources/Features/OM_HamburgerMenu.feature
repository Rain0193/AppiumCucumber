Feature: Messages Functionalities

#  Background:
#    Given  Launch the Oncomate App
#    When   Provide Security Pin as 0000

  @OM-104
  Scenario: Create and Send Message to Single Recipient

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    Then   Click on Hamburger Menu
    Then   HamburgerMenu Screen should display the label Help for helpLabel
    Then   HamburgerMenu Screen should display the label Settings for settingsLabel
    Then   HamburgerMenu Screen should display the label About for aboutLabel
    Then   HamburgerMenu Screen should display the label Logput for logoutLabel
