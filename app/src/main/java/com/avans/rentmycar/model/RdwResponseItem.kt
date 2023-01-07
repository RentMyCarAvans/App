package com.avans.rentmycar.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class RdwResponseItem(
    @Json(name = "aantal_cilinders") val aantalCilinders: String?,
    @Json(name = "aantal_deuren") val aantalDeuren: String?,
    @Json(name = "aantal_rolstoelplaatsen") val aantalRolstoelplaatsen: String?,
    @Json(name = "aantal_wielen") val aantalWielen: String?,
    @Json(name = "aantal_zitplaatsen") val aantalZitplaatsen: String?,
    @Json(name = "afstand_hart_koppeling_tot_achterzijde_voertuig") val afstandHartKoppelingTotAchterzijdeVoertuig: String?,
    @Json(name = "afstand_voorzijde_voertuig_tot_hart_koppeling") val afstandVoorzijdeVoertuigTotHartKoppeling: String?,
    @Json(name = "api_gekentekende_voertuigen_assen") val apiGekentekendeVoertuigenAssen: String?, // https://opendata.rdw.nl/resource/3huj-srit.json
    @Json(name = "api_gekentekende_voertuigen_brandstof") val apiGekentekendeVoertuigenBrandstof: String?, // https://opendata.rdw.nl/resource/8ys7-d773.json
    @Json(name = "api_gekentekende_voertuigen_carrosserie") val apiGekentekendeVoertuigenCarrosserie: String?, // https://opendata.rdw.nl/resource/vezc-m2t6.json
    @Json(name = "api_gekentekende_voertuigen_carrosserie_specifiek") val apiGekentekendeVoertuigenCarrosserieSpecifiek: String?, // https://opendata.rdw.nl/resource/jhie-znh9.json
    @Json(name = "api_gekentekende_voertuigen_voertuigklasse") val apiGekentekendeVoertuigenVoertuigklasse: String?, // https://opendata.rdw.nl/resource/kmfi-hrps.json
    @Json(name = "breedte") val breedte: String?,
    @Json(name = "bruto_bpm") val brutoBpm: String?,
    @Json(name = "catalogusprijs") val catalogusprijs: String?,
    @Json(name = "cilinderinhoud") val cilinderinhoud: String?,
    @Json(name = "code_toelichting_tellerstandoordeel") val codeToelichtingTellerstandoordeel: String?,
    @Json(name = "datum_eerste_tenaamstelling_in_nederland") val datumEersteTenaamstellingInNederland: String?,
    @Json(name = "datum_eerste_tenaamstelling_in_nederland_dt") val datumEersteTenaamstellingInNederlandDt: String?,
    @Json(name = "datum_eerste_toelating") val datumEersteToelating: String?,
    @Json(name = "datum_eerste_toelating_dt") val datumEersteToelatingDt: String?,
    @Json(name = "datum_tenaamstelling") val datumTenaamstelling: String?,
    @Json(name = "datum_tenaamstelling_dt") val datumTenaamstellingDt: String?,
    @Json(name = "eerste_kleur") val eersteKleur: String?,
    @Json(name = "europese_voertuigcategorie") val europeseVoertuigcategorie: String?,
    @Json(name = "export_indicator") val exportIndicator: String?,
    @Json(name = "handelsbenaming") val handelsbenaming: String?,
    @Json(name = "inrichting") val inrichting: String?,
    @Json(name = "jaar_laatste_registratie_tellerstand") val jaarLaatsteRegistratieTellerstand: String?,
    @Json(name = "kenteken") val kenteken: String?,
    @Json(name = "lengte") val lengte: String?,
    @Json(name = "massa_ledig_voertuig") val massaLedigVoertuig: String?,
    @Json(name = "massa_rijklaar") val massaRijklaar: String?,
    @Json(name = "maximum_massa_samenstelling") val maximumMassaSamenstelling: String?,
    @Json(name = "maximum_massa_trekken_ongeremd") val maximumMassaTrekkenOngeremd: String?,
    @Json(name = "maximum_trekken_massa_geremd") val maximumTrekkenMassaGeremd: String?,
    @Json(name = "merk") val merk: String?,
    @Json(name = "openstaande_terugroepactie_indicator") val openstaandeTerugroepactieIndicator: String?,
    @Json(name = "plaats_chassisnummer") val plaatsChassisnummer: String?,
    @Json(name = "taxi_indicator") val taxiIndicator: String?, //
    @Json(name = "technische_max_massa_voertuig") val technischeMaxMassaVoertuig: String?,
    @Json(name = "tellerstandoordeel") val tellerstandoordeel: String?,
    @Json(name = "tenaamstellen_mogelijk") val tenaamstellenMogelijk: String?,
    @Json(name = "toegestane_maximum_massa_voertuig") val toegestaneMaximumMassaVoertuig: String?,
    @Json(name = "tweede_kleur") val tweedeKleur: String?,
    @Json(name = "type") val type: String?,
    @Json(name = "typegoedkeuringsnummer") val typegoedkeuringsnummer: String?,
    @Json(name = "uitvoering") val uitvoering: String?,
    @Json(name = "variant") val variant: String?,
    @Json(name = "vermogen_massarijklaar") val vermogenMassarijklaar: String?,
    @Json(name = "vervaldatum_apk") val vervaldatumApk: String?,
    @Json(name = "vervaldatum_apk_dt") val vervaldatumApkDt: String?,
    @Json(name = "voertuigsoort") val voertuigsoort: String?,
    @Json(name = "volgnummer_wijziging_eu_typegoedkeuring") val volgnummerWijzigingEuTypegoedkeuring: String?,
    @Json(name = "wacht_op_keuren") val wachtOpKeuren: String?,
    @Json(name = "wam_verzekerd") val wamVerzekerd: String?,
    @Json(name = "wielbasis") val wielbasis: String?,
    @Json(name = "zuinigheidsclassificatie") val zuinigheidsclassificatie: String?
)