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
    private final Class<? extends Exception> expectedException;
    private final static String entryString = "Hello World!";
    private static final ByteBufAllocator allocator = UnpooledByteBufAllocator.DEFAULT;
    private static final int entrySize = 1024;
    private static final int cacheCapability = 2 * entrySize;
    private static WriteCache cache;

    public WriteCacheGetTest(long ledgerId, long entryId, Class<? extends Exception> expectedException){
        this.ledgerId = ledgerId;
        this.entryId = entryId;
        this.expectedException = expectedException;
    }

    @Before
    public void configure(){
        cache = new WriteCache(this.allocator, this.cacheCapability);

        ByteBuf byteBuf = Unpooled.buffer(16);
        byteBuf.writeBytes("Aloha!".getBytes());
        cache.put(0, 0, byteBuf);
        cache.put(0, 1, byteBuf);
        cache.put(1, 0, byteBuf);
        cache.put(1, 1, byteBuf);
    }

    @Parameterized.Parameters
    public static Collection parameters(){
        ByteBuf validEntry = allocator.buffer(entrySize);
        validEntry.writeBytes(entryString.getBytes());

        return Arrays.asList(new Object[][] {
                {-1, -1, IllegalArgumentException.class }, // 0
                {-1,  0, IllegalArgumentException.class }, // 1
                {-1,  1, IllegalArgumentException.class }, // 2
                { 0, -1, null                           }, // 3
                { 0,  0, null                           }, // 4
                { 0,  1, null                           }, // 5
                { 1, -1, null                           }, // 6
                { 1,  0, null                           }, // 7
                { 1,  1, null                           }, // 8
        });
    }

    @Test
    public void getTest() {
        if(expectedException == null){
            Assertions.assertDoesNotThrow(() -> {
                ByteBuf byteBuf = cache.get(ledgerId, entryId);
                if (ledgerId == 0 && entryId == 0 || ledgerId == 0 && entryId == 1 || ledgerId == 1 && entryId == 0 || ledgerId == 1 && entryId == 1){
                    assertEquals("Aloha!", byteBuf.toString(Charset.defaultCharset()));
                } else {
                    assertNull(byteBuf);
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
