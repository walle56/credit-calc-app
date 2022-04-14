package com.walle.credit.calc.model;

import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue
    private Long Id;

    private String name;
    private String isin;
    private String headquarter;

    // this will create separate table bank_to_credit_offer for link bank entity with credit offers as OneToMany
    @ElementCollection
    @CollectionTable(name = "bank_to_credit_offer")
    private Map<String, Double> creditOffers;

}
