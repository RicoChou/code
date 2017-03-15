##### 其他1. junit 用法，before, beforeClass, after, afterClass 的执行顺序

> **@Before**：初始化方法   对于每一个测试方法都要执行一次（注意与BeforeClass区别，后者是对于所有方法执行一次）

> **@After**：释放资源  对于每一个测试方法都要执行一次（注意与AfterClass区别，后者是对于所有方法执行一次）

>**@BeforeClass**：针对所有测试，只执行一次，且必须为static void

>**@AfterClass**：针对所有测试，只执行一次，且必须为static void

> 一个JUnit4的单元测试用例执行顺序为：
@BeforeClass -> @Before -> @Test -> @After -> @AfterClass;

##### 多线程2. [分布式锁][DST_LOCK]
> [zookeeper][ZOOKEEPER] 实现：基于 zookeeper 瞬时有序节点实现的分布式锁，大致思想即为：每个客户端对某个功能加锁时，在 zookeeper 上的与该功能对应的指定节点的目录下，生成一个唯一的瞬时有序节点。判断是否获取锁的方式很简单，只需要判断有序节点中序号最小的一个。当释放锁的时候，只需将这个瞬时节点删除即可。同时，其可以避免服务宕机导致的锁无法释放，而产生的死锁问题。**优点：**锁安全性高，zk 可持久化。**缺点：**性能开销比较高。因为其需要动态产生、销毁瞬时节点来实现锁功能。

> **memcached** 实现：memcached 带有 add 函数，利用 add 函数的特性即可实现分布式锁。add 和 set 的区别在于：如果多线程并发 set ，则每个 set 都会成功，但最后存储的值以最后的 set 的线程为准。而 add 的话则相反，add 会添加第一个到达的值，并返回 true，后续的添加则都会返回 false。利用该点即可很轻松地实现分布式锁。**优点：**并发高效。 **缺点：** （1）memcached 采用列入 LRU 置换策略，所以如果内存不够，可能导致缓存中的锁信息丢失。（2）memcached 无法持久化，一旦重启，将导致信息丢失。

> **redis**实现：redis 分布式锁即可以结合 zk 分布式锁锁高度安全和 memcached 并发场景下效率很好的优点，可以利用 jedis 客户端实现。参考 [Redis 实现分布式锁][REDIS_DST_LOCK]

##### 其他3. [Nginx][NGINX_TAOBAO] 的请求转发算法，如何配置根据权重转发
[NGINX_TAOBAO]: http://tengine.taobao.org/book/
- **round-robin：**意思是循环轮询，默认算法，Nginx会把第1个请求给10.10.10.1，把第2个请求给10.10.10.2，第3个请求给10.10.10.1，以此类推。
- **least-connected：**最少连接，即每次都找连接数最少的服务器来转发请求。当 Nginx 接收到一个请求时，A正在处理的请求数是10，B正在处理的请求数是20，则 Nginx 会把当前请求交给A来处理。要启用最少连接负载算法只需要在定义服务器组时加上 least_conn ：
```C
upstream app1 {
    least_conn;
    server 10.10.10.1;
    server 10.10.10.2;
}
```
- **ip-hash：**算法会根据请求的客户端 IP 地址来决定当前请求应该交给谁。使用 ip-hash 算法时 Nginx 会确保来自同一客户端的请求都分发到同一服务器。要使用 ip-hash 算法时只需要在定义服务器组时加上 ip-hash 指令：
```C
upstream app1 {
    ip_hash;
    server 10.10.10.1;
    server 10.10.10.2;
}
```
- **weighted：**算法也就是权重算法，会根据每个服务的权重来分发请求，权重大的请求相对会多分发一点，权重小的会少分发一点。这通常应用于多个服务器的性能不一致时。需要使用权重算法时只需要在定义服务器组时在服务器后面指定参数 weight：
```C
upstream app1 {
    server 10.10.10.1 weight=3;
    server 10.10.10.2;
}
```

