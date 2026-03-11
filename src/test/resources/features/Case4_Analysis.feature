@AnalysisPath
Feature: Flight Data Extraction and Analysis

  Scenario Outline: Extract flights and analyze data for route <Departure> to <Destination>
    Given the user navigates to the Enuygun homepage
    When the user searches for a round-trip flight from "<Departure>" to "<Destination>" departing on "<StartDate>" and returning on "<EndDate>"
    And the user extracts all flight data and saves it to a CSV file
    Then the user analyzes the data for minimum, maximum, and average prices

    Examples:
      | Departure | Destination | StartDate  | EndDate    |
      | İstanbul  | Lefkoşa     | 2026-03-20 | 2026-03-25 |