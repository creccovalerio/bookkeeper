package org.apache.bookkeeper.bookie.storage.ldb.writeCacheTest;

import io.netty.buffer.*;
import junit.framework.TestCase;
import org.apache.bookkeeper.bookie.storage.ldb.WriteCache;
import org.junit.After;
import org.junit.Before;
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
    private boolean expected;
    private boolean result;
    private static final ByteBufAllocator allocator = UnpooledByteBufAllocator.DEFAULT;
    private static final int entrySize = 1024;
    private static final int cacheCapability = 2 * entrySize;
    private  static final int invalidSize = 100;
    private static WriteCache cache;


    public WriteCachePutTest(long ledgerId, long entryId, ByteBuf entry, boolean expected){
        this.ledgerId = ledgerId;
        this.entryId = entryId;
        this.entry = entry;
        this.expected = expected;
    }

    @Before
    public void configure(){
        cache = new WriteCache(this.allocator, this.cacheCapability);
    }

    @Parameterized.Parameters
    public static Collection parameters(){

        ByteBuf validEntry = allocator.buffer(entrySize);
        validEntry.writerIndex(validEntry.capacity());
        ByteBuf invalidEntry = allocator.buffer(cacheCapability + invalidSize);
        invalidEntry.writeBytes(generateRandomString(cacheCapability + invalidSize).getBytes());

        return Arrays.asList(new Object[][] {
                {-1, -1, null,         false},  // 0
                {-1, -1, validEntry,   false},  // 1
                {-1, -1, invalidEntry, false},  // 2
                {-1,  0, null,         false},  // 3
                {-1,  0, validEntry,   false},  // 4
                {-1,  0, invalidEntry, false},  // 5
                {-1,  1, null,         false},  // 6
                {-1,  1, validEntry,   false},  // 7
                {-1,  1, invalidEntry, false},  // 8
                { 0, -1, null,         false},  // 9
                { 0, -1, validEntry,   false},  // 10
                { 0, -1, invalidEntry, false},  // 11
                { 0,  0, null,         false},  // 12
                { 0,  0, validEntry,   true},   // 13
                { 0,  0, invalidEntry, false},  // 14
                { 0,  1, null,         false},  // 15
                { 0,  1, validEntry,   true},   // 16
                { 0,  1, invalidEntry, false},  // 17
                { 1, -1, null,         false},  // 18
                { 1, -1, validEntry,   false},  // 19
                { 1, -1, invalidEntry, false},  // 20
                { 1,  0, null,         false},  // 21
                { 1,  0, validEntry,   true},   // 22
                { 1,  0, invalidEntry, false},  // 23
                { 1,  1, null,         false},  // 24
                { 1,  1, validEntry,   true},   // 25
                { 1,  1, invalidEntry, false},  // 26
        });
    }

    @Test
    public void putTest() {
        try {
            this.result = cache.put(this.ledgerId, this.entryId, this.entry);
        } catch (Exception e) {
            this.result = false;
        }
        assertEquals(expected, result);
    }

    public static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    @After
    public void cleanUp() {
        cache.close();
    }

}
