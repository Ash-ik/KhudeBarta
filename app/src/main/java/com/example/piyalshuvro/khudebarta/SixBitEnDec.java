package com.example.piyalshuvro.khudebarta;
public class SixBitEnDec {

    final static public int SIX_BIT = 6;
    final static public int FIVE_BIT = 5;
    public SixBitEnDec() {
    }
    byte[] encode(String txt, int bit){

        txt = wordencode(txt);

        int length = txt.length();
        float tmpRet1=0,tmpRet2=0;
        if(bit==7){
            tmpRet1=7.0f;
            tmpRet2=8.0f;
        }
        else if(bit==8)
        {
            tmpRet1=8.0f;
            tmpRet2=8.0f;
        }
        byte encoded[]=new byte[(int)(tmpRet1*Math.ceil(length/tmpRet2))];
        char str[]=new char[length];
        txt.getChars(0,length,str,0);
        int chaVal = 0;
        String temp;
        String strBinary = new String("");
        for (int i = 0;i<length; i++){
            temp = Integer.toBinaryString(toValue(str[i]));
            while(temp.length()%bit != 0){
                temp="0"+temp;
            }
            strBinary=strBinary+temp;
        }
        while(strBinary.length()%8 != 0){
            strBinary=strBinary+"0";
        }
        Integer tempInt =new Integer(0);
        for(int i=0 ; i<strBinary.length();i=i+8){
            tempInt = tempInt.valueOf(strBinary.substring(i,i+8),2);
            encoded[i/8]=tempInt.byteValue();
        }
        return encoded;
    }

    String decode(byte[] encoded, int bit){
        String strTemp = new String("");
        String strBinary = new String("");
        String strText = new String("");
        Integer tempInt =new Integer(0);
        int intTemp=0;
        for(int i = 0;i<encoded.length;i++){
            if(encoded[i]<0){
                intTemp = (int)encoded[i]+256;
            }else
                intTemp = (int)encoded[i];
            strTemp = Integer.toBinaryString(intTemp);
            while(strTemp.length()%8 != 0){
                strTemp="0"+strTemp;
            }
            strBinary = strBinary+strTemp;
        }
        for(int i=0 ; i<strBinary.length();i=i+bit){
            tempInt = Integer.valueOf(strBinary.substring(i,i+bit),2);
            strText = strText + toChar(tempInt.intValue());
        }

        strText=worddecode(strText);
        return strText;
    }

