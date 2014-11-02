// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.text;


// Referenced classes of package android.support.v4.text:
//            TextDirectionHeuristicsCompat

private static class mLookForRtl
    implements ionAlgorithm
{

    public static final mLookForRtl INSTANCE_LTR = new <init>(false);
    public static final <init> INSTANCE_RTL = new <init>(true);
    private final boolean mLookForRtl;

    public int checkRtl(CharSequence charsequence, int i, int j)
    {
        int k;
        boolean flag;
        int l;
        int i1;
        k = 1;
        flag = false;
        l = i;
        i1 = i + j;
_L6:
        if (l >= i1) goto _L2; else goto _L1
_L1:
        TextDirectionHeuristicsCompat.access$200(Character.getDirectionality(charsequence.charAt(l)));
        JVM INSTR tableswitch 0 1: default 56
    //                   0 62
    //                   1 81;
           goto _L3 _L4 _L5
_L3:
        l++;
          goto _L6
_L4:
        if (!mLookForRtl) goto _L8; else goto _L7
_L7:
        k = 0;
_L10:
        return k;
_L8:
        flag = true;
          goto _L3
_L5:
        if (!mLookForRtl) goto _L10; else goto _L9
_L9:
        flag = true;
          goto _L3
_L2:
        if (!flag) goto _L12; else goto _L11
_L11:
        if (!mLookForRtl)
        {
            return 0;
        }
          goto _L10
_L12:
        return 2;
          goto _L3
    }


    private ionAlgorithm(boolean flag)
    {
        mLookForRtl = flag;
    }
}
