package com.viber.bot.data.model;

public class KeyboardButtons {
    
    private KeyboardButtons() {}
    
    public static final String HELP_BUTTON = "{"
            + "\"Type\": \"keyboard\","
            + "\"Buttons\": [ "
            + "{"
            + "\"Columns\": 2,"
            + "\"Rows\": 2,"
            + "\"BgColor\": \"#f9ede9\","
            + "\"Text\": \"Привіт\","
            + "\"TextSize\": \"large\","
            + "\"TextHAlign\": \"center\","
            + "\"TextVAlign\": \"middle\","
            + "\"ActionType\": \"reply\","
            + "\"ActionBody\": \"Привіт\""
            + "}, {"
            + "\"Columns\": 2,"
            + "\"Rows\": 2,"
            + "\"BgColor\": \"#f9ede9\","
            + "\"Text\": \"Каруселі\","
            + "\"TextSize\": \"large\","
            + "\"TextHAlign\": \"center\","
            + "\"TextVAlign\": \"middle\","
            + "\"ActionType\": \"reply\","
            + "\"ActionBody\": \"Каруселі\""
            + "}, {"
            + "\"Columns\": 2,"
            + "\"Rows\": 2,"
            + "\"BgColor\": \"#f9ede9\","
            + "\"Text\": \"Стикер\","
            + "\"TextSize\": \"large\","
            + "\"TextHAlign\": \"center\","
            + "\"ActionType\": \"reply\","
            + "\"ActionBody\": \"Стикер\""
            + "} ] }";
}
