Feature: Messages Functionalities

#  Background:
#    Given  Launch the Oncomate App
#    When   Provide Security Pin as 0000

  @OM-201
  Scenario Outline: Create and Send Message to Single Recipient

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    And    Click on Plus button on message screen
    And    Enter subject <subject> for new message
    And    Enter body <body> for new message
    And    Click on add recipient for new message
    And    Select recipient <To-Recipient> for new message
    And    Click Send button for new message
    And    Click on Home icon from Header
    Examples:
      | subject                    |   body      | To-Recipient  |
      | Automation Created Message |   Test Body | Adam Ellis    |

  @OM-112
  Scenario Outline: Create and Send Message to Multiple recipients

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    And    Click on Plus button on message screen
    And    Enter subject <subject> for new message
    And    Enter body <body> for new message
    And    Click on add CC recipient for new message
    And    Select recipient <CC-Recipient> for new message
    And    Click on add recipient for new message
    And    Select recipient <To-Recipient> for new message
    And    Click Send button for new message
    And    Click on Home icon from Header
    Examples:
      | subject                    |   body      |   To-Recipient    |   CC-Recipient    |
      | Automation Created Message |   Test Body |    Adam Ellis     |     Adam Ellis    |

  @OM-201
  Scenario Outline: Send Message from Sent Messages Tab.

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    And    Click on Sent messages Tab
    And    Click on Plus button on message screen
    And    Enter subject <subject> for new message
    And    Enter body <body> for new message
    And    Click on add recipient for new message
    And    Select recipient <To-Recipient> for new message
    And    Click Send button for new message
    And    Click on Home icon from Header
    Examples:
      | subject                    |   body      | To-Recipient  |
      | Automation Created Message |   Test Body |  Adam Ellis   |

  @OM-14 @OM-161
  Scenario Outline: Create and send message from landing screen

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Swipe on Messages Menu option
    Then   Click on Add Message Icon
    And    Enter subject <subject> for new message
    And    Enter body <body> for new message
    And    Click on add recipient for new message
    And    Select recipient <To-Recipient> for new message
    And    Click Send button for new message

    Examples:
      | subject                    |   body      | To-Recipient  |
      | Automation Created Message |   Test Body | Adam Ellis    |

  @OM-201
  Scenario: Verify the Message sending fucntionality without entering the mandatory fileds.

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    And    Click on Plus button on message screen
    And    Click Send button for new message
    Then   Click on Ok button Associated with the Alert Message
    And    Click on Back Button
    And    Click on Home icon from Header

  @OM-15
  Scenario Outline: Verify the sent message in Sent Messages tab

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    And    Click on Plus button on message screen
    And    Enter subject <message> for new message
    And    Enter body <body> for new message
    And    Click on add recipient for new message
    And    Select recipient <To-Recipient> for new message
    And    Click Send button for new message
    Then   Click on Sent messages Tab
    And    Verify <message> is displaying in sent messages tab or not
    And    Click on Home icon from Header
    Examples:
      | message                    |   body      | To-Recipient  |
      | Automation Created Message |   Test Body |  Adam Ellis   |

  @OM-201
  Scenario Outline: Send Message without subject and without Message.

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    And    Click on Plus button on message screen
    And    Click on add recipient for new message
    And    Select recipient <To-Recipient> for new message
    And    Click Send button for new message
    Then   Click on Send button to send message without subject
    And    Click on Send button to send message without Message
    And    Click on Home icon from Header

    Examples:
      | To-Recipient  |
      |  Adam Ellis   |

  @OM-201
  Scenario Outline: Send Message without subject and with Message.


    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    And    Click on Plus button on message screen
    And    Click on add recipient for new message
    And    Select recipient <To-Recipient> for new message
    And    Click Send button for new message
    Then   Click on Send button to send message without subject
    And    Click on Add Content button to add the Message content
    And    Enter body <body> for new message
    And    Click Send button for new message
    Then   Click on Send button to send message without subject
    And    Click on Home icon from Header

    Examples:
      | To-Recipient  |   body      |
      |  Adam Ellis   |   Test Body |

  @OM-201
  Scenario Outline: Send Message with subject and without Message.

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    And    Click on Plus button on message screen
    And    Click on add recipient for new message
    And    Select recipient <To-Recipient> for new message
    And    Click Send button for new message
    Then   Click on Add Subject button to add message subject
    And    Enter subject <message> for new message
    And    Click Send button for new message
    And    Click on Send button to send message without Message
    And    Click on Home icon from Header

    Examples:
      | To-Recipient  |   message                 |
      | Adam Ellis    |   Test Automation Created |

  @OM-293
  Scenario Outline: dirty check on new message screen, Accept

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    And    Click on Plus button on message screen
    And    Enter subject <subject> for new message
    And    Enter body <body> for new message
    And    Click on add recipient for new message
    And    Select recipient <To-Recipient> for new message
    And    Click on Back Button
    Then   Click on Yes button associated with the aleart Message
    Then   Messages Screen should display the label Messages for MessagesLabel
    And    Click on Home icon from Header

    Examples:
      | subject                    |   body      | To-Recipient  |
      | Automation Created Message |   Test Body | Adam Ellis    |


  @OM-293
  Scenario Outline: dirty check on new message screen, Reject

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    And    Click on Plus button on message screen
    And    Enter subject <subject> for new message
    And    Enter body <body> for new message
    And    Click on add recipient for new message
    And    Select recipient <To-Recipient> for new message
    And    Click on Back Button
    Then   Click on No button associated with the aleart Message
    Then   Messages Screen should display the label New Message for newMessageLabel
    And    Click on Back Button
    Then   Click on Yes button associated with the aleart Message
    And    Click on Home icon from Header
    Examples:
      | subject                    |   body      | To-Recipient  |
      | Automation Created Message |   Test Body | Adam Ellis    |

  @OM-113
  Scenario: E-Messaging, Message Details view.

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    Then   Click on Message
    And    Messages Screen should display the label Message Details  for messageDetailsLabel
    And    Verify the Sender Details
    And    Verify Message Timestamp
    And    Verify Subject
    Then   Verify More Details link is displayed or not
    And    Click on More Details link
    And    Verify To Receipient is displayed or not
    Then   Verify Less Details link is displayed or not.
    And    Click on Less Details link
    And    Click on Back Button
    And    Click on Home icon from Header

  @OM-112
  Scenario Outline: Send Reply for the received message

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    Then   Click on Message
    And    Click on Reply Message Button
    And    Enter <Reply> for the message
    And    Click Send button for new message
    And    Click on Back Button
    And    Click on Home icon from Header

    Examples:
      | Reply                     |
      | Automation Reply Message  |


  @OM-112
  Scenario Outline: Send Reply for all  receipients for the received message

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    Then   Click on Message
    And    Click on Reply All Button
    And    Enter <Reply> for the message
    And    Click Send button for new message
    And    Click on Back Button
    And    Click on Home icon from Header

    Examples:
      | Reply                         |
      | Automation Reply All Message  |

  @OM-112
  Scenario Outline: Forward Message

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    Then   Click on Message
    And    Click on Forward Button
    And    Click on add recipient for new message
    And    Select recipient <To-Recipient> for new message
    And    Click Send button for new message
    And    Click on Send button to send message without Message
    And    Click on Back Button
    And    Click on Home icon from Header

    Examples:
      | To-Recipient  |
      |  Adam Ellis   |

  @OM-359
    Scenario Outline: Verify Characters limit in subject line.

    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on messages tab on landing page
    And    Click on Plus button on message screen
    And    Enter subject <subject> 25 times.
    Then   Verify the display of warning message
    And    Enter subject <subject2> for message
    Then   Verify the display of warning message
    Examples:
      | subject     | subject2  |
      | Test        | T         |



