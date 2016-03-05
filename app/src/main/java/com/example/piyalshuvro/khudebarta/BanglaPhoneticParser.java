package com.example.piyalshuvro.khudebarta;

import java.util.HashMap;

/**
 * BanglaPhoneticParser Class contains method to convert a English text to corresponding Phonetic Bangla text
 *
 * @author Sk. Imran Hossain
 * @version 1.0
 */
public class BanglaPhoneticParser {
    public HashMap<String, String> mapSorborno = new HashMap<>();
    public HashMap<String, String> mapKar = new HashMap<>();
    public HashMap<String, String> mapByanjonBorno = new HashMap<>(); // number apatoto byanjon borner list e ache
    public HashMap<String, String> mapJuktoBorno = new HashMap<>();

    public static String[] oneChar = {"A", "W", "w", "F", "E", "V", "N", "a", "i", "I",
            "u", "U", "e", "o", "O", "k", "g", "c", "j", "T", "D", "t", "d",
            "n", "p", "f", "b", "v", "m", "z", "r", "l", "S", "s", "h", "R",
            "y", "Y", ":", "q", "Q", "P", "G", "H", "J", "K", "L", "C", "V",
            "B", "M", "Z", "^", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static String[] twoChar = {"rr", "ng", "Tw", "Tm", "Ty", "DD", "Dy", "NT",
            "nc", "jj", "jy", "jr", "jw", "cy", "gg", "kt", "hh", "HH", "SH",
            "tT", "hl", "hr", "hy", "hm", "hw", "sp", "sn", "st", "sT", "hn",
            "hN", "sl", "sr", "sy", "sm", "sw", "sf", "Sl", "Sr", "Sy", "Sm",
            "Sw", "Sn", "St", "sk", "lg", "lk", "zy", "ml", "Sc", "ll", "ly",
            "lm", "lv", "lw", "lb", "lp", "lD", "lT", "bl", "br", "by", "bb",
            "bd", "bj", "fl", "mr", "my", "mm", "mv", "mw", "mb", "mf", "mp",
            "mn", "vl", "vr", "vy", "nw", "nn", "fr", "ps", "pl", "pr", "py",
            "pp", "pn", "pt", "pT", "ns", "ny", "nm", "dr", "dy", "dm", "dv",
            "dw", "nd", "nt", "nD", "nT", "Ny", "Nm", "Nw", "Nn", "dd", "dg",
            "tr", "ty", "tm", "tw", "tn", "tt", "TT", "nj", "ee", "oo", "OI",
            "OU", "kh", "gh", "Ng", "ch", "jh", "NG", "Th", "Dh", "th", "dh",
            "ph", "bh", "sh", "Sh", "Rh", "kk", "kT", "kw", "km", "ky", "kr",
            "kl", "kx", "ks", "gN", "dn", "gn", "gw", "gm", "gy", "gr", "gl",
            "cc", "Kh", "Gh", "Ch", "Jh", "Ph", "Bh", "cr", "ND", "Dr", "Cr"};

    public static String[] threeChar = {"spr", "NTh", "Dhr", "NDh", "NDy", "Dhy", "NGc",
            "jjh", "jjw", "ghn", "ktr", "kTr", "nTr", "ngo", "sth", "sty",
            "stw", "skh", "sTr", "skr", "skl", "sph", "ShT", "Shk", "shl",
            "shr", "shy", "shm", "shw", "shn", "sht", "Shm", "Shw", "Shf",
            "Shp", "ShN", "rrg", "rrk", "Sch", "shc", "lbh", "ldh", "bdh",
            "phl", "mvr", "mbh", "mph", "mpr", "mth", "bhl", "bhr", "bhy",
            "ndh", "ndr", "ndw", "phr", "dbh", "ddh", "ddw", "ndy", "nth",
            "ntr", "nty", "ntw", "nTh", "dhr", "dhy", "dhm", "dhw", "dhn",
            "dgh", "thr", "thy", "thw", "tmy", "tth", "ttw", "NGj", "nch",
            "kkh", "kxw", "kxN", "kxm", "kxy", "khy", "khr", "gdh", "gny",
            "ghy", "ghr", "Ngk", "nk", "nky", "Ngg", "Ngm", "cch", "nDr",
            "NDr", "dvr", "chr", "gru", "grU", "lTr"};

    static String[] fourChar = {"hrri", "sthy", "Shkr", "Shph", "Shpr", "ShTh", "ShTr",
            "ShTy", "rrkhy", "rrky", "rrkh", "shch", "mbhr", "ndhr", "ndhy",
            "NGch", "kkhw", "kkhN", "kkhm", "kkhy", "Ngky", "Ngkx", "Ngkh",
            "Nggy", "Nggh", "cchw", "cchr", "dbhr"};

    static String[] fiveChar = {"ShThy", "Ngkkh", "Ngghy", "Ngghr"};

    public BanglaPhoneticParser() {

        //TODO: map gula character er order e sajiye nile subidha hobe

        // Sorborner mapping
        mapSorborno.put("rri", "ঋ");
        mapSorborno.put("ee", "ঈ");
        mapSorborno.put("oo", "উ");
        mapSorborno.put("OI", "ঐ");
        mapSorborno.put("OU", "ঔ");
        mapSorborno.put("o", "অ");
        mapSorborno.put("i", "ই");
        mapSorborno.put("I", "ঈ");
        mapSorborno.put("u", "উ");
        mapSorborno.put("U", "ঊ");
        mapSorborno.put("e", "এ");
        mapSorborno.put("E", "এ");
        mapSorborno.put("O", "ও");
        mapSorborno.put("a", "আ");
        mapSorborno.put("A", "আ");
        // Sorborner mapping

        // Kar mapping
        mapKar.put("rri", "ৃ");
        mapKar.put("ee", "ী");
        mapKar.put("oo", "ু");
        mapKar.put("OI", "ৈ");
        mapKar.put("OU", "ৌ");
        mapKar.put("i", "ি");
        mapKar.put("I", "ী");
        mapKar.put("u", "ু");
        mapKar.put("U", "ূ");
        mapKar.put("e", "ে");
        mapKar.put("E", "ে");
        mapKar.put("O", "ো");
        mapKar.put("a", "া");
        mapKar.put("A", "া");
        // Kar mapping

        // Byanjon borner mapping
        mapByanjonBorno.put("F", "ফ");
        mapByanjonBorno.put("SH", "ষ");
        mapByanjonBorno.put("V", "ভ");
        mapByanjonBorno.put("tT", "ৎ");
        mapByanjonBorno.put("0", "০");
        mapByanjonBorno.put("1", "১"); //TODO: number o onnano jinish alada map e rakha jete pare
        mapByanjonBorno.put("2", "২");
        mapByanjonBorno.put("3", "৩");
        mapByanjonBorno.put("4", "৪");
        mapByanjonBorno.put("5", "৫");
        mapByanjonBorno.put("6", "৬");
        mapByanjonBorno.put("7", "৭");
        mapByanjonBorno.put("8", "৮");
        mapByanjonBorno.put("9", "৯");
        mapByanjonBorno.put("k", "ক");
        mapByanjonBorno.put("hh", "্");
        mapByanjonBorno.put("HH", "্");
        mapByanjonBorno.put("kh", "খ");
        mapByanjonBorno.put("Kh", "খ");
        mapByanjonBorno.put("g", "গ");
        mapByanjonBorno.put("gh", "ঘ");
        mapByanjonBorno.put("Gh", "ঘ");
        mapByanjonBorno.put("Ng", "ঙ");
        mapByanjonBorno.put("c", "চ");
        mapByanjonBorno.put("ch", "ছ");
        mapByanjonBorno.put("Ch", "ছ");
        mapByanjonBorno.put("j", "জ");
        mapByanjonBorno.put("jh", "ঝ");
        mapByanjonBorno.put("Jh", "ঝ");
        mapByanjonBorno.put("NG", "ঞ");
        mapByanjonBorno.put("T", "ট");
        mapByanjonBorno.put("Th", "ঠ");
        mapByanjonBorno.put("D", "ড");
        mapByanjonBorno.put("Dh", "ঢ");
        mapByanjonBorno.put("N", "ণ");
        mapByanjonBorno.put("t", "ত");
        mapByanjonBorno.put("th", "থ");
        mapByanjonBorno.put("d", "দ");
        mapByanjonBorno.put("dh", "ধ");
        mapByanjonBorno.put("n", "ন");
        mapByanjonBorno.put("p", "প");
        mapByanjonBorno.put("ph", "ফ");
        mapByanjonBorno.put("Ph", "ফ");
        mapByanjonBorno.put("f", "ফ");
        mapByanjonBorno.put("b", "ব");
        mapByanjonBorno.put("bh", "ভ");
        mapByanjonBorno.put("Bh", "ভ");
        mapByanjonBorno.put("v", "ভ");
        mapByanjonBorno.put("m", "ম");
        mapByanjonBorno.put("z", "য");
        mapByanjonBorno.put("r", "র");
        mapByanjonBorno.put("l", "ল");
        mapByanjonBorno.put("sh", "শ");
        mapByanjonBorno.put("S", "শ");
        mapByanjonBorno.put("Sh", "ষ");
        mapByanjonBorno.put("s", "স");
        mapByanjonBorno.put("h", "হ");
        mapByanjonBorno.put("R", "ড়");
        mapByanjonBorno.put("Rh", "ঢ়");
        mapByanjonBorno.put("y", "য়");
        mapByanjonBorno.put("Y", "য়");
        mapByanjonBorno.put("ng", "ং");
        mapByanjonBorno.put(":", "ঃ");
        mapByanjonBorno.put("q", "ক");
        mapByanjonBorno.put("Q", "ক");
        mapByanjonBorno.put("P", "প");
        mapByanjonBorno.put("G", "গ");
        mapByanjonBorno.put("H", "হ");
        mapByanjonBorno.put("J", "জ");
        mapByanjonBorno.put("K", "ক");
        mapByanjonBorno.put("L", "ল");
        mapByanjonBorno.put("C", "চ");
        mapByanjonBorno.put("B", "ব");
        mapByanjonBorno.put("M", "ম");
        mapByanjonBorno.put("Z", "্য");
        mapByanjonBorno.put("^", "ঁ");
        mapByanjonBorno.put("w", "ও");
        mapByanjonBorno.put("W", "ও");
        // Byanjon borner mapping

        // jukto borner mapping
        //TODO: check korte hobe kono jukto borno missing ache kina
        mapJuktoBorno.put("spr", "স্প্র‌");
        mapJuktoBorno.put("rr", "র্");
        mapJuktoBorno.put("kk", "ক্ক");
        mapJuktoBorno.put("ngo", "ঙ্গ");
        mapJuktoBorno.put("kT", "ক্ট");
        mapJuktoBorno.put("kt", "ক্ত");
        mapJuktoBorno.put("ktr", "ক্ত্র");
        mapJuktoBorno.put("kTr", "ক্ট্র");
        mapJuktoBorno.put("kw", "ক্ব");
        mapJuktoBorno.put("km", "ক্ম");
        mapJuktoBorno.put("ky", "ক্য");
        mapJuktoBorno.put("kZ", "ক্য");
        mapJuktoBorno.put("kr", "ক্র");
        mapJuktoBorno.put("kl", "ক্ল");
        mapJuktoBorno.put("kkh", "ক্ষ");
        mapJuktoBorno.put("kx", "ক্ষ");
        mapJuktoBorno.put("kkhw", "ক্ষ্ব");
        mapJuktoBorno.put("kxw", "ক্ষ্ব");
        mapJuktoBorno.put("kkhN", "ক্ষ্ণ");
        mapJuktoBorno.put("kxN", "ক্ষ্ণ");
        mapJuktoBorno.put("kkhm", "ক্ষ্ম");
        mapJuktoBorno.put("kxm", "ক্ষ্ম");
        mapJuktoBorno.put("kkhy", "ক্ষ্য");
        mapJuktoBorno.put("kxy", "ক্ষ্য");
        mapJuktoBorno.put("kkhZ", "ক্ষ্য");
        mapJuktoBorno.put("kxZ", "ক্ষ্য");
        mapJuktoBorno.put("ks", "ক্স");
        mapJuktoBorno.put("khy", "খ্য");
        mapJuktoBorno.put("khZ", "খ্য");
        mapJuktoBorno.put("khr", "খ্র");
        mapJuktoBorno.put("gN", "গ্ণ");
        mapJuktoBorno.put("gdh", "গ্ধ");
        mapJuktoBorno.put("gn", "গ্ন");
        mapJuktoBorno.put("gny", "গ্ন্য");
        mapJuktoBorno.put("gnZ", "গ্ন্য");
        mapJuktoBorno.put("gw", "গ্ব");
        mapJuktoBorno.put("gm", "গ্ম");
        mapJuktoBorno.put("gy", "গ্য");
        mapJuktoBorno.put("gZ", "গ্য");
        mapJuktoBorno.put("gr", "গ্র");
        mapJuktoBorno.put("gl", "গ্ল");
        mapJuktoBorno.put("ghn", "ঘ্ন");
        mapJuktoBorno.put("ghy", "ঘ্য");
        mapJuktoBorno.put("ghZ", "ঘ্য");
        mapJuktoBorno.put("ghr", "ঘ্র");
        mapJuktoBorno.put("Ngk", "ঙ্ক");
        mapJuktoBorno.put("nk", "ঙ্ক");
        mapJuktoBorno.put("nky", "ঙ্ক্য");
        mapJuktoBorno.put("Ngky", "ঙ্ক্য");
        mapJuktoBorno.put("nkZ", "ঙ্ক্য");
        mapJuktoBorno.put("NgkZ", "ঙ্ক্য");
        mapJuktoBorno.put("Ngkkh", "ঙ্ক্ষ");
        mapJuktoBorno.put("Ngkx", "ঙ্ক্ষ");
        mapJuktoBorno.put("Ngkh", "ঙ্খ");
        mapJuktoBorno.put("Ngg", "ঙ্গ");
        mapJuktoBorno.put("Nggy", "ঙ্গ্য");
        mapJuktoBorno.put("NggZ", "ঙ্গ্য");
        mapJuktoBorno.put("Ngm", "ঙ্ম");
        mapJuktoBorno.put("Nggh", "ঙ্ঘ");
        mapJuktoBorno.put("Ngghy", "ঙ্ঘ্য");
        mapJuktoBorno.put("NgghZ", "ঙ্ঘ্য");
        mapJuktoBorno.put("Ngghr", "ঙ্ঘ্র");
        mapJuktoBorno.put("cc", "চ্চ");
        mapJuktoBorno.put("cch", "চ্ছ");
        mapJuktoBorno.put("cchw", "চ্ছ্ব");
        mapJuktoBorno.put("cchr", "চ্ছ্র");
        mapJuktoBorno.put("cNG", "চ্ঞ");
        mapJuktoBorno.put("cy", "চ্য");
        mapJuktoBorno.put("cZ", "চ্য");
        mapJuktoBorno.put("jj", "জ্জ");
        mapJuktoBorno.put("jjw", "জ্জ্ব");
        mapJuktoBorno.put("jjh", "জ্ঝ");
        mapJuktoBorno.put("gg", "জ্ঞ");
        mapJuktoBorno.put("jNG", "জ্ঞ");
        mapJuktoBorno.put("jw", "জ্ব");
        mapJuktoBorno.put("jy", "জ্য");
        mapJuktoBorno.put("jZ", "জ্য");
        mapJuktoBorno.put("jr", "জ্র");
        mapJuktoBorno.put("nc", "ঞ্চ");
        mapJuktoBorno.put("NGc", "ঞ্চ");
        mapJuktoBorno.put("Tw", "ট্ব");
        mapJuktoBorno.put("Tm", "ট্ম");
        mapJuktoBorno.put("Ty", "ট্য");
        mapJuktoBorno.put("TZ", "ট্য");
        mapJuktoBorno.put("Tr", "ট্র");
        mapJuktoBorno.put("DD", "ড্ড");
        mapJuktoBorno.put("Dy", "ড্য");
        mapJuktoBorno.put("Dz", "ড্য");
        mapJuktoBorno.put("Dr", "ড্র");
        mapJuktoBorno.put("Dhy", "ঢ্য");
        mapJuktoBorno.put("DhZ", "ঢ্য");
        mapJuktoBorno.put("Dhr", "ঢ্র");
        mapJuktoBorno.put("NT", "ণ্ট");
        mapJuktoBorno.put("NTh", "ণ্ঠ");
        mapJuktoBorno.put("ND", "ণ্ড");
        mapJuktoBorno.put("NDy", "ণ্ড্য");
        mapJuktoBorno.put("NDZ", "ণ্ড্য");
        mapJuktoBorno.put("NDr", "ণ্ড্র");
        mapJuktoBorno.put("NDh", "ণ্ঢ");
        mapJuktoBorno.put("nch", "ঞ্ছ");
        mapJuktoBorno.put("NGch", "ঞ্ছ");
        mapJuktoBorno.put("nj", "ঞ্জ");
        mapJuktoBorno.put("NGj", "ঞ্জ");
        mapJuktoBorno.put("TT", "ট্ট");
        mapJuktoBorno.put("tt", "ত্ত");
        mapJuktoBorno.put("ttw", "ত্ত্ব");
        mapJuktoBorno.put("tth", "ত্থ");
        mapJuktoBorno.put("tn", "ত্ন");
        mapJuktoBorno.put("tw", "ত্ব");
        mapJuktoBorno.put("tm", "ত্ম");
        mapJuktoBorno.put("tmy", "ত্ম্য");
        mapJuktoBorno.put("tmZ", "ত্ম্য");
        mapJuktoBorno.put("ty", "ত্য");
        mapJuktoBorno.put("tZ", "ত্য");
        mapJuktoBorno.put("tr", "ত্র");
        mapJuktoBorno.put("thw", "থ্ব");
        mapJuktoBorno.put("thy", "থ্য");
        mapJuktoBorno.put("thZ", "থ্য");
        mapJuktoBorno.put("thr", "থ্র");
        mapJuktoBorno.put("dg", "দ্গ");
        mapJuktoBorno.put("dgh", "দ্ঘ");
        mapJuktoBorno.put("dd", "দ্দ");
        mapJuktoBorno.put("Nn", "ণ্ন");
        mapJuktoBorno.put("Nw", "ণ্ব");
        mapJuktoBorno.put("Nm", "ণ্ম");
        mapJuktoBorno.put("Ny", "ণ্য");
        mapJuktoBorno.put("NZ", "ণ্য");
        mapJuktoBorno.put("dhn", "ধ্ন");
        mapJuktoBorno.put("dhw", "ধ্ব");
        mapJuktoBorno.put("dhm", "ধ্ম");
        mapJuktoBorno.put("dhy", "ধ্য");
        mapJuktoBorno.put("dhZ", "ধ্য");
        mapJuktoBorno.put("dhr", "ধ্র");
        mapJuktoBorno.put("nT", "ন্ট");
        mapJuktoBorno.put("nTr", "ন্ট্র");
        mapJuktoBorno.put("nTh", "ন্ঠ");
        mapJuktoBorno.put("nD", "ন্ড");
        mapJuktoBorno.put("nt", "ন্ত");
        mapJuktoBorno.put("ntw", "ন্ত্ব");
        mapJuktoBorno.put("nty", "ন্ত্য");
        mapJuktoBorno.put("ntZ", "ন্ত্য");
        mapJuktoBorno.put("ntr", "ন্ত্র");
        mapJuktoBorno.put("nth", "ন্থ");
        mapJuktoBorno.put("nd", "ন্দ");
        mapJuktoBorno.put("ndy", "ন্দ্য");
        mapJuktoBorno.put("ndZ", "ন্দ্য");
        mapJuktoBorno.put("ddw", "দ্দ্ব");
        mapJuktoBorno.put("ddh", "দ্ধ");
        mapJuktoBorno.put("dw", "দ্ব");
        mapJuktoBorno.put("dv", "দ্ভ");
        mapJuktoBorno.put("dbh", "দ্ভ");
        mapJuktoBorno.put("dm", "দ্ম");
        mapJuktoBorno.put("dy", "দ্য");
        mapJuktoBorno.put("dZ", "দ্য");
        mapJuktoBorno.put("dr", "দ্র");
        mapJuktoBorno.put("nm", "ন্ম");
        mapJuktoBorno.put("ny", "ন্য");
        mapJuktoBorno.put("nZ", "ন্য");
        mapJuktoBorno.put("ns", "ন্স");
        mapJuktoBorno.put("pT", "প্ট");
        mapJuktoBorno.put("pt", "প্ত");
        mapJuktoBorno.put("pn", "প্ন");
        mapJuktoBorno.put("pp", "প্প");
        mapJuktoBorno.put("py", "প্য");
        mapJuktoBorno.put("pZ", "প্য");
        mapJuktoBorno.put("pr", "প্র");
        mapJuktoBorno.put("pl", "প্ল");
        mapJuktoBorno.put("ps", "প্স");
        mapJuktoBorno.put("fr", "ফ্র");
        mapJuktoBorno.put("phr", "ফ্র");
        mapJuktoBorno.put("ndw", "ন্দ্ব");
        mapJuktoBorno.put("ndr", "ন্দ্র");
        mapJuktoBorno.put("ndh", "ন্ধ");
        mapJuktoBorno.put("ndhy", "ন্ধ্য");
        mapJuktoBorno.put("ndhZ", "ন্ধ্য");
        mapJuktoBorno.put("ndhr", "ন্ধ্র");
        mapJuktoBorno.put("nn", "ন্ন");
        mapJuktoBorno.put("nw", "ন্ব");
        mapJuktoBorno.put("vy", "ভ্য");
        mapJuktoBorno.put("vZ", "ভ্য");
        mapJuktoBorno.put("bhy", "ভ্য");
        mapJuktoBorno.put("bhZ", "ভ্য");
        mapJuktoBorno.put("vr", "ভ্র");
        mapJuktoBorno.put("bhr", "ভ্র");
        mapJuktoBorno.put("vl", "ভ্ল");
        mapJuktoBorno.put("bhl", "ভ্ল");
        mapJuktoBorno.put("mth", "ম্থ");
        mapJuktoBorno.put("mn", "ম্ন");
        mapJuktoBorno.put("mp", "ম্প");
        mapJuktoBorno.put("mpr", "ম্প্র");
        mapJuktoBorno.put("mf", "ম্ফ");
        mapJuktoBorno.put("mph", "ম্ফ");
        mapJuktoBorno.put("mb", "ম্ব");
        mapJuktoBorno.put("mw", "ম্ব");
        mapJuktoBorno.put("mv", "ম্ভ");
        mapJuktoBorno.put("mbh", "ম্ভ");
        mapJuktoBorno.put("mvr", "ম্ভ্র");
        mapJuktoBorno.put("mbhr", "ম্ভ্র");
        mapJuktoBorno.put("mm", "ম্ম");
        mapJuktoBorno.put("my", "ম্য");
        mapJuktoBorno.put("mZ", "ম্য");
        mapJuktoBorno.put("mr", "ম্র");
        mapJuktoBorno.put("fl", "ফ্ল");
        mapJuktoBorno.put("phl", "ফ্ল");
        mapJuktoBorno.put("bj", "ব্জ");
        mapJuktoBorno.put("bd", "ব্দ");
        mapJuktoBorno.put("bdh", "ব্ধ");
        mapJuktoBorno.put("bb", "ব্ব");
        mapJuktoBorno.put("by", "ব্য");
        mapJuktoBorno.put("bZ", "ব্য");
        mapJuktoBorno.put("rZ", "র‍্য");
        mapJuktoBorno.put("br", "ব্র");
        mapJuktoBorno.put("bl", "ব্ল");
        mapJuktoBorno.put("lT", "ল্ট");
        mapJuktoBorno.put("lD", "ল্ড");
        mapJuktoBorno.put("ldh", "ল্ধ");
        mapJuktoBorno.put("lp", "ল্প");
        mapJuktoBorno.put("lb", "ল্ব");
        mapJuktoBorno.put("lw", "ল্ব");
        mapJuktoBorno.put("lv", "ল্ভ");
        mapJuktoBorno.put("lbh", "ল্ভ");
        mapJuktoBorno.put("lm", "ল্ম");
        mapJuktoBorno.put("ly", "ল্য");
        mapJuktoBorno.put("lZ", "ল্য");
        mapJuktoBorno.put("ll", "ল্ল");
        mapJuktoBorno.put("shc", "শ্চ");
        mapJuktoBorno.put("Sc", "শ্চ");
        mapJuktoBorno.put("shch", "শ্ছ");
        mapJuktoBorno.put("Sch", "শ্ছ");
        mapJuktoBorno.put("ml", "ম্ল");
        mapJuktoBorno.put("zy", "য্য");
        mapJuktoBorno.put("zZ", "য্য");
        mapJuktoBorno.put("rrk", "র্ক");
        mapJuktoBorno.put("rrkh", "র্খ");
        mapJuktoBorno.put("rrg", "র্গ");
        mapJuktoBorno.put("rrky", "র্ক্য");
        mapJuktoBorno.put("rrkZ", "র্ক্য");
        mapJuktoBorno.put("rrkhy", "র্খ্য");
        mapJuktoBorno.put("rrkhZ", "র্খ্য");
        mapJuktoBorno.put("lk", "ল্ক");
        mapJuktoBorno.put("lg", "ল্গ");
        mapJuktoBorno.put("ShTy", "ষ্ট্য");
        mapJuktoBorno.put("ShTZ", "ষ্ট্য");
        mapJuktoBorno.put("ShTr", "ষ্ট্র");
        mapJuktoBorno.put("ShTh", "ষ্ঠ");
        mapJuktoBorno.put("ShThy", "ষ্ঠ্য");
        mapJuktoBorno.put("ShThZ", "ষ্ঠ্য");
        mapJuktoBorno.put("ShN", "ষ্ণ");
        mapJuktoBorno.put("Shp", "ষ্প");
        mapJuktoBorno.put("Shpr", "ষ্প্র");
        mapJuktoBorno.put("Shph", "ষ্ফ");
        mapJuktoBorno.put("Shf", "ষ্ফ");
        mapJuktoBorno.put("Shw", "ষ্ব");
        mapJuktoBorno.put("Shm", "ষ্ম");
        mapJuktoBorno.put("sk", "স্ক");
        mapJuktoBorno.put("sht", "শ্ত");
        mapJuktoBorno.put("St", "শ্ত");
        mapJuktoBorno.put("shn", "শ্ন");
        mapJuktoBorno.put("Sn", "শ্ন");
        mapJuktoBorno.put("shw", "শ্ব");
        mapJuktoBorno.put("Sw", "শ্ব");
        mapJuktoBorno.put("shm", "শ্ম");
        mapJuktoBorno.put("Sm", "শ্ম");
        mapJuktoBorno.put("shy", "শ্য");
        mapJuktoBorno.put("shZ", "শ্য");
        mapJuktoBorno.put("Sy", "শ্য");
        mapJuktoBorno.put("SZ", "শ্য");
        mapJuktoBorno.put("shr", "শ্র");
        mapJuktoBorno.put("Sr", "শ্র");
        mapJuktoBorno.put("shl", "শ্ল");
        mapJuktoBorno.put("Sl", "শ্ল");
        mapJuktoBorno.put("Shk", "ষ্ক");
        mapJuktoBorno.put("Shkr", "ষ্ক্র");
        mapJuktoBorno.put("ShT", "ষ্ট");
        mapJuktoBorno.put("sf", "স্ফ");
        mapJuktoBorno.put("sph", "স্ফ");
        mapJuktoBorno.put("sw", "স্ব");
        mapJuktoBorno.put("sm", "স্ম");
        mapJuktoBorno.put("sy", "স্য");
        mapJuktoBorno.put("sZ", "স্য");
        mapJuktoBorno.put("sr", "স্র");
        mapJuktoBorno.put("sl", "স্ল");
        mapJuktoBorno.put("skl", "স্ক্ল");
        mapJuktoBorno.put("hN", "হ্ণ");
        mapJuktoBorno.put("hn", "হ্ন");
        mapJuktoBorno.put("skr", "স্ক্র");
        mapJuktoBorno.put("sT", "স্ট");
        mapJuktoBorno.put("sTr", "স্ট্র");
        mapJuktoBorno.put("skh", "স্খ");
        mapJuktoBorno.put("st", "স্ত");
        mapJuktoBorno.put("stw", "স্ত্ব");
        mapJuktoBorno.put("sty", "স্ত্য");
        mapJuktoBorno.put("stZ", "স্ত্য");
        mapJuktoBorno.put("sth", "স্থ");
        mapJuktoBorno.put("sthy", "স্থ্য");
        mapJuktoBorno.put("sthZ", "স্থ্য");
        mapJuktoBorno.put("sn", "স্ন");
        mapJuktoBorno.put("sp", "স্প");
        mapJuktoBorno.put("hw", "হ্ব");
        mapJuktoBorno.put("hm", "হ্ম");
        mapJuktoBorno.put("hy", "হ্য");
        mapJuktoBorno.put("hZ", "হ্য");
        mapJuktoBorno.put("hr", "হ্র");
        mapJuktoBorno.put("hl", "হ্ল");
        mapJuktoBorno.put("hrri", "হৃ");
        mapJuktoBorno.put("gru", "গ্রু");
        mapJuktoBorno.put("grU", "গ্রূ");
        mapJuktoBorno.put("lTr", "ল্ট্র");
        mapJuktoBorno.put("cr", "চ্র");
        mapJuktoBorno.put("Cr", "চ্র");
        mapJuktoBorno.put("nDr", "ন্ড্র");
        mapJuktoBorno.put("dvr", "দ্ভ্র");
        mapJuktoBorno.put("chr", "ছ্র");
        mapJuktoBorno.put("dbhr", "দ্ভ্র");

    }

    /**
     * A function for calling the main Convert function for this test case only.
     * It won't be used in our keyboard as there we need word by word conversion only :)
     *
     * @param englishTextToParse -> English string to convert
     * @return Converted Bangla String
     */
    public static String parse(String englishTextToParse) {

        BanglaPhoneticParser tempParser = new BanglaPhoneticParser();
        String convertedText = "";

        int i=0,space=0,length=englishTextToParse.length();
        for(i=0;i<length;i++)
        {
            if(englishTextToParse.charAt(i)==' ')
                space++;
            else
                break;
        }

        if(space!=0)
        {
            for(i=0;i<space;i++)
                convertedText=convertedText+" ";

            if(space==length) return convertedText;
            else
                englishTextToParse=englishTextToParse.substring(space+1,englishTextToParse.length());

        }

        String[] separatedWord = englishTextToParse.split(" ");
        for (i = 0; i < separatedWord.length - 1; i++)
            convertedText = convertedText + tempParser.Convert(separatedWord[i]) + " ";

        convertedText = convertedText + tempParser.Convert(separatedWord[i]);

        return convertedText;
    }

    /**
     * Does the actual English to Bangla Phonetic Conversion
     *
     * @param textToConvert -> English string to convert
     * @return Converted Bangla String
     */
    private StringBuilder Convert(String textToConvert) {

        //Je character gular lower and uppercase e same conversion tader sob lowercase e convert kora hocche
        textToConvert = textToConvert.
                replace("A", "a").
                replace("B", "b").
                replace("B", "b").
                replace("B", "b").
                replace("C", "c").
                replace("E", "e").
                replace("F", "f").
                replace("G", "g").
                replace("H", "h").
                replace("J", "j").
                replace("K", "k").
                replace("L", "l").
                replace("M", "m").
                replace("P", "p").
                replace("Q", "q").
                replace("V", "v").
                replace("Y", "y").
                replace("X", "x");
        //TODO: Upore redundant mapping bad dite hobe (sample: we don't need to map both 'a' and 'A' as only 'a' is real :P)
        //Je character gular lower and uppercase e same conversion tader sob lowercase e convert kora hocche

        StringBuilder s = new StringBuilder(textToConvert);

        // converting x
        s = changeX(s.toString(), "x");
        // converting x

        // 5 char er gula
        for (String aFiveChar : fiveChar)
            s = change(s.toString(), aFiveChar, mapJuktoBorno.get(aFiveChar));
        // 5 char er gula

        // 4 char er gula
        for (String aFourChar : fourChar)
            s = change(s.toString(), aFourChar, mapJuktoBorno.get(aFourChar));
        // 4 char er gula

        // 3char er gula
        for (String aThreeChar : threeChar)
            s = change(s.toString(), aThreeChar, mapJuktoBorno.get(aThreeChar));

        s = changeSorborno(s.toString(), "rri");
        // 3char er gula

        // 2char er gulan
        for (String aTwoChar : twoChar)
            if (mapSorborno.containsKey(aTwoChar))
                s = changeSorborno(s.toString(), aTwoChar);
            else if (mapByanjonBorno.containsKey(aTwoChar))
                s = change(s.toString(), aTwoChar, mapByanjonBorno.get(aTwoChar));
            else if (mapJuktoBorno.containsKey(aTwoChar))
                s = change(s.toString(), aTwoChar, mapJuktoBorno.get(aTwoChar));
        // 2char er gula

        // 1char er gula
        for (String aOneChar : oneChar)
            if (mapSorborno.containsKey(aOneChar))
                s = changeSorborno(s.toString(), aOneChar);
            else if (mapByanjonBorno.containsKey(aOneChar))
                s = change(s.toString(), aOneChar, mapByanjonBorno.get(aOneChar));
        // 1char er gula


        // o re replace krtesi
        s = change(s.toString(), "o", "");
        // o re replace krtesi

        return s;

    }

    /**
     * Replaces all occurrences of a substring ch with nch in string txt
     */
    private StringBuilder change(String txt, String ch, String nch) {
        String temp = txt.replace(ch, nch);
        return new StringBuilder(temp);
    }

    /**
     * Handles sorborno and kar related changes
     */
    private StringBuilder changeSorborno(String txt, String ch) {

        StringBuilder sx = new StringBuilder();
        sx.append(txt);
        if (ch.compareToIgnoreCase("a") == 0) {
            StringBuilder asx = new StringBuilder();
            for (int i = 0; i < txt.length(); i++) {
                if (i == 0) {
                    if (("" + txt.charAt(i)).compareToIgnoreCase("a") == 0) {
                        asx.append("আ");
                    } else
                        asx.append(txt.charAt(i));
                } else {
                    if (("" + txt.charAt(i)).compareToIgnoreCase("a") == 0 && (mapSorborno.containsValue("" + txt.charAt(i - 1)) || mapSorborno.containsKey("" + txt.charAt(i - 1))
                            || mapKar.containsKey("" + txt.charAt(i - 1)) || mapKar
                            .containsValue("" + txt.charAt(i - 1)))) {
                        if (("" + txt.charAt(i - 1)).compareTo("আ") == 0 || ("" + txt
                                .charAt(i - 1)).compareTo("া") == 0 || ("" + txt
                                .charAt(i - 1)).compareTo("a") == 0 || ("" + txt
                                .charAt(i - 1)).compareTo("A") == 0)
                            asx.append("আ");
                        else
                            asx.append("য়া");
                    } else if (("" + txt.charAt(i)).compareToIgnoreCase("a") == 0 && (mapByanjonBorno.containsValue("" + txt.charAt(i - 1)) || mapByanjonBorno.containsKey("" + txt.charAt(i - 1))
                            || mapJuktoBorno.containsKey("" + txt.charAt(i - 1)) || mapJuktoBorno
                            .containsValue("" + txt.charAt(i - 1))))
                        asx.append("া");
                    else
                        asx.append(txt.charAt(i));
                }
            }
            return asx;
        } else {
            int ofe = sx.indexOf(ch, 0);

            for (int ofs = 0; ofs < txt.length() && ofe != -1; ofs = ofe + 1) {
                ofe = sx.indexOf(ch, ofs);
                if (ofe == -1)
                    break;
                else {
                    if (ofe == 0)
                        sx.replace(ofe, ofe + ch.length(), mapSorborno.get(ch));
                    else {

                        if (ch.compareTo("o") == 0
                                && (mapSorborno.containsValue("" + txt.charAt(ofe - 1))
                                || mapSorborno.containsKey("" + txt.charAt(ofe - 1))
                                || mapKar.containsKey("" + txt.charAt(ofe - 1)) || mapKar
                                .containsValue("" + txt.charAt(ofe - 1)))) {

                            sx.replace(ofe, ofe + 1, "ও");
                        } else if (mapSorborno.containsKey("" + txt.charAt(ofe - 1))
                                || mapSorborno.containsValue("" + txt.charAt(ofe - 1))
                                || mapKar.containsKey("" + txt.charAt(ofe - 1))
                                || mapKar.containsValue("" + txt.charAt(ofe - 1))
                                || ("" + txt.charAt(ofe - 1)).compareTo("o") == 0) {

                            sx.replace(ofe, ofe + ch.length(), mapSorborno.get(ch));
                        } else {
                            if (("" + txt.charAt(ofe)).compareTo("o") != 0) {
                                sx.replace(ofe, ofe + ch.length(), mapKar.get(ch));

                            }

                        }
                    }
                }

            }
        }
        return sx;
    }

    /**
     * Handles how x is converted
     */
    private StringBuilder changeX(String txt, String ch) {
        //TODO: x kothay bosle kemne convert hobe sei rule ta thikvabe identify korte hobe
        String s;
        StringBuilder sx = new StringBuilder();

        for (int i = 0; i < txt.length(); i++) {
            if (i == 0) {
                if (("" + txt.charAt(i)).compareToIgnoreCase("x") == 0) {
                    sx.append("এক্স");
                } else
                    sx.append(txt.charAt(i));
            } else {
                if (("" + txt.charAt(i)).compareToIgnoreCase("x") == 0) {
                    if (isAlphabet(txt.charAt(i - 1)))
                        sx.append("ক্স");
                    else
                        sx.append("এক্স");
                } else
                    sx.append(txt.charAt(i));
            }
        }
        return sx;
    }


    private boolean isAlphabet(int code) {
        return Character.isLetter(code);
    }

}