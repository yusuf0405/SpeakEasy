package org.speak.easy.ui.core.theme.colors


val lightPalette = SpeakEasyColors(
    /** Text **/
    /** Text **/
    textHeadline = TextHeadline,
    textPrimary = TextPrimary,
    onTextPrimary = TextPrimaryDark,
    textSecondary = TextSecondary,
    textTertiary = TextTertiary,
    textInverted = TextInverted,
    textPositive = TextPositive,
    textNegative = TextNegative,
    textPrimaryLink = TextPrimaryLink,
    textPrimaryLinkInverted = TextPrimaryLinkInverted,
    textSecondaryLink = TextSecondaryLink,

    /** Background **/

    /** Background **/
    backgroundPrimary = BackgroundPrimary,
    onBackgroundPrimary = BackgroundPrimaryDark,
    backgroundPrimaryElevated = BackgroundPrimaryElevated,
    backgroundModal = BackgroundModal,
    backgroundStroke = BackgroundStroke,
    backgroundSecondary = BackgroundSecondary,
    backgroundSecondaryElevated = BackgroundSecondaryElevated,
    backgroundInverted = BackgroundInverted,
    backgroundOverlay = BackgroundOverlay,
    backgroundHover = BackgroundHover,
    backgroundNavbarIos = BackgroundNavbarIos,

    /** Icons **/

    /** Icons **/
    iconsPrimary = IconsPrimary,
    iconsSecondary = IconsSecondary,
    iconsTertiary = IconsTertiary,

    /** Controls **/

    /** Controls **/
    controlsPrimaryActive = ControlPrimaryActive,
    controlsSecondaryActive = ControlSecondaryActive,
    controlsTertiaryActive = ControlTertiaryActive,
    controlsInactive = ControlInactive,
    controlsAlternative = ControlAlternative,
    controlsActiveTabBar = ControlActiveTabbar,
    controlsInactiveTabBar = ControlInactiveTabbar,

    /** Accent **/

    /** Accent **/
    accentActive = AccentActive,
    accentPositive = AccentPositive,
    accentWarning = AccentWarning,
    accentNegative = AccentNegative,

    accentActiveInverted = AccentActiveInverted,
    accentPositiveInverted = AccentPositiveInverted,
    accentWarningInverted = AccentWarningInverted,
    accentNegativeInverted = AccentNegativeInverted,

    primary = Primary,

    isDark = false
)

// Не ограничивать видимость,
// т.к. предоставляем возможность расширения палитры
val darkPalette = SpeakEasyColors(
    textHeadline = TextHeadlineDark,
    textPrimary = TextPrimaryDark,
    onTextPrimary = TextPrimary,
    textSecondary = TextSecondaryDark,
    textTertiary = TextTertiaryDark,
    textInverted = TextInvertedDark,
    textPositive = TextPositiveDark,
    textNegative = TextNegativeDark,
    textPrimaryLink = TextPrimaryLinkDark,
    textPrimaryLinkInverted = TextPrimaryLinkInvertedDark,
    textSecondaryLink = TextSecondaryLinkDark,

    /** Background **/

    /** Background **/
    backgroundPrimary = BackgroundPrimaryDark,
    onBackgroundPrimary = BackgroundPrimary,
    backgroundPrimaryElevated = BackgroundPrimaryElevatedDark,
    backgroundModal = BackgroundModalDark,
    backgroundStroke = BackgroundStrokeDark,
    backgroundSecondary = BackgroundSecondaryDark,
    backgroundSecondaryElevated = BackgroundSecondaryElevatedDark,
    backgroundInverted = BackgroundInvertedDark,
    backgroundOverlay = BackgroundOverlayDark,
    backgroundHover = BackgroundHoverDark,
    backgroundNavbarIos = BackgroundNavbarIosDark,

    /** Icons **/

    /** Icons **/
    iconsPrimary = IconsPrimaryDark,
    iconsSecondary = IconsSecondaryDark,
    iconsTertiary = IconsTertiaryDark,

    /** Controls **/

    /** Controls **/
    controlsPrimaryActive = ControlPrimaryActiveDark,
    controlsSecondaryActive = ControlSecondaryActiveDark,
    controlsTertiaryActive = ControlTertiaryActiveDark,
    controlsInactive = ControlInactiveDark,
    controlsAlternative = ControlAlternativeDark,
    controlsActiveTabBar = ControlActiveTabbarDark,
    controlsInactiveTabBar = ControlInactiveTabbarDark,

    /** Accent **/

    /** Accent **/
    accentActive = AccentActiveDark,
    accentPositive = AccentPositiveDark,
    accentWarning = AccentWarningDark,
    accentNegative = AccentNegativeDark,

    accentActiveInverted = AccentActiveInvertedDark,
    accentPositiveInverted = AccentPositiveInvertedDark,
    accentWarningInverted = AccentWarningInvertedDark,
    accentNegativeInverted = AccentNegativeInvertedDark,

    primary = Primary,

    isDark = true
)