//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.astroganit.kundlilib.matchmilan;

import com.astroganit.kundlilib.util.Constants;
import com.astroganit.kundlilib.util.ProcessingErrorException;

public class MatchMaking implements IMatchMaking {
    private String a = "0";
    public String[] areaOfLife = new String[8];
    private double b;
    protected String[] arrNadi = new String[3];
    protected String[] tara = new String[9];
    protected String[] arrGana = new String[3];
    protected String[] GrahaName = new String[7];
    protected String[] yoni = new String[14];
    protected String[] vashya = new String[5];
    protected String[] varna = new String[4];

    public MatchMaking() {
        try {
            this.initializesArrayValues();
        } catch (Exception var1) {
        }
    }

    public boolean calculateCompatibilityForMangalDosh(int maleMangalDoshNumber, int femaleMangalDoshNumber) {
        return Math.abs(maleMangalDoshNumber - femaleMangalDoshNumber) <= 1;
    }

    public int calculateGana(double moonDeg) {
        int moonDeg1 = (int)(moonDeg / 13.333333D) + 1;
        byte var2 = -1;
        switch(moonDeg1) {
        case 1:
        case 5:
        case 7:
        case 8:
        case 13:
        case 15:
        case 17:
        case 22:
        case 27:
            var2 = 0;
            break;
        case 2:
        case 4:
        case 6:
        case 11:
        case 12:
        case 20:
        case 21:
        case 25:
        case 26:
            var2 = 1;
            break;
        case 3:
        case 9:
        case 10:
        case 14:
        case 16:
        case 18:
        case 19:
        case 23:
        case 24:
            var2 = 2;
        }

        return var2;
    }

    public int calculateGraha(int ras) {
        byte var2 = -1;
        switch(ras) {
        case 1:
        case 8:
            var2 = 2;
            break;
        case 2:
        case 7:
            var2 = 5;
            break;
        case 3:
        case 6:
            var2 = 3;
            break;
        case 4:
            var2 = 1;
            break;
        case 5:
            var2 = 0;
            break;
        case 9:
        case 12:
            var2 = 4;
            break;
        case 10:
        case 11:
            var2 = 6;
        }

        return var2;
    }

    public int calculateNadi(double moonDeg) {
        int moonDeg1 = (int)(moonDeg / 13.333333D) + 1;
        byte var2 = -1;
        switch(moonDeg1) {
        case 1:
        case 6:
        case 7:
        case 12:
        case 13:
        case 18:
        case 19:
        case 24:
        case 25:
            var2 = 0;
            break;
        case 2:
        case 5:
        case 8:
        case 11:
        case 14:
        case 17:
        case 20:
        case 23:
        case 26:
            var2 = 1;
            break;
        case 3:
        case 4:
        case 9:
        case 10:
        case 15:
        case 16:
        case 21:
        case 22:
        case 27:
            var2 = 2;
        }

        return var2;
    }

    public int calculateNak(double moonDeg) {
        int moonDeg1;
        return (moonDeg1 = (int)(moonDeg / 13.333333D)) + 1;
    }

    public int[] CalculateTara(double degOfBoy, double degOfGirl) {
        int degOfBoy1 = this.calculateNak(degOfBoy);
        int var2 = this.calculateNak(degOfGirl);
        int[] degOfGirl1 = new int[2];
        int var4 = degOfBoy1 - var2 + 1;
        degOfBoy1 = var2 - degOfBoy1 + 1;
        if (var4 <= 0) {
            var4 += 27;
        }

        if (degOfBoy1 <= 0) {
            degOfBoy1 += 27;
        }

        var2 = degOfBoy1 % 9;
        degOfBoy1 = var4 % 9;
        degOfGirl1[0] = var2;
        degOfGirl1[1] = degOfBoy1;
        return degOfGirl1;
    }

