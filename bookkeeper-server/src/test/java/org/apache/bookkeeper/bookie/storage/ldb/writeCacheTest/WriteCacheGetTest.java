package org.apache.bookkeeper.bookie.storage.ldb.writeCacheTest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import junit.framework.TestCase;
import org.apache.bookkeeper.bookie.storage.ldb.WriteCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class WriteCacheGetTest extends TestCase {

    private long ledgerId;
    private long entryId;
    private int maxSegmentSize;
    private final Class<? extends Exception> expectedException;
    private static final ByteBufAllocator allocator = UnpooledByteBufAllocator.DEFAULT;
    private static final int entrySize = 1024;
    private static final int cacheCapability = 2 * entrySize;
    private static WriteCache cache;

    public WriteCacheGetTest(long ledgerId, long entryId, int maxSegmentSize, Class<? extends Exception> expectedException){
        this.ledgerId = ledgerId;
        this.entryId = entryId;
        this.maxSegmentSize = maxSegmentSize;
        this.expectedException = expectedException;
    }

    @Before
    public void configure(){

    }

    @Parameterized.Parameters
    public static Collection parameters(){
        return Arrays.asList(new Object[][] {
                {-1, -1, entrySize, IllegalArgumentException.class }, // 0
                {-1,  0, entrySize, IllegalArgumentException.class }, // 1
                {-1,  1, entrySize, IllegalArgumentException.class }, // 2
                { 0, -1, entrySize, null                           }, // 3
                { 0,  0, entrySize, null                           }, // 4
                { 0,  1, entrySize, null                           }, // 5
                { 1, -1, entrySize, null                           }, // 6
                { 1,  0, entrySize, null                           }, // 7
                { 1,  1, entrySize, null                           }, // 8

                //killing mutants
                {1, 1, 512, null},   // 9
                {1, 1, entrySize, null}, //10
        });
    }

    @Test
    public void getTest() {
        if(maxSegmentSize != 0){
            cache = new WriteCache(this.allocator, this.cacheCapability,this.maxSegmentSize);
        }else{
            cache = new WriteCache(this.allocator, this.cacheCapability);
        }

        ByteBuf byteBufPut = Unpooled.buffer(16);
        byteBufPut.writeBytes("Aloha!".getBytes());

        cache.put(0, 0, byteBufPut);
        cache.put(0, 1, byteBufPut);
        cache.put(1, 0, byteBufPut);
        cache.put(1, 1, byteBufPut);

        if(expectedException == null){
            Assertions.assertDoesNotThrow(() -> {
                ByteBuf byteBufGet = cache.get(ledgerId, entryId);
                if (ledgerId == 0 && entryId == 0 || ledgerId == 0 && entryId == 1 || ledgerId == 1 && entryId == 0 || ledgerId == 1 && entryId == 1){
                    assertEquals("Aloha!", byteBufGet.toString(Charset.defaultCharset()));
                } else {
                    assertNull(byteBufGet);
                }
            });
        }else{
            Assertions.assertThrows(expectedException, () -> {
                // Exception occurred
                cache.get(ledgerId, entryId);
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
