# Java Virtural Machine
## PROJECT 1: Lexical Analyzer

The objective of Project 1 is to implement a lexical analyzer for our VM language, which has 7 token categories ⟨unsigned int⟩ through ⟨comma⟩ defined by the following EBNF:

```
⟨digit⟩ → 0 | 1 | ... | 9
⟨unsigned int⟩ → {⟨digit⟩}+
⟨signed int⟩ → (+|−) {⟨digit⟩}+
⟨float⟩ → [+|−] ( {⟨digit⟩}+ "." {⟨digit⟩} | "." {⟨digit⟩}+ )
⟨floatE⟩ → ⟨float⟩ (e|E) [+|−] {⟨digit⟩}+
⟨instruction name⟩ → "iconst" | "iload" | "istore" | "fconst" | "fload" | "fstore" |
                                    "iadd" | "isub" | "imul" | "idiv" | "fadd" | "fsub" | "fmul" | "fdiv" |
                                    "intToFloat" |
                                    "icmpeq" | "icmpne" | "icmplt" | "icmple" | "icmpgt" | "icmpge" |
                                    "fcmpeq" | "fcmpne" | "fcmplt" | "fcmple" | "fcmpgt" | "fcmpge" |
                                    "goto" | "invoke" | "return" | "ireturn" | "freturn" | "print"
⟨colon⟩ → ":"
⟨comma⟩ → ","
```
According to the above definitions, the integers and floating-point numbers may be signed with "+" or "−". Moreover, the integer or fractional part, but not both, of a string in ⟨float⟩ may be empty. The instruction names are case-sensitive.

