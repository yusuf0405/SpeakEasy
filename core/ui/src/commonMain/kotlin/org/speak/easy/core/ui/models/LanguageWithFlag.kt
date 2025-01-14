package org.speak.easy.core.ui.models

import org.jetbrains.compose.resources.DrawableResource
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.bulgaria
import speakeasy.core.ui.generated.resources.china
import speakeasy.core.ui.generated.resources.czech_republic
import speakeasy.core.ui.generated.resources.denmark
import speakeasy.core.ui.generated.resources.estonia
import speakeasy.core.ui.generated.resources.finland
import speakeasy.core.ui.generated.resources.france
import speakeasy.core.ui.generated.resources.hungary
import speakeasy.core.ui.generated.resources.indonesia
import speakeasy.core.ui.generated.resources.italy
import speakeasy.core.ui.generated.resources.spain
import speakeasy.core.ui.generated.resources.england
import speakeasy.core.ui.generated.resources.germany
import speakeasy.core.ui.generated.resources.greece
import speakeasy.core.ui.generated.resources.japan
import speakeasy.core.ui.generated.resources.south_korea
import speakeasy.core.ui.generated.resources.lithuania
import speakeasy.core.ui.generated.resources.norway
import speakeasy.core.ui.generated.resources.latvia
import speakeasy.core.ui.generated.resources.netherlands
import speakeasy.core.ui.generated.resources.poland
import speakeasy.core.ui.generated.resources.portugal
import speakeasy.core.ui.generated.resources.romania
import speakeasy.core.ui.generated.resources.russia
import speakeasy.core.ui.generated.resources.slovakia
import speakeasy.core.ui.generated.resources.slovenia
import speakeasy.core.ui.generated.resources.sweden
import speakeasy.core.ui.generated.resources.turkey
import speakeasy.core.ui.generated.resources.ukraine

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