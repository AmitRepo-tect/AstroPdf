//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.astroganit.kundlilib.panchangamduration;


import java.util.Calendar;
import com.astroganit.kundlilib.purehoro.PureHoro;

public class PanchagamDuration extends PureHoro {
    private Calendar e;
    private int g;
    private int k;
    private int l;
    private int n;
    private int o;
    private int p;
    private int q;
    private int[] r = new int[9];
    private int[] t = new int[9];
    private String u;
    private int v;
    private int w;
    private int x;
    private int z;
    private int A;
    private int B;
    private int C;
    private String D;
    private int E;
    private int F;
    private int G;
    private int H;
    private int I;
    private String[] J = new String[9];
    private float[] K = new float[9];
    private String L;

    public PanchagamDuration() {
    }

    public StringBuffer getKPPanchang(int planet) {
        double var2 = this.plnt[planet + 13] - this.plnt[planet];
        double var4 = this.plnt[planet] % 13.333D;
        double var6 = 13.333D - var4;
        double var8 = 3600.0D / var2;
        int var26 = (int)(-var4 * var8);
        int var3 = (int)(var6 * var8);
        String[] var31 = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
        this.e.add(13, var26);
        this.q = this.e.get(1);
        this.w = this.e.get(2) + 1;
        this.u = var31[this.w - 1];
        this.x = this.e.get(5);
        this.z = this.e.get(11);
        this.A = this.e.get(12);
        this.B = this.e.get(13);
        Calendar var27;
        (var27 = Calendar.getInstance()).set(this.l, this.k - 1, this.g, this.n, this.o, this.p);
        var27.add(13, var3);
        this.C = var27.get(1);
        this.v = var27.get(2) + 1;
        this.D = var31[this.v - 1];
        this.E = var27.get(5);
        this.F = var27.get(11);
        this.G = var27.get(12);
        this.H = var27.get(13);
        this.I = this.getNakshatra(this.plnt[planet]);
        this.L = this.nak[this.I];
        StringBuffer var28;
        (var28 = new StringBuffer()).append("\n\t\t       K. P.  P A N C H A N G\n ");
        var28.append("\t\t      ************************\n");
        var28.append("NAKSHATRA\t\t\tENTRY-TIME                \t\t EXIT-TIME\n");
        var28.append("---------\t\t\t----------                 \t\t------------\n");
        var28.append("            H\tMT\tSEC\tDAY\tMONTH\tYEAR        H\tMT\tSEC\tDAY\tMONTH\tYEAR\n\n");
        var28.append(this.L + "\t" + this.z + "\t" + this.A + "\t" + this.B + "\t" + this.x + "\t" + this.u + "\t\t" + this.q + "\t\t" + this.F + "\t" + this.G + "\t" + this.H + "\t" + this.E + "\t" + this.D + "\t\t" + this.C + "\n");
        System.out.println(var28);
        Calendar var30;
        (var30 = Calendar.getInstance()).set(this.q, this.w, this.x, this.z, this.A, this.B);
        Calendar var32;
        (var32 = Calendar.getInstance()).set(this.C, this.v, this.E, this.F, this.G, this.H);
        this.q = -this.q;
        this.w = -this.w;
        this.x = -this.x;
        this.z = -this.z;
        this.A = -this.A;
        this.B = -this.B;
        var32.add(1, this.q);
        var32.add(2, this.w);
        var32.add(5, this.x);
        var32.add(10, this.z);
        var32.add(12, this.A);
        var32.add(13, this.B);
        int var5 = var32.get(5);
        System.out.println("difday" + var5);
        int var35 = var27.get(11);
        int var7 = var27.get(12);
        var26 = var27.get(13);
        double var21 = 0.0D;
        if (planet == 1) {
            var21 = (double)(var5 * 24 * 60 + this.z * 60 + this.A + this.B / 60 + this.F * 60 + this.G + this.H / 60);
        } else if (planet == 2) {
            if (var32.get(5) != 0) {
                var21 = (double)(1440 + this.z * 60 + this.A + this.B / 60 + this.F * 60 + this.G + this.H / 60);
                System.out.println("PPPP");
            } else {
                var21 = (double)(var35 * 60 + var7 + var26 / 60);
            }
        }

        planet = this.I % 9;
        this.J[0] = this.x1[planet];
        double var24 = var21 / 120.0D;
        double[] var29;
        (var29 = new double[9])[0] = this.y1[planet] * var24;
        double[] var33 = new double[9];
        double[] var34 = new double[9];
        var33[0] = Math.floor(var29[0]);
        var34[0] = var29[0] - Math.floor(var29[0]);
        int[] var36;
        (var36 = new int[9])[0] = (int)(var33[0] * 60.0D + var34[0] * 60.0D);
        var30.add(13, var36[0]);
        int[] var37 = new int[9];
        int[] var38 = new int[9];
        this.r[0] = var30.get(5);
        this.t[0] = var30.get(11);
        var37[0] = var30.get(12);
        var38[0] = var30.get(13);
        this.K[0] = (float)(var37[0] + var38[0] / 60);
        ++planet;
        int var9 = 1;

        int var10;
        for(var10 = planet; var10 < 9; ++var10) {
            this.J[var9] = this.x1[var10];
            var29[var9] = this.y1[var10] * var24;
            var33[var9] = Math.floor(var29[var9]);
            var34[var9] = var29[var9] - Math.floor(var29[var9]);
            var36[var9] = (int)(var33[var9] * 60.0D + var34[var9] * 60.0D);
            var30.add(13, var36[var9]);
            this.r[var9] = var30.get(5);
            this.t[var9] = var30.get(11);
            var37[var9] = var30.get(12);
            var38[var9] = var30.get(13);
            this.K[var9] = (float)(var37[var9] + var38[var9] / 60);
            ++var9;
        }

        if (planet - 1 != 0) {
            for(var10 = 0; var10 < planet - 1; ++var10) {
                this.J[var9] = this.x1[var10];
                var29[var9] = this.y1[var10] * var24;
                var33[var9] = Math.floor(var29[var9]);
                var34[var9] = var29[var9] - Math.floor(var29[var9]);
                var36[var9] = (int)(var33[var9] * 60.0D + var34[var9] * 60.0D);
                var30.add(13, var36[var9]);
                this.r[var9] = var30.get(5);
                this.t[var9] = var30.get(11);
                var37[var9] = var30.get(12);
                var38[var9] = var30.get(13);
                this.K[var9] = (float)(var37[var9] + var38[var9] / 60);
                ++var9;
            }
        }

        StringBuffer var39;
        (var39 = new StringBuffer()).append("<TABLE width='90%'><TR>\n");
        var39.append("<TH>NAKSHATRA LORD<TH>SUB LORD<TH>TIME\n");
        var39.append("<TR>\n");
        var39.append("<TD>&nbsp;<TD>&nbsp;<TD>DAY - H : MT\n");

        for(planet = 0; planet < 9; ++planet) {
            var39.append("<TR>\n");
            var39.append("<TD>" + this.J[0] + "<TD>" + this.J[planet] + "<TD>" + this.r[planet] + " - " + this.t[planet] + " : " + this.K[planet] + "\n");
        }

        var39.append("</TABLE>\n");
        return var39;
    }