    int toValue(char ch){
        int chaVal = 0;
        switch(ch){
            case' ':chaVal=0;break; case'অ':chaVal=1;break;
            case'আ':chaVal=2;break; case'ই':chaVal=3;break;
            case'ঈ':chaVal=4;break; case'উ':chaVal=5;break;
            case'ঊ':chaVal=6;break; case'ঋ':chaVal=7;break;
            case'এ':chaVal=8;break; case'ঐ':chaVal=9;break;
            case'ও':chaVal=10;break; case'ঔ':chaVal=11;break;
            case'ক':chaVal=12;break; case'খ':chaVal=13;break;
            case'গ':chaVal=14;break; case'ঘ':chaVal=15;break;
            case'ঙ':chaVal=16;break; case'চ':chaVal=17;break;
            case'ছ':chaVal=18;break; case'জ':chaVal=19;break;
            case'ঝ':chaVal=20;break; case'ঞ':chaVal=21;break;
            case'ট':chaVal=22;break; case'ঠ':chaVal=23;break;
            case'ড':chaVal=24;break; case'ঢ':chaVal=25;break;
            case'ণ':chaVal=26;break; case'ত':chaVal=27;break;
            case'থ':chaVal=28;break; case'দ':chaVal=29;break;
            case'ধ':chaVal=30;break; case'ন':chaVal=31;break;
            case'প':chaVal=32;break; case'ফ':chaVal=33;break;
            case'ব':chaVal=34;break; case'ভ':chaVal=35;break;
            case'ম':chaVal=36;break; case'য':chaVal=37;break;
            case'র':chaVal=38;break; case'ল':chaVal=39;break;
            case'শ':chaVal=40;break; case'ষ':chaVal=41;break;
            case'স':chaVal=42;break; case'হ':chaVal=43;break;
            case'ড়':chaVal=44;break; case'ঢ়':chaVal=45;break;
            case'য়':chaVal=46;break; case'ৎ':chaVal=47;break;
            case'ং':chaVal=48;break; case'ঃ':chaVal=49;break;
            case'ঁ':chaVal=50;break; case'া':chaVal=51;break;
            case'ি':chaVal=52;break; case'ী':chaVal=53;break;
            case'ু':chaVal=54;break; case'ূ':chaVal=55;break;
            case'ৃ':chaVal=56;break; case'ে':chaVal=57;break;
            case'ৈ':chaVal=58;break;
            case'ৌ':chaVal=60;break;case'।':chaVal=61;break;
            case'্':chaVal=62;break;case',':chaVal=63;break;
            case'১':chaVal=64;break;
            case'২':chaVal=65;break;
            case'৩':chaVal=66;break;
            case'৪':chaVal=67;break;
            case'৫':chaVal=68;break;
            case'৬':chaVal=69;break;
            case'৭':chaVal=70;break;
            case'৮':chaVal=71;break;
            case'৯':chaVal=72;break;
            case'০':chaVal=73;break;
            case'!':chaVal=74;break;
            case'@':chaVal=75;break;
            case'#':chaVal=76;break;
            case'%':chaVal=77;break;
            case'&':chaVal=78;break;
            case'*':chaVal=79;break;
            case'(':chaVal=80;break;
            case')':chaVal=81;break;
            case':':chaVal=82;break;
            case'"':chaVal=83;break;
            case';':chaVal=84;break;
            case'{':chaVal=85;break;
            case'}':chaVal=86;break;
            case'[':chaVal=87;break;
            case']':chaVal=88;break;
            case'+':chaVal=89;break;
            case'-':chaVal=90;break;
            case'=':chaVal=91;break;
            case'.':chaVal=92;break;
            case'/':chaVal=93;break;
            case'<':chaVal=94;break;
            case'>':chaVal=95;break;
            case'?':chaVal=96;break;
            case'_':chaVal=97;break;
            case'1':chaVal=98;break;
            case'2':chaVal=99;break;
            case'3':chaVal=100;break;
            case'4':chaVal=101;break;
            case'5':chaVal=102;break;
            case'6':chaVal=103;break;
            case'7':chaVal=104;break;
            case'8':chaVal=105;break;
            case'9':chaVal=106;break;
            case'0':chaVal=107;break;
            case'৳':chaVal=108;break;
            case'$':chaVal=109;break;
            case'ো':chaVal=110;break;
//            case'D':chaVal=111;break;
//            case'P':chaVal=112;break;
//            case'p':chaVal=113;break;
            case'^':chaVal=114;break;
//            case'O':chaVal=115;break;
//            case'o':chaVal=116;break;
            case'a':chaVal=120;break;
            case'b':chaVal=121;break;
            case'c':chaVal=122;break;
            case'd':chaVal=123;break;
            case'e':chaVal=124;break;
            case'f':chaVal=125;break;
            case'g':chaVal=126;break;
            case'h':chaVal=127;break;
            case'i':chaVal=128;break;
            case'j':chaVal=129;break;
            case'k':chaVal=130;break;
            case'l':chaVal=131;break;
            case'm':chaVal=132;break;
            case'n':chaVal=133;break;
            case'o':chaVal=134;break;
            case'p':chaVal=135;break;
            case'q':chaVal=136;break;
            case'r':chaVal=137;break;
            case's':chaVal=138;break;
            case't':chaVal=139;break;
            case'u':chaVal=140;break;
            case'v':chaVal=141;break;
            case'w':chaVal=142;break;
            case'x':chaVal=143;break;
            case'y':chaVal=144;break;
            case'z':chaVal=145;break;
            case'A':chaVal=146;break;
            case'B':chaVal=147;break;
            case'C':chaVal=148;break;
            case'D':chaVal=149;break;
            case'E':chaVal=150;break;
            case'F':chaVal=151;break;
            case'G':chaVal=152;break;
            case'H':chaVal=153;break;
            case'I':chaVal=154;break;
            case'J':chaVal=155;break;
            case'K':chaVal=156;break;
            case'L':chaVal=157;break;
            case'M':chaVal=158;break;
            case'N':chaVal=159;break;
            case'O':chaVal=160;break;
            case'P':chaVal=161;break;
            case'Q':chaVal=162;break;
            case'R':chaVal=163;break;
            case'S':chaVal=164;break;
            case'T':chaVal=165;break;
            case'U':chaVal=166;break;
            case'V':chaVal=167;break;
            case'W':chaVal=168;break;
            case'X':chaVal=169;break;
            case'Y':chaVal=170;break;
            case'Z':chaVal=171;break;


            case '€':chaVal=59;break;
            case '¦':chaVal=172;break;
            case '‚':chaVal=173;break;
            case 'ƒ':chaVal=174;break;
            case '„':chaVal=175;break;
            case '…':chaVal=176;break;
            case '†':chaVal=177;break;
            case '‡':chaVal=178;break;
            case 'ˆ':chaVal=179;break;
            case '‰':chaVal=180;break;
            case 'Š':chaVal=181;break;
            case '‹':chaVal=182;break;
            case 'Œ':chaVal=183;break;
            case 'Φ':chaVal=184;break;
            case 'Ž':chaVal=185;break;


            case '‘':chaVal=188;break;
            case '’':chaVal=189;break;
            case '“':chaVal=190;break;
            case '”':chaVal=191;break;
            case '•':chaVal=192;break;
            case '–':chaVal=193;break;
            case '—':chaVal=194;break;
            case '˜':chaVal=195;break;
            case '™':chaVal=196;break;
            case 'š':chaVal=197;break;
            case '›':chaVal=198;break;
            case 'œ':chaVal=199;break;

            case 'ž':chaVal=201;break;
            case 'Ÿ':chaVal=202;break;
            case 'ü':chaVal=203;break;
            case '¡':chaVal=204;break;
            case '¢':chaVal=205;break;
            case '£':chaVal=206;break;
            case '¤':chaVal=207;break;
            case '¥':chaVal=208;break;

            case '§':chaVal=210;break;
            case '¨':chaVal=211;break;
            case '©':chaVal=212;break;
            case 'ª':chaVal=213;break;
            case '«':chaVal=214;break;
            case '¬':chaVal=215;break;
            case '­':chaVal=216;break;
            case '®':chaVal=217;break;
            case '¯':chaVal=218;break;
            case '°':chaVal=219;break;
            case '±':chaVal=220;break;
            case '²':chaVal=221;break;
            case '³':chaVal=222;break;
            case '´':chaVal=223;break;
            case 'µ':chaVal=224;break;
            case '¶':chaVal=225;break;
            case '·':chaVal=226;break;
            case '¸':chaVal=227;break;
            case '¹':chaVal=228;break;
            case 'º':chaVal=229;break;
            case '»':chaVal=230;break;
            case '¼':chaVal=231;break;
            case '½':chaVal=232;break;
            case '¾':chaVal=233;break;
            case '¿':chaVal=234;break;
            case 'À':chaVal=235;break;
            case 'Á':chaVal=236;break;
            case 'Â':chaVal=237;break;
            case 'Ã':chaVal=238;break;
            case 'Ä':chaVal=239;break;
            case 'Å':chaVal=240;break;
            case 'Æ':chaVal=241;break;
            case 'Ç':chaVal=242;break;
            case 'È':chaVal=243;break;
            case 'É':chaVal=244;break;
            case 'Ê':chaVal=245;break;
            case 'Ë':chaVal=246;break;
            case 'Ì':chaVal=247;break;
            case 'Í':chaVal=248;break;
            case 'Î':chaVal=249;break;
            case 'Ï':chaVal=250;break;
            case 'Ð':chaVal=251;break;
            case 'Ñ':chaVal=252;break;
            case 'Ò':chaVal=253;break;
            case 'Ó':chaVal=254;break;
            case 'Ô':chaVal=255;break;
            case 'Õ':chaVal=111;break;
            case 'Ö':chaVal=112;break;
            case '×':chaVal=113;break;

            case 'Ù':chaVal=115;break;
            case 'Ú':chaVal=116;break;
            case 'ø':chaVal=117;break;

            case '÷':chaVal=118;break;
            case 'ö':chaVal=119;break;



            default:chaVal=0;
        }
        return chaVal;
    }

