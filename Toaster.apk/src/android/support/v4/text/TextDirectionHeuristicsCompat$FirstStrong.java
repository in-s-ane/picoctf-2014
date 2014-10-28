// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.text;


// Referenced classes of package android.support.v4.text:
//            TextDirectionHeuristicsCompat

private static class nAlgorithm
    implements nAlgorithm
{

    public static final nAlgorithm INSTANCE = new <init>();

    public int checkRtl(CharSequence charsequence, int i, int j)
    {
        int k = 2;
        int l = i;
        for (int i1 = i + j; l < i1 && k == 2; l++)
        {
            k = TextDirectionHeuristicsCompat.access$100(Character.getDirectionality(charsequence.charAt(l)));
        }

        return k;
    }


    private nAlgorithm()
    {
    }
}
