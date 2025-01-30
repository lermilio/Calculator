package prog04;

import prog02.DirectoryEntry;

import java.util.ArrayList;
import java.util.EmptyStackException;

/** Implementation of the interface StackInterface<E> using an array.
 *   @author vjm
 */

public class ArrayStack<E> implements StackInterface<E> {
    // Data Fields
    /** Storage for stack. */
    E[] theData;

    /** Number of elements in stack. */
    int size = 0;

    private static final int INITIAL_CAPACITY = 4;

    /** Construct an empty stack with the default initial capacity. */
    public ArrayStack () {
        theData = (E[])new Object[INITIAL_CAPACITY];
    }

    protected void reallocate() {
        E[] newData = (E[])new Object[2 * theData.length];
        System.arraycopy(theData, 0, newData, 0,
                theData.length);
        theData = newData;
    }

    /** Pushes an item onto the top of the stack and returns the item
	pushed.
	@param obj The object to be inserted.
	@return The object inserted.
    */
    public E push (E obj) {
        // EXERCISE:  Check if array is full and do something about it.
        // Look at ArrayBasedPD.add
        if(theData.length == size){
            reallocate();
        }
        theData[size++] = obj;
        return obj;
    }

    /**
     * Returns the object at the top of the stack without removing it.
     * post: The stack remains unchanged.
     *
     * @return The object at the top of the stack.
     * @throws EmptyStackException if stack is empty.
     */
    @Override
    public E peek() {
        if(empty()){
            throw new EmptyStackException();
        }
        else{
            return theData[size-1];

        }
    }

    /** Returns the object at the top of the stack and removes it.
	post: The stack is one item smaller.
	@return The object at the top of the stack.
	@throws EmptyStackException if stack is empty.
    */
    public E pop () {
        if (empty()) {
            throw new EmptyStackException();
        }
        else{
            E x = theData[size-1];
            theData[size-1] = null;
            size--;
            return x;
        }
    }

    /**
     * Returns true if the stack is empty; otherwise, returns false.
     *
     * @return true if the stack is empty.
     */
    @Override
    public boolean empty() {
        if(size == 0){return true; }
        else{return false;}
    }
}
