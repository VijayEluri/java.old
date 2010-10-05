package javademo.net;

/**
 * @author Fan Hongtao
 */
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

public class AsyncServer implements Runnable
{
    private final static int MAX_LENGTH = 64 * 1024;
    
    private ByteBuffer readBuffer = ByteBuffer.allocate(MAX_LENGTH);
    
    private ByteBuffer writeBuffer = ByteBuffer.allocate(MAX_LENGTH);
    
    /** 服务端口 */
    private int port = 0;
    
    public AsyncServer(int port)
    {
        this.port = port;
        new Thread(this).start();
    }
    
    public void run()
    {
        log("Try to start server with port " + port);
        try
        {
            // 生成一个侦听端
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false); // 将侦听端设为异步方式
            Selector selector = Selector.open(); // 生成一个信号监视器
            // 侦听端绑定到一个端口
            serverChannel.socket().bind(new InetSocketAddress(port));
            // 设置侦听端所选的异步信号OP_ACCEPT, 这样才能够新的连接
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            log("echo server has been set up ......");
            
            while (true)
            {
                if (selector.select(5000) != 0)
                {
                    log("event occur");
                    processIOEvent(selector);
                }
                else
                {
                    // 没有指定的I/O事件发生
                    log("time out.....");
                }
                
                // do something other than sockets processing
                // for example, local msg-queue or DB
            }
        }
        catch (Exception e)
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
            if (key.isAcceptable())
            {
                // 侦听端信号触发
                ServerSocketChannel server = (ServerSocketChannel) key.channel();
                
                // 接受一个新的连接
                SocketChannel client = server.accept();
                client.configureBlocking(false);
                
                // 设置该socket的异步信号OP_READ
                // 当socket中有数据时，会触发 processIOEvent
                // 通过 key.isReadable() 判断，最终调用 receiveDataFromClient()
                client.register(selector, SelectionKey.OP_READ);
                
                log("Accept a client , port is " + client.socket().getPort());
            }
            if (key.isReadable())
            {
                log("Receive date from client");
                // 某socket可读信号
                receiveDataFromClient(key);
            }
            iterator.remove();
        }
    }
    
    /**
     * 处理来自客户端的消息
     * 
     * @param key 代表对应的客户端
     * 
     */
    public void receiveDataFromClient(SelectionKey key)
    {
        // 由key获取指定socket channel的引用
        SocketChannel clientChannel = (SocketChannel) key.channel();
        readBuffer.clear();
        
        try
        {
            // 读取数据到readBuffer
            while (clientChannel.read(readBuffer) > 0)
            {
                ; // do nothing
            }
            // 确保 readBuffer 可读
            readBuffer.flip();
            
            // 将 readBuffer 内容拷入 writeBuffer
            writeBuffer.clear();
            writeBuffer.put(readBuffer);
            writeBuffer.flip();
            
            // 将数据返回给客户端
            while (writeBuffer.hasRemaining())
            {
                clientChannel.write(writeBuffer);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            
            log("close a client , port is " + clientChannel.socket().getPort());
            key.cancel();
            try
            {
                key.channel().close();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
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
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        int port = 3456;
        if (args.length == 1)
        {
            port = Integer.parseInt(args[0]);
        }
        new AsyncServer(port);
    }
}
