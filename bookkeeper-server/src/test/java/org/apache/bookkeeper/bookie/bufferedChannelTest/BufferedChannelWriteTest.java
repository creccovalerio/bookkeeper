package org.apache.bookkeeper.bookie.bufferedChannelTest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;

import org.apache.bookkeeper.bookie.BufferedChannel;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.*;

@RunWith(value = Parameterized.class)

public class BufferedChannelWriteTest {

    private BufferedChannel bufferedChannel; 	// Buffering layer used before writing on FileChannel
    private FileChannel fileChannel; 			// Channel to read and write on file
    private ByteBuf srcBuffer; 					// Source ByteBuf which contains the data that will be written in the bufferedChannel
    private int entrySize;
    private int writeBuffCapacity;
    private RandomAccessFile randomAccessFile;  // File used to instantiate the FileChannel
    private byte[] randomArray;
    private long unpersistedBytes;			    // Limit of bytes that can be kept without persistence (flush) on file
    private boolean noException = true;
    private static final String TMP_DIR = "testTemp";
    private static final String LOG_FILE = "BCWriteFile";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    public BufferedChannelWriteTest(int writeBuffCapacity, int entrySize, long unpersistedBytes, Class<? extends Exception> expectedException) {
        this.entrySize = entrySize;
        this.writeBuffCapacity = writeBuffCapacity;
        this.unpersistedBytes = unpersistedBytes;
        if (expectedException!=null) {
            this.expectedException.expect(expectedException);
            this.noException = false;
        }
    }

    @BeforeClass
    static public void configureEnvironment() {
        // Creating the directory where the log file will be inserted
        if (!Files.exists(Paths.get(TMP_DIR))) {
            File tmp = new File(TMP_DIR);
            tmp.mkdir();
        }
    }

    @Before
    public void configure() throws IOException {
        File directory = new File(TMP_DIR);
        File logTestFile = File.createTempFile(LOG_FILE, ".log",directory);
        logTestFile.deleteOnExit();

        randomAccessFile = new RandomAccessFile(logTestFile, "rw");
        FileChannel fc = randomAccessFile.getChannel();
        this.fileChannel = fc;

        this.srcBuffer = generateRandomEntry(this.entrySize);
    }

    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][] {
                {-1, -2, 0, NegativeArraySizeException.class }, //0
                {-1, -1, 0, NegativeArraySizeException.class }, //1
                {-1,  0, 0, IllegalArgumentException.class   }, //2
                { 0, -1, 0, NegativeArraySizeException.class }, //3
                { 0,  0, 0, null                             }, //4
                { 1,  0, 0, null                             }, //5
                { 1,  1, 0, null                             }, //6
                { 1,  2, 0, null                             }, //7
                { 2,  1, 0, null                             }, //8
                { 2,  2, 0, null                             }, //9
                { 2,  3, 0, null                             }, //10
        });

    }

    @Test(timeout = 500)
    public void WriteTest() throws Exception{
        UnpooledByteBufAllocator allocator = UnpooledByteBufAllocator.DEFAULT;
        bufferedChannel = new BufferedChannel(allocator, fileChannel, writeBuffCapacity, unpersistedBytes);
        bufferedChannel.write(srcBuffer);

        /*
         *  If the entry is larger than the capacity of the WriteBuffer,
         *  fill the writeBuffer and flush the file until the remaining bytes of
         *  the entry enter completely into the bytebuffer.
         */
        int numBytesInWriteBuff;
        int numBytesInFileChannel = 0;
        if (entrySize > writeBuffCapacity) {
            int numFlush = entrySize/writeBuffCapacity;
            numBytesInFileChannel = numFlush * writeBuffCapacity;
            numBytesInWriteBuff = entrySize - numBytesInFileChannel;
        }
        else {
            numBytesInWriteBuff = entrySize;
        }

        // Inserting the written bytes into WriteBuffer in an array of bytes bytesInWriteBuf
        byte[] bytesInWriteBuff = new byte[numBytesInWriteBuff];
        bufferedChannel.writeBuffer.getBytes(0,bytesInWriteBuff);

        // Bytes that should be in the WriteBuf
        byte[] expectedBytes = Arrays.copyOfRange(this.randomArray, this.randomArray.length - numBytesInWriteBuff, this.randomArray.length);

        Assert.assertEquals(Arrays.toString(expectedBytes),Arrays.toString(bytesInWriteBuff));

        //Flush on file

        // Read the bytes in the channel file and write them inside a ByteBuffer buff
        ByteBuffer buff = ByteBuffer.allocate(numBytesInFileChannel);
        this.fileChannel.position(0);
        this.fileChannel.read(buff);

        byte[] bytesInFileChannel = buff.array();
        expectedBytes = Arrays.copyOfRange(this.randomArray, 0, numBytesInFileChannel);

        // verifying that any bytes written through flush are correctly contained in FileChannel
        Assert.assertEquals(Arrays.toString(expectedBytes), Arrays.toString(bytesInFileChannel));

        // Check that the File Channel location is correct
        Assert.assertEquals(numBytesInFileChannel, this.fileChannel.position());

        // Check that the BufferedChannel location is correct
        Assert.assertEquals(entrySize, this.bufferedChannel.position());
    }
    private ByteBuf generateRandomEntry(int size) {
        // Generating a random number of bytes
        this.randomArray = new byte[size];
        Random random = new Random();
        random.nextBytes(randomArray);

        // Write the generated bytes into byteBuf
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(randomArray);
        return byteBuf;
    }

    @After
    public void clear() throws IOException {
        if (this.noException) {
            this.randomAccessFile.close();
            this.bufferedChannel.clear();
            this.bufferedChannel.close();
        }
        this.fileChannel.close();
    }

    @AfterClass
    public static void clearEnvironment() {
        File directory = new File(TMP_DIR);
        String[] entries = directory.list();
        for(String s: entries){
            File currentFile = new File(directory.getPath(),s);
            currentFile.delete();
        }
        directory.delete();
    }
}