package com.avans.rentmycar.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class RdwResponseItem(
    @Json(name = "aantal_cilinders") val aantalCilinders: String, // 3
    @Json(name = "aantal_deuren") val aantalDeuren: String, // 5
    @Json(name = "aantal_rolstoelplaatsen") val aantalRolstoelplaatsen: String, // 0
    @Json(name = "aantal_wielen") val aantalWielen: String, // 4
    @Json(name = "aantal_zitplaatsen") val aantalZitplaatsen: String, // 5
    @Json(name = "afstand_hart_koppeling_tot_achterzijde_voertuig") val afstandHartKoppelingTotAchterzijdeVoertuig: String, // 0
    @Json(name = "afstand_voorzijde_voertuig_tot_hart_koppeling") val afstandVoorzijdeVoertuigTotHartKoppeling: String, // 0
    @Json(name = "api_gekentekende_voertuigen_assen") val apiGekentekendeVoertuigenAssen: String, // https://opendata.rdw.nl/resource/3huj-srit.json
    @Json(name = "api_gekentekende_voertuigen_brandstof") val apiGekentekendeVoertuigenBrandstof: String, // https://opendata.rdw.nl/resource/8ys7-d773.json
    @Json(name = "api_gekentekende_voertuigen_carrosserie") val apiGekentekendeVoertuigenCarrosserie: String, // https://opendata.rdw.nl/resource/vezc-m2t6.json
    @Json(name = "api_gekentekende_voertuigen_carrosserie_specifiek") val apiGekentekendeVoertuigenCarrosserieSpecifiek: String, // https://opendata.rdw.nl/resource/jhie-znh9.json
    @Json(name = "api_gekentekende_voertuigen_voertuigklasse") val apiGekentekendeVoertuigenVoertuigklasse: String, // https://opendata.rdw.nl/resource/kmfi-hrps.json
    @Json(name = "breedte") val breedte: String, // 0
    @Json(name = "bruto_bpm") val brutoBpm: String, // 2530
    @Json(name = "catalogusprijs") val catalogusprijs: String, // 20990
    @Json(name = "cilinderinhoud") val cilinderinhoud: String, // 998
    @Json(name = "code_toelichting_tellerstandoordeel") val codeToelichtingTellerstandoordeel: String, // 00
    @Json(name = "datum_eerste_tenaamstelling_in_nederland") val datumEersteTenaamstellingInNederland: String, // 20151027
    @Json(name = "datum_eerste_tenaamstelling_in_nederland_dt") val datumEersteTenaamstellingInNederlandDt: String, // 2015-10-27T00:00:00.000
    @Json(name = "datum_eerste_toelating") val datumEersteToelating: String, // 20151027
    @Json(name = "datum_eerste_toelating_dt") val datumEersteToelatingDt: String, // 2015-10-27T00:00:00.000
    @Json(name = "datum_tenaamstelling") val datumTenaamstelling: String, // 20220308
    @Json(name = "datum_tenaamstelling_dt") val datumTenaamstellingDt: String, // 2022-03-08T00:00:00.000
    @Json(name = "eerste_kleur") val eersteKleur: String, // ZWART
    @Json(name = "europese_voertuigcategorie") val europeseVoertuigcategorie: String, // M1
    @Json(name = "export_indicator") val exportIndicator: String, // Nee
    @Json(name = "handelsbenaming") val handelsbenaming: String, // CEE D
    @Json(name = "inrichting") val inrichting: String, // hatchback
    @Json(name = "jaar_laatste_registratie_tellerstand") val jaarLaatsteRegistratieTellerstand: String, // 2022
    @Json(name = "kenteken") val kenteken: String, // HF067X
    @Json(name = "lengte") val lengte: String, // 431
    @Json(name = "massa_ledig_voertuig") val massaLedigVoertuig: String, // 1179
    @Json(name = "massa_rijklaar") val massaRijklaar: String, // 1279
    @Json(name = "maximum_massa_samenstelling") val maximumMassaSamenstelling: String, // 2820
    @Json(name = "maximum_massa_trekken_ongeremd") val maximumMassaTrekkenOngeremd: String, // 500
    @Json(name = "maximum_trekken_massa_geremd") val maximumTrekkenMassaGeremd: String, // 1000
    @Json(name = "merk") val merk: String, // KIA
    @Json(name = "openstaande_terugroepactie_indicator") val openstaandeTerugroepactieIndicator: String, // Nee
    @Json(name = "plaats_chassisnummer") val plaatsChassisnummer: String, // r. dwarsbalk by voorzitting
    @Json(name = "taxi_indicator") val taxiIndicator: String, // Nee
    @Json(name = "technische_max_massa_voertuig") val technischeMaxMassaVoertuig: String, // 1820
    @Json(name = "tellerstandoordeel") val tellerstandoordeel: String, // Logisch
    @Json(name = "tenaamstellen_mogelijk") val tenaamstellenMogelijk: String, // Ja
    @Json(name = "toegestane_maximum_massa_voertuig") val toegestaneMaximumMassaVoertuig: String, // 1820
    @Json(name = "tweede_kleur") val tweedeKleur: String, // Niet geregistreerd
    @Json(name = "type") val type: String, // JD
    @Json(name = "typegoedkeuringsnummer") val typegoedkeuringsnummer: String, // e4*2007/46*0496*09
    @Json(name = "uitvoering") val uitvoering: String, // M6BAZ1
    @Json(name = "variant") val variant: String, // B5PH1
    @Json(name = "vermogen_massarijklaar") val vermogenMassarijklaar: String, // 0.06
    @Json(name = "vervaldatum_apk") val vervaldatumApk: String, // 20240308
    @Json(name = "vervaldatum_apk_dt") val vervaldatumApkDt: String, // 2024-03-08T00:00:00.000
    @Json(name = "voertuigsoort") val voertuigsoort: String, // Personenauto
    @Json(name = "volgnummer_wijziging_eu_typegoedkeuring") val volgnummerWijzigingEuTypegoedkeuring: String, // 0
    @Json(name = "wacht_op_keuren") val wachtOpKeuren: String, // Geen verstrekking in Open Data
    @Json(name = "wam_verzekerd") val wamVerzekerd: String, // Ja
    @Json(name = "wielbasis") val wielbasis: String, // 265
    @Json(name = "zuinigheidsclassificatie") val zuinigheidsclassificatie: String // A
)