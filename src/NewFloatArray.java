public class NewFloatArray extends Instruction {

    int n;

    public NewFloatArray(int size){
        this.n = size;
    }

    @Override
    String instName() {
        return "newFloatArray " + n;
    }

    @Override
    void execute() {

        int size = 1;
        Int [] temp = new Int[n];
        for( int i = 0; i < n ; i++ ){
            temp[i] = (Int)VM.opStack[VM.top-(n-1)+i];
            size = temp[i].val * size;
        }
        if( size <= -1 ){
            System.out.println("Error: Attempt to construct array with non-positive index size");
            VM.pc = 50000;
            return;
        }
        VM.top = VM.top-n;

        FloatArray aRef = new FloatArray(new Floatp[size], temp);
        VM.top++;
        VM.opStack[VM.top] = aRef;


        VM.pc++;

        VM.updateOpStackPeakSize();
    }
}
