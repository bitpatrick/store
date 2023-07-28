package it.bitprojects.store.model;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

import org.javamoney.moneta.Money;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Balance {

	private final int TOKEN_EUR_PRICE = 1;

	private NumberValue tokenQty;
	private CurrencyUnit currency;
	private NumberValue currencyQty;

	private ExchangeRateProvider provider = MonetaryConversions.getExchangeRateProvider("ECB");

	/**
	 * conversione da quantità token a valuta del pasese corrente
	 * 
	 * @return
	 */
	public void calculateCurrencyQty() {

		if (currency.getCurrencyCode().equals("EUR")) {
			this.currencyQty = tokenQty;
			return;
		}

		// Crea un importo monetario in EURO
		MonetaryAmount moneyInEUR = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(tokenQty).create();;

		// Ottieni la conversione per la nostra currency
		CurrencyConversion conversion = getConversion();

		// Converte l'importo per la nostra currency
		MonetaryAmount moneyInCurrency = moneyInEUR.with(conversion);

		this.currencyQty = moneyInCurrency.getNumber();
	}


	private CurrencyConversion getConversion() {
		return provider.getCurrencyConversion(this.currency);
	}

	public NumberValue getTokensPrice(double tokenQty) {

		MonetaryAmount monetaryInEUR = Money.of(tokenQty, "EUR");
		
		CurrencyConversion conversion = getConversion();
		return monetaryInEUR.with(conversion).getNumber();
	}

}