    char toChar(int val){
        char ch = ' ';
        switch(val){
            case 0:ch=' ';break; case 1:ch='অ';break;
            case 2:ch='আ';break; case 3 :ch='ই';break;
            case 4:ch='ঈ';break; case 5 :ch='উ';break;
            case 6:ch='ঊ';break; case 7 :ch='ঋ';break;
            case 8:ch='এ';break; case 9 :ch='ঐ';break;
            case 10:ch='ও';break; case 11:ch='ঔ';break;
            case 12:ch='ক';break; case 13:ch='খ';break;
            case 14:ch='গ';break; case 15:ch='ঘ';break;
            case 16:ch='ঙ';break; case 17:ch='চ';break;
            case 18:ch='ছ';break; case 19:ch='জ';break;
            case 20:ch='ঝ';break; case 21:ch='ঞ';break;
            case 22:ch='ট';break; case 23:ch='ঠ';break;
            case 24:ch='ড';break; case 25:ch='ঢ';break;
            case 26:ch='ণ';break; case 27:ch='ত';break;
            case 28:ch='থ';break; case 29:ch='দ';break;
            case 30:ch='ধ';break; case 31 :ch='ন';break;
            case 32:ch='প';break; case 33:ch='ফ';break;
            case 34:ch='ব';break; case 35:ch='ভ';break;
            case 36:ch='ম';break; case 37:ch='য';break;
            case 38:ch='র';break; case 39:ch='ল';break;
            case 40:ch='শ';break; case 41:ch='ষ';break;
            case 42:ch='স';break; case 43:ch='হ';break;
            case 44:ch='ড়';break; case 45:ch='ঢ়';break;
            case 46:ch='য়';break; case 47:ch='ৎ';break;
            case 48:ch='ং';break; case 49:ch='ঃ';break;
            case 50:ch='ঁ';break; case 51:ch='া';break;
            case 52:ch='ি';break; case 53:ch='ী';break;
            case 54:ch='ু';break; case 55:ch='ূ';break;
            case 56:ch='ৃ';break; case 57:ch='ে';break;
            case 58:ch='ৈ';break; case 110:ch='ো';break;
            case 60:ch='ৌ';break; case 61:ch='।';break;
            case 62:ch='্';break; case 63:ch=',';break;
            case 64:ch='১';break;
            case 65:ch='২';break;
            case 66:ch='৩';break;
            case 67:ch='৪';break;
            case 68:ch='৫';break;
            case 69:ch='৬';break;
            case 70:ch='৭';break;
            case 71:ch='৮';break;
            case 72:ch='৯';break;
            case 73:ch='০';break;
            case 74:ch='!';break;
            case 75:ch='@';break;
            case 76:ch='#';break;
            case 77:ch='%';break;
            case 78:ch='&';break;
            case 79:ch='*';break;
            case 80:ch='(';break;
            case 81:ch=')';break;
            case 82:ch=':';break;
            case 83:ch='"';break;
            case 84:ch=';';break;
            case 85:ch='{';break;
            case 86:ch='}';break;
            case 87:ch='[';break;
            case 88:ch=']';break;
            case 89:ch='+';break;
            case 90:ch='-';break;
            case 91:ch='=';break;
            case 92:ch='.';break;
            case 93:ch='/';break;
            case 94:ch='<';break;
            case 95:ch='>';break;
            case 96:ch='?';break;
            case 97:ch='_';break;
            case 98:ch='1';break;
            case 99:ch='2';break;
            case 100:ch='3';break;
            case 101:ch='4';break;
            case 102:ch='5';break;
            case 103:ch='6';break;
            case 104:ch='7';break;
            case 105:ch='8';break;
            case 106:ch='9';break;
            case 107:ch='0';break;
            case 108:ch='৳';break;
            case 109:ch='$';break;

//            case 111:ch='D';break;
//            case 112:ch='P';break;
//            case 113:ch='p';break;
//
            case 114:ch='^';break;
//
//
//            case 115:ch='O';break;
//            case 116:ch='o';break;

            case 120:ch='a';break;
            case 121:ch='b';break;
            case 122:ch='c';break;
            case 123:ch='d';break;
            case 124:ch='e';break;
            case 125:ch='f';break;
            case 126:ch='g';break;
            case 127:ch='h';break;
            case 128:ch='i';break;
            case 129:ch='j';break;
            case 130:ch='k';break;
            case 131:ch='l';break;
            case 132:ch='m';break;
            case 133:ch='n';break;
            case 134:ch='o';break;
            case 135:ch='p';break;
            case 136:ch='q';break;
            case 137:ch='r';break;
            case 138:ch='s';break;
            case 139:ch='t';break;
            case 140:ch='u';break;
            case 141:ch='v';break;
            case 142:ch='w';break;
            case 143:ch='x';break;
            case 144:ch='y';break;
            case 145:ch='z';break;
            case 146:ch='A';break;
            case 147:ch='B';break;
            case 148:ch='C';break;
            case 149:ch='D';break;
            case 150:ch='E';break;
            case 151:ch='F';break;
            case 152:ch='G';break;
            case 153:ch='H';break;
            case 154:ch='I';break;
            case 155:ch='J';break;
            case 156:ch='K';break;
            case 157:ch='L';break;
            case 158:ch='M';break;
            case 159:ch='N';break;
            case 160:ch='O';break;
            case 161:ch='P';break;
            case 162:ch='Q';break;
            case 163:ch='R';break;
            case 164:ch='S';break;
            case 165:ch='T';break;
            case 166:ch='U';break;
            case 167:ch='V';break;
            case 168:ch='W';break;
            case 169:ch='X';break;
            case 170:ch='Y';break;
            case 171:ch='Z';break;


            case 59:ch='€';break;
            case 172:ch='¦';break;
            case 173:ch='‚';break;
            case 174:ch='ƒ';break;
            case 175:ch='„';break;
            case 176:ch='…';break;
            case 177:ch='†';break;
            case 178:ch='‡';break;
            case 179:ch='ˆ';break;
            case 180:ch='‰';break;
            case 181:ch='Š';break;
            case 182:ch='‹';break;
            case 183:ch='Œ';break;
            case 184:ch='Φ';break;

            case 185:ch='Ž';break;


            case 188:ch='‘';break;
            case 189:ch='’';break;
            case 190:ch='“';break;
            case 191:ch='”';break;
            case 192:ch='•';break;
            case 193:ch='–';break;
            case 194:ch='—';break;
            case 195:ch='˜';break;
            case 196:ch='™';break;
            case 197:ch='š';break;
            case 198:ch='›';break;
            case 199:ch='œ';break;

            case 201:ch='ž';break;
            case 202:ch='Ÿ';break;
            case 203:ch='ü';break;
            case 204:ch='¡';break;
            case 205:ch='¢';break;
            case 206:ch='£';break;
            case 207:ch='¤';break;
            case 208:ch='¥';break;

            case 210:ch='§';break;
            case 211:ch='¨';break;
            case 212:ch='©';break;
            case 213:ch='ª';break;
            case 214:ch='«';break;
            case 215:ch='¬';break;
            case 216:ch='­';break;
            case 217:ch='®';break;
            case 218:ch='¯';break;
            case 219:ch='°';break;
            case 220:ch='±';break;
            case 221:ch='²';break;
            case 222:ch='³';break;
            case 223:ch='´';break;
            case 224:ch='µ';break;
            case 225:ch='¶';break;
            case 226:ch='·';break;
            case 227:ch='¸';break;
            case 228:ch='¹';break;
            case 229:ch='º';break;
            case 230:ch='»';break;
            case 231:ch='¼';break;
            case 232:ch='½';break;
            case 233:ch='¾';break;
            case 234:ch='¿';break;
            case 235:ch='À';break;
            case 236:ch='Á';break;
            case 237:ch='Â';break;
            case 238:ch='Ã';break;
            case 239:ch='Ä';break;
            case 240:ch='Å';break;
            case 241:ch='Æ';break;
            case 242:ch='Ç';break;
            case 243:ch='È';break;
            case 244:ch='É';break;
            case 245:ch='Ê';break;
            case 246:ch='Ë';break;
            case 247:ch='Ì';break;
            case 248:ch='Í';break;
            case 249:ch='Î';break;
            case 250:ch='Ï';break;
            case 251:ch='Ð';break;
            case 252:ch='Ñ';break;
            case 253:ch='Ò';break;
            case 254:ch='Ó';break;
            case 255:ch='Ô';break;
            case 111:ch='Õ';break;
            case 112:ch='Ö';break;
            case 113:ch='×';break;

            case 115:ch='Ù';break;
            case 116:ch='Ú';break;
            case 117:ch='ø';break;
            case 118:ch='÷';break;
            case 119:ch='ö';break;
            default:ch=' ';
        }
        return ch;
    }

