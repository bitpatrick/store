package it.bitprojects.store.dto;

import javax.money.CurrencyUnit;
import javax.money.NumberValue;

public record BalanceDto(NumberValue tokenQty,CurrencyUnit currency,NumberValue currencyQty) {
}