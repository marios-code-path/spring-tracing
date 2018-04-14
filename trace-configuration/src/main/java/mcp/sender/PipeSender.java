package mcp.sender;

import zipkin2.Call;
import zipkin2.codec.Encoding;
import zipkin2.reporter.Sender;

import java.util.List;

public abstract class PipeSender extends Sender{

    @Override
    public Encoding encoding() {
        return null;
    }

    @Override
    public int messageMaxBytes() {
        return 0;
    }

    @Override
    public int messageSizeInBytes(List<byte[]> encodedSpans) {
        return 0;
    }

    // Goes to the pipe
    @Override
    public Call<Void> sendSpans(List<byte[]> encodedSpans) {
        return null;
    }
}
