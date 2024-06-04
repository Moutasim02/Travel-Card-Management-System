package com.demo.travelcardsystem.entity;

import com.demo.travelcardsystem.service.util.FareCalculator;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
@AllArgsConstructor
@Component
public class TravelCardObserver implements Observer<TravelCard>{

    private FareCalculator fareCalculator;

    @Override
    public void reactOnChange(TravelCard travelCard) {
        Journey journey =  travelCard.getCurrentJourney();

        //When journey is completed then add max charge back and debit chargeable fare
        if(journey.isJourneyCompleted()) {
            travelCard.addCredit(fareCalculator.getTravelStrategy().getRuleCollection().getMaxFare());
            debitChargeableFare(travelCard);
        } else {
            // If journey starts the charge max amount
            travelCard.debitAmount(fareCalculator.getTravelStrategy().getRuleCollection().getMaxFare());
        }
    }

    private void debitChargeableFare(TravelCard card) {
        Double maxFare = fareCalculator.getTravelStrategy().getRuleCollection().getMaxFare();

        Double fareAmount = Optional.of(fareCalculator.calculate(card.getCurrentJourney())).orElse(maxFare);
        // Fare Amount is either the calculated fareAmount or the max Fare
        card.debitAmount(fareAmount);
    }
}