    String wordencode(String str)
    {

        str = str.replaceAll("মার","€");

        str = str.replaceAll("আমি","‚");
        str = str.replaceAll("তুমি","ƒ");
        str = str.replaceAll("তোর","„");
        str = str.replaceAll("না","…");
        str = str.replaceAll("কি অবস্থা","†");
        str = str.replaceAll("কি খবর","‡");
        str = str.replaceAll("ভালো আছি","ˆ");
        str = str.replaceAll("কেমন আছো","‰");
        str = str.replaceAll("হম","Š");
        str = str.replaceAll("কি করো","‹");
        str = str.replaceAll("কিছু না","Œ");

        str = str.replaceAll("ভালো থেকো","Ž");

        str = str.replaceAll("তিনি","‘");
        str = str.replaceAll("ছিল","’");
        str = str.replaceAll("জন্য","“");
        str = str.replaceAll("যখন","”");
        str = str.replaceAll("হয়","•");
        str = str.replaceAll("সঙ্গে","–");
        str = str.replaceAll("তার","—");
        str = str.replaceAll("আছে","˜");
        str = str.replaceAll("এই","™");
        str = str.replaceAll("থেকে","š");
        str = str.replaceAll("গরম","›");
        str = str.replaceAll("কিন্তু","œ");
        str = str.replaceAll("কি","ú");
        str = str.replaceAll("কিছু","ž");
        str = str.replaceAll("হয়","Ÿ");
        str = str.replaceAll("এটা","ü");
        str = str.replaceAll("বা","¡");
        str = str.replaceAll("সম","¢");
        str = str.replaceAll("ভাই","£");
        str = str.replaceAll("এর","¤");
        str = str.replaceAll("থেকে","¥");
        str = str.replaceAll("এবং","¦");
        str = str.replaceAll("একটি","§");
        str = str.replaceAll("মধ্যে","¨");
        str = str.replaceAll("আমরা","©");
        str = str.replaceAll("অন্যান্য","ª");
        str = str.replaceAll("ভিন্ন","«");
        str = str.replaceAll("কিভাবে","¬");
        str = str.replaceAll("প্রতি","­");
        str = str.replaceAll("বাসা","®");
        str = str.replaceAll("কর","¯");
        str = str.replaceAll("বার","°");
        str = str.replaceAll("উৎ","±");
        str = str.replaceAll("পরি","²");
        str = str.replaceAll("প্র","³");
        str = str.replaceAll("অভি","´");
        str = str.replaceAll("অতি","µ");
        str = str.replaceAll("উপ","¶");
        str = str.replaceAll("অপ","·");
        str = str.replaceAll("বার","¸");
        str = str.replaceAll("স্ব","¹");
        str = str.replaceAll("ক্ষ","º");
        str = str.replaceAll("মাত্র","»");
        str = str.replaceAll("দিতে","¼");
        str = str.replaceAll("আমাদের","½");
        str = str.replaceAll("কার","¾");
        str = str.replaceAll("অধি","¿");
        str = str.replaceAll("খুব","À");
        str = str.replaceAll("হার","Á");
        str = str.replaceAll("ঠিক","Â");
        str = str.replaceAll("হবে","Ã");
        str = str.replaceAll("পর","Ä");
        str = str.replaceAll("তাই","Å");
        str = str.replaceAll("সব","Å");
        str = str.replaceAll("চেয়ে","Æ");
        str = str.replaceAll("পারে","Ç");
        str = str.replaceAll("নিজ","È");
        str = str.replaceAll("পারা","É");
        str = str.replaceAll("গঞ্জ","Ê");
        str = str.replaceAll("জ্ঞান","Ë");
        str = str.replaceAll("এক","Ì");
        str = str.replaceAll("হচ্ছে","Í");
        str = str.replaceAll("সময়","Î");
        str = str.replaceAll("সব","Ï");
        str = str.replaceAll("তাম","Ð");
        str = str.replaceAll("হীন","Ñ");
        str = str.replaceAll("তলা","Ò");
        str = str.replaceAll("বল","Ó");
        str = str.replaceAll("হয়","Ô");
        str = str.replaceAll("অনু","Õ");
        str = str.replaceAll("মাত্রা","Ö");
        str = str.replaceAll("ফল","×");
        str = str.replaceAll("ভালো","Ø");
        str = str.replaceAll("খারাপ","Ù");
        str = str.replaceAll("মানুষ","Ú");


        return str;
    }