##### java4. 用hashmap实现Redis有什么问题（死锁，死循环，可用ConcurrentHashmap）
[容量和多线程并发问题][HASHMAP_REDIS]
[HASHMAP_REDIS]: http://blog.csdn.net/mydreamongo/article/details/8917843
另：[Java 和 Redis 中 Hashmap 异同][HASHMAP_DIFF]
[HASHMAP_DIFF]: http://www.cnblogs.com/ironPhoenix/p/6048477.html
##### 多线程5. 线程的状态，以及线程的阻塞的方式
[线程的五种状态以及改变状态的三种方法](http://www.cnblogs.com/garfieldcgf/p/5518929.html)
![PIC_THREAD_STATES][PIC_THREAD_STATES]
[PIC_THREAD_STATES]: http://www.blogjava.net/images/blogjava_net/santicom/360%E6%88%AA%E5%9B%BE20110901211600850.jpg
1. 新建状态（New）：
当用 new 操作符创建一个线程时， 例如 new Thread(r)，线程还没有开始运行，此时线程处在新建状态。 当一个线程处于新生状态时，程序还没有开始运行线程中的代码

2. 就绪状态（Runnable）：
一个新创建的线程并不自动开始运行，要执行线程，必须调用线程的 start() 方法。当线程对象调用 start() 方法即启动了线程，start() 方法创建线程运行的系统资源，并调度线程运行 run() 方法。**当 start() 方法返回后**，线程就处于就绪状态。处于就绪状态的线程并不一定立即运行 run() 方法，线程还必须同其他线程竞争 CPU 时间，只有获得 CPU 时间才可以运行线程。因为在单 CPU 的计算机系统中，不可能同时运行多个线程，一个时刻仅有一个线程处于运行状态。因此此时可能有多个线程处于就绪状态。对多个处于就绪状态的线程是由 Java 运行时系统的线程调度程序（thread scheduler）来调度的。

3. 运行状态（Running）：
当线程获得 CPU 时间后，它才进入运行状态，真正开始执行 run() 方法.

4. 阻塞状态（Blocked）：
线程运行过程中，可能由于各种原因进入阻塞状态：

    （1）线程通过调用sleep方法进入睡眠状态；
    （2）线程调用一个在I/O上被阻塞的操作，即该操作在输入输出操作完成之前不会返回到它的调用者；
    （3）线程试图得到一个锁，而该锁正被其他线程持有；
    （4）线程在等待某个触发条件；
    等等

    所谓阻塞状态是正在运行的线程没有运行结束，暂时让出 CPU ，这时其他处于就绪状态的线程就可以获得 CPU 时间，进入运行状态。

5. 死亡状态（Dead）：
有两个原因会导致线程死亡：
（1） run() 方法正常退出而自然死亡；
（2） 一个未捕获的异常终止了 run() 方法而使线程猝死。
为了确定线程在当前是否存活着（就是要么是可运行的，要么是被阻塞了），需要使用 isAlive() 方法。如果是可运行或被阻塞，这个方法返回true；如果线程仍旧是 new 状态且不是可运行的， 或者线程死亡了，则返回 false。

6. sleep和wait的区别
7. hashmap的底层实现
8. 一万个人抢100个红包，如何实现（不用队列），如何保证2个人不能抢到同一个红包，可用分布式锁
9. java内存模型，垃圾回收机制，不可达算法
10. 两个Integer的引用对象传给一个swap方法在方法内部交换引用，返回后，两个引用的值是否会发现变化



11. aop的底层实现，动态代理是如何动态，假如有100个对象，如何动态的为这100个对象代理
12. 是否用过maven install。 maven test。Git（make install是安装本地jar包）
13. tomcat的各种配置，如何配置docBase
14. spring的bean配置的几种方式
15. web.xml的配置
16. spring的监听器。
17. zookeeper的实现机制，有缓存，如何存储注册服务的
18. IO会阻塞吗？readLine是不是阻塞的
19. 用过spring的线程池还是java的线程池？
20. 字符串的格式化方法 （20，21这两个问题问的太低级了）



21. 时间的格式化方法
22. 定时器用什么做的
23. 线程如何退出结束
24. java有哪些锁？乐观锁 悲观锁 synchronized 可重入锁 读写锁,用过reentrantlock吗？reentrantlock与synmchronized的区别
25. ThreadLocal的使用场景
26. java的内存模型，垃圾回收机制
27. 为什么线程执行要调用start而不是直接run（直接run，跟普通方法没什么区别，先调start，run才会作为一个线程方法运行）
28. qmq消息的实现机制(qmq是去哪儿网自己封装的消息队列)
29. 遍历hashmap的三种方式

30. jvm的一些命令



31. memcache和redis的区别

32. MySQL的行级锁加在哪个位置
33. ConcurrentHashmap的锁是如何加的？是不是分段越多越好
34. myisam和innodb的区别（innodb是行级锁，myisam是表级锁）
35. mysql其他的性能优化方式

36. Linux系统日志在哪里看

37. 如何查看网络进程

38. 统计一个整数的二进制表示中bit为1的个数

39. jvm内存模型，java内存模型

40. 如何把java内存的数据全部dump出来

41. 如何手动触发全量回收垃圾，如何立即触发垃圾回收

42. hashmap如果只有一个写其他全读会出什么问题

43. git rebase

44. MongoDB和Hbase的区别

45. 如何解决并发问题
46. volatile的用途
47. java线程池（好像之前我的理解有问题）
48. mysql的binlog
49. 代理模式
50. mysql是如何实现事务的

51. 读写分离何时强制要读主库，读哪个从库是通过什么方式决定的，从库的同步mysql用的什么方式
52. mysql的存储引擎
53. mysql的默认隔离级别，其他隔离级别
54. 将一个链表反转（用三个指针，但是每次只发转一个）
55. spring Aop的实现原理，具体说说
56. 何时会内存泄漏，内存泄漏会抛哪些异常
57. 是否用过Autowire注解
58. spring的注入bean的方式
59. sql语句各种条件的执行顺序，如select， where， order by， group by
60. select  xx from xx where xx and xx order by xx limit xx； 如何优化这个（看explain）

61. 四则元算写代码

62. 统计100G的ip文件中出现ip次数最多的100个ip
63. zookeeper的事物，结点，服务提供方挂了如何告知消费方
64. 5台服务器如何选出leader(选举算法)

65. 适配器和代理模式的区别
66. 读写锁
67. static加锁
68. 事务隔离级别
69. 门面模式，类图(外观模式)
70. mybatis如何映射表结构

71. 二叉树遍历
72. 主从复制
73. mysql引擎区别
74. 静态内部类加载到了哪个区？方法区

75. class文件编译后加载到了哪

76. web的http请求如何整体响应时间变长导致处理的请求数变少，该如何处理？用队列，当处理不了那么多http请求时将请求放到队列
中慢慢处理，web如何实现队列

77. 线程安全的单例模式

78. 快速排序性能考虑

79. volatile关键字用法

80. 求表的size，或做数据统计可用什么存储引擎



81. 读多写少可用什么引擎

82. 假如要统计多个表应该用什么引擎

83. concurrenhashmap求size是如何加锁的，如果刚求完一段后这段发生了变化该如何处理

84. 1000个苹果放10个篮子，怎么放，能让我拿到所有可能的个数

85. 可重入的读写锁，可重入是如何实现的？

86. 是否用过NIO

87. java的concurrent包用过没

88. sting s=new string("abc")分别在堆栈上新建了哪些对象

89. java虚拟机的区域分配，各区分别存什么

90. 分布式事务（JTA）

91. threadlocal使用时注意的问题（ThreadLocal和Synchonized都用于解决多线程并发访问。但是ThreadLocal与synchronized有本质的区别。synchronized是利用锁的机制，使变量或代码块在某一时该只能被一个线程访问。而ThreadLocal为每一个线程都提供了变量的副本，使得每个线程在某一时间访问到的并不是同一个对象，这样就隔离了多个线程对数据的数据共享。而Synchronized却正好相反，它用于在多个线程间通信时能够获得数据共享）

92. java有哪些容器(集合，tomcat也是一种容器)

93. 二分查找算法
94. myisam的优点，和innodb的区别
95. redis能存哪些类型
96. http协议格式，get和post的区别
97. 可重入锁中对应的wait和notify
98. redis能把内存空间交换进磁盘中吗(这个应该是可以的，但是那个面试官非跟我说不可以)
99. java线程池中基于缓存和基于定长的两种线程池，当请求太多时分别是如何处理的？定长的事用的队列，如果队列也满了呢？交换进磁盘？基于缓存的线程池解决方法呢？
100. synchronized加在方法上用的什么锁

101. 可重入锁中的lock和trylock的区别
102. innodb对一行数据的读会枷锁吗？不枷锁，读实际读的是副本
103. redis做缓存是分布式存的？不同的服务器上存的数据是否重复？guava cache呢？是否重复？不同的机器存的数据不同
104. 用awk统计一个ip文件中top10
105. 对表做统计时可直接看schema info信息，即查看表的系统信息
106. mysql目前用的版本
107. 公司经验丰富的人给了什么帮助？(一般boss面会问这些)
108. 自己相对于一样的应届生有什么优势
109. 自己的好的总结习惯给自己今后的工作带了什么帮助，举例为证

110. 原子类，线程安全的对象，异常的处理方式

111. 4亿个int数，如何找出重复的数（用hash方法，建一个2的32次方个bit的hash数组，每取一个int数，可hash下2的32次方找到它在hash数组中的位置，然后将bit置1表示已存在）
112. 4亿个url，找出其中重复的（考虑内存不够，通过hash算法，将url分配到1000个文件中，不同的文件间肯定就不会重复了，再分别找出重复的）
有1万个数组，每个数组有1000个整数，每个数组都是降序的，从中找出最大的N个数，N<1000

113. LinkedHashmap的底层实现
114. 类序列化时类的版本号的用途，如果没有指定一个版本号，系统是怎么处理的？如果加了字段会怎么样？
115. Override和Overload的区别，分别用在什么场景
116. java的反射是如何实现的


[DST_LOCK]: http://surlymo.iteye.com/blog/2082684 "分布式锁的三种实现方式"
[ZOOKEEPER]: http://cailin.iteye.com/blog/2014486/ "zookeeper 原理"
[REDIS_DST_LOCK]: http://blog.csdn.net/java2000_wl/article/details/8740911
