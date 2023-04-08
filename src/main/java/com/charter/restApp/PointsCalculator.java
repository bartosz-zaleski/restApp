package com.charter.restApp;

public class PointsCalculator {

    public static int calculate(int cents) {

        // Round down the "cents" part to 0

        float dollars = cents / 100;
        cents = ((int) dollars) * 100;

        int pointsOverHundred = 0;
        int pointsOverFifty = 0;

        // forcibly round down to

        // 2 points for every dollar over $100
        if(cents >= 10000) {
            pointsOverHundred += (cents - 10000) * 2 / 100;
        }

        // 1 point for every dollar over $50 and below $100
        if(cents >= 5000) {
            pointsOverFifty += (cents - 5000) / 100;
            if(pointsOverFifty > 50) {
                pointsOverFifty = 50;
            }
        }

        return(pointsOverFifty + pointsOverHundred);
    }

    public static int dollarsToCentsRoundDownCentsToZero(float dollars) {
        // This method takes "human" price and converts it to cents.
        // Also the decimal point of the price is rounded down to 0

        return(((int) dollars) * 100);
    }
}
