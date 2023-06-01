package org.apache.bookkeeper.bookie.storage.ldb.writeCacheTest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import junit.framework.TestCase;
import org.apache.bookkeeper.bookie.storage.ldb.WriteCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class WriteCacheGetTest extends TestCase {

    private long ledgerId;
    private long entryId;
    private ByteBuf entry;
    private ByteBuf expected;
    private ByteBuf result;
    private final static String entryString = "Hello World!";
    private static final ByteBufAllocator allocator = UnpooledByteBufAllocator.DEFAULT;
    private static final int entrySize = 1024;
    private static final int cacheCapability = 2 * entrySize;
    private static WriteCache cache;

    public WriteCacheGetTest(long ledgerId, long entryId, ByteBuf expected){
        this.ledgerId = ledgerId;
        this.entryId = entryId;
        this.expected = expected;
    }

    @Before
    public void configure(){
        cache = new WriteCache(this.allocator, this.cacheCapability);
    }

    @Parameterized.Parameters
    public static Collection parameters(){
        ByteBuf validEntry = allocator.buffer(entrySize);
        validEntry.writeBytes(entryString.getBytes());

        return Arrays.asList(new Object[][] {
                {-1, -1, null      }, // 0
                {-1,  0, null      }, // 1
                {-1,  1, null      }, // 2
                { 0, -1, null      }, // 3
                { 0,  0, validEntry}, // 4
                { 0,  1, validEntry}, // 5
                { 1, -1, null      }, // 6
                { 1,  0, validEntry}, // 7
                { 1,  1, validEntry}, // 8

        });
    }

    @Test
    public void getTest() {
        this.entry = allocator.buffer(entrySize);
        this.entry.writeBytes(entryString.getBytes());

        try {
            cache.put(this.ledgerId, this.entryId, this.entry);
        } catch (Exception e) {
            //this.result = null;
        }

        try {
            this.result = cache.get(this.ledgerId, this.entryId);
        } catch (Exception e) {
            this.result = null;
        }

        assertEquals(expected, result);
    }

    @After
    public void cleanUp() {
        cache.close();
    }
}
