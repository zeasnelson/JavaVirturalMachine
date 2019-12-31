public class FALoad extends Instruction {

    private int n;

    public FALoad(int n){
        this.n = n;
    }


    @Override
    String instName() {
        return "faload";
    }

    @Override
    void execute() {
        Data aRef = VM.opStack[VM.top-n];
        Data [] ei = new Data[n];
        for( int i = 0; i < n; i++ ){
            ei[i] = VM.opStack[VM.top-n+(i+1)];
        }
        VM.top = VM.top-n;
        FloatArray fArray = (FloatArray) aRef;
        int rank = getRank(ei, fArray.sizeList);
        VM.opStack[VM.top] = ((FloatArray) aRef).a[rank].cloneData();
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
