public class ALoad extends Instruction{

    private int k;

    public ALoad(int k){
        this.k = k;
    }

    @Override
    String instName() {
        return "aload " + k;
    }

    @Override
    void execute() {
        Data aRef = VM.runtimeStack[VM.topR].vars[k];
        VM.opStack[++VM.top] = aRef;
        VM.pc++;
        VM.updateOpStackPeakSize();
    }
}
