package com.inforart.assignment.items.AssignmentItem.outerservice.hnb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyExchangeDto {

    private String broj_tecajnice;
    private String datum_primjene;
    private String drzava;
    private String drzava_iso;
    private String sifra_valute;
    private String valuta;
    private String kupovni_tecaj;
    private String srednji_tecaj;
    private String prodajni_tecaj;

}