    public int calculateVarna(int rasi) {
        if (rasi != 4 && rasi != 8 && rasi != 12) {
            if (rasi != 1 && rasi != 5 && rasi != 9) {
                return rasi != 2 && rasi != 6 && rasi != 10 ? 3 : 2;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    public int calculateVashya(double moonDeg) throws ProcessingErrorException {
        if (moonDeg < 60.0D || moonDeg >= 255.0D && moonDeg < 285.0D) {
            return 0;
        } else if (moonDeg >= 60.0D && moonDeg < 90.0D || moonDeg >= 150.0D && moonDeg < 210.0D || moonDeg >= 240.0D && moonDeg < 255.0D || moonDeg >= 300.0D && moonDeg < 330.0D) {
            return 1;
        } else if (moonDeg >= 90.0D && moonDeg < 120.0D || moonDeg >= 285.0D && moonDeg < 300.0D || moonDeg >= 330.0D && moonDeg < 360.0D) {
            return 2;
        } else if (moonDeg >= 120.0D && moonDeg < 150.0D) {
            return 3;
        } else if (moonDeg >= 210.0D && moonDeg < 240.0D) {
            return 4;
        } else {
            throw new ProcessingErrorException(Constants.ProcessingErrorMessage);
        }
    }

    public int calculateYoni(double moonDeg) {
        int moonDeg1 = (int)(moonDeg / 13.333333D) + 1;
        byte var2 = -1;
        switch(moonDeg1) {
        case 1:
        case 24:
            var2 = 0;
            break;
        case 2:
        case 27:
            var2 = 1;
            break;
        case 3:
        case 8:
            var2 = 2;
            break;
        case 4:
        case 5:
            var2 = 3;
            break;
        case 6:
        case 19:
            var2 = 4;
            break;
        case 7:
        case 9:
            var2 = 5;
            break;
        case 10:
        case 11:
            var2 = 6;
            break;
        case 12:
        case 26:
            var2 = 7;
            break;
        case 13:
        case 15:
            var2 = 8;
            break;
        case 14:
        case 16:
            var2 = 9;
            break;
        case 17:
        case 18:
            var2 = 10;
            break;
        case 20:
        case 22:
            var2 = 11;
            break;
        case 21:
            var2 = 12;
            break;
        case 23:
        case 25:
            var2 = 13;
        }

        return var2;
    }

    public String getAreaOfLife(int areaOfLife) {
        return this.areaOfLife[areaOfLife - 1];
    }

    public String getGana(double moonDeg) {
        return this.arrGana[this.calculateGana(moonDeg)];
    }

    public String getGraha(int ras) {
        return this.GrahaName[this.calculateGraha(ras)];
    }

    public int getMaximumBhakoot() {
        return 7;
    }

    public int getMaximumGana() {
        return 6;
    }

    public int getMaximumGrahaMaitri() {
        return 5;
    }

    public int getMaximumNadi() {
        return 8;
    }

    public int getMaximumTara() {
        return 3;
    }

    public int getMaximumVarna() {
        return 1;
    }

    public int getMaximumVashya() {
        return 2;
    }

    public int getMaximumYoni() {
        return 4;
    }

    public String getNadi(double moonDeg) {
        return this.arrNadi[this.calculateNadi(moonDeg)];
    }

    public String[] getTara(double degOfBoy, double degOfGirl) {
        int degOfBoy1 = this.calculateNak(degOfBoy);
        int var2 = this.calculateNak(degOfGirl);
        String[] degOfGirl1 = new String[2];
        int var4 = degOfBoy1 - var2 + 1;
        degOfBoy1 = var2 - degOfBoy1 + 1;
        if (var4 <= 0) {
            var4 += 27;
        }

        if (degOfBoy1 <= 0) {
            degOfBoy1 += 27;
        }

        var2 = degOfBoy1 % 9;
        degOfBoy1 = var4 % 9;
        degOfGirl1[0] = this.tara[var2];
        degOfGirl1[1] = this.tara[degOfBoy1];
        return degOfGirl1;
    }

    public double getTotal() {
        return this.b;
    }

    public String getVarna(int rasi) {
        return this.varna[this.calculateVarna(rasi)];
    }

    public String getVashya(double moonDeg) {
        int var3 = -1;

        try {
            var3 = this.calculateVashya(moonDeg);
        } catch (Exception var4) {
        }

        return this.vashya[var3];
    }

    public String getYoni(double moonDeg) {
        return this.yoni[this.calculateYoni(moonDeg)];
    }

    public void initializesArrayValues() throws Exception {
       /* Constants var1 = new Constants();
        Constants.LoadProperties(this.a);*/
        this.varna = new String[]{"ब्राहमण", "क्षत्रिय", "वैश्य", "शूद्र"};
        this.yoni = new String[]{"अश्व", "गज", "मेष", "सर्प", "श्वान", "विलाव", "मूषक", "गउ", "महिष", "व्याध", "मृग", "वानर", "नकुल", "सिंह"};
        this.vashya = new String[]{"चतुष्पद", "मानव", "जलचर", "वनचर", "कीट"};
        this.arrNadi = new String[]{"आदि", "मध्य", "अन्त"};
        this.tara = new String[]{"जन्म", "सम्पत", "विपत", "क्षेम", "प्रत्यय", "साधक", "वध", "मित्र", "अतिमित्र"};
        this.arrGana = new String[]{"देव", "मानव", "राक्षस"};
        this.GrahaName = new String[]{"सूर्य", "चन्द्र", "मंगल", "बुध", "गुरु", "शुक्र", "शनि"};
        this.areaOfLife = new String[]{"कार्यक्षेत्र", "आधिपत्य", "भाग्य", "मानसिकता", "सामंजस्य", "गुण", "प्रेम", "स्वास्थ्य"};
    }


    public int matchBhakutGuna(int maleRashi, int femaleRashi) {
        maleRashi = (new int[][]{{7, 0, 7, 7, 0, 0, 7, 0, 0, 7, 7, 0}, {0, 7, 0, 7, 7, 0, 0, 7, 0, 0, 7, 7}, {7, 0, 7, 0, 7, 7, 0, 0, 7, 0, 0, 7}, {7, 7, 0, 7, 0, 7, 7, 0, 0, 7, 0, 0}, {0, 7, 7, 0, 7, 0, 7, 7, 0, 0, 7, 0}, {0, 0, 7, 7, 0, 7, 0, 7, 7, 0, 0, 7}, {7, 0, 0, 7, 7, 0, 7, 0, 7, 7, 0, 0}, {0, 7, 0, 0, 7, 7, 0, 7, 0, 7, 7, 0}, {0, 0, 7, 0, 0, 7, 7, 0, 7, 0, 7, 7}, {7, 0, 0, 7, 0, 0, 7, 7, 0, 7, 0, 7}, {7, 7, 0, 0, 7, 0, 0, 7, 7, 0, 7, 0}, {0, 7, 7, 0, 0, 7, 0, 0, 7, 7, 0, 7}})[femaleRashi - 1][maleRashi - 1];
        this.b += (double)maleRashi;
        return maleRashi;
    }

    public int matchGanaGuna(double degOfBoy, double degOfGirl) {
        int degOfBoy1 = this.calculateGana(degOfBoy);
        int var2 = this.calculateGana(degOfGirl);
        degOfBoy1 = (new int[][]{{6, 5, 1}, {6, 6, 0}, {0, 0, 6}})[var2][degOfBoy1];
        this.b += (double)degOfBoy1;
        return degOfBoy1;
    }

    public double matchGrahaMitraGuna(int maleRashi, int femaleRashi) {
        maleRashi = this.calculateGraha(maleRashi);
        femaleRashi = this.calculateGraha(femaleRashi);
        double var6 = (new double[][]{{5.0D, 5.0D, 5.0D, 4.0D, 5.0D, 0.0D, 0.0D}, {5.0D, 5.0D, 4.0D, 1.0D, 4.0D, 0.5D, 0.5D}, {5.0D, 4.0D, 5.0D, 0.5D, 5.0D, 3.0D, 0.5D}, {4.0D, 1.0D, 0.5D, 5.0D, 0.5D, 5.0D, 4.0D}, {5.0D, 4.0D, 5.0D, 0.5D, 5.0D, 0.5D, 3.0D}, {0.0D, 0.5D, 3.0D, 5.0D, 0.5D, 5.0D, 5.0D}, {0.0D, 0.5D, 0.5D, 4.0D, 3.0D, 5.0D, 5.0D}})[femaleRashi][maleRashi];
        this.b += var6;
        return var6;
    }

    public int matchNadiGuna(double degOfBoy, double degOfGirl) {
        int degOfBoy1 = this.calculateNadi(degOfBoy);
        int var2 = this.calculateNadi(degOfGirl);
        if (degOfBoy1 == var2) {
            return 0;
        } else {
            this.b += 8.0D;
            return 8;
        }
    }

    public double matchTaraGuna(double degOfBoy, double degOfGirl) {
        int degOfBoy1 = this.calculateNak(degOfBoy);
        int var2 = this.calculateNak(degOfGirl);
        int degOfGirl1 = degOfBoy1 - var2 + 1;
        int var4 = var2 - degOfBoy1 + 1;
        if (degOfGirl1 <= 0) {
            degOfGirl1 += 27;
        }

        if (var4 <= 0) {
            var4 += 27;
        }

        if ((degOfBoy1 = degOfGirl1 % 9) == 0) {
            ++degOfBoy1;
        }

        boolean var6;
        if (degOfBoy1 != 1 && degOfBoy1 != 2 && degOfBoy1 != 4 && degOfBoy1 != 6 && degOfBoy1 != 8 && degOfBoy1 != 0) {
            var6 = false;
        } else {
            var6 = true;
        }

        boolean degOfBoy2;
        if ((degOfBoy1 = var4 % 9) != 1 && degOfBoy1 != 2 && degOfBoy1 != 4 && degOfBoy1 != 6 && degOfBoy1 != 8 && degOfBoy1 != 0) {
            degOfBoy2 = false;
        } else {
            degOfBoy2 = true;
        }

        if (degOfBoy2 && var6) {
            this.b += 3.0D;
            return 3.0D;
        } else if (!degOfBoy2 && !var6) {
            return 0.0D;
        } else if ((!degOfBoy2 || var6) && (degOfBoy2 || !var6)) {
            --this.b;
            return -1.0D;
        } else {
            ++this.b;
            return 1.5D;
        }
    }

    public int matchVarnaGuna(int maleRashi, int femaleRashi) {
        maleRashi = this.calculateVarna(maleRashi);
        if (this.calculateVarna(femaleRashi) >= maleRashi) {
            ++this.b;
            return 1;
        } else {
            return 0;
        }
    }

    public double matchVashyaGuna(double degOfBoy, double degOfGirl) throws ProcessingErrorException {
        int degOfBoy1 = this.calculateVashya(degOfBoy);
        int var2 = this.calculateVashya(degOfGirl);
        double var8 = (new double[][]{{2.0D, 1.0D, 1.0D, 0.5D, 1.0D}, {1.0D, 2.0D, 0.5D, 0.0D, 1.0D}, {1.0D, 0.5D, 2.0D, 1.0D, 1.0D}, {0.0D, 0.0D, 1.0D, 2.0D, 0.0D}, {1.0D, 0.0D, 1.0D, 0.0D, 2.0D}})[var2][degOfBoy1];
        this.b += var8;
        return var8;
    }

    public int matchYoniGuna(double degOfBoy, double degOfGirl) {
        int degOfBoy1 = this.calculateYoni(degOfBoy);
        int var2 = this.calculateYoni(degOfGirl);
        degOfBoy1 = (new int[][]{{4, 2, 3, 2, 2, 3, 3, 3, 0, 1, 3, 2, 2, 1}, {2, 4, 3, 2, 2, 3, 3, 3, 3, 1, 3, 2, 2, 0}, {3, 3, 4, 2, 2, 3, 3, 3, 0, 1, 3, 0, 2, 1}, {2, 2, 2, 4, 2, 1, 1, 2, 2, 2, 2, 1, 0, 2}, {2, 2, 2, 2, 4, 1, 2, 2, 2, 2, 0, 2, 2, 2}, {3, 3, 3, 1, 1, 4, 0, 3, 3, 2, 3, 2, 2, 2}, {3, 3, 3, 1, 2, 0, 4, 3, 3, 2, 3, 2, 1, 1}, {3, 3, 3, 2, 2, 3, 3, 4, 3, 0, 3, 2, 2, 1}, {0, 3, 3, 2, 2, 3, 3, 3, 4, 1, 1, 2, 2, 1}, {1, 1, 1, 2, 2, 2, 2, 1, 1, 4, 1, 2, 2, 1}, {3, 3, 3, 2, 0, 3, 2, 3, 3, 1, 4, 2, 2, 3}, {2, 2, 0, 1, 2, 2, 2, 2, 2, 2, 2, 4, 2, 2}, {2, 2, 2, 0, 2, 2, 1, 2, 2, 2, 2, 2, 4, 2}, {1, 0, 1, 2, 2, 2, 2, 1, 1, 2, 1, 2, 2, 4}})[var2][degOfBoy1];
        this.b += (double)degOfBoy1;
        return degOfBoy1;
    }

    public void setLanguageCode(String lang) {
        this.a = lang;
    }
}
