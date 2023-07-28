package it.bitprojects.store.model;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

import lombok.Data;

@Data
public class Balance {

	private final int TOKEN_EUR_PRICE = 1;

	private NumberValue tokenQty;
	private CurrencyUnit currency;
//	private NumberValue currencyQty;
	
	
	public Balance(NumberValue tokenQty, CurrencyUnit currency) {
		this.tokenQty = tokenQty;
		this.currency = currency;
		
//		this.currencyQty=calculateCurrencyQty();
	}


	private NumberValue calculateCurrencyQty() {
		
		if(currency.getCurrencyCode().equals("EUR")){
			return this.tokenQty;
		}
		
		// Crea un provider di tassi di cambio
        ExchangeRateProvider provider = MonetaryConversions.getExchangeRateProvider("ECB");

        // Crea un importo monetario in dollari USA
        MonetaryAmount moneyInEUR = Monetary.getDefaultAmountFactory()
                .setCurrency("EUR").setNumber(tokenQty).create();

        // Ottieni la conversione per la nostra currency
        CurrencyConversion conversion = provider.getCurrencyConversion(this.currency);

        // Converte l'importo per la nostra currency
        MonetaryAmount moneyInCurrency= moneyInEUR.with(conversion);
        
        return moneyInCurrency.getNumber();
	}
}