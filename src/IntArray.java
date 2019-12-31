final public class IntArray extends Array {

    Int [] a;
    int [] sizeList;

    public IntArray(Int [] a, Int [] sizeList){
        this.a = a;
        this.sizeList = new int[sizeList.length];
        for( int i = 0; i < sizeList.length; i++ ){
            this.sizeList[i] = sizeList[i].val;
        }
    }

    @Override
    public String toString() {
        String array = "int array of size list [";
        for( int i : sizeList ){
            array += (i + ", ");
        }
        array += "]";
        return array;
    }

    @Override
    Data cloneData() {
        return this;
    }
}
