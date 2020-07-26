package ru.otus.spring.config;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {
  @Value("${socket.port}")
  private Integer port;

  @Bean
  public SocketIOServer socketIOServer() {
    SocketConfig socketConfig = new SocketConfig();
    socketConfig.setTcpNoDelay(true);
    socketConfig.setSoLinger(0);
    com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
    config.setSocketConfig(socketConfig);
    config.setPort(port);
    return new SocketIOServer(config);
  }

}
