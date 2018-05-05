package compiler;

public interface Constants extends ScannerConstants, ParserConstants
{
    int EPSILON  = 0;
    int DOLLAR   = 1;

    int t_numbers = 2;
    int t_variable = 3;
    int t_binary = 4;
    int t_number_float = 5;
    int t_hexa_decima = 6;
    int t_int = 7;
    int t_float = 8;
    int t_string = 9;
    int t_char = 10;
    int t_byte = 11;
    int t_unsigned = 12;
    int t_long = 13;
    int t_bool = 14;
    int t_void = 15;
    int t_do = 16;
    int t_if = 17;
    int t_else = 18;
    int t_else_if = 19;
    int t_while = 20;
    int t_for = 21;
    int t_return = 22;
    int t_break = 23;
    int t_switch = 24;
    int t_case = 25;
    int t_TOKEN_26 = 26; //"+"
    int t_TOKEN_27 = 27; //"-"
    int t_TOKEN_28 = 28; //"*"
    int t_TOKEN_29 = 29; //"/"
    int t_TOKEN_30 = 30; //"%"
    int t_TOKEN_31 = 31; //">"
    int t_TOKEN_32 = 32; //"<"
    int t_TOKEN_33 = 33; //">="
    int t_TOKEN_34 = 34; //"<="
    int t_TOKEN_35 = 35; //"="
    int t_TOKEN_36 = 36; //"!="
    int t_TOKEN_37 = 37; //"&&"
    int t_TOKEN_38 = 38; //"||"
    int t_TOKEN_39 = 39; //"!"
    int t_TOKEN_40 = 40; //"~"
    int t_TOKEN_41 = 41; //">>"
    int t_TOKEN_42 = 42; //"<<"
    int t_TOKEN_43 = 43; //"^"
    int t_TOKEN_44 = 44; //"|"
    int t_TOKEN_45 = 45; //"&"
    int t_TOKEN_46 = 46; //"("
    int t_TOKEN_47 = 47; //")"
    int t_TOKEN_48 = 48; //"{"
    int t_TOKEN_49 = 49; //"}"
    int t_TOKEN_50 = 50; //"["
    int t_TOKEN_51 = 51; //"]"
    int t_TOKEN_52 = 52; //";"
    int t_TOKEN_53 = 53; //","
    int t_TOKEN_54 = 54; //"."
    int t_TOKEN_55 = 55; //"=>"
    int t_TOKEN_56 = 56; //":"
    int t_TOKEN_57 = 57; //"->"
    int t_TOKEN_58 = 58; //"++"
    int t_TOKEN_59 = 59; //"--"
    int t_TOKEN_60 = 60; //"'"
    int t_end_line = 61;
    int t_echo = 62;
    int t_put = 63;
    int t_comment_line = 64;
    int t_comment_multi_lines = 65;
    int t_w = 66;

}
