@AirlineSorting
Feature: Price Sorting for Specific Airline

  Scenario Outline: Validate price sorting for specific airlines
    Given the user navigates to the Enuygun homepage
    When the user searches for a round-trip flight from "<origin>" to "<destination>" departing on "<depDate>" and returning on "<retDate>"
    And the user applies the departure time filter "<timeRange>" on the flight listing page
    And the user selects "<airline>" from the airline filters
    Then verify that all displayed flights are "<airline>" flights
    And verify that flight prices are sorted in ascending order

    Examples:
      | origin   | destination | depDate    | retDate    | timeRange     | airline           |
      | İstanbul | Ankara      | 2026-03-20 | 2026-03-25 | 10:00 - 18:00 | Türk Hava Yolları |
    # | İzmir    | Antalya     | 2026-05-01 | 2026-05-05 | 10:00 - 18:00 | Pegasus           |