    String worddecode(String str)
    {



        str = str.replaceAll("€","মার");

        str = 	str.replaceAll("‚","আমি");
        str = str.replaceAll("ƒ","তুমি");
        str = str.replaceAll("„","তোর");
        str = str.replaceAll("…","না");
        str = str.replaceAll("†","কি অবস্থা");
        str = str.replaceAll("‡","কি খবর");
        str = str.replaceAll("ˆ","ভালো আছি");
        str = str.replaceAll("‰","কেমন আছো");
        str = str.replaceAll("Š","হম");
        str = str.replaceAll("‹","কি করো");
        str = str.replaceAll("Œ","কিছু না");

        str = str.replaceAll("Ž","ভালো থেকো");

        str = str.replaceAll("‘","তিনি");
        str = str.replaceAll("’","ছিল");
        str = str.replaceAll("“","জন্য");
        str = str.replaceAll("”","যখন");
        str = str.replaceAll("•","হয়");
        str = str.replaceAll("–","সঙ্গে");
        str = str.replaceAll("—","তার");
        str = str.replaceAll("˜","আছে");
        str = str.replaceAll("™","এই");
        str = str.replaceAll("š","থেকে");
        str = str.replaceAll("›","গরম");
        str = str.replaceAll("œ","কিন্তু");
        str = str.replaceAll("ú","কি");
        str = str.replaceAll("ž","কিছু");
        str = str.replaceAll("Ÿ","হয়");
        str = str.replaceAll("ü","এটা");
        str = str.replaceAll("¡","বা");
        str = str.replaceAll("¢","সম");
        str = str.replaceAll("£","ভাই");
        str = str.replaceAll("¤","এর");
        str = str.replaceAll("¥","থেকে");
        str = str.replaceAll("¦","এবং");
        str = str.replaceAll("§","একটি");
        str = str.replaceAll("¨","মধ্যে");
        str = str.replaceAll("©","আমরা");
        str = str.replaceAll("ª","অন্যান্য");
        str = str.replaceAll("«","ভিন্ন");
        str = str.replaceAll("¬","কিভাবে");
        str = str.replaceAll("­","প্রতি");
        str = str.replaceAll("®","বাসা");
        str = str.replaceAll("¯","কর");
        str = str.replaceAll("°","বার");
        str = str.replaceAll("±","উৎ");
        str = str.replaceAll("²","পরি");
        str = str.replaceAll("³","প্র");
        str = str.replaceAll("´","অভি");
        str = str.replaceAll("µ","অতি");
        str = str.replaceAll("¶","উপ");
        str = str.replaceAll("·","অপ");
        str = str.replaceAll("¸","বার");
        str = str.replaceAll("¹","স্ব");
        str = str.replaceAll("º","ক্ষ");
        str = str.replaceAll("»","মাত্র");
        str = str.replaceAll("¼","দিতে");
        str = str.replaceAll("½","আমাদের");
        str = str.replaceAll("¾","কার");
        str = str.replaceAll("¿","অধি");
        str = str.replaceAll("À","খুব");
        str = str.replaceAll("Á","হার");
        str = str.replaceAll("Â","ঠিক");
        str = str.replaceAll("Ã","হবে");
        str = str.replaceAll("Ä","পর");
        str = str.replaceAll("Å","তাই");
        str = str.replaceAll("Å","সব");
        str = str.replaceAll("Æ","চেয়ে");
        str = str.replaceAll("Ç","পারে");
        str = str.replaceAll("È","নিজ");
        str = str.replaceAll("É","পারা");
        str = str.replaceAll("Ê","গঞ্জ");
        str = str.replaceAll("Ë","জ্ঞান");
        str = str.replaceAll("Ì","এক");
        str = str.replaceAll("Í","হচ্ছে");
        str = str.replaceAll("Î","সময়");
        str = str.replaceAll("Ï","সব");
        str = str.replaceAll("Ð","তাম");
        str = str.replaceAll("Ñ","হীন");
        str = str.replaceAll("Ò","তলা");
        str = str.replaceAll("Ó","বল");
        str = str.replaceAll("Ô","হয়");
        str = str.replaceAll("Õ","অনু");
        str = str.replaceAll("Ö","মাত্রা");
        str = str.replaceAll("×","ফল");
        str = str.replaceAll("Ø","ভালো");
        str = str.replaceAll("Ù","খারাপ");
        str = str.replaceAll("Ú","মানুষ");


        return str;



    }

}