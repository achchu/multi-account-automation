Feature: Login
  Validation of the login feature for perkbox

  Scenario: Verify if all available accounts are visible when signing in with correct email address
    Given I am on perkbox login page
    When I enter valid email
    And I click on continue
    Then I should see all the accounts available to me

  Scenario: Verify that the user is redirected to the correct account page when clicking on an account name
    Given I am on perkbox login page
    And I enter valid email
    And I click on continue
    And I click on an account name
    When I click on confirm
    Then I should see the email prepopulated in the login page