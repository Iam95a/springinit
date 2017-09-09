package com.chen.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile  boolean stop;

    public MultiplexerTimeServer(int port) {
        try {
            selector=Selector.open();
            serverSocketChannel=ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("time server is start in port"+port);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys=selector.selectedKeys();
                Iterator<SelectionKey> it=selectionKeys.iterator();
                SelectionKey key=null;
                while(it.hasNext()){
                    key=it.next();
                    it.remove();
                    handleInput(key);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(selector!=null){
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private void handleInput(SelectionKey key) throws  Exception{
        if(key.isValid()){
            if(key.isAcceptable()){
                ServerSocketChannel ssc=(ServerSocketChannel)key.channel();
                SocketChannel sc=ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector,SelectionKey.OP_READ);

            }
            if(key.isReadable()){
                SocketChannel sc=(SocketChannel) key.channel();
                ByteBuffer by=ByteBuffer.allocate(1024);
                int readBytes=sc.read(by);
                if(readBytes>0){
                    by.flip();
                    byte[] bytes=new byte[by.remaining()];
                    by.get(bytes);
                    String body=new String(bytes,"UTF-8");
                    System.out.println("the time server receive order: "+body);
                    String currentTime="query time order".equalsIgnoreCase(body)?new Date().toString():
                            "bad order";
                    doWrite(sc,currentTime);
                }
            }
        }
    }

    private void doWrite(SocketChannel sc,String response)
    throws Exception{
        if(response!=null&&response.trim().length()>0){
            byte[] bytes=response.getBytes();
            ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            sc.write(writeBuffer);
        }
    }
}
