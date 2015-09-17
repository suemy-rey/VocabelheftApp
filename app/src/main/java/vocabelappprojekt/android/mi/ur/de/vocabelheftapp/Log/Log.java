package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Log;

public class Log
{
    public static final boolean DEBUG = true;
    public static final String TAG = "Vocab";

    public static void e(String tag, String msg)
    {
        if (DEBUG)
        {
            android.util.Log.e(tag, msg);
        }
    }

    public static void e(String msg)
    {
        if (DEBUG)
        {
            android.util.Log.e(TAG, msg);
        }
    }
}