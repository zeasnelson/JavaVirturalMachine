public class AStore extends Instruction{

    private int k;

    public AStore(int k){
        this.k = k;
    }

    @Override
    String instName() {
        return "astore " + k;
    }

    @Override
    void execute() {
        Data aRef = VM.opStack[VM.top];
        VM.top--;
        VM.runtimeStack[VM.topR].vars[k] = aRef;
        VM.pc++;
        VM.updateOpStackPeakSize();
    }
}
