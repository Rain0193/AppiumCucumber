Feature: Messages Functionalities

#  Background:
#    Given  Launch the Oncomate App
#    When   Provide Security Pin as 0000

  @OM-161
  Scenario Outline: Create Appointment from Landing screen with First Available appointment
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Swipe on Calendar Menu option
    Then   Click on Add Appointment Icon
    And    Provide Contact number as <Number>
    And    Provide Appointment reason as <Reason>
    Then   Click on First Available Appointment button.
    And    Click Ok Button

    Examples:
      | Reason                         |Number      |
      | Automation Created Appointment |9087654321  |

  @OM-118
  Scenario Outline: Request appointment for specific date and time
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Swipe on Calendar Menu option
    Then   Click on Add Appointment Icon
    And    Provide Contact number as <Number>
    And    Provide Appointment reason as <Reason>
    Then   Click on Choose dates button.
    And    Click on Calendar icon
    And    Click on Next Month button
    Then   Select Appointment <date>
    And    Click on Ok button
    Then   Select Appointment Timing as <Time1>
    And    Click Done Button
    Then   Click Send Button
    And    Click on Ok Button

    Examples:
      | Reason                         |Number      | date        | Time1       |
      | Automation Created Appointment |9087654321  | 2018.05.28  | 5-7pm       |


  @OM-118 @writeThis
  Scenario Outline: Validate
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Swipe on Calendar Menu option
    Then   Click on Add Appointment Icon
    And    Provide Contact number as <Number>
    And    Provide Appointment reason as <Reason>
    Then   Click on Choose dates button.
    And    Click on Calendar icon
    And    Click on Next Month button
    Then   Select Appointment <date>
    And    Click on Ok button
    Then   Select Appointment Timing as <Time1>
    And    Click Done Button
    Then   Click Send Button
    And    Click on Ok Button

    Examples:
      | Reason                         |Number      | date        | Time1       |
      | Automation Created Appointment |9087654321  | 2018.05.28  | 5-7pm       |

  @OM-118
  Scenario Outline: Edit appointment while creating it from landing page
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Swipe on Calendar Menu option
    Then   Click on Add Appointment Icon
    And    Provide Contact number as <Number>
    And    Provide Appointment reason as <Reason>
    Then   Click on Choose dates button.
    And    Click on Calendar icon
    And    Click on Next Month button
    Then   Select Appointment <date>
    And    Click on Ok button
    Then   Select Appointment Timing as <Time1>
    And    Click Done Button
    And    Click on Edit Details Button
    And    Deselect the appointment <Time1>
    Then   Select Appointment Timing as <Time2>
    And    Click Done Button
    Then   Click Send Button
    And    Click on Ok Button

    Examples:
      | Reason                         |Number      | date        | Time1        | Time2        |
      | Automation Created Appointment |9087654321  | 2018.05.28  | 9-11am       | 5-7pm        |

  @OM-118
  Scenario Outline: Appointment request message should be present in Sent item of Messages
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Swipe on Calendar Menu option
    Then   Click on Add Appointment Icon
    And    Provide Contact number as <Number>
    And    Provide Appointment reason as <Reason>
    Then   Click on Choose dates button.
    And    Click on Calendar icon
    And    Click on Next Month button
    Then   Select Appointment <date>
    And    Click on Ok button
    Then   Select Appointment Timing as <Time1>
    And    Click Done Button
    Then   Click Send Button
    And    Click on Ok Button
    And    Click on messages tab on landing page
    And    Click on Sent messages Tab
    Then   Click on Message
    Then   Verify appointment request sent for <Reason> or not

    Examples:
      | Reason                         |Number      | date        | Time1        |
      | Automation Created Appointment |9087654321  | 2018.05.28  | 9-11am       |


  @OM-118 @Sample @ignore
  Scenario: Launch native Calander
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on Calendar