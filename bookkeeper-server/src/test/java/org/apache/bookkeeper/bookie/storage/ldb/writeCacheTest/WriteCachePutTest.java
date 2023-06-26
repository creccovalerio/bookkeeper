package org.apache.bookkeeper.bookie.storage.ldb.writeCacheTest;

import io.netty.buffer.*;
import junit.framework.TestCase;
import org.apache.bookkeeper.bookie.storage.ldb.WriteCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
    private final Class<? extends Exception> expectedException;
    private static final ByteBufAllocator allocator = UnpooledByteBufAllocator.DEFAULT;
    private static final int entrySize = 1024;
    private static final int cacheCapability = 2 * entrySize;
    private  static final int invalidSize = 5;
    private static WriteCache cache;
    private enum ObjType {
        NULL, VALID, INVALID
    }

    public WriteCachePutTest(long ledgerId, long entryId, ObjType entryType, Class<? extends Exception> expectedException){
        this.ledgerId = ledgerId;
        this.entryId = entryId;
        this.expectedException = expectedException;
        switch(entryType) {
            case NULL:
                this.entry = null;
                break;
            case VALID:
                ByteBuf validEntry = allocator.buffer(entrySize);
                validEntry.writerIndex(validEntry.capacity());
                this.entry = validEntry;
                break;
            case INVALID:
                ByteBuf invalidEntry = allocator.buffer(invalidSize*entrySize);
                invalidEntry.writerIndex(invalidEntry.capacity());
                this.entry = invalidEntry;
                break;
        }
    }

    @Before
    public void configure(){
        cache = new WriteCache(this.allocator, this.cacheCapability);
    }

    @Parameterized.Parameters
    public static Collection parameters(){

        return Arrays.asList(new Object[][] {
                {-1, -1, ObjType.NULL,     NullPointerException.class     },  // 0
                {-1, -1, ObjType.VALID,    IllegalArgumentException.class },  // 1
                {-1, -1, ObjType.INVALID,  null                           },  // 2
                {-1,  0, ObjType.NULL,     NullPointerException.class     },  // 3
                {-1,  0, ObjType.VALID,    IllegalArgumentException.class },  // 4
                {-1,  0, ObjType.INVALID,  null                           },  // 5
                {-1,  1, ObjType.NULL,     NullPointerException.class     },  // 6
                {-1,  1, ObjType.VALID,    IllegalArgumentException.class },  // 7
                {-1,  1, ObjType.INVALID,  null                           },  // 8
                { 0, -1, ObjType.NULL,     NullPointerException.class     },  // 9
                { 0, -1, ObjType.VALID,    IllegalArgumentException.class },  // 10
                { 0, -1, ObjType.INVALID,  null                           },  // 11
                { 0,  0, ObjType.NULL,     NullPointerException.class     },  // 12
                { 0,  0, ObjType.VALID,    null                           },  // 13
                { 0,  0, ObjType.INVALID,  null                           },  // 14
                { 0,  1, ObjType.NULL,     NullPointerException.class     },  // 15
                { 0,  1, ObjType.VALID,    null                           },  // 16
                { 0,  1, ObjType.INVALID,  null                           },  // 17
                { 1, -1, ObjType.NULL,     NullPointerException.class     },  // 18
                { 1, -1, ObjType.VALID,    IllegalArgumentException.class },  // 19
                { 1, -1, ObjType.INVALID,  null                           },  // 20
                { 1,  0, ObjType.NULL,     NullPointerException.class     },  // 21
                { 1,  0, ObjType.VALID,    null                           },  // 22
                { 1,  0, ObjType.INVALID,  null                           },  // 23
                { 1,  1, ObjType.NULL,     NullPointerException.class     },  // 24
                { 1,  1, ObjType.VALID,    null                           },  // 25
                { 1,  1, ObjType.INVALID,  null                           },  // 26
        });
    }

    @Test
    public void putTest() {
        if(expectedException == null){
            Assertions.assertDoesNotThrow(() -> {
                assertEquals(0, cache.count());
                cache.put(ledgerId, entryId, entry);
                if (entry.capacity() == entrySize){
                    assertEquals(1,cache.count());
                }
            });
        }else{
            Assertions.assertThrows( expectedException, () -> {
                cache.put(ledgerId, entryId, entry);
                Assertions.fail();
            });
        }
    }

    @After
    public void cleanUp() {
        cache.clear();
        cache.close();
    }

}
