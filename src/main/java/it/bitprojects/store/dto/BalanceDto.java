package it.bitprojects.store.dto;

import it.bitprojects.store.model.Currency;

public record BalanceDto(Currency currency, double quantity) {

}