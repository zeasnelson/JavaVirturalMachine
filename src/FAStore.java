public class FAStore extends Instruction {

    private int n;

    public FAStore(int n){
        this.n = n;
    }

    @Override
    String instName() {
        return "fastore " + n;
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
        FloatArray a = (FloatArray) aRef;
        int rank = getRank(ei, a.sizeList);

        if( rank > a.sizeList.length || rank < 0 ){
            System.out.println("Error: Attempt to store into array element at out-of-bounds index " + rank + " on array length: " + a.a.length);
            VM.pc = 5000;
            return;
        }

        a.a[rank] = (Floatp)e;
        VM.pc++;
        VM.updateOpStackPeakSize();
    }

    public int getRank(Data [] ei, int [] size ){
        int rank = 0;
        for( int i = 0; i < ei.length; i++ ){
            Int b = (Int)ei[i];
            rank = rank*size[i]+b.val;
        }
        return rank;
    }
}
