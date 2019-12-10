package com.main.javaio.stream.iterate;

import java.io.InputStream;
import java.io.IOException;

public class RollingBufferInputStream {

    InputStream source = null;

    protected byte[] buffer = null;
    protected int start = 0; //current location in buffer.
    protected int end = 0; //current limit of data read
    //into the buffer
    //= next element to read into.

    protected int bytesRead = 0;

    public RollingBufferInputStream(InputStream source, byte[] buffer) throws IOException {
        this.source = source;
        this.buffer = buffer;
        fillDataFromStreamIntoBuffer();
    }

    /**
     * 获取缓冲区
     *
     * @return
     */
    public byte[] getBuffer() {
        return buffer;
    }

    /**
     * 缓冲区读指针游标
     *
     * @return
     */
    public int getStart() {
        return start;
    }

    /**
     * 缓冲区有效区域游标
     *
     * @return
     */
    public int getEnd() {
        return end;
    }

    /**
     * 缓冲区可用字节大小
     *
     * @return
     */
    public int availableBytes() {
        return this.end - this.start;
    }

    /**
     * 是否有可读字节
     *
     * @param numberOfBytes
     * @return
     * @throws IOException
     */
    public boolean hasAvailableBytes(int numberOfBytes) throws IOException {
        /**
         Checks if the buffer has the required number of bytes available.
         检查缓冲区是否存在可用字节
         If not, checks if the stream has more data.
         没有可用呢，检查流是否有存在更多字节
         If the stream has more data, checks if the buffer has space for enough data at the bottom, or not.
         如果流中存在更多字节，检查缓冲区是否有存储其余数据么
         If the buffer does not have enough space left, the buffer is compacted.
         如果缓冲区没有足够的可用空间，压缩缓冲区
         The buffer is filled with as much data as possible.
         缓冲区，填充可容纳最大字节
         */
        if (!hasAvailableBytesInBuffer(numberOfBytes)) {
            if (streamHasMoreData()) {
                if (!bufferHasSpaceFor(numberOfBytes)) {
                    compact();
                }
                fillDataFromStreamIntoBuffer();
            }
        }
        return hasAvailableBytesInBuffer(numberOfBytes);
    }

    /**
     * 查询缓冲区，是否有目标可用空间
     *
     * @param numberOfBytes
     * @return
     */
    private boolean hasAvailableBytesInBuffer(int numberOfBytes) {
        return (this.end - this.start) >= numberOfBytes;
    }

    /**
     * 填充可用空间
     *
     * @throws IOException
     */
    private void fillDataFromStreamIntoBuffer() throws IOException {
        this.bytesRead = this.source.read(this.buffer, this.end,
                this.buffer.length - this.end);
        this.end += this.bytesRead;
    }

    /**
     * 压缩缓冲区
     */
    private void compact() {
        int bytesToCopy = end - start;

        for (int i = 0; i < bytesToCopy; i++) {
            this.buffer[i] = this.buffer[start + i];
        }

        this.start = 0;
        this.end = bytesToCopy;
    }


    public void moveStart(int noOfBytesToMove) {
        if (this.start + noOfBytesToMove > this.end) {
            throw new RuntimeException(
                    "Attempt to move buffer 'start' beyond 'end'. start= "
                            + this.start + ", end: " + this.end + ", bytesToMove: "
                            + noOfBytesToMove);
        }
        this.start += noOfBytesToMove;
    }

    private boolean bufferHasSpaceFor(int numberOfBytes) {
        return (this.buffer.length - this.start) >= numberOfBytes;
    }

    public boolean streamHasMoreData() {
        return this.bytesRead > -1;
    }
}
