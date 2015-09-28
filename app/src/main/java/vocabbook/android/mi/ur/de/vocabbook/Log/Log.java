package vocabbook.android.mi.ur.de.vocabbook.Log;

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