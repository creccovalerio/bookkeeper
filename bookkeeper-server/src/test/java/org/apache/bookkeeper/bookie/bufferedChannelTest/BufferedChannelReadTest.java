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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

@RunWith(Parameterized.class)
public class BufferedChannelReadTest {

    private int fileSize;
    private int startIndex;
    private int readLength;
    private int buffSize;
    private FileChannel fileChannel;
    private BufferedChannel bufferedChannel;
    private byte[] randomBytes;
    private boolean noException = true;
    private boolean doWrite;
    private static final String TMP_DIR = "testTemp";
    private static final String TMP_FILE = "BCReadFile";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    public BufferedChannelReadTest(int fileSize, int startIndex, int readLenght, int buffSize, boolean doWrite, Class<? extends Exception> expectedException) {
        this.fileSize = fileSize;
        this.startIndex = startIndex;
        this.readLength = readLenght;
        this.buffSize = buffSize;
        this.doWrite = doWrite;
        if (expectedException != null) {
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
        generateRandomFile(this.fileSize);
        Path filePath = Paths.get(TMP_DIR,TMP_FILE);
        this.fileChannel = FileChannel.open(filePath, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        this.fileChannel.position(this.fileChannel.size());

        UnpooledByteBufAllocator allocator = UnpooledByteBufAllocator.DEFAULT;
        this.bufferedChannel = new BufferedChannel(allocator, this.fileChannel, this.buffSize);

        if (this.doWrite) {
            writeInWriteBuff();
        }
    }

    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][] {
                // File Size / Start Index / Read Length / Buffer Size / Expected Exception
                {-1,  0,  0,  0, false, NegativeArraySizeException.class     }, //0
                { 0, -1,  0,  0, false, ArrayIndexOutOfBoundsException.class }, //1
                { 0,  0, -1,  0, false, IllegalArgumentException.class       }, //2
                { 0,  0,  0, -1, false, IllegalArgumentException.class       }, //3
                { 0,  0,  0,  0, false, null                                 }, //4
                { 1,  0,  2,  1, false, IOException.class                    }, //5
                { 2,  0,  1,  2, false, null                                 }, //6
                { 3,  1,  2,  2, false, null                                 }, //7

                // added to improve coverage
                { 5,  9,  3,  2, true, IOException.class                     }  //8

        });
    }

    @Test
    public void ReadTest() throws Exception {
        ByteBuf readDestBuff = Unpooled.buffer();
        // Size of the destination buffer of read method set to the number of bytes i want to read
        readDestBuff.capacity(readLength);

        // Number of read bytes from bufferedChannel
        int numReadBytes = this.bufferedChannel.read(readDestBuff, this.startIndex,this.readLength);
        System.out.println("Read OP --> Num Bytes letti: " + numReadBytes);

        byte[] bytesRead = readDestBuff.array();

        int numBytesExpected;
        if (this.fileSize - this.startIndex >= this.readLength) {
            numBytesExpected = this.readLength;
        }
        else {
            numBytesExpected =  this.randomBytes.length - this.startIndex - this.readLength;
        }
        byte[] expectedBytes = Arrays.copyOfRange(this.randomBytes, this.startIndex, this.startIndex + numBytesExpected);

        Assert.assertEquals(Arrays.toString(expectedBytes), Arrays.toString(bytesRead));
    }

    // Write on the writeBuffer before to read
    private void writeInWriteBuff() throws IOException {
        ByteBuf writeBuf = Unpooled.buffer();
        writeBuf.writeBytes(this.randomBytes);
        this.bufferedChannel.write(writeBuf);
    }

    // Generate a file which contains random generated bytes
    private void generateRandomFile(int size) throws IOException {
        this.randomBytes = new byte[size];
        Random rd = new Random();
        rd.nextBytes(randomBytes);

        FileOutputStream fileStream = new FileOutputStream(TMP_DIR+"/"+TMP_FILE);
        fileStream.write(this.randomBytes);
        fileStream.close();
    }

    @After
    public void clear() throws IOException {
        if (this.noException) {
            this.bufferedChannel.clear();
            this.bufferedChannel.close();
            this.fileChannel.close();
        }
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