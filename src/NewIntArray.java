public class NewIntArray extends Instruction {

    private int n;

    public NewIntArray(int size){
        this.n = size;
    }

    @Override
    String instName() {
        return "newIntArray " + n;
    }


    @Override
    void execute() {
        int size = 1;
        Int [] temp = new Int[n];
        for( int i = 0; i < n ; i++ ){
            temp[i] = (Int)VM.opStack[VM.top-(n-1)+i];
            size = temp[i].val * size;
        }
        VM.top = VM.top-n;

        IntArray aRef = new IntArray(new Int[size], temp);
        VM.top++;
        VM.opStack[VM.top] = aRef;


        VM.pc++;

        VM.updateOpStackPeakSize();
    }
}
