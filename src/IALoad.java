public class IALoad extends Instruction {

    private int n;

    public IALoad(int n){
        this.n = n;
    }

    @Override
    String instName() {
        return "iaload";
    }

    @Override
    void execute() {
        Data aRef = VM.opStack[VM.top-n];
        Data [] ei = new Data[n];
        for( int i = 0; i < n; i++ ){
            ei[i] = VM.opStack[VM.top-n+(i+1)];
        }
        VM.top = VM.top-n;
        IntArray array = (IntArray)aRef;

        int rank = getRank(ei, array.sizeList);
        IntArray sizeList = (IntArray)aRef;
        if( sizeList.a[rank] == null ){
            System.out.println("Error: Attempt to load array element at out-of-bounds index,");
            VM.pc = 5000;
            return;
        }
        VM.opStack[VM.top] = sizeList.a[rank].cloneData();
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