    public int getMonthNo(int monthNo) {
        return (new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11})[monthNo - 1];
    }

    public String[] getNakshatraEndTime() {
        return new String[]{String.valueOf(this.F), String.valueOf(this.G), String.valueOf(this.H), String.valueOf(this.E), this.D, String.valueOf(this.C)};
    }

    public String getNakshatraForPanchagam() {
        return this.L;
    }

    public String getNakshatraLordForPanchagam() {
        return this.J[0];
    }

    public String[] getNakshatraStartTime() {
        return new String[]{String.valueOf(this.z), String.valueOf(this.A), String.valueOf(this.B), String.valueOf(this.x), this.u, String.valueOf(this.q)};
    }

    public int[] getPanchagamDay() {
        return this.r;
    }

    public int[] getPanchagamHour() {
        return this.t;
    }

    public float[] getPanchagamMinute() {
        return this.K;
    }

    public String[] getSubLordForPanchagam() {
        return this.J;
    }


    public void setPanchagamTime() {
        this.g = this.d;
        this.k = this.m;
        this.l = this.y;
        this.n = this.h;
        this.o = this.mt;
        this.p = this.s;
        this.e = Calendar.getInstance();
        this.e.set(this.l, this.k - 1, this.g, this.n, this.o, this.p);
    }
}
