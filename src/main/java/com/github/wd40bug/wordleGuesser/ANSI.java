package com.github.wd40bug.wordleGuesser;

public class ANSI {
    @SuppressWarnings("ALL")

    public static final String Reset = "\u001b[0m";
    public static final String Red = "\u001b[31m";
    public static final String Black = "\u001b[30m";
    public static final String Green = "\u001b[32m";
    public static final String Yellow = "\u001b[33m";
    public static final String Blue = "\u001b[34m";
    public static final String Magenta = "\u001b[35m";
    public static final String Cyan = "\u001b[36m";
    public static final String White = "\u001b[37m";

    public static final String Bright_Red = "\u001b[31;1m";
    public static final String Bright_Black = "\u001b[30;1m";
    public static final String Bright_Green = "\u001b[32;1m";
    public static final String Bright_Yellow = "\u001b[33;1m";
    public static final String Bright_Blue = "\u001b[34;1m";
    public static final String Bright_Magenta = "\u001b[35;1m";
    public static final String Bright_Cyan = "\u001b[36;1m";
    public static final String Bright_White = "\u001b[37;1m";

    public static final String Background_Red = "\u001b[41m";
    public static final String Background_Black = "\u001b[40m";
    public static final String Background_Green = "\u001b[42m";
    public static final String Background_Yellow = "\u001b[43m";
    public static final String Background_Blue = "\u001b[44m";
    public static final String Background_Magenta = "\u001b[45m";
    public static final String Background_Cyan = "\u001b[46m";
    public static final String Background_White = "\u001b[47m";

    public static final String Bright_Background_Red = "\u001b[41m";
    public static final String Bright_Background_Black = "\u001b[40m";
    public static final String Bright_Background_Green = "\u001b[42m";
    public static final String Bright_Background_Yellow = "\u001b[43m";
    public static final String Bright_Background_Blue = "\u001b[44m";
    public static final String Bright_Background_Magenta = "\u001b[45m";
    public static final String Bright_Background_Cyan = "\u001b[46m";
    public static final String Bright_Background_White = "\u001b[47m";

    public static final String Bold = "\u001b[1m";
    public static final String Underlined = "\u001b[4m";
    public static final String Invert_Color = "\u001b[7m";

    public static final String Cursor_Up = "\u001b[{n}A";
    public static final String Cursor_Down = "\u001b[{n}B";
    public static final String Cursor_Left = "\u001b[{n}C";
    public static final String Cursor_Right = "\u001b[{n}D";

    public static final String Clear_Screen = "\u001b[{2}J";
    public static final String Clear_Line = "\u001b[{2}K";

}
