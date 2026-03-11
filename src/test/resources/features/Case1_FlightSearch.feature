@FlightSearch
Feature: Flight Search and Time Filtering Validation

  Scenario Outline: Verify flight search functionality with time filtering
    Given the user navigates to the Enuygun homepage
    When the user searches for a round-trip flight from "<departure_city>" to "<arrival_city>" departing on "<departure_date>" and returning on "<return_date>"
    And the user applies the departure time filter "<timeRange>" on the flight listing page
    Then verify that all displayed flights have departure times within 10 and 18
    And verify the search results match the selected route "<departure_city>" to "<arrival_city>"

    Examples:
      | departure_city | arrival_city | departure_date | return_date | timeRange     |
      | İstanbul       | Ankara       | 2026-03-20     | 2026-03-25  | 10:00 - 18:00 |