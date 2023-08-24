package it.bitprojects.store.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "stocks")
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
	  @OneToOne
	    @MapsId
	    @JoinColumn(name="id_product")
	    private Product product;

	    @Id
	    private Integer id_product;

	    private Integer qty;
}
