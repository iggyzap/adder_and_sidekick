package com.iggy.zap.sidekick.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.example.proxy.HexDumpProxyInitializer;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple wrapper around netty hexdump proxy application to establish a client - facing service.
 */
public class RouterApplication {

    private final String appHost;
    private final int appPort;
    private final int routerPort;

    private EventLoopGroup acceptor = new NioEventLoopGroup();
    private EventLoopGroup worker = new NioEventLoopGroup();
    private Channel serverChannel;

    private Logger logger = LoggerFactory.getLogger(getClass());


    public RouterApplication(String appHost, int appPort, int routerPort) {
        this.appHost = appHost;
        this.appPort = appPort;
        this.routerPort = routerPort;
    }

    /**
     * Does initialisation dance around allocating resources
     *
     * @throws InterruptedException if aquiring channel failed
     * @return instance of this application for call chaining
     */
    public RouterApplication init() throws InterruptedException {
        ServerBootstrap b = new ServerBootstrap();
        ChannelFuture c = b.group(acceptor, worker)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new HexDumpProxyInitializer(appHost, appPort))
                .childOption(ChannelOption.AUTO_READ, false)
                .bind(routerPort).sync();
        serverChannel = c.channel();
        logger.info("Established http application from {} to {}:{}", serverChannel.localAddress(), appHost, appPort);
        return this;
    }

    /**
     * Runs router. Right now it only forwards requests to singular host / backend. This method blocks until
     * server channel is closed
     *
     * @throws InterruptedException
     */
    public void run() throws InterruptedException {
        // Configure the client.
        try {
            serverChannel.closeFuture().sync();
        } finally {
            // Shut down the event loop to terminate all threads.
            acceptor.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

    /**
     * Asks to terminate services
     */
    public void shutdown() {
        if (serverChannel != null) {
            logger.info("Shutting down server channel...");
            serverChannel.close();
        }
    }

    public static void main(String[] args) throws Exception {
        String HOST = System.getProperty("host", "127.0.0.1");
        int APP_PORT = Integer.parseInt(System.getProperty("port", "8080"));
        int PORT_LISTEN = Integer.parseInt(System.getProperty("port", "18080"));

        new RouterApplication(HOST, APP_PORT, PORT_LISTEN).init().run();
    }
}
