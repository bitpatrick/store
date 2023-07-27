package it.bitprojects.store.dto;

import javax.money.CurrencyUnit;

public record BalanceDto(double tokenQty,CurrencyUnit currency,double currencyQty) {
}