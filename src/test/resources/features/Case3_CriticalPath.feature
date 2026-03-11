@CriticalPath
Feature: Critical Path - Flight Booking Flow

  Scenario Outline: Validate end-to-end flight booking flow until payment page
    Given the user navigates to the Enuygun homepage
    When the user searches for a round-trip flight from "<departure_city>" to "<arrival_city>" departing on "<departure_date>" and returning on "<return_date>"
    And the user selects the first available flight
    And the user fills in the passenger information using keys:
      | email           | phone           | firstName           | lastName           | tcNo           |
      | passenger_email | passenger_phone | passenger_firstName | passenger_lastName | passenger_tcNo |
    And the user proceeds to the payment page
    Then verify that the payment page is displayed

    Examples:
      | departure_city | arrival_city | departure_date | return_date |
      | İstanbul       | Ankara       | 2026-03-20     | 2026-03-25  |