final class Print extends Instruction
{
    int index; // print value of vars[index]

    Print(int i)
    {
        index = i;
    }

    public String toString()
    {
        return "print "+index;
    }

    String instName()
    {
        return "print";
    }

    void execute()
    {
        System.out.println(VM.runtimeStack[VM.topR].vars[index]);
        VM.pc++;
    }
}