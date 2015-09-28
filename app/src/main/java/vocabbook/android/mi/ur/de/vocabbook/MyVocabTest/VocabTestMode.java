package vocabbook.android.mi.ur.de.vocabbook.MyVocabTest;

public class VocabTestMode
{
    public enum testMode
    {
        VOCAB_TEST,
        TRANSLATE_TEST
    }

    public static testMode currentMode = testMode.VOCAB_TEST;

    public static boolean isState(testMode mode)
    {
        if (currentMode == mode)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void changeMode(testMode newMode)
    {
        if (currentMode == newMode)
        {
            return;
        }
        else
        {
            currentMode = newMode;
        }
        return;
    }

    public static boolean isVocabTest()
    {
        return isState(testMode.VOCAB_TEST);
    }

    public static boolean isTranslateTest()
    {
        return isState(testMode.TRANSLATE_TEST);
    }
}