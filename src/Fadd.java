final class Fadd extends ArithInst
{
    String instName()
    {
        return "fadd";
    }

    void execute()
    {
        ((Floatp)(VM.opStack[VM.top-1])).val = ((Floatp)(VM.opStack[VM.top-1])).val + ((Floatp)(VM.opStack[VM.top])).val;
        VM.opStack[VM.top] = null;
        VM.top--;
        VM.pc++;
    }
}