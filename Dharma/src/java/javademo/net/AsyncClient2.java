package javademo.net;

/**
 * 随机发送字符串
 * @author Fan Hongtao
 */
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

class AsyncClient2
{
    private final static int MAX_LENGTH = 64 * 1024;
    
    private ByteBuffer readBuffer = ByteBuffer.allocate(MAX_LENGTH);
    
    private ByteBuffer writeBuffer = ByteBuffer.allocate(MAX_LENGTH);
    
    private SocketChannel clientChannel;
    
    private final static String[] CLIENT_MSGS = { "Hello, world", "Test program", "Ansyc client", "this is a demo" };
    
    public AsyncClient2(String host, int port)
    {
        log("Try to connect to " + host + " : " + port);
        try
        {
            // 生成一个socket channel, 并连接到 Server
            InetSocketAddress addr = new InetSocketAddress(host, port);
            clientChannel = SocketChannel.open(addr);
            log("connection has been established, local port is " + clientChannel.socket().getLocalPort());
            clientChannel.configureBlocking(false); // 设置为非阻塞方式
            
            Selector selector = Selector.open(); // 生成一个信号监视器
            clientChannel.register(selector, SelectionKey.OP_READ);
            long lastTime = System.currentTimeMillis();
            
            while (true)
            {
                if (selector.select(1000) != 0)
                {
                    log("event occur");
                    processIOEvent(selector);
                }
                else
                {
                    log("time out");
                }
                long currTime = System.currentTimeMillis();
                if (currTime - lastTime > 1000) // 每秒钟判断一次，（防止产生的消息太多）
                {
                    lastTime = currTime;
                    int randomInt = new Random().nextInt(8);
                    if (randomInt < CLIENT_MSGS.length)
                    {
                        String msg = CLIENT_MSGS[randomInt];
                        log("Send to server [" + msg + "]");
                        
                        // 把回射消息放入writeBuffer中
                        writeBuffer.clear();
                        writeBuffer.put(msg.getBytes());
                        writeBuffer.flip();
                        
                        // 发送消息
                        while (writeBuffer.hasRemaining())
                        {
                            clientChannel.write(writeBuffer);
                        }
                        writeBuffer.clear();
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 处理IO事件
     * 
     * @param selector
     * @throws IOException
     */
    private void processIOEvent(Selector selector)
        throws IOException
    {
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        while (iterator.hasNext())
        {
            SelectionKey key = iterator.next();
            if (key.isReadable())
            {
                log("Receive date from server");
                receiveFromServer();
            }
            iterator.remove();
        }
    }
    
    /**
     * 读取server端发回的数据，并显示
     * 
     * @throws IOException
     */
    public void receiveFromServer()
        throws IOException
    {
        readBuffer.clear();
        int count = clientChannel.read(readBuffer);
        readBuffer.flip();
        
        byte[] temp = new byte[readBuffer.limit()];
        readBuffer.get(temp);
        log("reply is " + count + " bytes long, and content is: " + new String(temp));
        readBuffer.clear();
    }
    
    /**
     * 记录日志
     * @param logInfo 日志信息
     */
    @SuppressWarnings("deprecation")
    private void log(String logInfo)
    {
        Date date = new Date();
        System.out.println(date.toLocaleString() + ", " + logInfo);
    }
    
    public static void main(String args[])
    {
        String host = "localhost";
        int port = 3456;
        
        if (args.length == 1)
        {
            host = args[0];
        }
        else if (args.length > 1)
        {
            host = args[0];
            port = Integer.parseInt(args[1]);
        }
        
        new AsyncClient2(host, port);
    }
}