**The following is a DFA to accept the token categories, except for ⟨instruction name⟩.**<br><br>
![DFA](https://github.com/zeasnelson/JavaVirturalMachine/blob/master/DFA-VM.svg)


The DFA has an auxiliary final state id defining ⟨id⟩ → {⟨letter⟩}+ to implement the extraction of the instruction names in the following way:
Create 33 additional special DFA states for the instruction names.
The DFA initially accepts the instruction names as identifiers.
Each time the DFA accepts an identifier, check if it is one of the instruction names. If so, move the DFA to the corresponding instruction-name state; otherwise, issue a lexical error message.
The implementation should be based on the above DFA. Your lexical analyzer program should clearly separate the driver and the state-transition function so that the driver will remain invariant and only state-transition functions will change from DFA to DFA. The enumerated or integer type is suggested for representation of states.





## PROJECT 2: Parser, Instruction Store

This project is a continuation of Project 1. Looking ahead to Project 3 and 4, you are to implement the virtual machine in object-oriented manner, taking advantage of inheritance, with a view to smooth addition of more data types and instructions. In trade-off, the runtime efficiency of the virtual machine is sacrificed somewhat as compared with implementations in purely procedural style.

The objective of Project 2 is to implement a top-down parser and an instruction store for our VM language. The EBNF is given below.
```
⟨instruction list⟩ → { ⟨instruction unit⟩ }+
⟨instruction unit⟩ → [ ⟨label⟩ ] ⟨instruction⟩
⟨label⟩ → ⟨unsigned int⟩ ":"
⟨instruction⟩ →
       "iconst" ⟨unsigned int⟩ | "iconst" ⟨signed int⟩ | "iload" ⟨unsigned int⟩ | "istore" ⟨unsigned int⟩ |
       "fconst" ⟨float⟩ | "fconst" ⟨floatE⟩ | "fload" ⟨unsigned int⟩ | "fstore" ⟨unsigned int⟩ |
       "iadd" | "isub" | "imul" | "idiv" | "fadd" | "fsub" | "fmul" | "fdiv" |
       "intToFloat" |
       ⟨cmp inst name⟩ ⟨unsigned int⟩ |
       "goto" ⟨unsigned int⟩ |
       "invoke" ⟨unsigned int⟩ "," ⟨unsigned int⟩ "," ⟨unsigned int⟩ |
       "return" | "ireturn" | "freturn" |
       "print" ⟨unsigned int⟩
⟨cmp inst name⟩ →
       "icmpeq" | "icmpne" | "icmplt" | "icmple" | "icmpgt" | "icmpge" |
       "fcmpeq" | "fcmpne" | "fcmplt" | "fcmple" | "fcmpgt" | "fcmpge"
```

A lexical analyzer for this language's tokens has been implemented in Project 1.

Since the syntax is so simple, your "top-down parser" will be just a while-loop whose body parses and stores instructions. Represent the instructions by class objects. Build your instruction class hierarchy in such a way that additional instructions can be incorporated smoothly. More specifically, create an abstract class named Instruction at the root of the hierarchy and use the leaf classes to represent the actual instructions. Store the operands of instructions in appropriate class fields. Implement the signed/unsigned integers by 32-bit int type, floating-point numbers by double-precision 64-bit double type.

The following is the operational semantics of the instructions. They will be implemented in Project 3. In this project the semantics should help arrangement of the class hierarchy and choice of suitable field names of instruction classes.
#### push value onto operand stack
    iconst k : push integer constant k onto stack
    iload k : push integer at address k in variable area onto stack
    fconst x : push floating-point constant x onto stack
    fload k : push floating-point value at address k in variable area onto stack
#### pop top of operand stack and store
    istore k : pop top of stack, which is assumed to be an integer, and store it in address k in variable area
    fstore k : pop top of stack, which is assumed to be a floating-point number, and store it in address k in variable area
#### arithmetic
    iadd, isub, imul, idiv : pop top two integer values from stack, apply operator to stack[top-1] and stack[top], push result onto stack
    fadd, fsub, fmul, fdiv : pop top two floating-point values from stack, apply operator to stack[top-1] and stack[top], push result onto stack
#### type conversion
    intToFloat : convert integer at top of stack to floating-point number
#### comparison-jump
    icmpeq k :   pop top two integer values from stack; if stack[top−1] = stack[top] then go to instruction at address k
    icmpne k :   pop top two integer values from stack; if stack[top−1] ≠ stack[top] then go to instruction at address k
    icmplt k :   pop top two integer values from stack; if stack[top−1] < stack[top] then go to instruction at address k
    icmple k :   pop top two integer values from stack; if stack[top−1] ≤ stack[top] then go to instruction at address k
    icmpgt k :   pop top two integer values from stack; if stack[top−1] > stack[top] then go to instruction at address k
    icmpge k :   pop top two integer values from stack; if stack[top−1] ≥ stack[top] then go to instruction at address k
    Likewise for fcmpeq, fcmpne, fcmplt, fcmple, fcmpgt, fcmpge
#### unconditional jump
    goto k : go to instruction at address k
#### function invocation
    invoke k1, k2, k3 : invoke function code starting at label k1, k2 = the # of parameters, k3 = the # of local variables
#### function return
    return : return from void-type function
    ireturn : return from function whose return type is integer
    freturn : return from function whose return type is floating-point
#### print
    print k : print value at address k in variable area on the screen

<br>
Implement the instruction store by a 1-dimensional array of objects of Instruction class type. The array indexes will serve as instruction addresses. The array size of 1000 should be sufficient for our projects. In the following description, instStore is the name of the array.

The labels used in the instruction list, including the target labels in comparison-jump, goto, and invoke instructions, must be mapped to the corresponding indexes of the instruction array. This can be achieved as follows. As the parser extracts an instruction preceded by a label L and to be stored in instStore[i], record the pair (L, i) in a suitable table/map. After the instructions have been parsed and stored, scan the array instStore and replace the label L used in each comparison-jump, goto, and invoke instruction by the corresponding array index i by looking it up in the table/map. (I've used java.util.HashMap for this purpose.)

An appropriate error message should be issued when the first syntax error is found; in this project there is no need to recover from it and continue parsing. (Real-world parsers perform some type of syntax-error recovery and attempt to find more syntax errors.)

Labels may be any non-negative integers and may occur in any order in the instruction list. They must, however, obey the following "semantic rules":
No label may occur more than once in an instruction list.
The target label of each comparison-jump, goto, and invoke instruction must occur in the instruction list.
If these rules are violated, your program must issue appropriate error messages.

The lexical analyzer program is to read an input text file, extract the tokens in it, and write them out one by one on separate lines. Each token should be flagged with its category. The output should be sent to an output text file. Whenever invalid tokens are found, error messages should be printed, and the reading process should continue.



## PROJECT 3: Implementation of Instructions

You are to implement an interpreter of the instructions of our virtual machine in this project. First, review the operational semantics of the instructions provided in Project 2 description.

Although our VM has only two data types, integers and floating-point numbers, it is beneficial to use an inheritance hierarchy with a view to smooth addition of more data types. Create an abstract class at the root of this hierarchy and two subclasses of integers and floating-point numbers. In the following this abstract class will be called Data.

Implement a single, universal operand stack for all function calls. The operand stack will contain objects of the Data class. In the following it will be referred to as stack.

Implement the variable area of each activation record by a 1-dimensional array of Data objects. In the following this array will be referred to as vars. So "the value in address k in variable area" will be implemented by vars[k].

Create a class of activation records. This class has just two fields: the vars array and the return address. The runtime stack will contain objects of this class.

Implement the program counter by an integer variable. In the following it will be called pc. The value of pc ranges over the indexes of the instStore array, and instStore[pc] has the instruction currently being executed.

**The following is the detailed operation of the invoke and return instructions.**
#### invoke k1, k2, k3
* Construct a new activation record object ar.
* Construct a new vars array of size k2+k3 in ar.
* Pop top k2 objects from the operand stack; assign stack[top−k2+1], …, stack[top], respectively, to vars[0], …, vars[k2−1]. *This performs parameter passing.*
* Set the return address in ar to pc+1.
* Push ar onto the runtime stack.
* Set pc to k1.
#### return, ireturn, freturn
* Set pc to the return address contained in the activation record at top of the runtime stack.
* Pop the top activation record from the runtime stack.
* The operand and runtime stacks may be implemented by arrays, linked lists, or a library stack class.

The implementation of the other instructions should be relatively straightforward from the operational semantics provided in Project 2 description.

In the Instruction class at the root of the instruction hierarchy, declare an abstract function execute(), or interpret() if you prefer, and provide its body code in suitable descendant classes to implement execution/interpretation of single instructions. The interpretation of a whole instruction list starts by setting pc to 0, then repeatedly executes instStore[pc] until instStore[pc] = null. (So we have to assume instStore[] array has at least one null at the end.) Note that execute()/interpret() must update the value of pc properly.

Presume that the instruction list is always type correct, namely that each typed instruction, whenever executed, will have operand values of the required data types.

Also, the program is to compute and display the peak sizes of the operand and runtime stacks.

Your program is to read any text file containing (what is intended to be) an instruction list, parse and store the instructions in instStore array, and display the stored instructions along with their sequential indexes in an output file. It will then interpret the instruction list. The values to be printed by print instructions and the peak sizes of the operand/runtime stacks are to be displayed on the screen.

My virtual machine (written in Java) needs about 2 minutes to compute Ackermann(3, 12).




## PROJECT 4: Array Instruction

In this project you are to extend the virtual machine by incorporating instructions for arrays.

An array constructor with arguments E1, …, En constructs an array [0.. e1−1, …, 0.. en−1] where the ek are the values of the expressions Ek, 1 ≤ k ≤ n. Implement all arrays by 1-dimensional arrays storing the elements in row-major order, so that the value of [i1, …, in] will be stored in [rank(i1, …, in)]. Our virtual machine can construct two types of arrays: integer arrays and floating-point arrays. Represent each array by an object with two fields: a size list of e1, …, en and a reference to the 1-dimensional array in row-major order. The classes of such objects should be descendant classes of Data class – they will be referred to as array objects below.

**The following is the operational semantics of the array instructions to be implemented.
array constructor**.

- newIntArray n : 
  - Pop top n integers from operand stack. 
  - Let ei = stack[top−n+i], 1 ≤ i ≤ n. 
  - Construct a new 1-dimensional integer array a of size e1 × ··· × en. 
  - Construct a new array object containing **a** together with size list (e1, ..., en) and push a reference to it onto operand stack. If ei ≤ 0 for any i, issue an appropriate runtime error message on the screen and terminate the virtual machine.
- newFloatArray n : The operation is the same as above except that it constructs a new 1-dimensional floating-point array **a**.
#### load array reference
    aload k : push the reference to an array object stored in vars[k] onto operand stack
#### store array reference
    astore k : pop the reference to an array object from operand stack and store it in vars[k]
#### load array element
- iaload n : 
  - Pop top n+1 objects from operand stack. 
  - Let aref = stack[top−n] and ei = stack[top−n+i], 1 ≤ i ≤ n. 
  - Extract integer array a and size list (s1, ..., sn) contained in array object aref. 
  - Compute a[rank(e1, ..., en)] and push it onto operand stack. *(In case you elect to use the sample program, be sure to push a[rank(e1, ..., en)].cloneData()).* 
  - If ei < 0 or ei ≥ si for any i, issue an "index out of bounds" runtime error message on the screen and terminate the virtual machine.
- faload n : The operation is the same as above except that array a will be a floating-point array.
#### store into array element
- iastore n : 
  - Pop top n+2 objects from operand stack. 
  - Let aref = stack[top−n−1], ei = stack[top−n−1+i], 1 ≤ i ≤ n, and e = stack[top]. 
  - Extract integer array a and size list (s1, ..., sn) contained in array object aref. 
  - Assign integer e to a[rank(e1, ..., en)]. *If ei < 0 or ei ≥ si for any i, issue an "index out of bounds" runtime error message on the screen and terminate the virtual machine.*
- fastore n : The operation is the same as above except that array a will be a floating-point array and e will be a floating-point number.
#### function return
- areturn : return from function whose return type is reference to array object
If you are comfortable with exception handling mechanisms, feel free to use them to handle runtime errors.

The following are the VM code patterns to use the above instructions properly.
```
code to construct new array [E1, ..., En]
code to evaluate E1
...
code to evaluate En
newIntArray n  // or newFloatArray n
code to load a[E1, ..., En]
aload k  // vars[k] has a reference to array object for a
code to evaluate E1
...
code to evaluate En
iaload n  // or faload n
code for a[E1, ..., En] = E
aload k  // vars[k] has a reference to array object for a
code to evaluate E1
...
code to evaluate En
code to evaluate E
iastore n  // or fastore n
```
The array objects are implemented as reference types. The effect of this is twofold: 
- each assignment of an array object to a variable will assign the reference to the array object, not a cloned array object, to the variable; 
- whenever an array object is passed to a formal parameter, the reference to the array object, not a cloned array object, will be passed. If you elect to use the sample program, this can be achieved by implementing cloneData() of your array classes as the identity function returning this target array object.
<br >
Implement the rank function, rank(e1, ..., en), by an efficient iterative algorithm whose runtime is linear in n. You might first solve Question 6 in Exercise Set #5 and then adapt the solution to computation of rank(e1, ..., en).

You have to modify the lexical analyzer and parser, and extend the Instruction class hierarchy to incorporate the above instructions. The following is the additional BNF for ⟨instruction⟩:
```
⟨instruction⟩ →
       "newIntArray" ⟨unsigned int⟩ | "newFloatArray" ⟨unsigned int⟩ |
       "aload" ⟨unsigned int⟩ | "astore" ⟨unsigned int⟩ |
       "iaload" ⟨unsigned int⟩ | "iastore" ⟨unsigned int⟩ |
       "faload" ⟨unsigned int⟩ | "fastore" ⟨unsigned int⟩ |
       "areturn"
```
Presume that the instruction list is always type correct, namely that each typed instruction, whenever executed, will have operand values of the required data types.

Your program is to read any text file containing (what is intended to be) an instruction list, parse and store the instructions in instStore array, and display the stored instructions along with their sequential indexes in an output file. It will then interpret the instruction list. The values to be printed by print instructions, the runtime error messages, and the peak sizes of the operand/runtime stacks are to be displayed on the screen.

