public class IAStore extends Instruction {

    private int n;

    public IAStore(int n){
        this.n = n;
    }

    @Override
    String instName() {
        return "iastore " + n;
    }

    @Override
    void execute() {
        Data aRef = VM.opStack[VM.top-n-1];
        Data [] ei = new Data[n];
        for( int i = 1; i <= n; i++){
            ei[i-1] = VM.opStack[VM.top-n-1+i];
        }
        Data e = VM.opStack[VM.top];
        VM.top = VM.top - (n+2);
        IntArray a = (IntArray) aRef;
        a.a[getRank(ei, a.sizeList)] = (Int)e;
        VM.pc++;

        VM.updateOpStackPeakSize();
    }

    public int getRank(Data [] ei, int [] size ){
        int rank = 0;
        for( int i = 0; i < ei.length; i++ ){
            Int b = (Int)ei[i];
            rank = rank * size[i] + b.val;
        }
        return rank;
    }
}
