1：实现基于Socket的服务端与客户端
2：使用线程池支持并发情况下的服务
缺点：
当前Socket通信底层为BIO同步阻塞模型
    -> accept方法：阻塞接收客户端的连接
    -> connect方法：和服务端建立连接，连接的过程中connect会阻塞 (client 中的 new Socket(ip, port))

以至于再怎么使用线程池优化，其效率还是不高
于是 java 1.4提出了NIO的模型(同步非阻塞的IO模型)
