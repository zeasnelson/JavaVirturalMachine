final class Floatp extends Data
{
    double val;

    Floatp(double d)
    {
        val = d;
    }

    public String toString()
    {
        return String.valueOf(val);
    }

    Floatp cloneData()
    {
        return new Floatp(val);
    }
}