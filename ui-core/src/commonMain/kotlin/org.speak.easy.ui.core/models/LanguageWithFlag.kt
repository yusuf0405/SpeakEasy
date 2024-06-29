package org.speak.easy.ui.core.models

import org.jetbrains.compose.resources.DrawableResource
import speakeasy.ui_core.generated.resources.Res
import speakeasy.ui_core.generated.resources.bulgaria
import speakeasy.ui_core.generated.resources.china
import speakeasy.ui_core.generated.resources.czech_republic
import speakeasy.ui_core.generated.resources.denmark
import speakeasy.ui_core.generated.resources.estonia
import speakeasy.ui_core.generated.resources.finland
import speakeasy.ui_core.generated.resources.france
import speakeasy.ui_core.generated.resources.hungary
import speakeasy.ui_core.generated.resources.indonesia
import speakeasy.ui_core.generated.resources.italy
import speakeasy.ui_core.generated.resources.spain
import speakeasy.ui_core.generated.resources.england
import speakeasy.ui_core.generated.resources.germany
import speakeasy.ui_core.generated.resources.greece
import speakeasy.ui_core.generated.resources.japan
import speakeasy.ui_core.generated.resources.south_korea
import speakeasy.ui_core.generated.resources.lithuania
import speakeasy.ui_core.generated.resources.norway
import speakeasy.ui_core.generated.resources.latvia
import speakeasy.ui_core.generated.resources.netherlands
import speakeasy.ui_core.generated.resources.poland
import speakeasy.ui_core.generated.resources.portugal
import speakeasy.ui_core.generated.resources.romania
import speakeasy.ui_core.generated.resources.russia
import speakeasy.ui_core.generated.resources.slovakia
import speakeasy.ui_core.generated.resources.slovenia
import speakeasy.ui_core.generated.resources.sweden
import speakeasy.ui_core.generated.resources.turkey
import speakeasy.ui_core.generated.resources.ukraine

enum class LanguageWithFlag(
    val languageCode: String,
    val flagRes: DrawableResource
) {
    UKRAINIAN("UK", Res.drawable.ukraine),
    TURKISH("TR", Res.drawable.turkey),
    SWEDISH("SV", Res.drawable.sweden),
    SLOVENIAN("SL", Res.drawable.slovenia),
    SLOVAK("SK", Res.drawable.slovakia),
    RUSSIAN("RU", Res.drawable.russia),
    ROMANIAN("RO", Res.drawable.romania),
    PORTUGUESE("PT", Res.drawable.portugal),
    POLISH("PL", Res.drawable.poland),
    DUTCH("NB", Res.drawable.netherlands),
    NORWEGIAN("NL", Res.drawable.norway),
    LATVIAN("LV", Res.drawable.latvia),
    LITHUANIAN("LT", Res.drawable.lithuania),
    KOREAN("KO", Res.drawable.south_korea),
    ITALIAN("IT", Res.drawable.italy),
    JAPANESE("JA", Res.drawable.japan),
    HUNGARIAN("HU", Res.drawable.hungary),
    INDONESIAN("ID", Res.drawable.indonesia),
    FRENCH("FR", Res.drawable.france),
    FINNISH("FI", Res.drawable.finland),
    ESTONIAN("ET", Res.drawable.estonia),
    SPANISH("ES", Res.drawable.spain),
    ENGLAND("EN", Res.drawable.england),
    GREEK("EL", Res.drawable.greece),
    GERMAN("DE", Res.drawable.germany),
    DENMARK("DA", Res.drawable.denmark),
    CZECH("CS", Res.drawable.czech_republic),
    CHINA("ZH", Res.drawable.china),
    BULGARIAN("BG", Res.drawable.bulgaria);

    companion object {

        fun find(code: String) = entries.find { it.languageCode == code.uppercase() }?.flagRes
    }
}