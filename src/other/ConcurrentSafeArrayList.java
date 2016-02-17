package other;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Possibly the most useful class I have ever made, always wanted this.
 *
 * Basically allows you to access the List concurrently without getting the ConcurrentAccessException
 *
 * @param <ObjType>
 */
public class ConcurrentSafeArrayList<ObjType> {
    private final ArrayList<ObjType> list = new ArrayList<>();
    private final Lock readLock;
    private final Lock writeLock;

    public ConcurrentSafeArrayList() {
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();
    }

    public int size() {
        int size = -1;

        readLock.lock();     //better safe than sorry...
        try {
            size = list.size();
        } finally {
            readLock.unlock();   //better safe than sorry...
        }

        return size;
    }

    public void add(ObjType obj) {
        writeLock.lock();
        try {
            list.add(obj);
        } finally {
            writeLock.unlock();
        }
    }

    public void remove(ObjType obj) {
        writeLock.lock();

        try {
            list.remove(obj);
        } finally {
            writeLock.unlock();
        }
    }

    public ObjType get(int index) {
        ObjType obj = null;

        readLock.lock();
        try {
            obj = list.get(index);
        } finally {
            readLock.unlock();
        }

        return obj;
    }

    public int indexOf(ObjType obj) {
        int index = -1;
        readLock.lock();
        try {
            index = list.indexOf(obj);
        } finally {
            readLock.unlock();
        }

        return index;
    }
}