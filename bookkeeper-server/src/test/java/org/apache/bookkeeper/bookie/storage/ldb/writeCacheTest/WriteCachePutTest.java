package org.apache.bookkeeper.bookie.storage.ldb.writeCacheTest;

import io.netty.buffer.*;
import junit.framework.TestCase;
import org.apache.bookkeeper.bookie.storage.ldb.WriteCache;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

@RunWith(value = Parameterized.class)
public class WriteCachePutTest extends TestCase {

    private long ledgerId;
    private long entryId;
    private ByteBuf entry;
    private int maxSegmentSize;
    private Object expectedResult;
    private static final ByteBufAllocator allocator = UnpooledByteBufAllocator.DEFAULT;
    private static final int entrySize = 1024;
    private static final int cacheCapability = 2 * entrySize;
    private static final int invalidSize = 2;
    private WriteCache cache;
    private boolean result;

    public WriteCachePutTest(long ledgerId, long entryId, ByteBuf entryType,int maxSegmentSize, Object expectedResult){
        this.ledgerId = ledgerId;
        this.entryId = entryId;
        this.entry = entryType;
        this.maxSegmentSize = maxSegmentSize;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection parameters(){

        ByteBuf validEntry = allocator.buffer(entrySize);
        validEntry.writeBytes(buildString(entrySize).getBytes());

        ByteBuf invalidEntry = allocator.buffer(invalidSize*cacheCapability);
        invalidEntry.writeBytes(buildString(invalidSize*cacheCapability).getBytes());

        ByteBuf cacheSizeEntry = allocator.buffer(cacheCapability);
        cacheSizeEntry.writeBytes(buildString(cacheCapability).getBytes());

        return Arrays.asList(new Object[][] {
                {-1, -1, null        , entrySize,                   NullPointerException.class     },  // 0
                {-1, -1, validEntry  , entrySize,                   IllegalArgumentException.class },  // 1
                {-1, -1, invalidEntry, cacheCapability*invalidSize, false                          },  // 2
                {-1,  0, null        , entrySize,                   NullPointerException.class     },  // 3
                {-1,  0, validEntry  , entrySize,                   IllegalArgumentException.class },  // 4
                {-1,  0, invalidEntry, cacheCapability*invalidSize, false                          },  // 5
                {-1,  1, null        , entrySize,                   NullPointerException.class     },  // 6
                {-1,  1, validEntry  , entrySize,                   IllegalArgumentException.class },  // 7
                {-1,  1, invalidEntry, cacheCapability*invalidSize, false                          },  // 8
                { 0, -1, null        , entrySize,                   NullPointerException.class     },  // 9
                { 0, -1, validEntry  , entrySize,                   IllegalArgumentException.class },  // 10
                { 0, -1, invalidEntry, cacheCapability*invalidSize, false                          },  // 11
                { 0,  0, null        , entrySize,                   NullPointerException.class     },  // 12
                { 0,  0, validEntry  , entrySize,                   true                           },  // 13
                { 0,  0, invalidEntry, cacheCapability*invalidSize, false                          },  // 14
                { 0,  1, null        , entrySize,                   NullPointerException.class     },  // 15
                { 0,  1, validEntry  , entrySize,                   true                           },  // 16
                { 0,  1, invalidEntry, cacheCapability*invalidSize, false                          },  // 17
                { 1, -1, null        , entrySize,                   NullPointerException.class     },  // 18
                { 1, -1, validEntry  , entrySize,                   IllegalArgumentException.class },  // 19
                { 1, -1, invalidEntry, cacheCapability*invalidSize, false                          },  // 20
                { 1,  0, null        , entrySize,                   NullPointerException.class     },  // 21
                { 1,  0, validEntry  , entrySize,                   true                           },  // 22
                { 1,  0, invalidEntry, cacheCapability*invalidSize, false                          },  // 23
                { 1,  1, null        , entrySize,                   NullPointerException.class     },  // 24
                { 1,  1, validEntry  , entrySize,                   true                           },  // 25
                { 1,  1, invalidEntry, cacheCapability*invalidSize, false                          },  // 26

                //increasing coverage
                { 1,  1, cacheSizeEntry, entrySize                , false                          },  // 27
                //killing mutants
                { 1,  1, cacheSizeEntry, cacheCapability          , true                           },  // 28
        });
    }

    @Test
    public void putTest() {

        cache = new WriteCache(allocator, cacheCapability, this.maxSegmentSize);

        try{
            assertEquals(0, cache.count());
            this.result = cache.put(this.ledgerId, this.entryId, this.entry);
            if (this.entry.capacity() == entrySize || this.maxSegmentSize == cacheCapability){
                assertEquals(1,cache.count());
            }else{
                assertEquals(0,cache.count());
            }
            assertEquals(this.expectedResult, this.result);
        }catch(Exception e){
            assertEquals(this.expectedResult, e.getClass());
        }

    }

    private static String buildString(int numChars) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numChars; i++) {
            builder.append(alphabet.charAt(i % alphabet.length()));
        }
        return builder.toString();
    }

    @After
    public void cleanUp() {
        cache.clear();
        cache.close();
    }

}
