final class Int extends Data
{
    int val;

    Int(int i)
    {
        val = i;
    }

    public String toString()
    {
        return String.valueOf(val);
    }

    Int cloneData()
    {
        return new Int(val);
    }
}