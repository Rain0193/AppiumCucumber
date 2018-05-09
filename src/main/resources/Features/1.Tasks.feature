Feature: Tasks

 # Background:
 #   Given  Launch the Oncomate App
 #   When   Provide Security Pin as 0000

  @OM-14 @OM-116 @OM-87 @ON-97 @OM-98
  Scenario: Verify the label of Tasks in Landing Page
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    HomePage Screen should display the label Tasks for tasksMenu

  @OM-211
  Scenario: Verify the label of Pending Task
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on Tasks Section in app
    Then   Pending Tasks should open by default
    And    PendingTasks Screen should display the label Tasks for taskLabel
    And    Click on Home icon from Header

  @OM-370
  Scenario: Create Task from Landing screen
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    click on Surveys option from the Home screen
    Then   verify Burger menu icon should be present in Surveys screen
    Then   verify Home icon should be present in Surveys screen

  @OM-211
  Scenario: Verify the label of Completed Task
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on Tasks Section in app
    Then   Click on Completed Tab
    And    CompletedTasks Screen should display the label Tasks for taskLabel
    Then   Click on Pending Tab
    And    Click on Home icon from Header

  @OM-17  @OM-280
  Scenario Outline: Create New Task from Pending Task screen
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on Tasks Section in app
    And    Click on Add Task Button
    Then   Provide task data as <task>
    And    Click Save Button
    And    Newly created task <task> should present in Pending Tasks list
    And    Click on Home icon from Header
    Examples:
      | task                    |
      | Automation Created Task |

  @OM-17 @OM-280
  Scenario Outline: Edit Task Name from Pending Task
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on Tasks Section in app
    And    Click on Add Task Button
    Then   Provide task data as <task>
    And    Click Save Button
    And    Click on task <task>
    Then   Clear task name
    Then   Edit task as <edit>
    And    Click Save Button
    And    Click on Home icon from Header
    Examples:
      | task                    | edit                   |
      | Automation Created Task | Automation Task Edited |

  @OM-280 @OM-17
  Scenario Outline: Edit task and provide date
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on Tasks Section in app
    And    Click on Add Task Button
    Then   Provide task data as <task>
    And    Click Save Button
    And    Click on task <task>
    And    Provide task date as <date>
    And    Click Save Button
    And    Click on Home icon from Header
    Examples:
      | task                    | date       |
      | Automation Created Task | 2018.06.29 |

  @OM-17
  Scenario Outline: Create task with Task name and Date
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on Tasks Section in app
    And    Click on Add Task Button
    And    Provide task date as <date>
    And    Provide task data as <task>
    Then   Click Save Button
    And    Click on Home icon from Header
    Examples:
      | task                    | date       |
      | Automation Task Created | 2018.05.31 |

  @OM-211 @OM-17
  Scenario Outline: Move pending task to Completed screen
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on Tasks Section in app
    And    Click on Add Task Button
    And    Provide task date as <date>
    Then   Provide task data as <task>
    And    Click Save Button
    And    Check the checkbox to move <task> to completed screen
    And    Click on Home icon from Header
    Examples:
      | task                   | date       |
      | Automation Task Edited | 2018.05.31 |

  @OM-211 @OM-17
  Scenario Outline: Verify the task is moved to Completed screen or not
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on Tasks Section in app
    And    Click on Add Task Button
    And    Provide task date as <date>
    Then   Provide task data as <task>
    And    Click Save Button
    And    Check the checkbox to move <task> to completed screen
    Then   Click on Completed Tab
    And    Verify <task> moved to completed screen or not
    Then   Click on Pending Tab
    And    Click on Home icon from Header
    Examples:
      | task                   | date       |
      | Automation Task Edited | 2018.05.31 |

  @OM-211 @OM-17
  Scenario Outline: Move task from Completed screen to Pending screen
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on Tasks Section in app
    Then   Click on Pending Tab
    And    Click on Add Task Button
    And    Provide task date as <date>
    Then   Provide task data as <task>
    And    Click Save Button
    And    Check the checkbox to move <task> to completed screen
    Then   Click on Completed Tab
    And    Verify <task> moved to completed screen or not
    And    Check the checkbox to move <task> to pending screen
    Then   Click on Pending Tab
    And    Verify <task> moved to Pending screen or not
    And    Click on Home icon from Header
    Examples:
      | task                   | date       |
      | Automation Task Edited | 2018.05.28 |

  @OM-161 @OM-14 @OM-17
  Scenario Outline: Create Task from Landing screen
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Swipe on Tasks Menu option
    Then   Click on Add Task Icon
    And    Provide task date as <date>
    Then   Provide task data as <task>
    And    Click Save Button
    Then   Verify add task button should not be present
    Examples:
      | task                    | date       |
      | Automation Created Task | 2018.09.28 |

  @OM-293
  Scenario Outline: dirty check on new message task screen, Accept
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on Tasks Section in app
    And    Click on Add Task Button
    Then   Provide task data as <task>
    And    Click App Back Button
    And    Click on Yes button associated with the aleart Message
    Then   PendingTasks Screen should display the label Tasks for taskLabel
    And    Click on Home icon from Header

    Examples:
      | task                    |
      | Automation Created Task |

  @OM-293
  Scenario Outline: dirty check on new message task screen, Reject
    Given  Launch the Oncomate App
    When   Provide Security Pin as 0000
    And    Click on Tasks Section in app
    And    Click on Add Task Button
    Then   Provide task data as <task>
    And    Click App Back Button
    And    Click on No button associated with the aleart Message
    Then   NewTodo Screen should display the label New Task for newTask
    And    Click App Back Button
    And    Click on Yes button associated with the aleart Message
    And    Click on Home icon from Header
    Examples:
      | task                    |
      | Automation Created Task |